package pers.mofan.component.lookup;

import org.springframework.stereotype.Component;
import pers.mofan.component.bo.ComponentC;
import pers.mofan.component.bo.MyComponent;

/**
 * @author mofan
 * @date 2023/8/20 19:49
 */
@Component
public class ComponentCLookup extends BaseComponentLookup {
    @Override
    public Class<? extends MyComponent> getComponentIdentity() {
        return ComponentC.class;
    }
}
