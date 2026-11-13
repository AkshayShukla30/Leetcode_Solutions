class Solution {
    public long countNonDecreasingSubarrays(int[] nums, int k) {
        int n = nums.length;
        int[] stack = new int[n];
        int[] next = new int[n];
        int top = -1;

        for (int i = n - 1; i >= 0; i--) {
            while (top >= 0 && nums[stack[top]] <= nums[i]) {
                top--;
            }
            next[i] = top == -1 ? n : stack[top];
            stack[++top] = i;
        }

        int LOG = 1;
        while ((1 << LOG) <= n) LOG++;

        int[][] jump = new int[LOG][n + 1];
        long[][] sum = new long[LOG][n + 1];

        for (int i = 0; i <= n; i++) {
            jump[0][i] = i;
        }

        for (int i = 0; i < n; i++) {
            jump[0][i] = next[i];
            sum[0][i] = (long) nums[i] * (next[i] - i);
        }

        for (int j = 1; j < LOG; j++) {
            for (int i = 0; i <= n; i++) {
                int mid = jump[j - 1][i];
                jump[j][i] = jump[j - 1][mid];
                sum[j][i] = sum[j - 1][i] + sum[j - 1][mid];
            }
        }

        long[] prefix = new long[n + 1];

        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }

        long ans = 0;

        for (int r = 0; r < n; r++) {
            int low = 0, high = r;

            while (low < high) {
                int mid = low + (high - low) / 2;
                int cur = mid;
                long maxSum = 0;

                for (int j = LOG - 1; j >= 0; j--) {
                    if (jump[j][cur] <= r) {
                        maxSum += sum[j][cur];
                        cur = jump[j][cur];
                    }
                }

                maxSum += (long) nums[cur] * (r - cur + 1);

                long cost = maxSum - (prefix[r + 1] - prefix[mid]);

                if (cost <= k) {
                    high = mid;
                } else {
                    low = mid + 1;
                }
            }

            ans += r - low + 1;
        }

        return ans;
    }
}