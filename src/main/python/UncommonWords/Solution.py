from collections import defaultdict
from typing import List

class Solution:
    def uncommonFromSentences(self, s1: str, s2: str) -> List[str]:
        freq1, freq2 = defaultdict(int), defaultdict(int)
        s1, s2, res = s1.split(), s2.split(), []
        for i in range(max(len(s1), len(s2))):
            if i < len(s1):
                freq1[s1[i]] += 1
            if i < len(s2):
                freq2[s2[i]] += 1
        for i in range(max(len(s1), len(s2))):
            if i < len(s1) and (freq1[s1[i]] == 1 and not freq2[s1[i]]):
                res.append(s1[i])
            if i < len(s2) and (freq2[s2[i]] == 1 and not freq1[s2[i]]):
                res.append(s2[i])
        return res
            
        
print(Solution().uncommonFromSentences("this apple is sweet", "this apple is sour"))
print(Solution().uncommonFromSentences("apple apple", "banana"))