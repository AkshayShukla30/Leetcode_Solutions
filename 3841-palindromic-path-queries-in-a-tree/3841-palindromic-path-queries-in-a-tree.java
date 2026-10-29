import java.util.*;

class Solution {
    private int[][] up;
    private int[] parent, depth, tin, tout, baseMask;
    private int timer, log;
    private List<Integer>[] adj;
    private Fenwick bit;

    public List<Boolean> palindromePath(int n, int[][] edges, String s, String[] queries) {
        adj = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            adj[e[0]].add(e[1]);
            adj[e[1]].add(e[0]);
        }

        log = 1;
        while ((1 << log) <= n) {
            log++;
        }

        up = new int[log][n];
        parent = new int[n];
        depth = new int[n];
        tin = new int[n];
        tout = new int[n];
        baseMask = new int[n];

        char[] chars = s.toCharArray();
        int[] order = new int[n];
        int[] stack = new int[n];
        int top = 0, size = 0;
        timer = 0;

        stack[top++] = 0;
        parent[0] = 0;

        while (top > 0) {
            int u = stack[--top];

            tin[u] = timer++;
            order[size++] = u;

            baseMask[u] = (u == 0 ? 0 : baseMask[parent[u]])
                    ^ (1 << (chars[u] - 'a'));

            for (int v : adj[u]) {
                if (v == parent[u]) {
                    continue;
                }

                parent[v] = u;
                depth[v] = depth[u] + 1;
                stack[top++] = v;
            }
        }

        int[] subtree = new int[n];
        Arrays.fill(subtree, 1);

        for (int i = n - 1; i > 0; i--) {
            int u = order[i];
            subtree[parent[u]] += subtree[u];
        }

        for (int i = 0; i < n; i++) {
            tout[i] = tin[i] + subtree[i] - 1;
        }

        for (int i = 0; i < n; i++) {
            up[0][i] = parent[i];
        }

        for (int j = 1; j < log; j++) {
            for (int i = 0; i < n; i++) {
                up[j][i] = up[j - 1][up[j - 1][i]];
            }
        }

        bit = new Fenwick(n);
        List<Boolean> result = new ArrayList<>();

        for (String q : queries) {
            String[] parts = q.split(" ");

            if (parts[0].equals("update")) {
                int u = Integer.parseInt(parts[1]);
                char c = parts[2].charAt(0);

                int delta = (1 << (chars[u] - 'a'))
                        ^ (1 << (c - 'a'));

                if (delta != 0) {
                    bit.rangeXor(tin[u], tout[u], delta);
                    chars[u] = c;
                }
            } else {
                int u = Integer.parseInt(parts[1]);
                int v = Integer.parseInt(parts[2]);
                int lca = findLca(u, v);

                int mask = getMask(u) ^ getMask(v)
                        ^ (1 << (chars[lca] - 'a'));

                result.add(Integer.bitCount(mask) <= 1);
            }
        }

        return result;
    }

    private int getMask(int u) {
        return baseMask[u] ^ bit.query(tin[u]);
    }

    private int findLca(int u, int v) {
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        int diff = depth[u] - depth[v];

        for (int j = 0; j < log; j++) {
            if ((diff & (1 << j)) != 0) {
                u = up[j][u];
            }
        }

        if (u == v) {
            return u;
        }

        for (int j = log - 1; j >= 0; j--) {
            if (up[j][u] != up[j][v]) {
                u = up[j][u];
                v = up[j][v];
            }
        }

        return parent[u];
    }

    private static class Fenwick {
        int[] tree;
        int n;

        Fenwick(int n) {
            this.n = n;
            tree = new int[n + 2];
        }

        void add(int idx, int value) {
            for (int i = idx + 1; i <= n; i += i & -i) {
                tree[i] ^= value;
            }
        }

        void rangeXor(int left, int right, int value) {
            add(left, value);

            if (right + 1 < n) {
                add(right + 1, value);
            }
        }

        int query(int idx) {
            int result = 0;

            for (int i = idx + 1; i > 0; i -= i & -i) {
                result ^= tree[i];
            }

            return result;
        }
    }
}