package pers.mofan.component.handler.impl;

import com.fasterxml.jackson.databind.JsonNode;
import pers.mofan.component.bo.ComponentB;
import pers.mofan.component.bo.MyComponent;
import pers.mofan.component.context.HandlerContext;
import pers.mofan.component.handler.BaseSingleComponentHandlerDelegate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import pers.mofan.component.util.HandlerUtils;

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
        System.out.println(HandlerUtils.getFieldAValue(component));
    }
}
