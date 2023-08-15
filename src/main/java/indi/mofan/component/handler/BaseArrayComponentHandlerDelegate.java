package indi.mofan.component.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import indi.mofan.component.manager.ComponentLocatorManager;
import indi.mofan.component.manager.support.DefaultComponentLocatorManager;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

/**
 * 单个顶级列表组件处理器
 *
 * @author mofan
 * @date 2023/8/13 19:47
 */
public abstract class BaseArrayComponentHandlerDelegate extends BaseSimpleArrayComponentHandler implements ComponentHandler {

    private final ComponentLocatorManager manager = new DefaultComponentLocatorManager();

    @Override
    public final boolean isArrayComponent() {
        return true;
    }

    @Override
    public final boolean loadSubComponentLocators() {
        return true;
    }

    @Override
    public final void addLocator(String locatorKey, String componentKey, String... componentKeys) {
        this.manager.addLocator(locatorKey, componentKey, componentKeys);
    }

    @Override
    public final Function<JsonNode, List<Optional<JsonNode>>> getLocator(String locatorKey) {
        return this.manager.getLocator(locatorKey);
    }

    @Override
    public final Function<JsonNode, List<Optional<JsonNode>>> getLocator(ObjectNode node) {
        return this.manager.getLocator(node);
    }

    @Override
    public final Set<String> getLocatorsKeySet() {
        return this.manager.getLocatorsKeySet();
    }
}
