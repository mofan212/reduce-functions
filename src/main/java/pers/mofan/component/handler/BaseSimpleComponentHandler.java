package pers.mofan.component.handler;

import com.fasterxml.jackson.databind.JsonNode;
import pers.mofan.component.store.ComponentLocatorStore;
import pers.mofan.component.store.support.DefaultComponentLocatorStore;

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
public abstract class BaseSimpleComponentHandler implements ComponentLocator, SimpleComponentHandler {

    private final ComponentLocatorStore componentLocatorStore = new DefaultComponentLocatorStore();

    @Override
    public void initSubComponentLocators() {
        // 默认实现下，不一定所有组件都有子组件
    }

    @Override
    public final boolean isArrayComponent() {
        return false;
    }

    @Override
    public final void addSubLocator(Class<? extends ComponentLocator> subComponentHandlerClazz, Function<JsonNode, List<Optional<JsonNode>>> subLocatorFunction) {
        this.componentLocatorStore.addSubLocator(subComponentHandlerClazz, subLocatorFunction);
    }

    @Override
    public final Function<JsonNode, List<Optional<JsonNode>>> getSubLocator(Class<? extends ComponentLocator> subComponentHandlerClazz) {
        return this.componentLocatorStore.getSubLocator(subComponentHandlerClazz);
    }

    @Override
    public final void handleSubComponent(Class<? extends ComponentLocator> subComponentHandlerClazz, JsonNode component, Consumer<JsonNode> subComponentElementConsumer) {
        ComponentLocator.super.handleSubComponent(subComponentHandlerClazz, component, subComponentElementConsumer);
    }

    @Override
    public final Set<Class<? extends ComponentLocator>> getSubLocatorsKeySet() {
        return this.componentLocatorStore.getSubLocatorsKeySet();
    }
}
