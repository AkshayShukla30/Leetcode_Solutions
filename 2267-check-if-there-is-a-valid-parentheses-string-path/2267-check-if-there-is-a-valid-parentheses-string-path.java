import java.util.*;

class Solution {
    public boolean hasValidPath(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        if ((m + n - 1) % 2 == 1)
            return false;

        if (grid[0][0] == ')' || grid[m - 1][n - 1] == '(')
            return false;

        Set<Integer>[] prev = new HashSet[n];

        for (int i = 0; i < m; i++) {
            Set<Integer>[] curr = new HashSet[n];

            for (int j = 0; j < n; j++) {
                curr[j] = new HashSet<>();

                int change = grid[i][j] == '(' ? 1 : -1;

                if (i == 0 && j == 0) {
                    curr[j].add(1);
                    continue;
                }

                if (i > 0) {
                    for (int balance : prev[j]) {
                        int nb = balance + change;
                        if (nb >= 0)
                            curr[j].add(nb);
                    }
                }

                if (j > 0) {
                    for (int balance : curr[j - 1]) {
                        int nb = balance + change;
                        if (nb >= 0)
                            curr[j].add(nb);
                    }
                }
            }

            prev = curr;
        }

        return prev[n - 1].contains(0);
    }
}