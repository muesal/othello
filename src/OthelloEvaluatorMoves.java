/**
 * This interface defines the mandatory methods for an evaluator, i.e., a class
 * that can take a position and return an integer value that represents a
 * heuristic evaluation of the position (positive numbers if the position is
 * better for the first player, white). Notice that an evaluator is not supposed
 * to make moves in the position to 'see into the future', but only evaluate the
 * static features of the position.
 * 
 * @author Henrik Bj&ouml;rklund
 */

public class OthelloEvaluatorMoves implements OthelloEvaluator {

	/** Count the number of moves the next player has.
	 * Idea from Paper: An Analysis of Heuristics in Othello, University of Washington */
	public int evaluate(OthelloPosition position) {
		// TODO: set move to current player (constructor which takes a player?)
		int my_moves = position.getMoves().size();
		position.nextMove();
		int opponents_moves = position.getMoves().size();

		int heuristic = 0;
		if (my_moves != -opponents_moves) {
			heuristic = 100 * (my_moves - opponents_moves) / (my_moves + opponents_moves);
		} else if (my_moves == 0 && opponents_moves == 0) {
			int score = position.score();
			if (position.toMove()) { // max's move means min had first move
				score = -score; // score for black
			}

			heuristic = score == 0 ? 50 : score > 0 ? 101 : -1; // center, more or less than extreme of heuristic
		}
		return heuristic - 50; // -50, since percent value: ratio <50% is more fabourable for player 2
	}

}