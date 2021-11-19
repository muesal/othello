
public class OthelloEvaluatorCompound implements OthelloEvaluator {

	OthelloEvaluator eval1, eval2;

	OthelloEvaluatorCompound(OthelloEvaluator eval1, OthelloEvaluator eval2) {
		super();
		this.eval1 = eval1;
		this.eval2 = eval2;
	}

	/** Compund the heuristics move and count, weigh them accordingly to the amount of pieces on the board.
	 * In early states of the game the mobility heuristic has more weigh, in later states the score is more important */
	public int evaluate(OthelloPosition position) {
		int e1 = eval1.evaluate(position);
		int e2 = eval2.evaluate(position);

		float w = position.getEmpty() / 64;

		return (int) (w * e1 + (1-w) * e2);
	}

}