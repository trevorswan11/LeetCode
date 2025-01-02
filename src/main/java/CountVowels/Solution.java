package CountVowels;

import java.util.Arrays;
import java.util.HashSet;

public class Solution {
    public int[] vowelStrings(String[] words, int[][] queries) {
        HashSet<Character> set = new HashSet<>();
        int[] counter = new int[words.length];
        int[] result = new int[queries.length];

        set.addAll(Arrays.asList(new Character[] {'a', 'e', 'i', 'o', 'u'}));
        for (int i = 0; i < words.length; i++)
            counter[i] = (set.contains(first(words[i])) && set.contains(last(words[i]))) ? 1 : 0;
        for (int i = 0; i < queries.length; i++) {
            int current = 0;
            for (int j = queries[i][0]; j <= queries[i][1]; j++)
                current += counter[j];
            result[i] = current;
        }
        return result;
    }

    private char first(String s) { return s.charAt(0); }
    private char last(String s) { return s.charAt(s.length() - 1); }

    public static void main(String[] args) {
        Solution s = new Solution();
        String[] words;
        int[][] queries;

        words = new String[] {"aba","bcb","ece","aa","e"};
        queries = new int[][] {{0,2},{1,4},{1,1}};

        System.out.println(Arrays.toString(s.vowelStrings(words, queries)));
    }
}