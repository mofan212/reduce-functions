package pers.mofan.component.handler;

import com.fasterxml.jackson.databind.JsonNode;
import pers.mofan.component.ComponentIdentity;
import pers.mofan.component.context.HandlerContext;
import org.springframework.lang.NonNull;

/**
 * @author mofan
 * @date 2023/8/13 15:23
 */
public interface SimpleComponentHandler extends ComponentIdentity {
    /**
     * 组件处理
     *
     * @param context   处理器上下文，组件处理时可能需要的一些信息
     * @param component 对象，组件对象，保证该值不为 null
     */
    void handleComponent(HandlerContext context, @NonNull JsonNode component);
}
