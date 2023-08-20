package pers.mofan.component.lookup;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import pers.mofan.component.ComponentIdentity;

import java.util.List;

/**
 * @author mofan
 * @date 2023/8/13 20:13
 */
public interface ComponentLookup extends ComponentIdentity {
    /**
     * 查找组件
     *
     * @param node 节点对象
     * @return 组件信息
     */
    List<JsonNode> lookup(ObjectNode node);
}
