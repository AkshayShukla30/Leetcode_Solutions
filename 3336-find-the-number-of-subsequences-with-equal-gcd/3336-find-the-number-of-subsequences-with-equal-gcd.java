class Solution {

    static final int MOD = 1_000_000_007;

    int n;
    int[] nums;

    Integer[][][] dp;

    public int subsequencePairCount(int[] nums) {

        this.n = nums.length;
        this.nums = nums;

        dp = new Integer[n + 1][201][201];

        return solve(0, 0, 0);
    }

    int solve(int idx, int g1, int g2) {

        if (idx == n) {

            if (g1 != 0 && g2 != 0 && g1 == g2)
                return 1;

            return 0;
        }

        if (dp[idx][g1][g2] != null)
            return dp[idx][g1][g2];

        long ans = 0;

        // Skip
        ans += solve(idx + 1, g1, g2);

        // Put in seq1
        int ng1 = (g1 == 0) ? nums[idx] : gcd(g1, nums[idx]);

        ans += solve(idx + 1, ng1, g2);

        // Put in seq2
        int ng2 = (g2 == 0) ? nums[idx] : gcd(g2, nums[idx]);

        ans += solve(idx + 1, g1, ng2);

        return dp[idx][g1][g2] = (int) (ans % MOD);
    }

    int gcd(int a, int b) {

        while (b != 0) {

            int t = a % b;
            a = b;
            b = t;
        }

        return a;
    }
}