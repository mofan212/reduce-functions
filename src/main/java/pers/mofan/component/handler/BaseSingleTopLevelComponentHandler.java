package pers.mofan.component.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import pers.mofan.component.context.HandlerContext;
import pers.mofan.component.store.TopLevelComponentLocatorStore;
import pers.mofan.component.store.support.DefaultTopLevelComponentLocatorStore;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

/**
 * 非列表顶层组件处理器
 *
 * @author mofan
 * @date 2023/8/13 19:40
 */
public abstract class BaseSingleTopLevelComponentHandler extends BaseSimpleComponentHandler implements TopLevelComponentHandler, ApplicationContextAware {

    private ApplicationContext applicationContext;

    private final TopLevelComponentLocatorStore store = new DefaultTopLevelComponentLocatorStore();

    public final Function<JsonNode, List<Optional<JsonNode>>> getLocator(ObjectNode node) {
        return this.store.getLocator(node);
    }

    @Override
    public final Function<JsonNode, List<Optional<JsonNode>>> getLocator(String locatorKey) {
        return this.store.getLocator(locatorKey);
    }

    @Override
    public final void addLocator(String locatorKey, String componentKey, String... componentKeys) {
        this.store.addLocator(locatorKey, componentKey, componentKeys);
    }

    @Override
    public final Set<String> getLocatorsKeySet() {
        return this.store.getLocatorsKeySet();
    }

    @SuppressWarnings("unchecked")
    public final void handleSubComponent(Class<? extends SimpleComponentHandler> handlerClazz,
                                         HandlerContext handlerContext, JsonNode component) {
        if (ComponentLocator.class.isAssignableFrom(handlerClazz)) {
            handleSubComponent((Class<? extends ComponentLocator>) handlerClazz, component,
                    jsonNode -> applicationContext.getBean(handlerClazz).handleComponent(handlerContext, jsonNode));
        }
    }

    @Override
    public final void afterSingletonsInstantiated() {
        TopLevelComponentHandler.super.afterSingletonsInstantiated();
    }

    @Override
    public final void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
