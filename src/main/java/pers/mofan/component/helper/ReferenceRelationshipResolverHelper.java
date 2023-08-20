package pers.mofan.component.helper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Maps;
import pers.mofan.component.lookup.ComponentLookupDispatcher;
import pers.mofan.util.CastUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author mofan
 * @date 2023/8/13 20:52
 */
@Component
public class ReferenceRelationshipResolverHelper {
    @Autowired
    private ComponentLookupDispatcher dispatcher;

    public static final Set<Class<?>> REFERENCED_COMPONENT_IDENTITIES = Collections.emptySet();

    public Map<Class<?>, List<JsonNode>> findReferencedComponents(ArrayNode arrayNode) {
        Map<Class<?>, List<JsonNode>> componentInfo = Maps.newHashMapWithExpectedSize(3);
        for (JsonNode jsonNode : arrayNode) {
            if (!(jsonNode instanceof ObjectNode)) {
                continue;
            }
            ObjectNode objectNode = CastUtils.cast(jsonNode);
            for (Map.Entry<Class<?>, List<JsonNode>> entry : dispatcher.lookup(objectNode, ReferenceRelationshipResolverHelper.REFERENCED_COMPONENT_IDENTITIES).entrySet()) {
                List<JsonNode> jsonNodes = componentInfo.computeIfAbsent(entry.getKey(), (key) -> new ArrayList<>());
                jsonNodes.addAll(entry.getValue());
            }
        }
        return componentInfo;
    }
}
