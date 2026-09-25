import java.util.*;

class Solution {
    String s;
    int i;

    public List<String> braceExpansionII(String expression) {
        s = expression;
        i = 0;

        Set<String> set = parse();

        List<String> ans = new ArrayList<>(set);
        Collections.sort(ans);

        return ans;
    }

    Set<String> parse() {
        Set<String> res = new HashSet<>();

        while (i < s.length() && s.charAt(i) != '}') {
            res.addAll(parseTerm());

            if (i < s.length() && s.charAt(i) == ',') {
                i++;
            }
        }

        return res;
    }

    Set<String> parseTerm() {
        Set<String> res = new HashSet<>();
        res.add("");

        while (i < s.length()
                && s.charAt(i) != ','
                && s.charAt(i) != '}') {

            Set<String> next = parseFactor();
            Set<String> temp = new HashSet<>();

            for (String a : res) {
                for (String b : next) {
                    temp.add(a + b);
                }
            }

            res = temp;
        }

        return res;
    }

    Set<String> parseFactor() {
        if (s.charAt(i) == '{') {
            i++;
            Set<String> res = parse();
            i++;
            return res;
        }

        Set<String> res = new HashSet<>();
        res.add(String.valueOf(s.charAt(i++)));
        return res;
    }
}