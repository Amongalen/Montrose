# Task1
## Decryptor

There is an encryping algorithm that takes an input text file. The algorithm take each line in that file an places 
it in one random file out of *n* output files. 
Every line in he output files is preceded with a number saying which line it was in the original file.

##### Example input file
```
Some
random
text
in
a
few
lines
```

##### First output file

```
1 Some
3 text
7 lines
```


##### Second output file

```
2 random
4 in
5 a
6 few
```

A task was to write a program to decrypt files created by such algorithm. 
As an input the program takes a path to a folder with *n* encrypted files.

*n* ranges from 2 o 100

Each file may contain thousands of lines, each line may be 1000 characters long.


## Plots

There was some story behind this task but the main idea was to find the shortest series containing elements with the highest sum.
The program as an input takes amount of elements in a series and a list of those elements. As an output returns number of the first and the last elements in such subseries and sum of its elements.
Elements in a series can be both positive and negative.

##### Example series
```
1 2 1 -4 1 3 5 1 -3 1 1 1
```
##### A subseries we are looking for
```
1 3 5 1
```
##### A programs output
```
5 8 10 //subseries start at 5th element and ends at 8th element, the sum of its elements is 10.
```

