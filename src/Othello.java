import java.util.concurrent.*;

// start for white: ./othellostart /home/salome/Documents/othello/src/othello.sh ./othello_naive 5
// start for black: ./othellostart ./othello_naive /home/salome/Documents/othello/src/othello.sh 5

public class Othello {

    public static void main(String[] args) throws InterruptedException {
        // get / set input arguments
        String board;
        int limit;
        if (args.length < 2 || args[0].length() != 65) {
            System.out.println("Two arguments are required. The first should be a string of length 65 representing the " +
                    "board, the second the time limit in seconds.\n" +
                    "Setting board to initial state, time limit to 5.");
            board = "WEEEEEEEEEEEEEEEEEEEEEEEEEEEOXEEEEEEXOEEEEEEEEEEEEEEEEEEEEEEEEEEE";
            limit = 5;
        } else {
            board = args[0];
            limit = Integer.parseInt(args[1]);
        }
        long endTime = System.currentTimeMillis() + limit * 950L; // timestamp of when the algorithm should terminate

        // initialise Othello
        OthelloPosition position = new OthelloPosition(board);

         OthelloEvaluatorCompound evaluator = new OthelloEvaluatorCompound(new OthelloEvaluatorMoves(), new OthelloEvaluatorCount());
        // OthelloEvaluator evaluator = new OthelloEvaluatorMoves();
        // OthelloEvaluator evaluator = new OthelloEvaluatorCount();

        OthelloAlgorithm algorithm = new OthelloAlgorithmAlphaBeta(evaluator);
        algorithm.setPlayer(board.charAt(0) == 'W');

        // depth 1 can/must always be executed
        int depth = 1;
        algorithm.setSearchDepth(depth);
        OthelloAction action = algorithm.searchAction(position);

        // start iterative deepening
        final ExecutorService service = Executors.newSingleThreadExecutor();
        // update the remaining time
        long remainingTime = endTime - System.currentTimeMillis();

        // then go as deep as possible
        while (remainingTime > 0) {

            // increase the search depth
            algorithm.setSearchDepth(++depth);
            // create a thread for the search algorithm
            final Future<OthelloAction> search = service.submit(() -> (algorithm.searchAction(position)));

            try {
                // get the result of the search, continue in this thread after remainingTime Milliseconds
                action = search.get(remainingTime, TimeUnit.MILLISECONDS);
            } catch (TimeoutException e) {
                // interrupt the search algorithm
                algorithm.interrupt();
            } catch (ExecutionException e) {
                // This may occur if something else in the get() does not go as expected
                e.printStackTrace();
            }

            // update the remaining time
            remainingTime = endTime - System.currentTimeMillis();
        }
        // shut down the service
        service.shutdownNow();

        // System.err.println("Interrupted at depth " + depth); // debug print

        // print the action
        action.print();
    }
}
