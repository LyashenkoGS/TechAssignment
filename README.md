# TechAssignment

## Solution:
* Create a temporary file
* iterate over lines over the original file
* for each line iterate over characters and copy all elements to a new String
checking if the character not belong to a comment block
* append the new String to the temporary file

Suggested implementation iterates over all characters in a file so the algorithm 
time complexity is O(n) where n is number of characters in the original file

Space complexity. To perform algorithm it requires to load in memory a single line and create 
a new line. This is try for files of any size, so required RAM size is constant but the size
of a temporary file linearly depends on the original file size so the space complexity
is O(n) 
 
A more efficient and complex solution may be implemented using  java.io.RandomAccessFile 
with an in-place algorithm


