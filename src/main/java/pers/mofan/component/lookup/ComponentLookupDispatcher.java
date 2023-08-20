package pers.mofan.component.lookup;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import pers.mofan.component.ComponentIdentity;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author mofan
 * @date 2023/8/13 20:16
 */
@Component
public class ComponentLookupDispatcher implements ApplicationContextAware {

    private Map<Class<?>, ComponentLookup> lookupMap;

    public Map<Class<?>, List<JsonNode>> lookup(ObjectNode objectNode, Set<Class<?>> componentIdentities) {
        if (CollectionUtils.isEmpty(componentIdentities)) {
            return Collections.emptyMap();
        }
        return componentIdentities.stream().collect(Collectors.toMap(Function.identity(),
                // 不一定转入的组件 id 都有 Lookup 的实现
                i -> Optional.ofNullable(lookupMap.get(i)).map(j -> j.lookup(objectNode)).orElse(Collections.emptyList())));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        lookupMap = applicationContext.getBeansOfType(ComponentLookup.class).values().stream()
                .collect(Collectors.toMap(ComponentIdentity::getComponentIdentity, Function.identity()));
    }
}
