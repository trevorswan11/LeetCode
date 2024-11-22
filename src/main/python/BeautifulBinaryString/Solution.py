class Solution:
    def minChanges(self, s: str) -> int:
        count = i = 0
        while i < len(s) - 1:
            if s[i] != s[i + 1]: count += 1
            i += 2
        return count
    def oneLiner(self, s: str) -> int:
        return sum(s[i] != s[i + 1] for i in range(0, len(s) - 1, 2))

print(Solution().minChanges("1001"))
print(Solution().oneLiner("1001"))
print(Solution().minChanges("10"))
print(Solution().oneLiner("10"))
print(Solution().minChanges("0000"))
print(Solution().oneLiner("0000"))