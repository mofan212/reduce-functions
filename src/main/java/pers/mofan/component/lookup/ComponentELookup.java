package pers.mofan.component.lookup;

import org.springframework.stereotype.Component;
import pers.mofan.component.bo.ComponentE;
import pers.mofan.component.bo.MyComponent;

/**
 * @author mofan
 * @date 2023/8/20 19:50
 */
@Component
public class ComponentELookup extends BaseComponentLookup {
    @Override
    public Class<? extends MyComponent> getComponentIdentity() {
        return ComponentE.class;
    }
}
