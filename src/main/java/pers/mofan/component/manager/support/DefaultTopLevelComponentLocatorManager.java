package pers.mofan.component.manager.support;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import pers.mofan.component.manager.TopLevelComponentLocatorManager;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author mofan
 * @date 2023/8/13 17:39
 */
public class DefaultTopLevelComponentLocatorManager implements TopLevelComponentLocatorManager {
    /**
     * <p>
     * 组件定位器
     *     <ul>
     *         <li>key: 节点唯一标识，采用节点的 type 和 activityType 的组装</li>
     *         <li>value: 定位函数，传入整个微流程节点对象，返回组件 JSON 节点信息</li>
     *     </ul>
     * </p>
     */
    private final Map<String, Function<JsonNode, List<Optional<JsonNode>>>> componentLocators = new ConcurrentHashMap<>();

    @Override
    public Function<JsonNode, List<Optional<JsonNode>>> getLocator(ObjectNode node) {
        return this.componentLocators.get(TopLevelComponentLocatorManager.buildLocatorKey(node));
    }

    @Override
    public Function<JsonNode, List<Optional<JsonNode>>> getLocator(String locatorKey) {
        return this.componentLocators.get(locatorKey);
    }

    @Override
    public Set<String> getLocatorsKeySet() {
        return this.componentLocators.keySet();
    }

    private void addLocator(String key, Function<JsonNode, List<Optional<JsonNode>>> locateFunction) {
        this.componentLocators.put(key, locateFunction);
    }

    @Override
    public void addLocator(String locatorKey, String componentKey, String... componentKeys) {
        addLocator(locatorKey, objectNode -> getComponentObjectNode(objectNode, componentKey, componentKeys));
    }

    private List<Optional<JsonNode>> getComponentObjectNode(JsonNode node, String componentKey, String... componentKeys) {
        return Stream.concat(Stream.of(componentKey), Stream.of(componentKeys)).distinct()
                .map(i -> Optional.ofNullable(node.get(i)))
                .collect(Collectors.toList());
    }
}
