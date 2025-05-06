package handTearing.acAutoMachine;

import java.util.*;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-04-19
 * @Description: 手写AC自动机
 * @Version: 1.0
 */
public class AcAutoMachine {
    /**
     * 字典树的根节点
     */
    private final AcTrie root;

    public AcAutoMachine(List<String> patterns) {
        root = new AcTrie();
        patterns.forEach(this::insert);
        build();
    }

    public Set<String> search(String text) {
        Set<String> result = new HashSet<>();
        AcTrie current = root;
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            // 如果没有这个字符，回溯
            while (current != null && !current.children.containsKey(ch)) {
                current = current.fail;
            }
            if (current == null) {
                // 回溯到root了
                current = root;
            }
            if (current.children.containsKey(ch)) {
                // 更新为这个子节点
                current = current.children.get(ch);
                // 将这个子节点的pattern加入结果集
                result.addAll(current.patterns);
                // 接着匹配，else不匹配不处理
            }
        }
        return result;
    }

    /**
     * 插入模式串
     */
    private void insert(String pattern) {
        AcTrie current = root;
        for (int i = 0; i < pattern.length(); i++) {
            char ch = pattern.charAt(i);
            if (!current.children.containsKey(ch)) {
                current.children.put(ch, new AcTrie());
            }
            current = current.children.get(ch);
        }
        current.patterns.add(pattern);
    }

    /**
     * 构建自动机（初始化fail）
     */
    private void build() {
        // 构建基于广度优先搜索
        Queue<AcTrie> queue = new LinkedList<>();
        root.fail = null;
        // 把root的子节点加入队列，开始迭代
        for (AcTrie child : root.children.values()) {
            // 子节点的fail直接指向父节点root
            child.fail = root;
            queue.offer(child);
        }
        while (!queue.isEmpty()) {
            // poll出来的节点的fail是已知的
            AcTrie current = queue.poll();

            // 处理current节点的子节点的fail
            for (Map.Entry<Character, AcTrie> childEntry : current.children.entrySet()) {
                Character childChar = childEntry.getKey();
                AcTrie childNode = childEntry.getValue();

                // 回溯到父节点（current）的failNode，如果该节点的子节点包含自己（有和自己有相同char的点，下同），则fail指向这个failNode的子节点中的自己
                // 否则需要不断回溯，直到null（root.fail）或者找到子节点包含自己的failNode
                AcTrie failNode = current.fail;
                while (failNode != null && !failNode.children.containsKey(childChar)) {
                    // 这个fail的子节点不包含自己，继续回溯
                    failNode = failNode.fail;
                }
                // 有可能failNode == null，也有可能failNode子节点包含了自己
                // 注意！fail指向的不是failNode，而是failNode的子节点中的自己
                childNode.fail = failNode == null ? root : failNode.children.get(childChar);

                // 将自己的fail节点保存的pattern加入到自己中
                childNode.patterns.addAll(childNode.fail.patterns);

                // 节点加入队列接着广搜
                queue.offer(childNode);
            }
        }
    }

    /**
     * 字典树
     */
    static class AcTrie {
        /**
         * 子节点也是字典树
         */
        final Map<Character, AcTrie> children = new HashMap<>();
        /**
         * 到该节点能匹配到的模式串集合
         */
        final Set<String> patterns = new HashSet<>();
        /**
         * 无法在该节点的子节点接着匹配的时候，回溯的节点
         */
        AcTrie fail;
    }

    public static void main(String[] args) {
        AcAutoMachine machine = new AcAutoMachine(Arrays.asList("he", "she", "her", "say", "shr"));
        Set<String> result = machine.search("sherhsay");
        System.out.println(result);
    }
}
