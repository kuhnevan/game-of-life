# Game of Life [![Build Status](https://travis-ci.org/kuhnevan/game-of-life.svg?branch=master)](https://travis-ci.org/kuhnevan/game-of-life)
This program is an implementation of John Conway's Game of Life (https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life)  
It simulates a population of "cells" and determines whether each cell living cell should live or die, and whether new cells should be born.

## The Rules
1. Any live cell with fewer than two live neighbors dies, as if caused by under population.
2. Any live cell with two or three live neighbors lives on to the next generation.
3. Any live cell with more than three live neighbors dies, as if by overpopulation.
4. Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.

## Screenshots
![alt text](ReadmeImages/initial_screen.png "The initial screen of the program")
![alt text](ReadmeImages/select_cells.png "Click on a cell to mark it as alive. Click on it again to mark it as not living. Click run to begin the simulation.")
![alt text](ReadmeImages/running_program.png "Sit back and enjoy your masterpiece. Click anywhere in the window to create a new grid.")

## Note
- Black represents living cells
- Entering over 50 on the width of the grid will make it hard or impossible to see whether you have selected a space on the grid
  
