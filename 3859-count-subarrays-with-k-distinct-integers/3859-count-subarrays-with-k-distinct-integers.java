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

        return solve(k) - solve(k + 1);
    }

    private long solve(int limit) {
        Map<Integer, Integer> map = new HashMap<>();

        long ans = 0;
        int left = 0;
        int valid = 0;

        for (int x : nums) {

            int count = map.getOrDefault(x, 0) + 1;
            map.put(x, count);

            if (count == m) {
                valid++;
            }

            while (map.size() >= limit && valid >= k) {
                int y = nums[left++];

                int c = map.get(y) - 1;

                if (c == m - 1) {
                    valid--;
                }

                if (c == 0) {
                    map.remove(y);
                } else {
                    map.put(y, c);
                }
            }

   ans += left;
        }

        return ans;
    }
}