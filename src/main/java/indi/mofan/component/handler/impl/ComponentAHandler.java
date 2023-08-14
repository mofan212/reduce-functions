package indi.mofan.component.handler.impl;

import com.fasterxml.jackson.databind.JsonNode;
import indi.mofan.component.bo.ComponentA;
import indi.mofan.component.bo.MyComponent;
import indi.mofan.component.context.HandlerContext;
import indi.mofan.component.handler.BaseSingleComponentHandlerDelegate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * @author mofan
 * @date 2023/8/14 14:07
 */
@Component
public class ComponentAHandler extends BaseSingleComponentHandlerDelegate {
    @Override
    public Class<? extends MyComponent> getComponentIdentity() {
        return ComponentA.class;
    }

    @Override
    public void initComponentLocators() {

    }

    @Override
    public void handleComponent(HandlerContext context, @NonNull JsonNode component) {

    }
}
