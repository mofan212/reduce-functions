package indi.mofan.component.handler.impl;

import com.fasterxml.jackson.databind.JsonNode;
import indi.mofan.component.bo.ComponentC;
import indi.mofan.component.bo.MyComponent;
import indi.mofan.component.context.HandlerContext;
import indi.mofan.component.handler.BaseSingleComponentHandlerDelegate;
import indi.mofan.component.handler.impl.simple.ComponentDHandler;
import indi.mofan.util.JacksonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * @author mofan
 * @date 2023/8/15 20:03
 */
@Component
public class ComponentCHandler extends BaseSingleComponentHandlerDelegate {

    @Autowired
    private ComponentAHandler aHandler;
    @Autowired
    private ComponentBHandler bHandler;
    @Autowired
    private ComponentDHandler dHandler;

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

    }
}
