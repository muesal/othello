public class Othello {

    public static void main(String[] args) {
        // get / set input arguments
        String board;
        int limit;
        if (args.length < 1) {
            System.out.println("Two arguments are required. The first should be a string of length 65 representing the " +
                    "board, the second the time limit in seconds.\n" +
                    "Setting board to initial state, time limit to 5.");
            board = "WEEEEEEEEEEEEEEEEEEEEEEEEEEEOXEEEEEEXOEEEEEEEEEEEEEEEEEEEEEEEEEEE";
            limit = 5;
        } else if (args.length < 2) {
            System.out.println("The second argument, the time limit in seconds, is missing. Setting it to 5.");
            board = args[0];
            limit = 5;
        } else {
            board = args[0];
            limit = Integer.parseInt(args[1]);
        }

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
        OthelloAction action = algorithm.searchAction(position);

        // todo: use a thread to have correct time (https://www.baeldung.com/java-stop-execution-after-certain-time)
        while(time < limit) {
            algorithm.setSearchDepth(++depth);
            action = algorithm.searchAction(position);
            time++;
        }

        action.print();
    }
}
