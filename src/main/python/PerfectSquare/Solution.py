class Solution:
    def isPerfectSquare(self,num: int) -> bool:
        low, high = 1, num
        while high >= low:
            middle = (high + low) // 2
            middle_sqrd = middle * middle
            if middle_sqrd == num:
                return True
            elif middle_sqrd < num:
                low = middle + 1
            else:
                high = middle - 1
        return False

print(Solution().isPerfectSquare(16))
print(Solution().isPerfectSquare(14))
print(Solution().isPerfectSquare(23))
print(Solution().isPerfectSquare(25))
print(Solution().isPerfectSquare(4))
print(Solution().isPerfectSquare(1))

