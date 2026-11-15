class Solution {
    public int numOfSubarrays(int[] arr) {
        int MOD = 1_000_000_007;

        long even = 1;
        long odd = 0;
        long sum = 0;
        long ans = 0;

        for (int num : arr) {
            sum += num;

            if (sum % 2 == 0) {
                ans += odd;
                even++;
            } else {
                ans += even;
                odd++;
            }

            ans %= MOD;
        }

        return (int) ans;
    }
}