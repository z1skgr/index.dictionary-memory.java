# File Processing
> A program that accepts one or more text files and creates a data stream on the disk that
answers specific questions related to the words. <br> <br>


__Data Stream__
* Index
* Dictionary



## Table of contents
* [General Info](#general-information)
* [Technologies Used](#technologies-used)
* [Setup](#setup)
* [Acknowledgements](#acknowledgements)

## General Information
Τhe program performs the following operations:
* Construction of the data structure in the main memory
    * Structure of pairs of strings and number
* Construction of archive structure 
    * Sequence of pages(128 bytes) of pair string - integer
* Search the data structure
    * Binary search[^1] to the dictionary and calculation of costs per page[^2][^3] and disk[^4] <br>


```mermaid
graph TD;

    A[_Dictionary_ <br><br> Statue 2 <br> Infinite 1 <br> Morning 30 <br> ... <br>]-->|1|B[a.txt 2 b.txt 4];
    A[_Dictionary_ <br><br> Statue 2 <br> Infinite 1 <br> Morning 30 <br> ... <br>]-->|2|C[c.txt 3 a.txt 5];
    A[_Dictionary_ <br><br> Statue 2 <br> Infinite 1 <br> Morning 30 <br> ... <br>]-->|43|D[c.txt 3 d.txt 40];

```


The dictionary contains all the words in the texts accompanied by a number. Each word points to the index. Each page in the dictionary contains 5 pairs.
The number specifies the page in the index that corresponds to the word. <br>

The graph shows the connection between the files. In the graph, we see that the word infinite is in the file a.txt in position 2bytes from the beginning of the file.

The index is a file whose page (128 bytes) stores pairs of the format __(filename – bytes, locations from the beginning of the text)__. Each page in the index contains 4 pairs.
The pages link to each other when we have redundancy in a word. <br>

```mermaid
graph TD;

    A[c.txt 3 b.txt 5 .... 3]-->|3|B[b.txt 18 c.txt 120 .... 6];
    B[b.txt 18 c.txt 120 .... 6]-->|6|C[b.txt 180 c.txt 10 .... NULL]


```

## Technologies Used
Java Integrated Development Environment (Eclipse IDE)



## Setup
To run this project, import project to IDE workshop.
Project contains samples of .txt files.
The files have to follow the format that mentioned in [General Info](#general-information).

## Acknowledgements
- This project was created for the requirements of the lesson Data Structures.


[^3]: Search inside the page does not count disk access.
[^2]: Read page costs one disk access.
[^4]: Every reading page in the index also costs an access to the disk. 
[^1]: http://interactivepython.org/runestone/static/pythonds/SortSearch/TheBinarySearch.html
