package indi.mofan.component.dispatcher;

import com.fasterxml.jackson.databind.node.ObjectNode;
import indi.mofan.component.handler.ComponentHandler;

import java.util.List;

/**
 * @author mofan
 * @date 2023/8/13 19:55
 */
public interface ComponentHandlerDispatcher {
    /**
     * 根据传入的节点分发到各个组件处理器中
     */
    List<ComponentHandler> dispatch(ObjectNode objectNode);
}
