from typing import Optional

class ListNode:
     def __init__(self, val=0, next=None):
         self.val = val
         self.next = next
         
class Solution:
    def isPalindromeSLOW(self, head: Optional[ListNode]) -> bool:
        stack = []
        queue = []
        ptr = head
        if ptr is None or ptr.next is None:
            return True
        while ptr is not None:
            stack.append(ptr.val)
            queue.append(ptr.val)
            ptr = ptr.next
        for i in range(len(stack)):
            if stack.pop() != queue.pop(0):
                return False
        return True
    def isPalindrome(self, head: Optional[ListNode]) -> bool:
        palindrome, ptr = [], head
        while ptr: palindrome.append(ptr.val); ptr = ptr.next
        return palindrome == palindrome[::-1]
    
test1 = ListNode(1, ListNode(2, ListNode(2, ListNode(1))))
print(Solution().isPalindrome(test1))
test2 = ListNode(1, ListNode(2))
print(Solution().isPalindrome(test2))