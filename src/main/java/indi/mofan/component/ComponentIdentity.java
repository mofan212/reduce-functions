package indi.mofan.component;

/**
 * @author mofan
 * @date 2023/8/13 17:20
 */
public interface ComponentIdentity {
    /**
     * @return 获取组件标识，通常以组件的 Class Name 作为标识
     * 组件对象化后，应该继承同一个基类，为了简化，泛型使用 ?
     */
    Class<?> getComponentIdentity();
}
