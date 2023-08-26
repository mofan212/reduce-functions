package pers.mofan.component.handler;

import org.springframework.beans.factory.SmartInitializingSingleton;
import pers.mofan.component.store.TopLevelComponentLocatorStore;

/**
 * 顶级组件定位器。所谓顶级组件，就是在节点中，能够直接通过一个 key 就定位到目标组件。
 *
 * @author mofan
 * @date 2023/8/13 17:21
 */
public interface TopLevelComponentLocator extends TopLevelComponentLocatorStore, ComponentLocator, SmartInitializingSingleton {

    /**
     * 初始化组件定位器
     */
    void initComponentLocators();

    @Override
    default void afterSingletonsInstantiated() {
        initComponentLocators();
        ComponentLocator.super.afterSingletonsInstantiated();
    }

    default void initSubComponentLocators() {
        // 默认实现下
    }
}
