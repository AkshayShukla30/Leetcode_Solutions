class Solution {
    public int closedIsland(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;

        for (int i = 0; i < n; i++) {
            dfs(grid, i, 0);
            dfs(grid, i, m - 1);
        }

        for (int j = 0; j < m; j++) {
            dfs(grid, 0, j);
            dfs(grid, n - 1, j);
        }

        int count = 0;

        for (int i = 1; i < n - 1; i++) {
            for (int j = 1; j < m - 1; j++) {
                if (grid[i][j] == 0) {
                    dfs(grid, i, j);
                    count++;
                }
            }
        }

        return count;
    }

    private void dfs(int[][] grid, int r, int c) {
        int n = grid.length;
        int m = grid[0].length;

        if (r < 0 || r >= n || c < 0 || c >= m || grid[r][c] == 1) {
            return;
        }

        grid[r][c] = 1;

        dfs(grid, r - 1, c);
        dfs(grid, r + 1, c);
        dfs(grid, r, c - 1);
        dfs(grid, r, c + 1);
    }
}