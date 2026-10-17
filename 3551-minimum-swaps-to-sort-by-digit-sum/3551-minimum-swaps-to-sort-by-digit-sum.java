
import java.util.*;

class Solution {
    public int minSwaps(int[] nums) {
        int n = nums.length;
        int[][] arr = new int[n][2];

        for (int i = 0; i < n; i++) {
            arr[i][0] = nums[i];
            arr[i][1] = digitSum(nums[i]);
        }

        Arrays.sort(arr, (a, b) -> {
            if (a[1] != b[1]) {
                return a[1] - b[1];
            }
            return a[0] - b[0];
        });

        Map<Integer, Integer> index = new HashMap<>();

        for (int i = 0; i < n; i++) {
            index.put(nums[i], i);
        }

        boolean[] visited = new boolean[n];
        int swaps = 0;

        for (int i = 0; i < n; i++) {
            if (visited[i] || index.get(arr[i][0]) == i) {
                continue;
            }

            int j = i;

            while (!visited[j]) {
                visited[j] = true;
                j = index.get(arr[j][0]);
            }

            swaps += countCycle(arr, index, i) - 1;
        }

        return swaps;
    }

    private int digitSum(int num) {
        int sum = 0;

        while (num > 0) {
            sum += num % 10;
            num /= 10;
        }

        return sum;
    }

    private int countCycle(int[][] arr, Map<Integer, Integer> index, int start) {
        int count = 0;
        int j = start;

        while (true) {
            count++;
            j = index.get(arr[j][0]);

            if (j == start) {
                break;
            }
        }

        return count;
    }
}
