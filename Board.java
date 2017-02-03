public class Board {
	private final int sizeX = 8, sizeY = 8;
	private int nbrValidMoves = 0, currPlayer;
	private final int invalidPos = -1, validPos = 0, p1Checker = 1, p2Checker = 2; 
	private int[][] board;

	public Board() {
		board = new int[sizeX][sizeY];
		for (int i = 0; i < sizeX; i++) {
			for (int j = 0; j < sizeY; j++) {
				board[i][j] = invalidPos;
			}
		}
		board[3][3] = p1Checker;
		board[4][3] = p2Checker;
		board[3][4] = p2Checker;
		board[4][4] = p1Checker;
		currPlayer = p2Checker;
		findValidMoves();
		currPlayer = p1Checker;
	}

	public Board(Board old) {
		board = new int[sizeX][sizeY];
		for (int i = 0; i < sizeX; i++) {
			for (int j = 0; j < sizeY; j++) {
				board[i][j] = old.board[i][j];
			}
		}
		this.currPlayer = old.currPlayer;
	}

	public boolean validMove(int x, int y) {
		if (x < 0 || x >= sizeX || y < 0 || y >= sizeY) {
			System.out.println("Invalid input.");
			return false;
		} else if (board[x][y] == validPos) {
			return true;
		}
		else {
			System.out.println("Invalid move.");
			return false;
		}
	}

	public boolean move(int x, int y) {
		if (validMove(x,y)) {
			board[x][y] = currPlayer;
			checkDir(x,y,"move");
			findValidMoves();
			if (currPlayer == p1Checker) currPlayer = p2Checker;
			else currPlayer = p1Checker;
			return true;
		} else {
			return false;
		}
	}

	private void findValidMoves() {
		//...Find valid moves (for next player).
		nbrValidMoves = 0;
		for (int i = 0; i < sizeX; i++) {
			for (int j = 0; j < sizeY; j++) {
				if (board[i][j] != p1Checker && board[i][j] != p2Checker) {
					board[i][j] = invalidPos;
					checkDir(i,j,"validation");
				}
			}
		}
	}

	private void checkDir(int i, int j, String type) {
		mainFor:
		for (int deltaX = -1; deltaX <= 1; deltaX++) {
			for (int deltaY = -1; deltaY <= 1; deltaY++) {
				if (!(deltaX == 0 && deltaY == 0) 
					&& (i+deltaX >= 0) && (i+deltaX < sizeX) 
					&& (j+deltaY >= 0) && (j+deltaY < sizeY)) { 
					
					if (type.equals("validation")) {
						if ((board[i+deltaX][j+deltaY] == currPlayer) 
							&& (step(i+deltaX,deltaX,j+deltaY,deltaY,type))) {
							board[i][j] = validPos;
							nbrValidMoves++;
							break mainFor;
						}
					} else if (type.equals("move")) {
						if ((board[i+deltaX][j+deltaY] != currPlayer) 
							&& (step(i+deltaX,deltaX,j+deltaY,deltaY,type))) {
							board[i][j] = currPlayer;
						}
					}
				}
			}
		}
	}

	private boolean step(int x, int deltaX, int y, int deltaY, String type) {
		if ((x < 0) || (x >= sizeX) 
			|| (y < 0) || (y >= sizeY)) {
			return false;
		}
		if (type.equals("validation")) {
			if (board[x][y] == currPlayer) return step(x+deltaX,deltaX,y+deltaY,deltaY,type);
			else if (board[x][y] != currPlayer && board[x][y] > 0) return true;
			else return false;
		} else {
			if (board[x][y] != currPlayer && board[x][y] > 0) {
				if (step(x+deltaX,deltaX,y+deltaY,deltaY,type)) {
					board[x][y] = currPlayer;
					return true;
				} else {
					return false;
				}
			}
			else if (board[x][y] == currPlayer) return true;
			return false;
		}
	}

	public void printBoard() {
		String checker;
		System.out.println("   a  b  c  d  e  f  g  h ");
		System.out.println("  ------------------------");
		for (int j = 0; j < sizeY; j++) {
			System.out.print((j+1) + "|");
			for (int i = 0; i < sizeX; i++) {
				if (board[i][j] == p1Checker) checker = "X";
				else if (board[i][j] == invalidPos) checker = " ";
				else if (board[i][j] == validPos) checker = ".";
				else if (board[i][j] == p2Checker) checker = "O";
				else checker = "Error";
				System.out.print(checker + " |");
			}
			System.out.println(" " + (j+1));
			System.out.println("  ------------------------");
		}
		System.out.println("   a  b  c  d  e  f  g  h  ");
		System.out.println();
	}

	// Each board is a state/node for the AI.
	public int[][] getBoard() {
		return board;
	}

	// Game ends when function returns zero.
	public int getNbrValidMoves() {
		return nbrValidMoves;
	}

	public int getCurrPlayer() {
		return currPlayer;
	}

	public int getScore(int player) {
		int score = 0;
		for (int i = 0; i < sizeX; i++) {
			for (int j = 0; j < sizeY; j++) {
				if (board[i][j] == player) score++;
			}
		}
		return score;
	}
}