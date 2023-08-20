package pers.mofan.component;

import pers.mofan.component.bo.MyComponent;

/**
 * @author mofan
 * @date 2023/8/13 17:20
 */
public interface ComponentIdentity {
    /**
     * @return 获取组件标识，以组件的 Class Name 作为标识
     */
    Class<? extends MyComponent> getComponentIdentity();
}
