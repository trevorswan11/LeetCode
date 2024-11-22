from typing import List

class Solution:
    def largestCombinationFAIL(self, candidates: List[int]) -> int:                             # fail :(
        combinations = [[]]
        for num in candidates:
            combinations.extend([combination + [num] for combination in combinations])
        largest = {}
        l_val = 0
        for arr in combinations:
            if len(candidates) == 1 or len(arr) not in [0, 1]:
                result = arr[0]
                for val in arr[1:]:
                    result &= val
                largest[result] = arr
                l_val = result if result > l_val else l_val
        return len(largest[l_val])
    
    def largestCombination(self, candidates: List[int]) -> int:
        max_set=0
        for b in range(24):
            b_bit_set=0
            for x in candidates:
                b_bit_set+=(x>>b &1)
            max_set=max(max_set, b_bit_set)
        return max_set
    
print(Solution().largestCombination([16,17,71,62,12,24,14]))
