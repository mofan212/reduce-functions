package pers.mofan.component.lookup;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Maps;
import com.google.common.graph.Graph;
import pers.mofan.component.ComponentIdentity;
import pers.mofan.component.bo.MyComponent;
import pers.mofan.component.handler.TopLevelComponentLocator;
import pers.mofan.component.handler.ComponentLocator;
import pers.mofan.component.util.GraphUtils;
import pers.mofan.util.CastUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author mofan
 * @date 2023/8/13 20:22
 */
@Component
public class ComponentRelationshipCollector implements ApplicationRunner, Ordered {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private List<ComponentLocator> componentLocators;

    private Map<Class<? extends MyComponent>, List<ComponentLocator>> processedComponentMap;

    private Graph<Class<? extends ComponentLocator>> subComponentRefGraph;

    /**
     * @param componentIdentity 组件标识
     * @return 最外层 Map 的 key，是否为列表组件；第二层 Map 的 key，顶级组件定位器的 key
     */
    public Map<Boolean, Map<String, List<Function<JsonNode, List<Optional<JsonNode>>>>>> collect(Class<? extends MyComponent> componentIdentity) {
        Collection<List<Class<? extends ComponentLocator>>> allRefPath = buildTargetComponentRefPath(componentIdentity);
        if (CollectionUtils.isEmpty(allRefPath)) {
            return Collections.emptyMap();
        }
        var result = new HashMap<Boolean, Map<String, List<Function<JsonNode, List<Optional<JsonNode>>>>>>();
        for (List<Class<? extends ComponentLocator>> singlePath : allRefPath) {
            if (CollectionUtils.isEmpty(singlePath)) {
                continue;
            }
            ComponentLocator targetComponent = applicationContext.getBean(singlePath.get(singlePath.size() - 1));
            boolean isArrayComponent = targetComponent.isArrayComponent();
            var locatorMap = result.computeIfAbsent(isArrayComponent, key -> new HashMap<>());
            List<ComponentLocator> handlers = singlePath.stream()
                    .map(i -> applicationContext.getBean(i))
                    .collect(Collectors.toList());
            for (Map.Entry<String, Function<JsonNode, List<Optional<JsonNode>>>> entry : reduceLocatePath(handlers).entrySet()) {
                String locatorKey = entry.getKey();
                var list = locatorMap.computeIfAbsent(locatorKey, key -> new ArrayList<>());
                list.add(entry.getValue());
            }
        }
        return result;
    }

    private Map<String, Function<JsonNode, List<Optional<JsonNode>>>> reduceLocatePath(List<ComponentLocator> handlers) {
        if (!TopLevelComponentLocator.class.isAssignableFrom(handlers.get(0).getClass())) {
            return Collections.emptyMap();
        }
        TopLevelComponentLocator firstHandler = CastUtils.cast(handlers.get(0));
        Map<String, Function<JsonNode, List<Optional<JsonNode>>>> nodeLocatorMap = firstHandler.getLocatorsKeySet().stream()
                .collect(Collectors.toMap(Function.identity(), firstHandler::getLocator));
        List<Function<JsonNode, List<Optional<JsonNode>>>> functions = new ArrayList<>();
        ComponentLocator preHandler = firstHandler;
        for (int i = 1; i < handlers.size(); i++) {
            ComponentLocator handler = handlers.get(i);
            Function<JsonNode, List<Optional<JsonNode>>> subLocator = preHandler.getSubLocator(handler.getClass());
            functions.add(subLocator);
            preHandler = handler;
        }
        Map<String, Function<JsonNode, List<Optional<JsonNode>>>> componentLocateMap = Maps.newHashMapWithExpectedSize(nodeLocatorMap.size());
        for (Map.Entry<String, Function<JsonNode, List<Optional<JsonNode>>>> entry : nodeLocatorMap.entrySet()) {
            Function<JsonNode, List<Optional<JsonNode>>> outerComponentLocator = entry.getValue();
            Stream<Function<JsonNode, List<Optional<JsonNode>>>> locatorFunctions = Stream.concat(Stream.of(outerComponentLocator), functions.stream());
            componentLocateMap.put(entry.getKey(), reduceFunctions(locatorFunctions));
        }
        return componentLocateMap;
    }

