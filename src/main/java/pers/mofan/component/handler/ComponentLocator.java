package pers.mofan.component.handler;

import pers.mofan.component.ComponentIdentity;
import pers.mofan.component.manager.ComponentLocatorManager;
import org.springframework.beans.factory.SmartInitializingSingleton;

/**
 * @author mofan
 * @date 2023/8/13 17:21
 */
public interface ComponentLocator extends ComponentIdentity, ComponentLocatorManager, SubComponentLocator, SmartInitializingSingleton {
    /**
     * 是否为列表组件
     *
     * @return true 表示列表组件，由多个相同组件组成的列表
     */
    boolean isArrayComponent();

    /**
     * 初始化组件定位器
     */
    void initComponentLocators();

    @Override
    default void afterSingletonsInstantiated() {
        initComponentLocators();
        SubComponentLocator.super.afterSingletonsInstantiated();
    }

    default void initSubComponentLocators() {
        // 默认实现下
    }
}
