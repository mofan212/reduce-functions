package pers.mofan.component.handler.impl;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import pers.mofan.component.bo.ComponentF;
import pers.mofan.component.bo.MyComponent;
import pers.mofan.component.context.HandlerContext;
import pers.mofan.component.handler.BaseSingleTopLevelComponentHandler;
import pers.mofan.component.handler.impl.simple.ComponentEsHandler;
import pers.mofan.util.JacksonUtils;

/**
 * @author mofan
 * @date 2023/8/15 20:18
 */
@Component
public class ComponentFHandler extends BaseSingleTopLevelComponentHandler {

    @Override
    public Class<? extends MyComponent> getComponentIdentity() {
        return ComponentF.class;
    }

    @Override
    public void initComponentLocators() {
        addLocator("D_TYPE", "componentF");
    }

    @Override
    public void initSubComponentLocators() {
        addSubLocator(ComponentEsHandler.class, component -> JacksonUtils.getJsonNodeList(component, "componentEs"));
    }

    @Override
    public void handleComponent(HandlerContext context, @NonNull JsonNode component) {
        handleSubComponent(ComponentEsHandler.class, context, component);
    }
}
