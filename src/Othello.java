import java.util.concurrent.*;

// start for white: ./othellostart /home/salome/Documents/othello/src/othello.sh ./othello_naive 5
// start for black: ./othellostart ./othello_naive /home/salome/Documents/othello/src/othello.sh 5

public class Othello {

    public static void main(String[] args) throws InterruptedException {
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
        long endTime = System.currentTimeMillis() + limit * 1000L; // end in now + 95% of the given limit

        // initialise Othello
        OthelloPosition position = new OthelloPosition(board);
        OthelloEvaluator evaluator = new OthelloEvaluatorMoves();
        OthelloAlgorithm algorithm = new OthelloAlgorithmAlphaBeta(evaluator);
        algorithm.setPlayer(board.charAt(0) == 'W');

        // depth 1 can/must always be executed
        int depth = 1;
        algorithm.setSearchDepth(depth);
        OthelloAction action = algorithm.searchAction(position);

        // start iterative deepening
        final ExecutorService service = Executors.newSingleThreadExecutor();
        long remainingTime = endTime - System.currentTimeMillis();

        // then get as deep as possible
        while (remainingTime > 0) {

            algorithm.setSearchDepth(++depth);
            final Future<OthelloAction> search = service.submit(() -> (algorithm.searchAction(position)));

            try {
                action = search.get(remainingTime, TimeUnit.MILLISECONDS);
            } catch (TimeoutException e) {
                System.err.println("Interrupted at depth " + depth + ", stalled " +
                        (endTime - System.currentTimeMillis()) + " ms");
                algorithm.interrupt();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            remainingTime = endTime - System.currentTimeMillis();
        }
        service.shutdownNow();

        action.print();
    }
}
