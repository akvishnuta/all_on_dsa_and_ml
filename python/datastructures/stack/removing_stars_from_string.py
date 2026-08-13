'''
You are given a string s, which contains stars *.

In one operation, you can:

Choose a star in s.
Remove the closest non-star character to its left, as well as remove the star itself.
Return the string after all stars have been removed.

Note:

The input will be generated such that the operation is always possible.
It can be shown that the resulting string will always be unique.
 

Example 1:

Input: s = "leet**cod*e"
Output: "lecoe"
Explanation: Performing the removals from left to right:
- The closest character to the 1st star is 't' in "leet**cod*e". s becomes "lee*cod*e".
- The closest character to the 2nd star is 'e' in "lee*cod*e". s becomes "lecod*e".
- The closest character to the 3rd star is 'd' in "lecod*e". s becomes "lecoe".
There are no more stars, so we return "lecoe".
Example 2:

Input: s = "erase*****"
Output: ""
Explanation: The entire string is removed, so we return an empty string.
 

Constraints:

1 <= s.length <= 105
s consists of lowercase English letters and stars *.
The operation above can be performed on s.
'''

class Solution:
    def removeStars(self, s: str) -> str:
        stack = []
        for i in range(len(s)):
            if (s[i] == "*"):
                stack.pop()
            else:
                stack.append(s[i])

        return "".join(stack)

s = Solution()
print(s.removeStars("leet**cod*e"))


'''
Method 1: Using a Standard List (Simplest)The easiest way to make a stack is using a standard Python list. It uses append() to push and pop() to pop.python# Initialize stack
stack = []

# Push elements
stack.append("A")
stack.append("B")
stack.append("C")

print("Stack:", stack)          # Output: ['A', 'B', 'C']

# Peek (look at the top item)
print("Top item:", stack[-1])   # Output: 'C'

# Pop elements
print("Popped:", stack.pop())   # Output: 'C'
print("Popped:", stack.pop())   # Output: 'B'
print("Stack now:", stack)      # Output: ['A']
Use code with caution.Pros: Clean, built-in, no imports required.Cons: Memory reallocation issues. As the list grows, Python occasionally needs to find a new block of memory and copy the old elements, leading to slow O(n) amortized push speeds.Method 2: Using collections.deque (Best Performance)For production code, use deque (double-ended queue) from the built-in collections module.pythonfrom collections import deque

# Initialize stack
stack = deque()

# Push elements
stack.append("X")
stack.append("Y")

# Pop elements
print("Popped:", stack.pop())   # Output: 'Y'
Use code with caution.Pros: Highly optimized. It is implemented as a doubly-linked list, ensuring that both push and pop operations consistently take O(1) constant time.Cons: Marginally more complex syntax if trying to randomly index elements.Method 3: Using queue.LifoQueue (Thread-Safe)If your code runs across multiple concurrent threads, use LifoQueue from the built-in queue module.pythonfrom queue import LifoQueue

# Initialize stack with max size (optional)
stack = LifoQueue(maxsize=3)

# Push elements
stack.put("Data1")
stack.put("Data2")

# Check size
print("Size:", stack.qsize())   # Output: 2

# Pop elements
print("Popped:", stack.get())   # Output: 'Data2'
Use code with caution.Pros: Fully safe for multi-threaded systems.Cons: Slowest option due to the overhead of thread-locking mechanics.

'''