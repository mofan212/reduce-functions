package indi.mofan.component.util;

import indi.mofan.component.bo.MyComponent;
import org.apache.commons.lang3.StringUtils;

/**
 * @author mofan
 * @date 2023/8/14 22:52
 */
public final class HandlerUtils {
    private HandlerUtils() {
    }

    /**
     * 根据某一组件对象，获取其顶级定位器的 key
     */
    public static String getComponentLocatorKey(MyComponent component) {
        return StringUtils.upperCase(component.getNodeType());
    }
}
