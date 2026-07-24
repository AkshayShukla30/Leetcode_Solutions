class Solution {
    public int uniqueXorTriplets(int[] nums) {
        final int MAX = 2048;

        boolean[][] dp = new boolean[4][MAX];
        dp[0][0] = true;

        for (int val : nums) {
            boolean[][] ndp = new boolean[4][MAX];

            for (int c = 0; c <= 3; c++) {
                for (int x = 0; x < MAX; x++) {
                    if (!dp[c][x]) continue;

                    ndp[c][x] = true;

                    if (c + 1 <= 3)
                        ndp[c + 1][x ^ val] = true;

                    if (c + 2 <= 3)
                        ndp[c + 2][x] = true;

                    if (c + 3 <= 3)
                        ndp[c + 3][x ^ val] = true;
                }
            }

            dp = ndp;
        }

        int ans = 0;
        for (boolean ok : dp[3])
            if (ok) ans++;

        return ans;
    }
}