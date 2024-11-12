class Solution:
    def minLengthBRUTE(self, s: str) -> int:
        while 'AB' in s or 'CD' in s:
            s = s.replace('AB', '').replace('CD', '')
        return len(s)
    def minLength(self, s: str) -> int:
        stack = []
        for c in s:
            if stack and (c == 'B' and stack[-1] == 'A'):
                stack.pop()
            elif stack and (c == 'D' and stack[-1] == 'C'):
                stack.pop()
            else:
                stack.append(c)
        return len(stack)
            

print(Solution().minLength("ABFCACDB"))
print(Solution().minLength("ACBBD"))
