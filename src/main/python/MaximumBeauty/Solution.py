from typing import List


class Solution:
    def maximumBeauty(self, nums: List[int], k: int) -> int:
        flag = True
        for i in range(len(nums) - 1):
            if nums[i] != nums[i + 1]:
                flag = False
                continue
        if flag: return len(nums)
        nums.sort()
        lim = 2 ** k
        length = len(nums)
        maxx = 0
        for i in range(length):
            for j in range(i + 1, length):
                if nums[j] - nums[i] <= lim:
                    if len(nums[i::j]) > maxx:
                        maxx = len(nums[i::j])
        return maxx
    
print(Solution().maximumBeauty([4,6,1,2], 2))
print(Solution().maximumBeauty([1,1,1,1], 10))