package pers.mofan.component.helper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.mofan.component.bo.MyComponent;
import pers.mofan.component.lookup.ComponentLookupDispatcher;
import pers.mofan.util.CastUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @author mofan
 * @date 2023/8/13 20:52
 */
@Component
public class ReferenceRelationshipResolverHelper {
    @Autowired
    private ComponentLookupDispatcher dispatcher;

    public Map<Class<? extends MyComponent>, List<JsonNode>> findReferencedComponents(ArrayNode arrayNode,
                                                                                      Set<Class<? extends MyComponent>> componentClazzSet,
                                                                                      boolean isArrayComponent) {
        Map<Class<? extends MyComponent>, List<JsonNode>> componentInfo = Maps.newHashMapWithExpectedSize(3);
        for (JsonNode jsonNode : arrayNode) {
            if (!(jsonNode instanceof ObjectNode)) {
                continue;
            }
            ObjectNode objectNode = CastUtils.cast(jsonNode);
            for (Map.Entry<Class<? extends MyComponent>, List<JsonNode>> entry : dispatcher.lookup(objectNode, componentClazzSet, isArrayComponent).entrySet()) {
                List<JsonNode> jsonNodes = componentInfo.computeIfAbsent(entry.getKey(), (key) -> new ArrayList<>());
                jsonNodes.addAll(entry.getValue());
            }
        }
        return componentInfo;
    }

    private List<JsonNode> findReferencedComponent(ArrayNode arrayNode, Class<? extends MyComponent> componentClazz, boolean isArrayComponent) {
        return Optional.ofNullable(this.findReferencedComponents(arrayNode, Set.of(componentClazz), isArrayComponent))
                .map(i -> i.get(componentClazz))
                .orElse(Collections.emptyList());
    }

    public List<JsonNode> findReferenceSingleComponent(ArrayNode arrayNode, Class<? extends MyComponent> componentClazz) {
        return Optional.ofNullable(this.findReferencedComponents(arrayNode, Set.of(componentClazz), false))
                .map(i -> i.get(componentClazz))
                .orElse(Collections.emptyList());
    }

    public List<JsonNode> findReferenceArrayComponent(ArrayNode arrayNode, Class<? extends MyComponent> componentClazz) {
        return Optional.ofNullable(this.findReferencedComponents(arrayNode, Set.of(componentClazz), true))
                .map(i -> i.get(componentClazz))
                .orElse(Collections.emptyList());
    }
}
