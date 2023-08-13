package indi.mofan.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author mofan
 * @date 2023/8/13 17:26
 */
public final class JacksonUtils {
    private JacksonUtils() {
    }

    public static final JsonMapper MAPPER = JsonMapper.builder()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .build();

    public static boolean hasTargetValue(JsonNode jsonNode, String key, String value) {
        if (jsonNode == null || StringUtils.isEmpty(key)) {
            return false;
        }
        return getStringValue(jsonNode, key).map(i -> i.equals(value)).orElse(Boolean.FALSE);
    }

    public static void setStringValue(JsonNode jsonNode, String key, String value) {
        Optional.ofNullable(jsonNode)
                .filter(JsonNode::isObject)
                .map(i -> ((ObjectNode) i))
                .filter(i -> i.has(key))
                .ifPresent(i -> i.put(key, value));
    }

    public static Optional<String> getStringValue(JsonNode jsonNode, String key) {
        return getValue(jsonNode, key).filter(JsonNode::isTextual).map(JsonNode::asText);
    }

    public static Optional<JsonNode> getValue(JsonNode jsonNode, String key) {
        return Optional.ofNullable(jsonNode).map(i -> i.get(key));
    }

    public static Optional<ObjectNode> getObjectValue(JsonNode jsonNode, String key) {
        return getValue(jsonNode, key).filter(i -> i instanceof ObjectNode).map(i -> ((ObjectNode) i));
    }

    public static List<Optional<JsonNode>> getJsonNodeList(JsonNode jsonNode, String key, String... keys) {
        return Stream.concat(Stream.of(key), Stream.of(keys))
                .map(i -> getValue(jsonNode, i))
                .collect(Collectors.toList());
    }

    public static Optional<ArrayNode> getArrayValue(JsonNode jsonNode, String key) {
        return getValue(jsonNode, key).filter(JsonNode::isArray).map(i -> ((ArrayNode) i));
    }
}
