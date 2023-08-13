package indi.mofan.component.manager.support;

import com.fasterxml.jackson.databind.JsonNode;
import indi.mofan.component.handler.SubComponentLocator;
import indi.mofan.component.manager.SubComponentLocatorManager;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * @author mofan
 * @date 2023/8/13 17:09
 */
public class DefaultSubComponentLocatorManager implements SubComponentLocatorManager {
    /**
     * <p>
     * 子组件定位器
     *      <ul>
     *          <li>key: 子组件定位器 key，可以子组件处理器的 Class，也可以自定义</li>
     *          <li>value: 定位函数，传入当前组件对象，返回对应的子组件 JSON 信息</li>
     *      </ul>
     * </p>
     */
    private final Map<Class<? extends SubComponentLocator>, Function<JsonNode, List<Optional<JsonNode>>>> subComponentLocators = new ConcurrentHashMap<>();

    @Override
    public void addSubLocator(Class<? extends SubComponentLocator> subComponentHandlerClazz, Function<JsonNode, List<Optional<JsonNode>>> locateFunction) {
        this.subComponentLocators.put(subComponentHandlerClazz, locateFunction);
    }

    @Override
    public Function<JsonNode, List<Optional<JsonNode>>> getSubLocator(Class<? extends SubComponentLocator> subComponentHandlerClazz) {
        return this.subComponentLocators.get(subComponentHandlerClazz);
    }

    @Override
    public Set<Class<? extends SubComponentLocator>> getSubLocatorsKeySet() {
        return this.subComponentLocators.keySet();
    }
}
