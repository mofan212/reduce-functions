package pers.mofan.component.handler.impl.simple;

import pers.mofan.component.handler.BaseSimpleArrayComponentHandler;
import pers.mofan.component.handler.SimpleComponentHandler;
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
