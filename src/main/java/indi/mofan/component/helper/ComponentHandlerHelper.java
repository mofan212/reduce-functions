package indi.mofan.component.helper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import indi.mofan.component.context.HandlerContext;
import indi.mofan.component.dispatcher.ComponentHandlerDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author mofan
 * @date 2023/8/13 20:05
 */
@Component
public class ComponentHandlerHelper {
    @Autowired
    private ComponentHandlerDispatcher dispatcher;

    public void handle(ObjectNode objectNode, HandlerContext context) {
        // 一个节点由多个组件组成
        dispatcher.dispatch(objectNode).forEach(handler -> {
            // 一个组件处理器处理的组件可能处理多个节点，相同的组件在不同的节点中可能拥有不能的定位方式
            Function<JsonNode, List<Optional<JsonNode>>> locator = handler.getLocator(objectNode);
            // 一个节点可以引用多个相同组件
            locator.apply(objectNode).forEach(i -> i.ifPresent(j -> handler.handleComponent(context, j)));
        });
    }
}
