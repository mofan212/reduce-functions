package pers.mofan.component.handler.impl.array;

import pers.mofan.component.bo.ComponentD;
import pers.mofan.component.bo.MyComponent;
import pers.mofan.component.handler.BaseArrayComponentHandlerDelegate;
import pers.mofan.component.handler.SimpleComponentHandler;
import pers.mofan.component.handler.impl.simple.ComponentDHandler;
import org.springframework.stereotype.Component;

/**
 * @author mofan
 * @date 2023/8/15 20:10
 */
@Component
public class ComponentDsHandler extends BaseArrayComponentHandlerDelegate {
    @Override
    public Class<? extends MyComponent> getComponentIdentity() {
        return ComponentD.class;
    }

    @Override
    protected Class<? extends SimpleComponentHandler> elementComponent() {
        return ComponentDHandler.class;
    }

    @Override
    public void initComponentLocators() {
        addLocator("C_TYPE", "componentDs");
    }
}
