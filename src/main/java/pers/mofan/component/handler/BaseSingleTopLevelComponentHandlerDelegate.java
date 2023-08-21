package pers.mofan.component.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import pers.mofan.component.context.HandlerContext;
import pers.mofan.component.manager.TopLevelComponentLocatorManager;
import pers.mofan.component.manager.support.DefaultTopLevelComponentLocatorManager;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

/**
 * 单个顶级组件处理器
 *
 * @author mofan
 * @date 2023/8/13 19:40
 */
public abstract class BaseSingleTopLevelComponentHandlerDelegate extends BaseSimpleComponentHandler implements ComponentHandler, ApplicationContextAware {

    private ApplicationContext applicationContext;

    private final TopLevelComponentLocatorManager manager = new DefaultTopLevelComponentLocatorManager();

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
        ComponentHandler.super.afterSingletonsInstantiated();
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
