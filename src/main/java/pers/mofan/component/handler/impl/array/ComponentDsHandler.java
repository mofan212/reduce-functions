package pers.mofan.component.handler.impl.array;

import org.springframework.stereotype.Component;
import pers.mofan.component.handler.BaseArrayComponentHandlerDelegate;
import pers.mofan.component.handler.SimpleComponentHandler;
import pers.mofan.component.handler.impl.simple.ComponentDHandler;

/**
 * @author mofan
 * @date 2023/8/15 20:10
 */
@Component
public class ComponentDsHandler extends BaseArrayComponentHandlerDelegate {

    @Override
    protected Class<? extends SimpleComponentHandler> elementComponent() {
        return ComponentDHandler.class;
    }

    @Override
    public void initComponentLocators() {
        addLocator("C_TYPE", "componentDs");
    }
}
