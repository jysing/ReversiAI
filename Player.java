import java.util.*;

public class Player {
	private boolean human;
	private Scanner scn;
	private Board board;
	private Minimax alg;
	private double timeLimit;
	private final int sizeX = 8, sizeY = 8;

	public Player(String type, Board board, double timeLimit) {
		scn = new Scanner(System.in);
		this.board = board;
		this.timeLimit = timeLimit;

		if (type.equals("A")) human = false;
		else human = true;
	}

	public void turn() {
		if (human) Hturn();
		else AIturn();
	}

	private void Hturn() {
		String validMove = "No valid move avaible";
		int[][] grid = board.getBoard();
		foundValidMove:
		for (int i = 0; i < sizeX; i++) {
			for (int j = 0; j < sizeY; j++) {
				if (grid[i][j] == 0) {
					validMove = intToString(i) + (j+1);
					break foundValidMove; 
				}
			}
		}
		char type = 'x';
		if (board.getCurrPlayer() == 2) type = 'o'; 
		System.out.println("Player " + board.getCurrPlayer() + " (" + type + ") turn. Enter position (ex: " + validMove + ").");
		String str = scn.nextLine();
		if (str.length() != 2) System.out.println("Invalid input.");
		else {
			int x = charToInt(str.charAt(0));
			int y = charToInt(str.charAt(1));
			board.move(x,y);
		}
	}

	private void AIturn() {
		System.out.println("AI taking it's turn");
		double endTime = System.currentTimeMillis() + timeLimit;
		int depth = 1;
		int[] oldOpt = null;
		while (System.currentTimeMillis() < endTime) {
			alg = new Minimax(endTime);
			int[] opt = alg.findOpt(board, depth);
			if (alg.finished()) {
				oldOpt = opt;
			}
			if (alg.fullTree) break;
			depth++;
		}
		board.move(oldOpt[0], oldOpt[1]);
		System.out.println("Final depth = " + depth);
	}

	private int charToInt(char ch) {
		ch = Character.toLowerCase(ch);
		switch (ch) {
			case '1':	return 0;
			case '2':	return 1;
			case '3':	return 2;
			case '4':	return 3;
			case '5':	return 4;
			case '6':	return 5;
			case '7':	return 6;
			case '8':	return 7;

			case 'a':	return 0;
			case 'b':	return 1;
			case 'c':	return 2;
			case 'd':	return 3;
			case 'e':	return 4;
			case 'f':	return 5;
			case 'g':	return 6;
			case 'h':	return 7;
			default:	return -1;
		}
	}

	private String intToString(int nbr) {
		switch (nbr) {
			case 0:	return "a";
			case 1:	return "b";
			case 2:	return "c";
			case 3:	return "d";
			case 4:	return "e";
			case 5:	return "f";
			case 6:	return "g";
			case 7:	return "h";
			default:	return null;
		}
	}

	private char intToChar(int nbr) {
		return 'a';
	}
}