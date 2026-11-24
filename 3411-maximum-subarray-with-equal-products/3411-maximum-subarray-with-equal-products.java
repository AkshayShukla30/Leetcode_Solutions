
class Solution {
    public int maxLength(int[] nums) {
        int n = nums.length;
        int ans = 0;

        for (int i = 0; i < n; i++) {
            long product = 1;
            long g = 0;
            long l = 1;

            for (int j = i; j < n; j++) {
                long x = nums[j];

                product *= x;
                g = gcd(g, x);
                l = (l / gcd(l, x)) * x;

                if (product == g * l) {
                    ans = Math.max(ans, j - i + 1);
                }
            }
        }

        return ans;
    }

    private long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}
