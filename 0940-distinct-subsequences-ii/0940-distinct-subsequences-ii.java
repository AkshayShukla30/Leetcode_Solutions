class Solution {
    public int distinctSubseqII(String s) {
        final long MOD = 1_000_000_007;
        long dp = 1;
        long[] last = new long[26];

        for (char c : s.toCharArray()) {
            int i = c - 'a';
            long newDp = (2 * dp - last[i] + MOD) % MOD;
            last[i] = dp;
            dp = newDp;
        }

        return (int) ((dp - 1 + MOD) % MOD);
    }
}