import java.util.*;

class Solution {

    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length;
        int n = classroom[0].length();

        int[][] litterId = new int[m][n];
        for (int[] row : litterId) {
            Arrays.fill(row, -1);
        }

        int startRow = 0, startCol = 0;
        int litterCount = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char ch = classroom[i].charAt(j);

                if (ch == 'S') {
                    startRow = i;
                    startCol = j;
                } else if (ch == 'L') {
                    litterId[i][j] = litterCount++;
                }
            }
        }

        if (litterCount == 0) return 0;

        int totalMask = 1 << litterCount;
        int allMask = totalMask - 1;

        // best[r][c][mask] = maximum remaining energy
        int[][][] best = new int[m][n][totalMask];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(best[i][j], -1);
            }
        }

        Queue<int[]> queue = new ArrayDeque<>();

        queue.offer(new int[]{startRow, startCol, 0, energy});
        best[startRow][startCol][0] = energy;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        int moves = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            while (size-- > 0) {
                int[] curr = queue.poll();

                int r = curr[0];
                int c = curr[1];
                int mask = curr[2];
                int remainingEnergy = curr[3];

                if (mask == allMask) return moves;

                if (remainingEnergy == 0) continue;

                for (int d = 0; d < 4; d++) {
                    int nr = r + dr[d];
                    int nc = c + dc[d];

                    if (nr < 0 || nr >= m || nc < 0 || nc >= n) continue;
                    if (classroom[nr].charAt(nc) == 'X') continue;

                    int newEnergy = remainingEnergy - 1;
                    int newMask = mask;
                    char cell = classroom[nr].charAt(nc);

                    if (cell == 'L') {
                        newMask |= (1 << litterId[nr][nc]);
                    }

                    if (cell == 'R') {
                        newEnergy = energy;
                    }

                    if (newEnergy > best[nr][nc][newMask]) {
                        best[nr][nc][newMask] = newEnergy;
                        queue.offer(new int[]{nr, nc, newMask, newEnergy});
                    }
                }
            }

            moves++;
        }

        return -1;
    }
}