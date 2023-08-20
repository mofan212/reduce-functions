package pers.mofan.component.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import pers.mofan.component.bo.MyComponent;
import pers.mofan.component.context.HandlerContext;
import pers.mofan.component.manager.support.DefaultSubComponentLocatorManager;
import pers.mofan.util.CastUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * 单个非顶级列表组件处理器
 *
 * @author mofan
 * @date 2023/8/13 17:10
 */
public abstract class BaseSimpleArrayComponentHandler implements SimpleComponentHandler, SimpleComponentLocator, ApplicationContextAware {

    private ApplicationContext applicationContext;

    private final DefaultSubComponentLocatorManager subComponentLocatorManager = new DefaultSubComponentLocatorManager();

    @Override
    public final void initSubComponentLocators() {
        if (SubComponentLocator.class.isAssignableFrom(elementComponent())) {
            Class<SubComponentLocator> clazz = CastUtils.cast(elementComponent());
            addSubLocator(clazz);
        }
    }

    @Override
    public Class<? extends MyComponent> getComponentIdentity() {
        return applicationContext.getBean(elementComponent()).getComponentIdentity();
    }

    public final void addSubLocator(Class<? extends SubComponentLocator> subComponentHandlerClazz) {
        this.addSubLocator(subComponentHandlerClazz, component -> {
            // 列表组件的子组件就是它的每项
            if (!(component instanceof ArrayNode)) {
                return Collections.emptyList();
            }
            return StreamSupport.stream(component.spliterator(), false)
                    .map(Optional::of)
                    .collect(Collectors.toList());
        });
    }

    @Override
    public final void addSubLocator(Class<? extends SubComponentLocator> subComponentHandlerClazz,
                                    Function<JsonNode, List<Optional<JsonNode>>> subLocatorFunction) {
        this.subComponentLocatorManager.addSubLocator(subComponentHandlerClazz, subLocatorFunction);
    }

    @Override
    public final Function<JsonNode, List<Optional<JsonNode>>> getSubLocator(Class<? extends SubComponentLocator> subComponentHandlerClazz) {
        return this.subComponentLocatorManager.getSubLocator(subComponentHandlerClazz);
    }

    @Override
    public final Set<Class<? extends SubComponentLocator>> getSubLocatorsKeySet() {
        return this.subComponentLocatorManager.getSubLocatorsKeySet();
    }

    @Override
    public final void handleSubComponent(Class<? extends SubComponentLocator> subComponentHandlerClazz,
                                   JsonNode component, Consumer<JsonNode> subComponentElementConsumer) {
        SimpleComponentLocator.super.handleSubComponent(subComponentHandlerClazz, component, subComponentElementConsumer);
    }

    @Override
    public final void handleComponent(HandlerContext context, @NonNull JsonNode component) {
        if (!(component instanceof ArrayNode)) {
            return;
        }
        for (JsonNode node : component) {
            if (node instanceof ObjectNode) {
                applicationContext.getBean(elementComponent()).handleComponent(context, node);
            }
        }
    }

    @Override
    public final void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 数组元素组件级联修改处理器
     *
     * @return 处理器的 Class
     */
    protected abstract Class<? extends SimpleComponentHandler> elementComponent();
}
