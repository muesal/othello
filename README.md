# Othello

Othello is a board game, where two players, *black* and *white*, in turn place stones of their respective color on a $8\times 8$ board.
The stones are black on one site and white on the other, so that they can be flipped to show the other color.
Each move consists of placing a stone on the board and then adapting the pieces on the board according to that stone.
All the opponent's stones may be turned, that lie on a straight line between the placed stone and another stone of the players color.
If no stone can be placed such that stones can be turned, the player must pass this move.
The game is over, as soon as no player can make another move, and the player with more stones on the board wins.
The board is then in a terminal position.

This repository contains a program, which evaluates all possible moves using alpha-beta search and returns the best option within a given time limit.
The implementaion is based on code from course *Artificial Intelligence - Methods and Applications (5DV181)*, Umeå University, HT 2021.


After the download the code can be compiled with *javac Othello.java* and executed with *java Othello <board> <time>*.
*board* should be a string of length 65, the first character being either 'W' or 'B' indicating whose turn it is, followed by the configuration of the board.
The board has 64 fields, each can be either empty ('E') or contain a white ('O') or black ('X') stone.
*time* is the time limit in seconds.
The program will run for no longer than the given amount of seconds.

The repository further contains a bash script, *othello.sh*, which takes the same input arguments and a flag for whether or not the code should be compiled first.
This script can be used to simulate a whole game, e.g., by using *test_code/othellostart* with two scripts like *othello.sh* and a time limit.
