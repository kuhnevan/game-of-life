/*
 * This class represents Conway's Game of Life
 * It includes a "board," which holds the cells,
 * a kill list, which keeps track of which cells need to be killed
 * in the next generation, a reanimate list, for keeping track of cells
 * that need to be reanimated in the next generation, 
 * and implements all four rules of the game.
 * Cells are represented as 1's and 0's: 1's are living cells, 0's are dead cells
 *
 * Original author: Evan Kuhn, 2/23/2018
 */

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;


public class GameOfLife { 
	
	// Global Variables ==========================================
	// The "board" that holds the cells
	private  int board[][];
	
	// The lists of cells to kill and cells to reanimate
	// Points are used to hold the row and column of each cell
	// The points DO NOT represent x, y values.
	// The points DO REPRESENT row, column values in an array
	private ArrayList<Point> kill_list, reanimate_list;
	
	// Constructors ==============================================
	public GameOfLife() {
		// Initialize the board
		board = new int[5][5];
		
		// Initialize the random variable that will populate the board
		Random rand = new Random();
		
		// Populate the board with 0's and 1's (dead cells and living cells)
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				board[i][j] = rand.nextInt(2);
			}
		}
		
		//Initialize the kill and reanimate lists
		kill_list = new ArrayList<Point>();
		reanimate_list = new ArrayList<Point>();
	}
	
	public GameOfLife(int[][] board) {
		this.board = board;
		
		//Initialize the kill and reanimate lists
		kill_list = new ArrayList<Point>();
		reanimate_list = new ArrayList<Point>();
	}
	
	// Getters ========================================================
	/*
	 * Returns the "game" board containing the cells
	 * @returns the board containing cells
	 */
	public int[][] getBoard() {
		return board;
	}
	
	/*
	 * Returns the list containing the locations of all cells that 
	 * should be killed
	 * @returns the kill list
	 */
	public ArrayList<Point> getKillList() {
		return kill_list;
	}
	
	/*
	 * Returns the list containing the locations of all cells that 
	 * should be reanimated
	 * @returns the reanimate list
	 */
	public ArrayList<Point> getReanimateList() {
		return reanimate_list;
	}
	
	// Methods ==================================================
	/*
	 * Updates the board to the "next generation" by killing the cells in the
	 * kill list and reanimating the cells on the reanimate list
	 */
	public void updateBoard() {
		// Note: x represents the row, y represents the column
		// They are NOT coordinates
		calculateNextState();
		for (Point p : kill_list) {
			kill(p.x, p.y);
		}
		for (Point p : reanimate_list) {
			reanimate(p.x, p.y);
		}
		
		// Clear the lists
		kill_list.clear();
		reanimate_list.clear();
	}
	
	/*
	 * Checks every cell, dead or alive, to see whether it should be alive
	 * or dead in the next generation.
	 * If it should be dead but isn't, it's added to the kill list
	 * If it should be alive but isn't, it's added to the reanimate list
	 */
	public void calculateNextState() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				int value = board[i][j];
				if (value == 0 && cellShouldReanimate(i, j)) {
					addToReanimateList(i, j);
				}
				if (value == 1 && cellShouldDie(i, j)) {
					addToKillList(i, j);
				}
			}
		}
	}
	
	/*
	 * Determines whether a cell should be dead
	 * @param row: the row, or first dimension, that the cell is in
	 * @param column: the column, or second dimension, that the cell is in
	 * @returns whether the cell should die
	 */
	public boolean cellShouldDie(int row, int col) {
		int num_neighbors = numberOfNeighbors(row, col);
		return num_neighbors != 2 && num_neighbors != 3;
	}
	
	/*
	 * Determines whether a cell should be alive
	 * @param row: the row, or first dimension, that the cell is in
	 * @param column: the column, or second dimension, that the cell is in
	 */
	public boolean cellShouldReanimate(int row, int col) {
		int num_neighbors = numberOfNeighbors(row, col);
		return num_neighbors == 3;
	}
	
	/*
	 * Gets the number of cells bordering the given cell
	 * @param row: the row, or first dimension, that the cell is in
	 * @param column: the column, or second dimension, that the cell is in
	 */
	public int numberOfNeighbors(int row, int column) {
		int num_of_neighbors = 0;
		
		int row_start = row - 1;
		int col_start = column - 1;
		int row_stop = row + 2;
		int col_stop = column + 2;
		
		/*
		if (row == 0 ) {
			row_start = row;
		}
		
		if (row >= board.length - 1) {
			row_stop = row;
		}
		
		if (column == 0 ) {
			col_start = column;
		}
		if (column >= board[0].length - 1) {
			col_stop = column;
		}*/
		
		for (int i = row_start; i < row_stop; i++) {
			for (int j = col_start; j < col_stop; j++) {
				if (accessBoard(i, j) == 1 && (i != row || j != column)) {
					num_of_neighbors++;
				}
			}
		}
		return num_of_neighbors;
	}
	
	/*
	 * Adds the cell to the list of cells to be killed
	 * @param row: the row on the board that the cell is contained in
	 * @param column: the column on the board that the cell is contained in
	 */
	public void addToKillList(int row, int column) {
		kill_list.add(new Point(row, column));
	}
	
	/*
	 * Adds the cell to the list of cells to be reanimated
	 * @param row: the row on the board that the cell is contained in
	 * @param column: the column on the board that the cell is contained in
	 */
	public void addToReanimateList(int row, int column) {
		reanimate_list.add(new Point(row, column));
	}
	
	/*
	 * Kills the given cell
	 * @param row: the row, or first dimension, that the cell is in
	 * @param column: the column, or second dimension, that the cell is in
	 */
	public void kill(int row, int column) {
		if (row < board.length && column < board[0].length) {
			board[row][column] = 0;
		} else {
			System.out.println("ARRAY OUT OF BOUNDS");
		}
	}
	
	/*
	 * Reanimates the given cell
	 * @param row: the row, or first dimension, that the cell is in
	 * @param column: the column, or second dimension, that the cell is in
	 */
	public void reanimate(int row, int column) {
		if (row < board.length && column < board[0].length) {
			board[row][column] = 1;
		} else {
			System.out.println("ARRAY OUT OF BOUNDS");
		}
	}
	
	/*
	 * An accessor method that lets the board behave like a circular array
	 * @param row: the row of the array
	 * @param column: the column of the array
	 * @returns the element at the given location
	 */
	public int accessBoard(int row, int column) {
		if (row < 0) {
			row  += board.length;
		}
		if (row >= board.length) {
			row -= board.length;
		}
		if (column < 0) {
			column  += board[0].length;
		}
		if (column >= board[0].length) {
			column -= board.length;
		}
		return board[row][column];
	}
}
