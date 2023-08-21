package pers.mofan.component.handler.impl;

import com.fasterxml.jackson.databind.JsonNode;
import pers.mofan.component.bo.ComponentA;
import pers.mofan.component.bo.MyComponent;
import pers.mofan.component.context.HandlerContext;
import pers.mofan.component.handler.BaseSingleTopLevelComponentHandlerDelegate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import pers.mofan.component.util.HandlerUtils;

/**
 * @author mofan
 * @date 2023/8/14 14:07
 */
@Component
public class ComponentAHandler extends BaseSingleTopLevelComponentHandlerDelegate {
    @Override
    public Class<? extends MyComponent> getComponentIdentity() {
        return ComponentA.class;
    }

    @Override
    public void initComponentLocators() {
        addLocator("A_TYPE", "componentA");
    }

    @Override
    public void handleComponent(HandlerContext context, @NonNull JsonNode component) {
        System.out.println(HandlerUtils.getFieldAValue(component));
    }
}
