
import java.util.*;

class Solution {
    public int minimumChanges(String s, int k) {
        int n = s.length();
        int INF = 1_000_000_000;

        int[][] cost = new int[n][n];

        for (int[] row : cost) {
            Arrays.fill(row, INF);
        }

        for (int len = 2; len <= n; len++) {
            for (int d = 1; d < len; d++) {
                if (len % d != 0) continue;

                for (int l = 0; l + len <= n; l++) {
                    int changes = 0;

                    for (int r = 0; r < d; r++) {
                        int size = (len - 1 - r) / d + 1;

                        for (int q = 0; q < size / 2; q++) {
                            int left = l + r + q * d;
                            int right = l + r + (size - 1 - q) * d;

                            if (s.charAt(left) != s.charAt(right)) {
                                changes++;
                            }
                        }
                    }

                    cost[l][l + len - 1] =
                        Math.min(cost[l][l + len - 1], changes);
                }
            }
        }

        int[][] dp = new int[k + 1][n + 1];

        for (int[] row : dp) {
            Arrays.fill(row, INF);
        }

        dp[0][0] = 0;

        for (int p = 1; p <= k; p++) {
            for (int i = 2 * p; i <= n; i++) {
                for (int j = 2 * (p - 1); j <= i - 2; j++) {
                    dp[p][i] = Math.min(
                        dp[p][i],
                        dp[p - 1][j] + cost[j][i - 1]
                    );
                }
            }
        }

        return dp[k][n];
    }
}
