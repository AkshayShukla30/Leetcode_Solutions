class Solution {
    public boolean uniformArray(int[] nums1) {
        int smallestOdd = Integer.MAX_VALUE;

        for (int x : nums1) {
            if (x % 2 == 1) {
                smallestOdd = Math.min(smallestOdd, x);
            }
        }

        if (smallestOdd == Integer.MAX_VALUE) {
            return true;
        }

        for (int x : nums1) {
            if (x % 2 == 0 && x <= smallestOdd) {
                return false;
            }
        }

        return true;
    }
}