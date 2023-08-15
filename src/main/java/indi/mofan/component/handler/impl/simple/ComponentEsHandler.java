package indi.mofan.component.handler.impl.simple;

import indi.mofan.component.handler.BaseSimpleArrayComponentHandler;
import indi.mofan.component.handler.SimpleComponentHandler;
import org.springframework.stereotype.Component;

/**
 * @author mofan
 * @date 2023/8/15 20:14
 */
@Component
public class ComponentEsHandler extends BaseSimpleArrayComponentHandler {
    @Override
    protected Class<? extends SimpleComponentHandler> elementComponent() {
        return ComponentEHandler.class;
    }
}
