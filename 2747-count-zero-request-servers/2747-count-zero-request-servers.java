import java.util.*;

class Solution {
    public int[] countServers(int n, int[][] logs, int x, int[] queries) {
        Arrays.sort(logs, (a, b) -> Integer.compare(a[1], b[1]));

        int m = queries.length;
        int[] ans = new int[m];

        Integer[] order = new Integer[m];
        for (int i = 0; i < m; i++) {
            order[i] = i;
        }

        Arrays.sort(order, (a, b) -> Integer.compare(queries[a], queries[b]));

        int[] freq = new int[n + 1];
        int active = 0;

        int left = 0;
        int right = 0;

        for (int idx : order) {
            int q = queries[idx];
            int start = q - x;

            // Add all requests whose time <= query time
            while (right < logs.length && logs[right][1] <= q) {
                int server = logs[right][0];

                if (freq[server] == 0) {
                    active++;
                }

                freq[server]++;
                right++;
            }

            // Remove requests strictly before interval start
            while (left < right && logs[left][1] < start) {
                int server = logs[left][0];

                freq[server]--;

                if (freq[server] == 0) {
                    active--;
                }

                left++;
            }

            ans[idx] = n - active;
        }

        return ans;
    }
}