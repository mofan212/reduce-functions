package pers.mofan.component.handler.impl.simple;

import com.fasterxml.jackson.databind.JsonNode;
import pers.mofan.component.context.HandlerContext;
import pers.mofan.component.handler.BaseSimpleComponentHandler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import pers.mofan.component.util.HandlerUtils;

/**
 * @author mofan
 * @date 2023/8/15 20:10
 */
@Component
public class ComponentEHandler extends BaseSimpleComponentHandler {
    @Override
    public void handleComponent(HandlerContext context, @NonNull JsonNode component) {
        System.out.println(HandlerUtils.getFieldAValue(component));
    }
}
