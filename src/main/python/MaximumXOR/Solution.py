from itertools import accumulate
from operator import xor
from typing import List

class Solution:
    def getMaximumXor(self, nums: List[int], maximumBit: int) -> List[int]:         # did not write myself
        return [*accumulate([2**maximumBit-1]+nums,xor)][:0:-1]
    
print(Solution().getMaximumXor([0,1,1,3], 2))
print(Solution().getMaximumXor([2,3,4,7], 3))
print(Solution().getMaximumXor([0,1,2,2,5,7], 3))