package pers.mofan.component.util;

import com.fasterxml.jackson.databind.JsonNode;
import pers.mofan.util.JacksonUtils;

/**
 * @author mofan
 * @date 2023/8/14 22:52
 */
public final class HandlerUtils {
    private HandlerUtils() {
    }

    public static String getFieldAValue(JsonNode jsonNode) {
        return JacksonUtils.getStringValue(jsonNode, "fieldA").orElse(null);
    }
}
