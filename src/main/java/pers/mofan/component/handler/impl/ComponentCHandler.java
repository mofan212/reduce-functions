package pers.mofan.component.handler.impl;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import pers.mofan.component.bo.ComponentC;
import pers.mofan.component.bo.MyComponent;
import pers.mofan.component.context.HandlerContext;
import pers.mofan.component.handler.BaseSingleTopLevelComponentHandler;
import pers.mofan.component.handler.impl.simple.ComponentDHandler;
import pers.mofan.util.JacksonUtils;

/**
 * @author mofan
 * @date 2023/8/15 20:03
 */
@Component
public class ComponentCHandler extends BaseSingleTopLevelComponentHandler {

    @Override
    public Class<? extends MyComponent> getComponentIdentity() {
        return ComponentC.class;
    }

    @Override
    public void initComponentLocators() {
        addLocator("B_TYPE", "componentC");
    }

    @Override
    public void initSubComponentLocators() {
        addSubLocator(ComponentAHandler.class, component -> JacksonUtils.getJsonNodeList(component, "componentA"));
        addSubLocator(ComponentBHandler.class, component -> JacksonUtils.getJsonNodeList(component, "componentB"));
        addSubLocator(ComponentDHandler.class, component -> JacksonUtils.getJsonNodeList(component, "componentD"));
    }

    @Override
    public void handleComponent(HandlerContext context, @NonNull JsonNode component) {
        handleSubComponent(ComponentAHandler.class, context, component);
        handleSubComponent(ComponentBHandler.class, context, component);
        handleSubComponent(ComponentDHandler.class, context, component);
    }
}
