package pers.mofan.component.handler;

import com.fasterxml.jackson.databind.JsonNode;
import pers.mofan.component.ComponentIdentity;
import pers.mofan.component.manager.ComponentLocatorManager;
import org.springframework.beans.factory.SmartInitializingSingleton;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * 组件定位器，组件下可能还会有子组件
 *
 * @author mofan
 * @date 2023/8/13 17:08
 */
public interface ComponentLocator extends ComponentLocatorManager, ComponentIdentity, SmartInitializingSingleton  {

    /**
     * 是否为列表组件
     *
     * @return true 表示列表组件，由多个相同组件组成的列表
     */
    boolean isArrayComponent();

    /**
     * 初始化子组件定位器，用于定位当前组件的子组件
     */
    void initSubComponentLocators();

    @Override
    default void afterSingletonsInstantiated() {
        initSubComponentLocators();
    }

    default void handleSubComponent(Class<? extends ComponentLocator> subComponentHandlerClazz,
                                    JsonNode component, Consumer<JsonNode> subComponentElementConsumer) {
        getSubLocator(subComponentHandlerClazz).apply(component).stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(subComponentElementConsumer);
    }
}
