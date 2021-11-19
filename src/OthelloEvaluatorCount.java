
public class OthelloEvaluatorCount implements OthelloEvaluator {

	/** Return the current score */
	public int evaluate(OthelloPosition position) {
		return position.score();
	}

}