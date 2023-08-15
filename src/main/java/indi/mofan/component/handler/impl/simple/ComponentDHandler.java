package indi.mofan.component.handler.impl.simple;

import com.fasterxml.jackson.databind.JsonNode;
import indi.mofan.component.context.HandlerContext;
import indi.mofan.component.handler.BaseSimpleComponentHandler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * @author mofan
 * @date 2023/8/15 20:02
 */
@Component
public class ComponentDHandler extends BaseSimpleComponentHandler {
    @Override
    public void handleComponent(HandlerContext context, @NonNull JsonNode component) {

    }
}
