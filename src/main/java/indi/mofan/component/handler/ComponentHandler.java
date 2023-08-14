package indi.mofan.component.handler;

import indi.mofan.component.bo.MyComponent;

/**
 * 标记接口，整和处理器和定位器
 *
 * @author mofan
 * @date 2023/8/13 17:29
 */
public interface ComponentHandler extends SimpleComponentHandler, ComponentLocator {
    /**
     * 添加组件处理器
     */
    void addLocator(MyComponent component, String componentKey, String... componentKeys);
}
