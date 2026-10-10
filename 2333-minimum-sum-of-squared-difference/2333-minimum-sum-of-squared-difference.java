class Solution {
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        int n = nums1.length;
        int[] diff = new int[n];
        int max = 0;
        long total = 0;

        for (int i = 0; i < n; i++) {
            diff[i] = Math.abs(nums1[i] - nums2[i]);
            max = Math.max(max, diff[i]);
            total += diff[i];
        }

        long k = (long) k1 + k2;

        if (total <= k) {
            return 0L;
        }

        int low = 0, high = max;

        while (low < high) {
            int mid = low + (high - low) / 2;
            long operations = 0;

            for (int d : diff) {
                if (d > mid) {
                    operations += d - mid;
                }
            }

            if (operations <= k) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        int level = low;
        long operations = 0;
        long ans = 0;

        for (int d : diff) {
            int reduced = Math.min(d, level);
            ans += (long) reduced * reduced;

            if (d > level) {
                operations += d - level;
            }
        }

        long remaining = k - operations;
        ans -= remaining * (2L * level - 1);

        return ans;
    }
}