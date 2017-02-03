import java.util.*;

public class Minimax {
	private final int sizeX = 8, sizeY = 8, validMove = 0;
	private double timeLimit;
	private int player;
	private boolean timedOut = false;

	public Minimax(double timeLimit) {
		this.timeLimit = timeLimit;
	}

	public Minimax() {
		timeLimit = System.currentTimeMillis() + Math.pow(10,3);
	}

	public int[] findOpt(Board board, int depth) {
		player = board.getCurrPlayer();
		Node root = new Node(board, null);
		maxValue(root, depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
		return root.bestMove;
	}

	private void maxValue(Node node, int depth, int alpha, int beta) {
		if (terminalTest(node, depth)) return;
		node.value = Integer.MIN_VALUE;
		for (Node next : node.getChildren()) {
			//Check time limit
			if (System.currentTimeMillis() >= timeLimit) {
				timedOut = true;
				return;
			}

			minValue(next, depth - 1, alpha, beta);
			if (next.value > node.value) {
				node.value = next.value;
				node.bestMove = next.path;
			}
			if (node.value >= beta) return;
			alpha = Integer.max(alpha, node.value);
		}
	}

	private void minValue(Node node, int depth, int alpha, int beta) {
		if (terminalTest(node, depth)) return;
		node.value = Integer.MAX_VALUE;
		for (Node next : node.getChildren()) {
			//Check time limit
			if (System.currentTimeMillis() >= timeLimit) {
				timedOut = true;
				return;
			}

			maxValue(next, depth - 1, alpha, beta);
			if (next.value < node.value) {
				node.value = next.value;
				node.bestMove = next.path;
			}
			if (node.value <= alpha) return;
			beta = Integer.min(beta, node.value);
		}
	}

	private boolean terminalTest(Node node, int depth) {
		if (node.getChildren().length == 0) {
			node.findChildren();
		}

		if (depth == 0 || node.getChildren().length == 0) {
			node.calculateValue();
			return true;
		}
		return false;
	}

	public boolean finished() {
		return !timedOut;
	}

	private class Node {
		private Board state;
		private int value;
		private Node[] children;
		private int[] path, bestMove;

		private Node(Board state, int[] path) {
			this.state = state;
			this.path = path;
			bestMove = new int[2];
			children = new Node[0];
		}

		private void findChildren() {
			children = new Node[state.getNbrValidMoves()];
			int added = 0;
			int[][] board = state.getBoard();
			for (int i = 0; i < sizeX; i++) {
				for (int j = 0; j < sizeY; j++) {
					if (board[i][j] == validMove) {
						Board child = new Board(state);
						child.move(i,j);
						int[] path = new int[2];
						path[0] = i;
						path[1] = j;
						children[added] = new Node(child, path);
						added++;
					}
				}
			}
		}

		private void calculateValue() {
			int[][] grid = state.getBoard();
			value = 0;
			for (int i = 0; i < sizeX; i++) {
				for (int j = 0; j < sizeY; j++) {
					if (grid[i][j] == player) {
						if ((i == 0 || i == sizeX - 1) && (j == 0 || j == sizeY - 1)) value += 500;
						else if ((i == 0 || i == sizeX - 1) || (j == 0 || j == sizeY - 1)) value += 101;
						else value += 100;
					}
					if (grid[i][j] != player && grid[i][j] > 0) {
						if ((i == 0 || i == sizeX - 1) && (j == 0 || j == sizeY - 1)) value -= 500;
						else if ((i == 0 || i == sizeX - 1) || (j == 0 || j == sizeY - 1)) value -= 101;
						else value -= 100;
					}
				}
			}
		}

		private Board getState() {
			return state;
		}

		private int getValue() {
			return value;
		}

		private Node[] getChildren() {
			return children;
		}
	}
}