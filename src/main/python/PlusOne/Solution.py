from typing import List

class Solution:
    def plusOne(self, digits: List[int]) -> List[int]:
        string = ''
        for digit in digits:
            string += str(digit)
        string =  str(int(string) + 1)
        result = []
        for c in string:
            result.append(int(c))
        return result
        
print(Solution().plusOne([1,2,3]))