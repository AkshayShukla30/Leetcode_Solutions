class Solution {
    static final int MOD = 1_000_000_007;

    public int uniquePaths(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        long[][] fromLeft = new long[m][n];
        long[][] fromTop = new long[m][n];

        // Robot starts at (0, 0)
        // Start cell is guaranteed to be empty.
        fromLeft[0][0] = 1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                long left = fromLeft[i][j];
                long top = fromTop[i][j];

                if (grid[i][j] == 1) {

                    // Entered mirror from left: reflect downward
                    if (i + 1 < m) {
                        fromTop[i + 1][j] =
                            (fromTop[i + 1][j] + left) % MOD;
                    }

                    // Entered mirror from top: reflect rightward
                    if (j + 1 < n) {
                        fromLeft[i][j + 1] =
                            (fromLeft[i][j + 1] + top) % MOD;
                    }

                } else {

                    // Empty cell: robot can choose right or down
                    long ways = (left + top) % MOD;

                    if (i == 0 && j == 0) {
                        ways = 1;
                    }

                    // Move right
                    if (j + 1 < n) {
                        fromLeft[i][j + 1] =
                            (fromLeft[i][j + 1] + ways) % MOD;
                    }

                    // Move down
                    if (i + 1 < m) {
                        fromTop[i + 1][j] =
                            (fromTop[i + 1][j] + ways) % MOD;
                    }
                }
            }
        }

        return (int) ((fromLeft[m - 1][n - 1]
                     + fromTop[m - 1][n - 1]) % MOD);
    }
}