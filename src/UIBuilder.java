import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class UIBuilder extends JPanel implements ActionListener, MouseListener {
	
	// GUI variables
	private JFrame frame;
	private int frame_width;
	private JPanel upper_panel;
	private JPanel button_panel;
	private Timer timer;
	private JLabel label;
	private JLabel restart_instructions;
	private JTextField text_field;
	private JButton submit_button;
	private JButton run_button;
	private JMenuBar menu_bar;
	private JMenu menu;
	private JMenuItem speed1, speed2, speed3, speed4;
	private JButton buttons[][];
	
	// Non GUI variables
	private int board[][];
	private int board_width;
	private GameOfLife life;
	private boolean shouldDrawBoard;
	
	// Constants
	private final String TIMER_COMMAND = "Timer";
	private final String SUBMIT = "Submit";
	private final String RUN = "Run";
	
	/*
	 * Brings up the initial window which asks for grid dimensions
	 */
	public UIBuilder() {
		shouldDrawBoard = false;
		addMouseListener(this);
		initializeWindow();
		initializeMenu();
	}

	public static void main(String[] args) throws InterruptedException {
	    EventQueue.invokeLater( new Runnable() {
	        public void run() {
	            UIBuilder ui = new UIBuilder();
	        }
	    } );
	}
	
	/*
	 * Initialize the board
	 */
	public void initializeBoard() {
		initializeBoardWidth();
		board = new int[board_width][board_width];
		
	}
	
	/*
	 * THIS IS WHERE YOU MAKE THE PATTERNS
	 * Initializes the board with a starting pattern of cells
	 * The board is a 2-D matrix: the first dimension is the row
	 * the second dimension is the column.
	 * Living cells are 1's, dead cells are 0's
	 */
	public void fillBoard() {
		/*Pentadecathlon pattern
		board = new int[board_width][board_width];
		board[16][11] = 1;
		board[15][11] = 1;
		board[14][10] = 1;
		board[14][12] = 1;
		board[13][11] = 1;
		board[12][11] = 1;
		board[11][11] = 1;
		board[10][11] = 1;
		board[9][10] = 1;
		board[9][12] = 1;
		board[8][11] = 1;
		board[7][11] = 1;
		*/
			
		/* Tub (still)
		board = new int[10][10];
		board[4][4] = 1;
		board[5][3] = 1;
		board[5][5] = 1;
		board[6][4] = 1;
		 */
	}
	
	/*
	 * Initialize the window and the variables it will/does contain
	 */
	public void initializeWindow() {
		this.setLayout(new BorderLayout(0, 0));
		
		frame = new JFrame();
		frame_width = 700;
		frame.setBounds(100, 0, frame_width, frame_width);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(this);
		
		label = new JLabel("Enter the width of your square grid: ");
		restart_instructions = new JLabel("Click \"Run\" to start the game. Once it's running, click within the window to end it.");
		text_field = new JTextField(3);
		
		submit_button = new JButton("Submit");
		submit_button.setActionCommand(SUBMIT);
		submit_button.addActionListener(this);
		
		run_button = new JButton("Run");
		run_button.setActionCommand(RUN);
		run_button.addActionListener(this);
		
		upper_panel = new JPanel();
		upper_panel.add(label);
		upper_panel.add(text_field);
		upper_panel.add(submit_button);
		
		button_panel = new JPanel();
		
		this.add(upper_panel, BorderLayout.PAGE_START);
		this.add(button_panel, BorderLayout.CENTER);
		
		frame.setVisible(true);
	}
	
	/*
	 * Sets board length as the value indicated by the text field
	 */
	public void initializeBoardWidth() {
		board_width = Integer.parseInt(text_field.getText());
	}
	
	/*
	 * Updates the window to fit the size of the board
	 * Adds a grid of buttons that allows the user to create a pattern
	 * Removes all old buttons and labels
	 * Adds instructions/buttons for running and stopping the game
	 */
	public void updateWindow() {
		
		button_panel.setLayout(new GridLayout(board_width, board_width));
		upper_panel.removeAll();
		upper_panel.add(restart_instructions);
		upper_panel.add(run_button);
		if (frame_width % board_width != 0) {
			frame_width -= frame_width % board_width;
			frame.setSize(frame_width, frame_width);
		} else {
			updateUI();
		}
	}
	
	/*
	 * Initialize the menu and add it to the window
	 */
	public void initializeMenu() {
		menu_bar = new JMenuBar();
		menu = new JMenu("Speed Settings");
		menu_bar.add(menu);
		
		speed1 = new JMenuItem("100 (fast)");
		speed1.setActionCommand("Speed1");
		speed1.addActionListener(this);
		
		speed2 = new JMenuItem("250");
		speed2.setActionCommand("Speed2");
		speed2.addActionListener(this);
		
		speed3 = new JMenuItem("500 (slow)");
		speed3.setActionCommand("Speed3");
		speed3.addActionListener(this);
		
		speed4 = new JMenuItem("1000 (very slow)");
		speed4.setActionCommand("Speed4");
		speed4.addActionListener(this);
		
		menu.add(speed1);
		menu.add(speed2);
		menu.add(speed3);
		menu.add(speed4);
		frame.setJMenuBar(menu_bar);
	}
	
	/*
	 * Initializes the buttons according to the board_width
	 */
	public void initializeButtons() {
		buttons = new JButton[board_width][board_width];
		for (int i = 0; i < board_width; i++) {
			for (int j = 0; j < board_width; j++) {
				buttons[i][j] = new JButton("");
				buttons[i][j].setActionCommand(i + " " + j);
				buttons[i][j].addActionListener(this);
				buttons[i][j].setOpaque(true);
				buttons[i][j].setText("");
				button_panel.add(buttons[i][j]);
			}
		}
	}
	
	/*
	 * InitiLize and start the timer
	 * Timer is used to update and repaint the board at the given interval
	 */
	public void initializeTimer() {
		timer = new Timer(100, this);
		timer.setInitialDelay(1000);
		timer.setActionCommand("Timer");
		timer.start();
	}
	
	/*
	 * Displays the board
	 */
	public void paint(Graphics g) {
		// Always call super
		super.paint(g);
		
		if (shouldDrawBoard) {
			drawBoard(g);
		}
	}
	
	/*
	 * Draws the board
	 * @param g: a graphics object
	 */
	public void drawBoard(Graphics g) {
		int board[][] = life.getBoard();
		// Draw every point
		for (int row = 0; row < board.length; row++) {
			for (int column = 0; column < board[0].length; column++) {
				if (board[row][column] == 1) {
					g.setColor(Color.black);
				} else {
					g.setColor(Color.white);
				}
				// Draw and scale the cell
				int cell_size = getCellSize();
				g.fillRect(column * cell_size, row * cell_size , cell_size, cell_size);
			}
		}
	}
	
	/*
	 * Gets the size the cell will be displayed at by dividing the
	 * width of the frame by the number of cells on the board
	 */
	public int getCellSize() {
		return frame_width / board_width;
	}
	
	/*
	 * Close the window and open a new one, restarting the program
	 */
	public void resetWindow() {
		frame.dispose();
		new UIBuilder();
	}

	/*
	 * The action performed at the timer intervals
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		
		if (command.equals(TIMER_COMMAND)) {
			life.updateBoard();
		} else if (command.equals(SUBMIT)) {
			initializeBoard();
			updateWindow();
			initializeButtons();
			
		} else if (command.equals(RUN)) {
			shouldDrawBoard = true;
			this.removeAll();
			life = new GameOfLife(board);
			initializeTimer();
			
		} else if (command.contains("Speed")) {
			timer.stop();
			if (command.contains("1")) {
				timer = new Timer(100, this);
			} else if (command.contains("2")) {
				timer = new Timer(250, this);
			} else if (command.contains("3")) {
				timer = new Timer(500, this);
			} else {
				timer = new Timer(1000, this);
			}
			
			timer.setActionCommand("Timer");
			timer.start();
			
		} else {
			String location[] = command.split(" ");
			int row = Integer.parseInt(location[0]);
			int column = Integer.parseInt(location[1]);
			
			if (buttons[row][column].getText().equals("")) {
					buttons[row][column].setText("X");
					board[row][column] = 1;
			} else {
				buttons[row][column].setText("");
				board[row][column] = 0;
			}
			
		}
		
		// Calls the paint method
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (shouldDrawBoard == true) {
			resetWindow();
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	

}
