package pers.mofan.component.manager;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import pers.mofan.component.constant.NodeConstants;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

/**
 * @author mofan
 * @date 2023/8/13 17:44
 */
public interface ComponentLocatorManager {

    /**
     * 添加组件定位器
     */
    void addLocator(String locatorKey, String componentKey, String... componentKeys);

    /**
     * 获取组件定位器
     */
    Function<JsonNode, List<Optional<JsonNode>>> getLocator(ObjectNode node);

    /**
     * 获取组件定位器
     */
    Function<JsonNode, List<Optional<JsonNode>>> getLocator(String locatorKey);

    /**
     * 获取组件定位器的 key 集合
     */
    Set<String> getLocatorsKeySet();

    /**
     * 根据 JSON 节点对象构建定位器的 key
     *
     * @param node JSON 节点信息
     * @return 定位器的 key
     */
    static String buildLocatorKey(ObjectNode node) {
        // 简单以节点的 nodeType 作为定位器的 key
        return node.get(NodeConstants.NODE_TYPE).asText();
    }
}
