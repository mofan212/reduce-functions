package pers.mofan;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.SneakyThrows;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import pers.mofan.component.bo.ComponentA;
import pers.mofan.component.bo.ComponentB;
import pers.mofan.component.bo.ComponentC;
import pers.mofan.component.bo.ComponentD;
import pers.mofan.component.bo.ComponentE;
import pers.mofan.component.context.HandlerContext;
import pers.mofan.component.helper.ComponentHandlerHelper;
import pers.mofan.component.helper.ReferenceRelationshipResolverHelper;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author mofan
 * @date 2023/8/20 16:27
 */
@SpringBootTest(classes = Application.class)
public class ReduceFunctionsTest implements WithAssertions {

    private static final JsonMapper MAPPER = JsonMapper.builder().build();
    private static ArrayNode arrayNode;

    @Autowired
    private ComponentHandlerHelper handlerHelper;
    @Autowired
    private ReferenceRelationshipResolverHelper resolverHelper;

    @BeforeAll
    @SneakyThrows
    public static void before() {
        ClassPathResource resource = new ClassPathResource("test.json");
        JsonNode jsonNode = MAPPER.readTree(resource.getInputStream());
        if (jsonNode instanceof ArrayNode) {
            arrayNode = ((ArrayNode) jsonNode);
            Assertions.assertEquals(4, arrayNode.size());
        } else {
            Assertions.fail();
        }
    }

    @Test
    public void testComponentHandlerHelper() {
        HandlerContext context = HandlerContext.builder().build();
        AtomicInteger integer = new AtomicInteger(0);
        arrayNode.forEach(node -> {
            if (node instanceof ObjectNode objectNode) {
                System.out.println(" 第 " + integer.addAndGet(1) + " 个节点: ");
                handlerHelper.handle(objectNode, context);
            } else {
                Assertions.fail();
            }
        });
    }

    @Test
    public void testFindReferencedComponents() {
        assertThat(resolverHelper.findReferencedComponent(arrayNode, ComponentA.class)).hasSize(2);
        assertThat(resolverHelper.findReferencedComponent(arrayNode, ComponentB.class)).hasSize(2);

        assertThat(resolverHelper.findReferencedComponent(arrayNode, ComponentC.class)).hasSize(1);

        // TODO: 2023/8/20 增加参数，根据参数找单个组件还是列表组件
        assertThat(resolverHelper.findReferencedComponent(arrayNode, ComponentD.class)).hasSize(4);
        assertThat(resolverHelper.findReferencedComponent(arrayNode, ComponentE.class)).hasSize(3);
    }
}
