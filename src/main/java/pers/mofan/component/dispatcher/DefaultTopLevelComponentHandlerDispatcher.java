package pers.mofan.component.dispatcher;

import com.fasterxml.jackson.databind.node.ObjectNode;
import pers.mofan.component.handler.TopLevelComponentHandler;
import pers.mofan.component.store.TopLevelComponentLocatorStore;
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
public class DefaultTopLevelComponentHandlerDispatcher implements TopLevelComponentHandlerDispatcher, ApplicationRunner {

    @Autowired
    private List<TopLevelComponentHandler> handlers;

    private final Map<String, List<TopLevelComponentHandler>> handlerMap = new ConcurrentHashMap<>();

    @Override
    public List<TopLevelComponentHandler> dispatch(ObjectNode objectNode) {
        List<TopLevelComponentHandler> handlers = handlerMap.get(TopLevelComponentLocatorStore.buildLocatorKey(objectNode));
        if (CollectionUtils.isEmpty(handlers)) {
            return Collections.emptyList();
        }
        return handlers;
    }

    @Override
    public void run(ApplicationArguments args) {
        for (TopLevelComponentHandler handler : handlers) {
            for (String locatorKey : handler.getLocatorsKeySet()) {
                List<TopLevelComponentHandler> handlerList = handlerMap.computeIfAbsent(locatorKey, i -> new ArrayList<>());
                handlerList.add(handler);
            }
        }
    }
}
