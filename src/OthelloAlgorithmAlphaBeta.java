import java.util.LinkedList;

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

public class OthelloAlgorithmAlphaBeta implements OthelloAlgorithm {

    OthelloEvaluator evaluator;
    int depth;
    protected boolean maxPlayer;
    protected boolean interrupted = false;

    public OthelloAlgorithmAlphaBeta(OthelloEvaluator evaluator) {
        setEvaluator(evaluator);
    }

    /**
     * Sets the <code>OthelloEvaluator</code> the algorithm is to use for
     * heuristic evaluation.
     */
    public void setEvaluator(OthelloEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    /**
     * Sets the current player.
     */
    public void setPlayer(boolean maxPlayer) {
        this.maxPlayer = maxPlayer;
    }

    /**
     * Returns the <code>OthelloAction</code> the algorithm considers to be the
     * best move, using alpha-beta pruning.
     */
    public OthelloAction searchAction(OthelloPosition position) throws InterruptedException {
        OthelloAction action = maxPlayer ? initialMaxValue(position) : initialMinValue(position); // TODO: pass because no moves possible takes long, why? something in the heuristic is bad, it makes really bad moves
        return action;
    }

    private OthelloAction initialMaxValue(OthelloPosition position) throws InterruptedException {

        LinkedList<OthelloAction> moves = position.getMoves();

        // if no moves are possible, return pass action
        if (moves.size() == 0) {
            return new OthelloAction("pass");
        }

        OthelloAction action = new OthelloAction(-1, -1);
        action.setValue(Integer.MIN_VALUE);
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        OthelloPosition new_position;

        // perform maxValue, updating the action
        for (OthelloAction move : moves) {
            try {
                // create new position, apply the action (may throw exception)
                new_position = position.makeMove(move);

                // call minValue on the new position
                int newValue = minValue(new_position, alpha, beta, depth - 1);

                // update value and alpha, if possible
                if (newValue > action.getValue()) {

                    action.setValue(newValue);
                    action.setRow(move.getRow());
                    action.setColumn(move.getColumn());

                    if (alpha < newValue) {
                        alpha = newValue;
                    }
                }
            } catch (IllegalMoveException e) {
                // makeMove could throw an exception TODO: how to handle exception
                e.printStackTrace();
            }
        }

        return action;
    }

    private OthelloAction initialMinValue(OthelloPosition position) throws InterruptedException {

        LinkedList<OthelloAction> moves = position.getMoves();

        // if no moves are possible, return pass action
        if (moves.size() == 0) {
            return new OthelloAction("pass");
        }

        OthelloAction action = new OthelloAction(-1, -1);
        action.setValue(Integer.MAX_VALUE);
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        OthelloPosition new_position;

        // perform maxValue, updating the action
        for (OthelloAction move : moves) {
            try {
                // create new position, apply the action (may throw exception)
                new_position = position.makeMove(move);

                // call maxValue on the new position
                int newValue = maxValue(new_position, alpha, beta, depth - 1);

                // update value and alpha, if possible
                if (newValue < action.getValue()) {

                    action.setValue(newValue);
                    action.setRow(move.getRow());
                    action.setColumn(move.getColumn());

                    if (beta > newValue) {
                        beta = newValue;
                    }
                }
            } catch (IllegalMoveException e) {
                // makeMove could throw an exception TODO: how to handle exception
                e.printStackTrace();
            }
        }

        return action;
    }

    /**
     * MaxValue method of alpha-beta pruning. Returns the value of its child with the biggest value, or own value if a
     * leave or if the iteration depth is reached.
     *
     * @param position OthelloPosition of the node
     * @param alpha    alpha value
     * @param beta     beta value
     * @param depth    iteration depth
     * @return maxValue that can be achieved, or value of the leave
     */
    private int maxValue(OthelloPosition position, int alpha, int beta, int depth) throws InterruptedException {
        if (interrupted) {
            throw new InterruptedException();
        }
        if (depth == 0) {
            // if depth is reached return heuristic value
            return evaluator.evaluate(position);
        }

        LinkedList<OthelloAction> moves = position.getMoves();

        // if no moves are possible, the game is either over or the player must pass
        if (moves.size() == 0) {
            position.nextMove();

            if (position.getMoves().size() == 0) {
                // if neither of the players can move, the game is over; return the score
                return position.score();
            }

            // else pass
            return minValue(position, alpha, beta, depth - 1);
        }

        int value = Integer.MIN_VALUE;
        OthelloPosition new_position;

        for (OthelloAction action : moves) {
            try {
                // create new position, apply the action (may throw exception)
                new_position = position.makeMove(action);

                // call minValue on the new position
                int min = minValue(new_position, alpha, beta, depth - 1);

                // update value and alpha, if possible
                if (min > value) {
                    value = min;
                    if (alpha < value) {
                        alpha = value;
                        if (alpha >= beta) {
                            return value;
                        }
                    }
                }
            } catch (IllegalMoveException e) {
                // makeMove could throw an exception TODO: how to handle exception
                e.printStackTrace();
            }
        }

        return value;
    }

    /**
     * MinValue method of alpha-beta pruning. Returns the value of its child with the smallest value, or own value if a
     * leave or if the iteration depth is reached.
     *
     * @param position OthelloPosition of the node
     * @param alpha    alpha value
     * @param beta     beta value
     * @param depth    iteration depth
     * @return minValue that can be achieved, or value of the leave
     */
    private int minValue(OthelloPosition position, int alpha, int beta, int depth) throws InterruptedException {
        if (interrupted) {
            throw new InterruptedException();
        }
        if (depth == 0) {
            // if depth is reached return heuristic value
            return evaluator.evaluate(position);
        }

        LinkedList<OthelloAction> moves = position.getMoves();

        // if no moves are possible, the game is either over or the player must pass
        if (moves.size() == 0) {
            position.nextMove();

            if (position.getMoves().size() == 1) {
                // if neither of the players can move, the game is over; return the score
                return position.score();
            }

            // else pass
            return maxValue(position, alpha, beta, depth - 1);
        }

        int value = Integer.MAX_VALUE;
        OthelloPosition new_position;

        for (OthelloAction action : moves) {
            try {
                // create new position, apply the action (may throw exception)
                new_position = position.makeMove(action);

                // call minValue on the new position
                int max = maxValue(new_position, alpha, beta, depth - 1);

                // update value and beta, if possible
                if (max < value) {
                    value = max;
                    if (beta > value) {
                        beta = value;
                        if (alpha >= beta) {
                            return value;
                        }
                    }
                }
            } catch (IllegalMoveException e) {
                // makeMove could throw an exception
                e.printStackTrace();
				// TODO: how to handle exception
            }
        }

        return value;
    }

    public void setSearchDepth(int depth) {
        this.depth = depth;
    }

    public void interrupt() {interrupted = true;}
}