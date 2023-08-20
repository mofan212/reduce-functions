package pers.mofan.component.handler;

import com.fasterxml.jackson.databind.JsonNode;
import pers.mofan.component.manager.SubComponentLocatorManager;
import org.springframework.beans.factory.SmartInitializingSingleton;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author mofan
 * @date 2023/8/13 17:08
 */
public interface SubComponentLocator extends SubComponentLocatorManager, SmartInitializingSingleton  {
    /**
     * 初始化子组件定位器
     */
    void initSubComponentLocators();

    @Override
    default void afterSingletonsInstantiated() {
        initSubComponentLocators();
    }

    default void handleSubComponent(Class<? extends SubComponentLocator> subComponentHandlerClazz,
                                    JsonNode component, Consumer<JsonNode> subComponentElementConsumer) {
        getSubLocator(subComponentHandlerClazz).apply(component).stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(subComponentElementConsumer);
    }
}
