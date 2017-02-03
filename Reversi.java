import java.util.*;

public class Reversi {
	public static void main(String args[]) {
		// Set up game
		Board gb = new Board();
		Scanner scn = new Scanner(System.in);
		double timeLimit = 0;

		boolean acc = false;
		while (!acc) {
			System.out.println("Enter a time limit (Seconds)");
			String str = scn.nextLine();
			try {
				timeLimit = Double.parseDouble(str);
				acc = true;
			} catch (Exception E) {
				System.out.println("invalid input.");
			}
		}
		//convert to nano
		timeLimit = timeLimit*Math.pow(10,3);

		System.out.println("Decide if player 1 is a human (H) or an AI (A):");
		String str = scn.nextLine();
		str = str.toUpperCase();
		while (!str.equals("H") && !str.equals("A")) {
			System.out.println("Please enter 'H' for Human or 'A' for AI");
			str = scn.nextLine();
			str = str.toUpperCase();
		}
		Player p1 = new Player(str,gb,timeLimit);
		System.out.println("Decide if player 2 is a human (H) or an AI (A):");
		str = scn.nextLine();
		str = str.toUpperCase();
		while (!str.equals("H") && !str.equals("A")) {
			System.out.println("Please enter 'H' for Human or 'A' for AI");
			str = scn.nextLine();
			str = str.toUpperCase();
		}
		System.out.println();
		Player p2 = new Player(str,gb,timeLimit);


		// Start the game
		gb.printBoard();
		while (gb.getNbrValidMoves() > 0) {
			if (gb.getCurrPlayer() == 1) p1.turn();
			else if (gb.getCurrPlayer() == 2) p2.turn();
			else {
				System.out.println("Error: Player " + gb.getCurrPlayer() + " can't exist.");
				System.out.println("Exiting program.");
				break;
			}
			gb.printBoard();
		}

		System.out.println("Score player 1: " + gb.getScore(1) + ", Score player 2: " + gb.getScore(2));
		if (gb.getScore(1) > gb.getScore(2)) {
			System.out.println("Player 1 wins!");
		} else if (gb.getScore(2) > gb.getScore(1)) {
			System.out.println("Player 2 wins!");
		} else {
			System.out.println("Draw!");
		}
	}
}