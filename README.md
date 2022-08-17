# MineSweeper-GUI

<img width="493" alt="Screen Shot 2022-08-17 at 2 34 37 PM" src="https://user-images.githubusercontent.com/86317681/185217923-e5fc63ab-87f6-465e-9e00-4b769c87702a.png">

Final project for Penn's CIS120 programming class: Minesweeper. 

Languages: Java

Packages Used: java.io, javax.swing, java.awt


Major Concepts: GUI, Event Handling, Recursion, File IO

## About The Project

This repository contains 5 different files associated with the actual game: Game, Cell, GameBoard, MineSweeper, and RunMineSweeper. Each file serves a different purpose in the larger project as a whole. 

1. Game: The purpose of the Game file is to select the game to be run. In this case, it is MineSweeper.
2. Cell: With the ultimate goal of maintaining as much modularity as possible in this project, a Cell class was designed to store information about each individual cell in the gameboard. Instance fields noted how many surrounding mines there are, whether the cell was clicked, whether the cell was flagged, and more.
3. GameBoard: GameBoard contains the major GUI components of this project. It redraws the GameBoard to update the status of the MineSweeper model as the game progresses.
4. MineSweeper: This file contains the major play logic of MineSweeper. It creates the gameboard, sets the mines, counts the number of adjacent mines, checks if the game is won, and more.
5. RunMineSweeper: RunMineSweeper established the top-level frame and widgets for the GUI component of the game. It contains listeners for clicks on the GUI associated with instructions and help.

The repository also contains several pre-written test files designed to test the functionality of the game and ensure it works properly. 

A losing game looks like this: 

<img width="492" alt="Screen Shot 2022-08-17 at 2 34 50 PM" src="https://user-images.githubusercontent.com/86317681/185218085-b14f757c-8fc9-478f-85dc-37d67c569d37.png">


A winning game looks like this: 

<img width="493" alt="Screen Shot 2022-08-17 at 2 41 32 PM" src="https://user-images.githubusercontent.com/86317681/185218137-d4676dc9-0403-4060-ae6e-56de1aa366d5.png">

