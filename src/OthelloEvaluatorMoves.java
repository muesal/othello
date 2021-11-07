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
		boolean originalMove = position.toMove(); // true, if Max turn
		if (!originalMove) {
			// it must be max turn
			position.nextMove();
		}
		int my_moves = position.getMoves().size();
		position.nextMove();
		int opponents_moves = position.getMoves().size();

		int heuristic = position.score();
		if (my_moves + opponents_moves != 0) {
			// if one or both move-counts are greater than 0 return the mobility heuristic
			heuristic = 64 * (my_moves - opponents_moves) / (my_moves + opponents_moves);
		}

		position.maxPlayer = originalMove; // restore original state

		return heuristic;
	}

}