import java.util.*;

class Solution {
    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int[] seat : reservedSeats) {
            int row = seat[0];
            int col = seat[1];

            if (col >= 2 && col <= 9) {
                map.put(row, map.getOrDefault(row, 0) | (1 << col));
            }
        }

        int ans = (n - map.size()) * 2;

        for (int mask : map.values()) {
            boolean left = (mask & 0b000000111100) == 0;
            boolean middle = (mask & 0b000011110000) == 0;
            boolean right = (mask & 0b001111000000) == 0;

            if (left && right) {
                ans += 2;
            } else if (left || middle || right) {
                ans++;
            }
        }

        return ans;
    }
}