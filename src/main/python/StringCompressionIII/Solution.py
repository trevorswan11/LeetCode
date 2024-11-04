class Solution:
    def compressedString(self, word: str) -> str:
        comp = []
        i = 0
        while i < len(word):
            char = word[i]
            count = 1
            while i + 1 < len(word) and word[i] == word[i + 1]:
                count += 1
                i += 1
            while count > 9:
                comp.append("9" + char)
                count -= 9
            if count > 0:
                comp.append(str(count) + char)
            i += 1
        return "".join(comp)

res = Solution().compressedString("abcde")
print(res)
res = Solution().compressedString("aaaaaaaaaaaaaabb")
print(res)