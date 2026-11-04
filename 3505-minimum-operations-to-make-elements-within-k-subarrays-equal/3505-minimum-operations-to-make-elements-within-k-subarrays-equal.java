import java.util.*;

class Solution {
    static class Fenwick {
        int n;
        long[] tree;

        Fenwick(int n) {
            this.n = n;
            tree = new long[n + 1];
        }

        void update(int i, long val) {
            while (i <= n) {
                tree[i] += val;
                i += i & -i;
            }
        }

        long query(int i) {
            long sum = 0;
            while (i > 0) {
                sum += tree[i];
                i -= i & -i;
            }
            return sum;
        }

        int kth(long k) {
            int idx = 0;
            int bit = Integer.highestOneBit(n);

            for (; bit > 0; bit >>= 1) {
                int next = idx + bit;

                if (next <= n && tree[next] < k) {
                    idx = next;
                    k -= tree[next];
                }
            }

            return idx + 1;
        }
    }

    public long minOperations(int[] nums, int x, int k) {
        int n = nums.length;

        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        int m = 0;
        for (int i = 0; i < n; i++) {
            if (i == 0 || sorted[i] != sorted[i - 1]) {
                sorted[m++] = sorted[i];
            }
        }

        int[] compressed = new int[n];

        for (int i = 0; i < n; i++) {
            compressed[i] = Arrays.binarySearch(sorted, 0, m, nums[i]) + 1;
        }

        Fenwick countTree = new Fenwick(m);
        Fenwick sumTree = new Fenwick(m);

        long[] cost = new long[n - x + 1];

        long windowSum = 0;

        for (int i = 0; i < n; i++) {
            countTree.update(compressed[i], 1);
            sumTree.update(compressed[i], nums[i]);
            windowSum += nums[i];

            if (i >= x) {
                countTree.update(compressed[i - x], -1);
                sumTree.update(compressed[i - x], -nums[i - x]);
                windowSum -= nums[i - x];
            }

            if (i >= x - 1) {
                int medianIndex = countTree.kth((x + 1) / 2);
                long median = sorted[medianIndex - 1];

                long leftCount = countTree.query(medianIndex);
                long leftSum = sumTree.query(medianIndex);

                long rightCount = x - leftCount;
                long rightSum = windowSum - leftSum;

                cost[i - x + 1] =
                    median * leftCount - leftSum
                    + rightSum - median * rightCount;
            }
        }

        long INF = Long.MAX_VALUE / 4;

        long[][] dp = new long[n + 1][k + 1];

        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], INF);
            dp[i][0] = 0;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= k; j++) {
                dp[i][j] = dp[i - 1][j];

                if (i >= x && dp[i - x][j - 1] != INF) {
                    dp[i][j] = Math.min(
                        dp[i][j],
                        dp[i - x][j - 1] + cost[i - x]
                    );
                }
            }
        }

        return dp[n][k];
    }
}