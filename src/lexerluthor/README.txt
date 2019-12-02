Project #1: Lexer. A2 Lexicon Token Output Format.
CECS 444 Friday Section 1 PM

Team Name: Code Talkers
Members: 
Ruben Baerga ID#010366978
Eric Aguirre ID #010366978

For this project, we used the NetBeans IDE Version 8.2.
"lexerLuthor" is the name of the project package.

Our solution includes one source file appropriately named LexerLuthor.java;
which has been tested to accept and create output near exactly to the
specs outlined in the assignment description and shown in the sample outputs 
given therein. Upon running it; you're prompted to enter the name of a file or 
file path to be lexed. We have NOT implemented error correction in our solution,
so please be merciful and don't break it with incorrect input! Our output also
shows the optional value output for int and float tokens.

The peek() method was written by Eric, with some slight modification by Ruben.
The printTokenInfo method was written by Ruben.
The id() method was written by Eric.
We worked together on writing the main method; of course.

We've also included 3 additional .txt files named sample1.txt, sample2.txt and 
sample3.txt which are equivalent to the a2-sample-1.acod, a2-sample-2.acod and
a2-sample-3.acod sample files respectively; shown on the assignment sheet. These
are the files with which our lexer program has been tested to match the sample 
outputs shown for them. It's here that Eric and I would like to point out an
exception: in lines 4 and 13 in the sample output given for a2-sample-3.acod;
there are MISTAKES!!! The fourth line shows the print token as an identifier 
(token ID 2) when it should be a keyword (token ID 23) and the 13th line shows
the int keyword as being token ID 15 when it ought to be 16. Our output 
correctly shows these changes.
