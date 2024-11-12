from typing import List                                 # Type annotation library

class Solution:
    def thirdMax(self, nums: List[int]) -> int:
        res = list(set(nums))                           # Negative inputs are placed at the end
        res.sort()                                      # Sort returns null
        return res[-1] if len(res) < 3 else res[-3]     # Ternary for readability and saving time

print(Solution().thirdMax([3,2,1]))
print(Solution().thirdMax([1,2]))
print(Solution().thirdMax([2,2,3,1]))
print(Solution().thirdMax([-1,2,3]))
