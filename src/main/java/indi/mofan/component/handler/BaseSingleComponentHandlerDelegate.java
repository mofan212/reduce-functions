package indi.mofan.component.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import indi.mofan.component.manager.ComponentLocatorManager;
import indi.mofan.component.manager.SubComponentLocatorManager;
import indi.mofan.component.manager.support.DefaultComponentLocatorManager;
import indi.mofan.component.manager.support.DefaultSubComponentLocatorManager;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 单个顶级组件处理器
 *
 * @author mofan
 * @date 2023/8/13 19:40
 */
public abstract class BaseSingleComponentHandlerDelegate implements ComponentHandler {

    private final ComponentLocatorManager manager = new DefaultComponentLocatorManager();

    private final SubComponentLocatorManager subComponentLocatorManager = new DefaultSubComponentLocatorManager();

    @Override
    public final boolean isArrayComponent() {
        return false;
    }

    public final Function<JsonNode, List<Optional<JsonNode>>> getLocator(ObjectNode node) {
        return this.manager.getLocator(node);
    }

    @Override
    public final Function<JsonNode, List<Optional<JsonNode>>> getLocator(String locatorKey) {
        return this.manager.getLocator(locatorKey);
    }

    @Override
    public final void addLocator(String locatorKey, String componentKey, String... componentKeys) {
        this.manager.addLocator(locatorKey, componentKey, componentKeys);
    }

    @Override
    public final Set<String> getLocatorsKeySet() {
        return this.manager.getLocatorsKeySet();
    }

    @Override
    public final Function<JsonNode, List<Optional<JsonNode>>> getSubLocator(Class<? extends SubComponentLocator> subComponentHandlerClazz) {
        return this.subComponentLocatorManager.getSubLocator(subComponentHandlerClazz);
    }

    @Override
    public final void addSubLocator(Class<? extends SubComponentLocator> subComponentHandlerClazz,
                                    Function<JsonNode, List<Optional<JsonNode>>> subLocatorFunction) {
        this.subComponentLocatorManager.addSubLocator(subComponentHandlerClazz, subLocatorFunction);
    }

    @Override
    public final Set<Class<? extends SubComponentLocator>> getSubLocatorsKeySet() {
        return this.subComponentLocatorManager.getSubLocatorsKeySet();
    }

    @Override
    public final void handleSubComponent(Class<? extends SubComponentLocator> subComponentHandlerClazz,
                                         JsonNode component, Consumer<JsonNode> subComponentElementConsumer) {
        ComponentHandler.super.handleSubComponent(subComponentHandlerClazz, component, subComponentElementConsumer);
    }

    @Override
    public final void afterSingletonsInstantiated() {
        ComponentHandler.super.afterSingletonsInstantiated();
    }
}
