
import java.util.Arrays;

class Solution {
    public long maxAlternatingSum(int[] nums) {
        long[] squares = new long[nums.length];

        for (int i = 0; i < nums.length; i++) {
            squares[i] = 1L * nums[i] * nums[i];
        }

        Arrays.sort(squares);

        long ans = 0;
        int n = squares.length;
        int half = n / 2;

        for (int i = 0; i < half; i++) {
            ans += squares[n - 1 - i];
            ans -= squares[i];
        }

        if (n % 2 == 1) {
            ans += squares[half];
        }

        return ans;
    }
}
