package pers.mofan.component.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import pers.mofan.component.store.TopLevelComponentLocatorStore;
import pers.mofan.component.store.support.DefaultTopLevelComponentLocatorStore;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

/**
 * 列表顶层组件处理器
 *
 * @author mofan
 * @date 2023/8/13 19:47
 */
public abstract class BaseArrayTopLevelComponentHandler extends BaseSimpleArrayComponentHandler implements TopLevelComponentHandler {

    private final TopLevelComponentLocatorStore store = new DefaultTopLevelComponentLocatorStore();

    @Override
    public final void addLocator(String locatorKey, String componentKey, String... componentKeys) {
        this.store.addLocator(locatorKey, componentKey, componentKeys);
    }

    @Override
    public final Function<JsonNode, List<Optional<JsonNode>>> getLocator(String locatorKey) {
        return this.store.getLocator(locatorKey);
    }

    @Override
    public final Function<JsonNode, List<Optional<JsonNode>>> getLocator(ObjectNode node) {
        return this.store.getLocator(node);
    }

    @Override
    public final Set<String> getLocatorsKeySet() {
        return this.store.getLocatorsKeySet();
    }

    @Override
    public final void afterSingletonsInstantiated() {
        TopLevelComponentHandler.super.afterSingletonsInstantiated();
    }
}
