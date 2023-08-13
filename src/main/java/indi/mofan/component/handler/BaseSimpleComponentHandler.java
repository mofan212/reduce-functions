package indi.mofan.component.handler;

import com.fasterxml.jackson.databind.JsonNode;
import indi.mofan.component.manager.SubComponentLocatorManager;
import indi.mofan.component.manager.support.DefaultSubComponentLocatorManager;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 单个非顶级组件处理器
 *
 * @author mofan
 * @date 2023/8/13 19:44
 */
public abstract class BaseSimpleComponentHandler implements SubComponentLocator, SimpleComponentHandler {

    private final SubComponentLocatorManager subComponentLocatorManager = new DefaultSubComponentLocatorManager();

    @Override
    public void initSubComponentLocators() {
        // 默认实现下，不一定所有组件都有子组件
    }

    @Override
    public final void addSubLocator(Class<? extends SubComponentLocator> subComponentHandlerClazz, Function<JsonNode, List<Optional<JsonNode>>> subLocatorFunction) {
        this.subComponentLocatorManager.addSubLocator(subComponentHandlerClazz, subLocatorFunction);
    }

    @Override
    public final Function<JsonNode, List<Optional<JsonNode>>> getSubLocator(Class<? extends SubComponentLocator> subComponentHandlerClazz) {
        return this.subComponentLocatorManager.getSubLocator(subComponentHandlerClazz);
    }

    @Override
    public final void handleSubComponent(Class<? extends SubComponentLocator> subComponentHandlerClazz, JsonNode component, Consumer<JsonNode> subComponentElementConsumer) {
        SubComponentLocator.super.handleSubComponent(subComponentHandlerClazz, component, subComponentElementConsumer);
    }

    @Override
    public final Set<Class<? extends SubComponentLocator>> getSubLocatorsKeySet() {
        return this.subComponentLocatorManager.getSubLocatorsKeySet();
    }

    @Override
    public final void afterSingletonsInstantiated() {
        SubComponentLocator.super.afterSingletonsInstantiated();
    }
}
