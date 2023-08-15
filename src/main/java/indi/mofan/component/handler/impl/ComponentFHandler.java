package indi.mofan.component.handler.impl;

import com.fasterxml.jackson.databind.JsonNode;
import indi.mofan.component.bo.ComponentF;
import indi.mofan.component.bo.MyComponent;
import indi.mofan.component.context.HandlerContext;
import indi.mofan.component.handler.BaseSingleComponentHandlerDelegate;
import indi.mofan.component.handler.impl.simple.ComponentEsHandler;
import indi.mofan.util.JacksonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * @author mofan
 * @date 2023/8/15 20:18
 */
@Component
public class ComponentFHandler extends BaseSingleComponentHandlerDelegate {

    @Autowired
    private ComponentEsHandler esHandler;

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

    }
}
