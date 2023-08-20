package pers.mofan.component.lookup;

import org.springframework.stereotype.Component;
import pers.mofan.component.bo.ComponentA;
import pers.mofan.component.bo.MyComponent;

/**
 * @author mofan
 * @date 2023/8/20 19:48
 */
@Component
public class ComponentALookup extends BaseComponentLookup {
    @Override
    public Class<? extends MyComponent> getComponentIdentity() {
        return ComponentA.class;
    }
}
