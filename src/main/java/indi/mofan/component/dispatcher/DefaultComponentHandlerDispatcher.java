package indi.mofan.component.dispatcher;

import com.fasterxml.jackson.databind.node.ObjectNode;
import indi.mofan.component.handler.ComponentHandler;
import indi.mofan.component.manager.ComponentLocatorManager;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author mofan
 * @date 2023/8/13 19:57
 */
@Component
public class DefaultComponentHandlerDispatcher implements ComponentHandlerDispatcher, ApplicationRunner {

    @Autowired
    private List<ComponentHandler> handlers;

    private final Map<String, List<ComponentHandler>> handlerMap = new ConcurrentHashMap<>();

    @Override
    public List<ComponentHandler> dispatch(ObjectNode objectNode) {
        List<ComponentHandler> handlers = handlerMap.get(ComponentLocatorManager.buildLocatorKey(objectNode));
        if (CollectionUtils.isEmpty(handlers)) {
            return Collections.emptyList();
        }
        return handlers;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        for (ComponentHandler handler : handlers) {
            for (String locatorKey : handler.getLocatorsKeySet()) {
                List<ComponentHandler> handlerList = handlerMap.computeIfAbsent(locatorKey, i -> new ArrayList<>());
                handlerList.add(handler);
            }
        }
    }
}
