class Solution {
    public String smallestPalindrome(String s) {
        int[] freq = new int[26];

        for (char ch : s.toCharArray()) {
            freq[ch - 'a']++;
        }

        StringBuilder firstHalf = new StringBuilder();
        char middle = 0;

        for (int i = 0; i < 26; i++) {
            if ((freq[i] & 1) == 1) {
                middle = (char) ('a' + i);
            }

            for (int j = 0; j < freq[i] / 2; j++) {
                firstHalf.append((char) ('a' + i));
            }
        }

        String secondHalf = new StringBuilder(firstHalf).reverse().toString();

        if (middle != 0) {
            return firstHalf.toString() + middle + secondHalf;
        }

        return firstHalf.toString() + secondHalf;
    }
}