import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class GameOfLifeTest {
	
	GameOfLife life;
	int board[][];
	
	@Before
	public void setup() {
		board = new int[10][10];
		board[2][0] = 1;
		
		life = new GameOfLife(board);
	}
	
	@Test
	public void testCalculateNextSate() {
		life.calculateNextState();
		assertTrue(life.getKillList().size() == 1 && life.getReanimateList().size() == 0);
	}
	
	@Test
	public void testUpdateBoard() {
		board = new int[10][10];
		board[2][0] = 1;
		board[5][1] = 1;
		board[5][2] = 1;
		board[5][3] = 1;
		life = new GameOfLife(board);
		
		for (int i : board[5]) {
			System.out.print(i + ", ");
		}
		System.out.println();
		
		life.updateBoard();
		
		System.out.println(life.getBoard()[6][2]);
		System.out.println(life.numberOfNeighbors(6, 2));
		for (int i : life.getBoard()[5]) {
			System.out.print(i + ", ");
		}
		System.out.println();
		assertTrue(life.getBoard()[6][2] == 1); 
	}

	@Test
	public void testKill() {
		life.kill(2, 0);
		assertTrue(life.getBoard()[2][0] == 0);
	}
	
	@Test
	public void testNumberOfNeighbors() {
		board = new int[10][10];
		board[2][0] = 1;
		board[5][1] = 1;
		board[5][2] = 1;
		board[5][3] = 1;
		life = new GameOfLife(board);
		assertTrue(life.numberOfNeighbors(6, 2) == 3);
	}
	
	@Test
	public void testCellShouldDie() {
		assertTrue(life.cellShouldDie(2, 0));
	}
	
	@Test
	public void testCellShouldReanimate() {
		assertTrue(!life.cellShouldReanimate(1, 0));
	}
	
	@Test
	public void testEmptyConstructor() {
		life = new GameOfLife();
		boolean containsOne = false;
		for (int i[] : life.getBoard()) {
			for (int j : i) {
				if (j == 1) {
					containsOne = true;
					break;
				}
			}
		}
		assertTrue(containsOne);
	}
	
	@Test
	public void testAccessBoard() {
		assertTrue(life.accessBoard(2, 0) == 0);
	}
	
	@After
	public void tearDown() {
		life = null;
	}

}
