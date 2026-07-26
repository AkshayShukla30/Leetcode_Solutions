import java.util.HashMap;
import java.util.Map;

class Solution {

    private int[] nums;
    private int k;
    private int m;

    public long countSubarrays(int[] nums, int k, int m) {
        this.nums = nums;
        this.k = k;
        this.m = m;

        return helper(k) - helper(k + 1);
    }

    private long helper(int limit) {
        Map<Integer, Integer> freq = new HashMap<>();

        long ans = 0;
        int left = 0;
        int qualified = 0;

        for (int x : nums) {

            int count = freq.getOrDefault(x, 0) + 1;
            freq.put(x, count);

            if (count == m) {
                qualified++;
            }

            while (freq.size() >= limit && qualified >= k) {
                int y = nums[left++];

                int c = freq.get(y) - 1;

                if (c == m - 1) {
                    qualified--;
                }

                if (c == 0) {
                    freq.remove(y);
                } else {
                    freq.put(y, c);
                }
            }

            ans += left;
        }

        return ans;
    }
}