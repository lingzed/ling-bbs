package com.ling.utils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * list to tree 工具类
 */
public class TreeUtil {

    /**
     * 树节点接口
     * 确保传入的类型包含 getId(), getParentId(), setChildren() 方法
     *
     * @param <T>
     */
    public interface TreeNode<T> {
        String getId();

        String getParentId();

        void setChildren(List<T> children);
    }

    /**
     * 创建HashMap，通过pid进行分组，将同一组的节点整合为list
     * key: pid
     * value: list<TreeNode>
     *
     * @param pid  父节点， 类型为String
     * @param list 目标list
     * @param <T>  树节点实现类
     * @return 转换成tree后的list
     */
    public static <T extends TreeNode<T>> List<T> listToTree(String pid, List<T> list) {
        return listToTree(pid, list, false);
    }

    /**
     * 创建HashMap，通过pid进行分组，将同一组的节点整合为list
     * 叶子节点填充空数组或null
     *
     * @param pid
     * @param list
     * @param leafToEmptyArray
     * @param <T>
     * @return
     */
    public static <T extends TreeNode<T>> List<T> listToTree(String pid, List<T> list, boolean leafToEmptyArray) {
        Map<String, List<T>> map = list.stream().collect(Collectors.groupingBy(TreeNode::getParentId));
        return buildTree(pid, map, leafToEmptyArray);
    }

    /**
     * 通过pid得到对应节点list，遍历list，递归将节点的子节点填充到自己
     * 返回包含子节点的节点list
     *
     * @param pid 父节点， 类型为String
     * @param map pid 与 list<TreeNode> 的映射
     * @param <T> 树节点实现类
     * @return 转换成tree后的list
     */
    private static <T extends TreeNode<T>> List<T> buildTree(String pid, Map<String, List<T>> map, boolean leafToEmptyArray) {
        // leafToEmptyArray 为 true 时，无子节点，填充[]，否则填充null
        List<T> list = map.getOrDefault(pid, leafToEmptyArray ? Collections.emptyList() : null);
        if (list != null) {
            for (T t : list) {
                t.setChildren(buildTree(t.getId(), map, leafToEmptyArray));
            }
        }
        return list;
    }
}