
import java.util.*;

class Solution {
    static final long MOD = 1_000_000_007L;

    long[] sum, sq, lazy;
    int n;

    public int sumCounts(int[] nums) {
        n = nums.length;
        sum = new long[4 * n];
        sq = new long[4 * n];
        lazy = new long[4 * n];

        int[] last = new int[100001];
        Arrays.fill(last, -1);

        long ans = 0;

        for (int i = 0; i < n; i++) {
            int x = nums[i];
            update(1, 0, n - 1, last[x] + 1, i);
            last[x] = i;
            ans = (ans + sq[1]) % MOD;
        }

        return (int) ans;
    }

    void apply(int node, int l, int r) {
        long len = r - l + 1L;
        sq[node] = (sq[node] + 2L * sum[node] + len) % MOD;
        sum[node] = (sum[node] + len) % MOD;
        lazy[node]++;
    }

    void push(int node, int l, int r) {
        if (lazy[node] == 0 || l == r) return;

        int mid = l + (r - l) / 2;
        applyLazy(node * 2, l, mid, lazy[node]);
        applyLazy(node * 2 + 1, mid + 1, r, lazy[node]);
        lazy[node] = 0;
    }

    void applyLazy(int node, int l, int r, long d) {
        long len = r - l + 1L;

        sq[node] = (sq[node] + 2L * d % MOD * sum[node] % MOD
                    + d * d % MOD * len % MOD) % MOD;
        sum[node] = (sum[node] + d * len) % MOD;
        lazy[node] = (lazy[node] + d) % MOD;
    }

    void update(int node, int l, int r, int ql, int qr) {
        if (ql > r || qr < l) return;

        if (ql <= l && r <= qr) {
            apply(node, l, r);
            return;
        }

        push(node, l, r);

        int mid = l + (r - l) / 2;
        update(node * 2, l, mid, ql, qr);
        update(node * 2 + 1, mid + 1, r, ql, qr);

        sum[node] = (sum[node * 2] + sum[node * 2 + 1]) % MOD;
        sq[node] = (sq[node * 2] + sq[node * 2 + 1]) % MOD;
    }
}
