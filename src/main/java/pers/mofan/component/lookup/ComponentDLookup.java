package pers.mofan.component.lookup;

import org.springframework.stereotype.Component;
import pers.mofan.component.bo.ComponentD;
import pers.mofan.component.bo.MyComponent;

/**
 * @author mofan
 * @date 2023/8/20 19:49
 */
@Component
public class ComponentDLookup extends BaseComponentLookup{
    @Override
    public Class<? extends MyComponent> getComponentIdentity() {
        return ComponentD.class;
    }
}
