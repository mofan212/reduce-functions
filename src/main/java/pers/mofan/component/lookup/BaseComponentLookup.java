package pers.mofan.component.lookup;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import pers.mofan.component.store.TopLevelComponentLocatorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * @author mofan
 * @date 2023/8/13 20:14
 */
@Component
public abstract class BaseComponentLookup implements ComponentLookup, ApplicationRunner, Ordered {

    @Autowired
    private ApplicationContext applicationContext;

    private final Map<String, List<Function<JsonNode, List<Optional<JsonNode>>>>> singleComponentReferenceRelationship = new ConcurrentHashMap<>();
    private final Map<String, List<Function<JsonNode, List<Optional<JsonNode>>>>> arrayComponentReferenceRelationship = new ConcurrentHashMap<>();

    @Override
    public final List<JsonNode> lookup(ObjectNode node, boolean isComponentArray) {
        var relationShip = isComponentArray ? this.arrayComponentReferenceRelationship : this.singleComponentReferenceRelationship;
        return relationShip.getOrDefault(TopLevelComponentLocatorStore.buildLocatorKey(node), Collections.emptyList()).stream()
                .flatMap(i -> i.apply(node).stream())
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    @Override
    public final void run(ApplicationArguments args) {
        var map = applicationContext.getBean(ComponentRelationshipCollector.class).collect(getComponentIdentity());
        singleComponentReferenceRelationship.putAll(map.getOrDefault(Boolean.FALSE, Collections.emptyMap()));
        arrayComponentReferenceRelationship.putAll(map.getOrDefault(Boolean.TRUE, Collections.emptyMap()));
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
