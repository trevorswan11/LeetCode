from typing import List

class Solution:
    def containsDuplicate(self, nums: List[int]) -> bool:
        return len(nums) is not len(set(nums))
    
print(Solution().containsDuplicate([ 1, 2, 3, 4, 1 ]))