package indi.mofan.component.handler.impl.array;

import indi.mofan.component.bo.ComponentD;
import indi.mofan.component.bo.MyComponent;
import indi.mofan.component.handler.BaseArrayComponentHandlerDelegate;
import indi.mofan.component.handler.SimpleComponentHandler;
import indi.mofan.component.handler.impl.simple.ComponentDHandler;
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
