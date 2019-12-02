CECS 277 Project 4: Mystery House
Ruben Baerga ID #010366978

List of files:
GaemzMastah.java
Arepl.java
IRepl.java
House.java

Bugs: None to speak of on my system and IDE. :D

Features:
The Room Cycle Generation Algorithm works by implementing a Fisher-Yates shuffle
on all of the rooms and creating a long base-six number string; which is used to
determine an exit from one room to the next. By doing this twice on half of the
rooms, then again two times on all of them to ensure there is a complete cycle;
we eliminate the necessity to do checks on every single exit of each room as 
well as the need to assign important rooms (drawing room, medkit, outside, 
zombie spawn) randomly. :D 
