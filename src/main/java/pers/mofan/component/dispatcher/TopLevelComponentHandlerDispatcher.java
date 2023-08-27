package pers.mofan.component.dispatcher;

import com.fasterxml.jackson.databind.node.ObjectNode;
import pers.mofan.component.handler.TopLevelComponentHandler;

import java.util.List;

/**
 * @author mofan
 * @date 2023/8/13 19:55
 */
public interface TopLevelComponentHandlerDispatcher {
    /**
     * 根据传入的节点分发到各个组件处理器中
     */
    List<TopLevelComponentHandler> dispatch(ObjectNode objectNode);
}
