package indi.mofan.component.util;

import com.google.common.graph.ElementOrder;
import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.Graphs;
import com.google.common.graph.MutableGraph;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author mofan
 * @date 2023/8/13 20:30
 * @link <a href="https://blog.csdn.net/abcbocheng/article/details/101102181">图算法 - 只需“五步” ，获取两节点间的所有路径（非递归方式）</a>
 */
public final class GraphUtils {
    private GraphUtils() {
    }

    /**
     * <p>数据调用图</p>
     * <p>
     * 这是一个有向图。假设有两个组件名为 A 和 B，
     * 在 A 组件中引用了 B，那么在该图中，存在两个节点 a 和 b，
     * 其中 a、b 的节点值分别为组件 A、B 的 ID，
     * a、b 两节点之间存在一条由 a 指向 b 的有向边 ab。
     * </p>
     *
     * @param refMap 引用信息
     * @return 引用图
     */
    public static <N> MutableGraph<N> buildGraph(Map<N, Collection<N>> refMap) {
        // 定义一个有向图
        MutableGraph<N> callGraph = GraphBuilder.directed()
                .nodeOrder(ElementOrder.<N>insertion())
                .expectedNodeCount(refMap.size() << 1)
                .allowsSelfLoops(false)
                .build();
        // 对有向图添加边
        for (Map.Entry<N, Collection<N>> entry : refMap.entrySet()) {
            N parentMfId = entry.getKey();
            Collection<N> value = entry.getValue();
            if (CollectionUtils.isEmpty(value)) {
                callGraph.addNode(parentMfId);
            } else {
                for (N subMfId : value) {
                    callGraph.putEdge(parentMfId, subMfId);
                }
            }
        }
        return callGraph;
    }

    /**
     * 获取图中到达目标节点的所有路径
     *
     * @param graph      图
     * @param targetNode 目标节点
     * @param <N>        节点类型
     * @return 到目标节点的所有路径（包括环）
     */
    public static <N> List<List<N>> getAllPath2TargetNode(Graph<N> graph, N targetNode) {
        // 翻转图
        Graph<N> transposeGraph = Graphs.transpose(graph);
        // 处理孤立节点
        if (!transposeGraph.nodes().contains(targetNode)) {
            return Collections.emptyList();
        }
        List<List<N>> path = new ArrayList<>();

        LinkedList<N> main = new LinkedList<>();
        main.push(targetNode);
        Deque<Collection<N>> secondary = new ArrayDeque<>();
        // 获取开始节点的所有前驱节点并将塞入辅栈中（new 一个集合，防止 UnsupportedOperationException）
        secondary.push(new ArrayList<>(graph.predecessors(targetNode)));
        while (!secondary.isEmpty()) {
            Collection<N> peek = secondary.peek();
            if (CollectionUtils.isNotEmpty(peek)) {
                Optional<N> any = peek.stream().findAny();
                if (any.isPresent()) {
                    N node = any.get();
                    main.push(node);
                    peek.remove(node);

                    Set<N> predecessors = graph.predecessors(node);
                    if (CollectionUtils.isEmpty(predecessors)) {
                        addPath(path, main, null);
                        main.pop();
                    } else {
                        List<N> collect = predecessors.stream().filter(i -> !main.contains(i)).collect(Collectors.toList());
                        if (collect.size() == predecessors.size()) {
                            secondary.push(collect);
                        } else {
                            List<N> sameNodeList = predecessors.stream().filter(main::contains).collect(Collectors.toList());
                            addPath(path, main, sameNodeList);
                            if (CollectionUtils.isNotEmpty(collect)) {
                                secondary.push(collect);
                            } else {
                                main.pop();
                            }
                        }
                    }
                }
            } else {
                cutTwoStack(main, secondary);
            }
        }
        return path;
    }

    /**
     * 添加路径
     *
     * @param path        路径集合
     * @param main        主栈
     * @param cycleStarts 环形调用链开始节点集合
     * @param <N>         节点类型
     */
    private static <N> void addPath(List<List<N>> path, Deque<N> main, Collection<N> cycleStarts) {
        Deque<N> deque = new ArrayDeque<>();
        main.descendingIterator().forEachRemaining(deque::push);
        if (CollectionUtils.isNotEmpty(cycleStarts)) {
            // cycleStarts 是一个列表，表示上游可能有多个环，每一个都要加进去
            Iterator<N> iterator = cycleStarts.iterator();
            while (iterator.hasNext()) {
                deque.push(iterator.next());
                List<N> tempList = new ArrayList<>();
                deque.iterator().forEachRemaining(tempList::add);
                path.add(tempList);
                // 还有元素，才 pop，减少一次 pop 的次数
                if (iterator.hasNext()) {
                    deque.pop();
                }
            }
        } else {
            path.add(new ArrayList<>(deque));
        }
    }

    /**
     * 削栈（移除两个栈的顶层节点）
     *
     * @param mainStack      主栈
     * @param secondaryStack 辅栈
     * @param <T>            节点类型
     */
    private static <T> void cutTwoStack(Deque<T> mainStack, Deque<Collection<T>> secondaryStack) {
        mainStack.pop();
        secondaryStack.pop();
    }
}
