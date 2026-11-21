
import java.util.*;

class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;

        List<Integer>[] children = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            children[i] = new ArrayList<>();
        }

        // Build original tree
        for (int i = 1; i < n; i++) {
            children[parent[i]].add(i);
        }

        int[] newParent = parent.clone();

        int[] last = new int[26];
        Arrays.fill(last, -1);

        int[] previous = new int[n];

        // Iterative DFS with enter/exit events
        int[] stack = new int[2 * n];
        int top = 0;
        stack[top++] = 0;

        while (top > 0) {
            int event = stack[--top];

            if (event >= 0) {
                int u = event;
                int c = s.charAt(u) - 'a';

                previous[u] = last[c];

                // Find nearest matching ancestor
                if (u != 0 && last[c] != -1) {
                    newParent[u] = last[c];
                }

                last[c] = u;

                // Exit event
                stack[top++] = ~u;

                // Visit children
                for (int v : children[u]) {
                    stack[top++] = v;
                }
            } else {
                int u = ~event;
                int c = s.charAt(u) - 'a';

                // Restore previous ancestor
                last[c] = previous[u];
            }
        }

        // Build modified tree
        List<Integer>[] modified = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            modified[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            modified[newParent[i]].add(i);
        }

        // Get traversal order of modified tree
        int[] order = new int[n];
        top = 0;
        int count = 0;

        stack[top++] = 0;

        while (top > 0) {
            int u = stack[--top];
            order[count++] = u;

            for (int v : modified[u]) {
                stack[top++] = v;
            }
        }

        // Calculate subtree sizes in reverse order
        int[] ans = new int[n];
        Arrays.fill(ans, 1);

        for (int i = n - 1; i >= 0; i--) {
            int u = order[i];

            if (newParent[u] != -1) {
                ans[newParent[u]] += ans[u];
            }
        }

        return ans;
    }
}
