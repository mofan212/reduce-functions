package indi.mofan.component.handler.impl;

import com.fasterxml.jackson.databind.JsonNode;
import indi.mofan.component.bo.ComponentB;
import indi.mofan.component.bo.MyComponent;
import indi.mofan.component.context.HandlerContext;
import indi.mofan.component.handler.BaseSingleComponentHandlerDelegate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * @author mofan
 * @date 2023/8/15 17:46
 */
@Component
public class ComponentBHandler extends BaseSingleComponentHandlerDelegate {
    @Override
    public Class<? extends MyComponent> getComponentIdentity() {
        return ComponentB.class;
    }

    @Override
    public void initComponentLocators() {
        addLocator("A_TYPE", "componentB");
    }

    @Override
    public void handleComponent(HandlerContext context, @NonNull JsonNode component) {

    }
}
