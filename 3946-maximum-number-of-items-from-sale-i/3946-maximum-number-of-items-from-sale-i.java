
import java.util.*;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        int[][] data = new int[n][2];

        for (int i = 0; i < n; i++) {
            int gain = 1;

            for (int j = 0; j < n; j++) {
                if (i != j && items[j][0] % items[i][0] == 0) {
                    gain++;
                }
            }

            data[i][0] = items[i][1];
            data[i][1] = gain;
        }

        Arrays.sort(data, (a, b) -> Integer.compare(b[0], a[0]));

        int NEG = -1_000_000_000;
        int[] dp = new int[budget + 1];
        Arrays.fill(dp, NEG);
        dp[0] = 0;

        int ans = 0;
        int i = 0;

        while (i < n) {
            int price = data[i][0];
            int j = i;

            while (j < n && data[j][0] == price) {
                j++;
            }

            int[] withP = new int[budget + 1];
            Arrays.fill(withP, NEG);

            for (int k = i; k < j; k++) {
                int gain = data[k][1];

                for (int c = budget; c >= price; c--) {
                    if (dp[c - price] != NEG) {
                        withP[c] = Math.max(withP[c], dp[c - price] + gain);
                    }

                    if (withP[c - price] != NEG) {
                        withP[c] = Math.max(withP[c], withP[c - price] + gain);
                    }
                }
            }

            for (int c = price; c <= budget; c++) {
                if (withP[c - price] != NEG) {
                    withP[c] = Math.max(withP[c], withP[c - price] + 1);
                }
            }

            for (int c = 0; c <= budget; c++) {
                ans = Math.max(ans, withP[c]);
            }

            for (int k = i; k < j; k++) {
                int cost = data[k][0];
                int gain = data[k][1];

                for (int c = budget; c >= cost; c--) {
                    if (dp[c - cost] != NEG) {
                        dp[c] = Math.max(dp[c], dp[c - cost] + gain);
                    }
                }
            }

            i = j;
        }

        return ans;
    }
}
