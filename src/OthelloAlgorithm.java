/**
 * This interface defines the mandatory methods for game playing algorithms,
 * i.e., algorithms that take an <code>OthelloAlgorithm</code> and return a
 * suggested move for the player who has the move.
 * 
 * The algorithm only defines the search method. The heuristic evaluation of
 * positions is given by an <code>OthelloEvaluator</code> which is given to the
 * algorithm.
 * 
 * @author Henrik Bj&ouml;rklund
 */

public interface OthelloAlgorithm {

	/**
	 * Sets the <code>OthelloEvaluator</code> the algorithm is to use for
	 * heuristic evaluation.
	 */
	void setEvaluator(OthelloEvaluator evaluator);

	/**
	 * Returns the <code>OthelloAction</code> the algorithm considers to be the
	 * best move.
	 */
	OthelloAction searchAction(OthelloPosition position) throws InterruptedException;

	/** Sets the maximum search depth of the algorithm. */
	void setSearchDepth(int depth);

	/** set the player, true if it is white's turn */
	void setPlayer(boolean maxPlayer);

	/** set the boolean interrupt to true, to interrupt the search*/
	void interrupt();
}