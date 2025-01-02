from typing import List


class Solution:
    def maximumBeauty(self, nums: List[int], k: int) -> int:
        flag = True
        for i in range(len(nums) - 1):
            if nums[i] != nums[i + 1]:
                flag = False
                break
        if flag: return len(nums)
        if k == 0: return 0
        nums.sort()
        lim = 2 * k
        length = len(nums)
        subs = []
        maxx = 0
        for start in range(length):
            for end in range(start, length):
                subs.append(nums[start:end + 1])
                
        for sub in subs:
            if (sub[-1] - sub[0]) <= lim:
                if len(sub) > maxx:
                    maxx = len(sub)
        return maxx 
    
print(Solution().maximumBeauty([4,6,1,2], 2))
print(Solution().maximumBeauty([1,1,1,1], 10))