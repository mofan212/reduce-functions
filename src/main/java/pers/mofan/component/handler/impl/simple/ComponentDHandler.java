package pers.mofan.component.handler.impl.simple;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import pers.mofan.component.bo.ComponentD;
import pers.mofan.component.bo.MyComponent;
import pers.mofan.component.context.HandlerContext;
import pers.mofan.component.handler.BaseSimpleComponentHandler;
import pers.mofan.component.util.HandlerUtils;

/**
 * @author mofan
 * @date 2023/8/15 20:02
 */
@Component
public class ComponentDHandler extends BaseSimpleComponentHandler {

    @Override
    public Class<? extends MyComponent> getComponentIdentity() {
        return ComponentD.class;
    }

    @Override
    public void handleComponent(HandlerContext context, @NonNull JsonNode component) {
        System.out.println(HandlerUtils.getFieldAValue(component));
    }
}
