
import java.util.*;

class Solution {
    public int sortableIntegers(int[] nums) {
        int n = nums.length;
        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        int ans = 0;

        for (int k = 1; k <= n; k++) {
            if (n % k != 0) {
                continue;
            }

            boolean valid = true;

            for (int start = 0; start < n && valid; start += k) {
                int descents = 0;

                for (int j = 0; j < k; j++) {
                    int curr = nums[start + j];
                    int next = nums[start + (j + 1) % k];

                    if (curr > next) {
                        descents++;
                    }
                }

                if (descents > 1) {
                    valid = false;
                }

                int min = Integer.MAX_VALUE;
                int max = Integer.MIN_VALUE;

                for (int j = 0; j < k; j++) {
                    min = Math.min(min, nums[start + j]);
                    max = Math.max(max, nums[start + j]);
                }

                int sortedMin = sorted[start];
                int sortedMax = sorted[start + k - 1];

                if (min != sortedMin || max != sortedMax) {
                    valid = false;
                }
            }

            if (valid) {
                ans += k;
            }
        }

        return ans;
    }
}
