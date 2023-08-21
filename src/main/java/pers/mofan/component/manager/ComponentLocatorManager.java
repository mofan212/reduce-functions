package pers.mofan.component.manager;

import com.fasterxml.jackson.databind.JsonNode;
import pers.mofan.component.handler.ComponentLocator;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

/**
 * @author mofan
 * @date 2023/8/13 17:16
 */
public interface ComponentLocatorManager {
    /**
     * 添加子组件定位器
     */
    void addSubLocator(Class<? extends ComponentLocator> subComponentHandlerClazz, Function<JsonNode, List<Optional<JsonNode>>> locateFunction);

    /**
     * 获取子组件定位器
     */
    Function<JsonNode, List<Optional<JsonNode>>> getSubLocator(Class<? extends ComponentLocator> subComponentHandlerClazz);

    /**
     * 获取子组件定位器的 key 集合
     */
    Set<Class<? extends ComponentLocator>> getSubLocatorsKeySet();
}
