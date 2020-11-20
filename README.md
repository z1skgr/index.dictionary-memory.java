# Data Structure
 File Process - Edit Files - Intermediate 

# Description of the project
A program that accepts one or more text files and creates a data structure on the disk that answers questions such as **'find texts containing the word 'statue''** or any other word. 

# Construction of the data structure in Central Memory
The program constructs the following file structure on disk. The file structure consists of the "Dictionary" and the "Index". The file structure in the shape indicates that the word "Infinite" of the Dictionary exists on the 1st page of the Index. There is the information that the word exists in file a.txt in position 2bytes from the the beginning of the file and in the b.txt to the 4bytes location from the beginning of the file.

# Construction of File Structure 
The file page is 128bytes in size. An 128bytes buffer in the central memory starts filling with string-integer pairs from the Dictionary. When it's full the buffer (will fit 9 such pairs and will have 2 bytes left) a new page is written at the end of the file. The buffer goes empty and continue the same until the
Dictionary in file

Index is a file whose each page stores pairs of the format
(file name – bytes from the beginning of the text)

e.g. the word "Infinite" in the
shape, is accompanied in the Dictionary by the integer 2 which means that it points to the 2nd page of the Index. Page 2 in the index has the records (b.txt.4) (a.txt.2) that indicate that the word "Infinite" exists in the file "a.txt" in the 2bytes position from the beginning and in the file "b.txt" in the 4bytes position from the beginning. If there's one word
in the Dictionary then there is at least one page in the Index for it.

# Search the data structure
A project which at its entrance accepts a word and in response
returns all texts in which the word exists and the location in which it is located. This program executes binary search in file and find if a words exists in Dictionary.


# How to run
The project needs input .txt files with specific format. Some files are given in this project as inputs.

The project has been created in java eclipse.
