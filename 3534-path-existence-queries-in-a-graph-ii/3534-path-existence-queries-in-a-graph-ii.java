import java.util.*;
class Solution {

    public int[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {

        int[][] arr = new int[n][2];

        for (int i = 0; i < n; i++) {
            arr[i][0] = nums[i];
            arr[i][1] = i;
        }

        Arrays.sort(arr, Comparator.comparingInt(a -> a[0]));

        int[] pos = new int[n];
        int[] comp = new int[n];

        int cid = 0;

        pos[arr[0][1]] = 0;
        comp[0] = 0;

        for (int i = 1; i < n; i++) {
            pos[arr[i][1]] = i;

            if (arr[i][0] - arr[i - 1][0] > maxDiff)
                cid++;

            comp[i] = cid;
        }

        int[] next = new int[n];
        int r = 0;

        for (int l = 0; l < n; l++) {
            while (r + 1 < n && arr[r + 1][0] - arr[l][0] <= maxDiff)
                r++;

            next[l] = r;
        }

        int LOG = 1;
        while ((1 << LOG) <= n)
            LOG++;

        int[][] up = new int[LOG][n];

        for (int i = 0; i < n; i++)
            up[0][i] = next[i];

        for (int k = 1; k < LOG; k++) {
            for (int i = 0; i < n; i++) {
                up[k][i] = up[k - 1][up[k - 1][i]];
            }
        }

        int[] ans = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            int u = pos[queries[i][0]];
            int v = pos[queries[i][1]];

            if (u == v) {
                ans[i] = 0;
                continue;
            }

            if (comp[u] != comp[v]) {
                ans[i] = -1;
                continue;
            }

            if (u > v) {
                int temp = u;
                u = v;
                v = temp;
            }

            int jumps = 0;
            int cur = u;

            for (int k = LOG - 1; k >= 0; k--) {
                if (up[k][cur] < v) {
                    cur = up[k][cur];
                    jumps += 1 << k;
                }
            }

            ans[i] = jumps + 1;
        }

        return ans;
    }
}