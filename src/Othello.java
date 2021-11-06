public class Othello {

    public static void main(String[] args) {
        // get / set input arguments
        String board = args.length >= 1 ? args[0] : "WEEEEEEEEEEEEEEEEEEEEEEEEEEEOXEEEEEEXOEEEEEEEEEEEEEEEEEEEEEEEEEEE";
        int limit = args.length >= 2 ? Integer.parseInt(args[1]) : 5;

        // TODO print arguments?

        // start the timer
        int time = 0; // todo: replace with timer

        // initialise Othello
        OthelloPosition position = new OthelloPosition(board);
        OthelloEvaluator evaluator = new OthelloEvaluatorMoves();
        OthelloAlgorithm algorithm = new OthelloAlgorithmAlphaBeta(evaluator);
        algorithm.setPlayer(board.charAt(0) == 'W');

        // start iterative deepening
        int depth = 1;
        algorithm.setSearchDepth(depth);
        OthelloAction action = algorithm.evaluate(position);

        // todo: use a thread to have correct time (https://www.baeldung.com/java-stop-execution-after-certain-time)
        while(time < limit) {
            algorithm.setSearchDepth(++depth);
            action = algorithm.evaluate(position);
            time++;
        }

        action.print();
    }
}
