class Solution {
    public String reverseParentheses(String s) {
        int n = s.length();
        int[] pair = new int[n];
        int[] stack = new int[n];
        int top = 0;

        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '(') {
                stack[top++] = i;
            } else if (s.charAt(i) == ')') {
                int j = stack[--top];
                pair[i] = j;
                pair[j] = i;
            }
        }

        StringBuilder ans = new StringBuilder();

        int i = 0;
        int direction = 1;

        while (i < n) {
            char ch = s.charAt(i);

            if (ch == '(' || ch == ')') {
                i = pair[i];
                direction = -direction;
            } else {
                ans.append(ch);
            }

            i += direction;
        }

        return ans.toString();
    }
}