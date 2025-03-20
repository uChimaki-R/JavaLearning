package algorithms;

public class Kmp {

    public static void main(String[] args) {
        System.out.println(kmp("abcbcc", "cbc"));
    }

    /**
     * kmp算法找出text中pattern字串的起始下标，不存在则返回-1
     */
    public static int kmp(String text, String pattern) {
        int n = text.length(), m = pattern.length();
        int[] next = new int[m];
        getNext(pattern, next);
        int i = 0, j = 0;
        while (i < n) {
            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            } else if (j > 0) { // 访问j-1下标，所以需要j>0
                j = next[j - 1];
            } else {
                // j=0 相当于重新开始匹配，i移动
                i++;
            }
            if (j == m) {
                return i - j;
            }
        }
        return -1;
    }

    /**
     * 计算next数组
     */
    private static void getNext(String pattern, int[] next) {
        next[0] = 0; // 单个字符没有前后缀
        int fixLength = 0, i = 1; // 定义最长前后缀长度fixLength和移进的下标i，注意i从1开始，从0开始则一开始就进入if分支，错误
        while (i < pattern.length()) { // 遍历用于匹配的字符串
            if (pattern.charAt(fixLength) == pattern.charAt(i)) {
                // 当前最长前缀的下一个字符和当前字符相同，二者构成更长的前后缀
                fixLength++; // 更新最长前后缀长度
                next[i] = fixLength; // 更新next数组
                i++; // 移动下标
            } else {
                if (fixLength == 0) { // =0的时候判断一下，因为下面的else里i不会移动，如果不断减少fixLength直到0后会到达这个if分支
                    // 前缀为0了依旧无法构成前后缀，next数组为0，继续判断下一个
                    next[i] = 0;
                    i++;
                } else {
                    // 通过下面的语句缩短当前的最长前后缀，以此尝试是否能与i下标的字符构成前后缀
                    // 原理：
                    // fixLength原本是i下标前那个字符串的最大前后缀长度，但是这个后缀无法和i下标合并构成更长的前后缀
                    // 但是不能说明前面这个字符串的短一点的前后缀无法和i下标构成前后缀
                    // 所以需要用更小的前后缀与i下标尝试构成新前后缀
                    // 但是我们next数组里记录的都是最长的，如何找到更短的前后缀？
                    // 因为前缀后缀的子串相同，要找出短一点的前后缀，等价于找到前缀/后缀这个字符串的最大前后缀，这是我们记录过的，即next[fixLength - 1]（前缀的最大前后缀）
                    // 这样更新了最大前后缀长度后，进入下一次比较（i不变），尝试短一点的前后缀是否能与i下标构成前后缀，如果一直不行的话fixLength会变为0，进入上面的if分支
                    fixLength = next[fixLength - 1];
                }
            }
        }
    }
}