    private Collection<List<Class<? extends ComponentLocator>>> buildTargetComponentRefPath(Class<? extends MyComponent> componentIdentity) {
        return this.processedComponentMap.getOrDefault(componentIdentity, Collections.emptyList()).stream()
                .map(ComponentLocator::getClass)
                .flatMap(i -> buildRefPaths(i).stream())
                .collect(Collectors.toSet());
    }

    private Collection<List<Class<? extends ComponentLocator>>> buildRefPaths(Class<? extends ComponentLocator> targetHandlerClazz) {
        List<List<Class<? extends ComponentLocator>>> paths = GraphUtils.getAllPath2TargetNode(this.subComponentRefGraph, targetHandlerClazz);
        Set<List<Class<? extends ComponentLocator>>> refPaths = new HashSet<>();
        for (List<Class<? extends ComponentLocator>> singlePath : paths) {
            boolean isComponentHandler = singlePath.stream().findFirst().map(TopLevelComponentLocator.class::isAssignableFrom).orElse(Boolean.FALSE);
            if (!isComponentHandler) {
                continue;
            }
            for (int i = 0; i < singlePath.size(); i++) {
                Class<? extends ComponentLocator> clazz = singlePath.get(i);
                if (!TopLevelComponentLocator.class.isAssignableFrom(clazz)) {
                    continue;
                }
                TopLevelComponentLocator handler = CastUtils.cast(applicationContext.getBean(clazz));
                if (CollectionUtils.isEmpty(handler.getLocatorsKeySet())) {
                    continue;
                }
                refPaths.add(singlePath.subList(i, singlePath.size()));
            }
        }
        // 有些组件可能没有任何一个组件引用它，只要它是 ComponentLocator 的子类就加进去
        if (TopLevelComponentLocator.class.isAssignableFrom(targetHandlerClazz)) {
            refPaths.add(Collections.singletonList(targetHandlerClazz));
        }
        return refPaths;
    }

    private <T> Function<T, List<Optional<T>>> reduceFunctions(Stream<Function<T, List<Optional<T>>>> locatorFunctions) {
        return locatorFunctions.reduce(
                t -> Collections.singletonList(Optional.ofNullable(t)),
                (fun1, fun2) -> fun1.andThen(list -> list.stream().filter(Optional::isPresent)
                        .map(Optional::get).flatMap(i -> fun2.apply(i).stream()).collect(Collectors.toList()))
        );
    }

    @Override
    public void run(ApplicationArguments args) {
        // 构建子组件引用图
        buildSubComponentRefGraph();
        // 构建处理组件 Map
        buildProcessedComponentMap();
    }

    private void buildSubComponentRefGraph() {
        Map<Class<? extends ComponentLocator>, Collection<Class<? extends ComponentLocator>>> refMap = new HashMap<>();
        for (ComponentLocator handler : componentLocators) {
            Set<Class<? extends ComponentLocator>> subLocatorsKeySet = handler.getSubLocatorsKeySet();
            Class<? extends ComponentLocator> handlerClazz = handler.getClass();
            if (CollectionUtils.isEmpty(subLocatorsKeySet)) {
                // 处理没有引用任何子组件的组件
                refMap.put(handlerClazz, Collections.emptySet());
                continue;
            }
            refMap.put(handlerClazz, subLocatorsKeySet);
        }
        this.subComponentRefGraph = GraphUtils.buildGraph(refMap);
    }

    private void buildProcessedComponentMap() {
        this.processedComponentMap = this.componentLocators.stream().collect(Collectors.groupingBy(ComponentIdentity::getComponentIdentity));
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE - 1;
    }
}
