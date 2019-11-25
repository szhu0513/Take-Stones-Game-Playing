import java.text.DecimalFormat;

public class AlphaBetaPruning {


    public AlphaBetaPruning() {
    }
    public static int depth = 0;
    public static int move = 0;
    public static double value = 0.0;
    public static int depthReached = 0;
    public static int nodesEvaluated = 0;
    public static int nodesVisited = 0;
    public static int countfactor = 0;
    public static double branchingFactor = 0.0;

    /**
     * This function will print out the information to the terminal,
     * as specified in the homework description.
     */
    public void printStats() {
        System.out.println("Move: "+ move);
        System.out.println("Value: "+ value);
        System.out.println("Number of Nodes Visited: "+ nodesVisited);
        System.out.println("Number of Nodes Evaluated: "+ nodesEvaluated);
        System.out.println("Max Depth Reached: "+ depthReached);
        branchingFactor = (double)countfactor/ (double)(nodesVisited - nodesEvaluated);
        //round number to 1 decimal value  
        System.out.println("Avg Effective Branching Factor: "+ Math.round(branchingFactor * 10) / 10.0);
    }

    /**
     * This function will start the alpha-beta search
     * @param state This is the current game state
     * @param depth This is the specified search depth
     */
    public void run(GameState state, int depth) {
    	AlphaBetaPruning.depth = depth;
        int j = 0;
        boolean maxPlayer = true;
        for (int i = 1; i < state.stones.length; i++) {
            if (!state.stones[i])
                j++;
       }
        if(j%2 == 1) {
        	// if the number is odd, the current move is minPlayer.
        	maxPlayer = false;
        }
        AlphaBetaPruning.value = alphabeta(state,0,Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY,maxPlayer);
    }

    /**
     * This method is used to implement alpha-beta pruning for both 2 players
     * @param state This is the current game state
     * @param depth Current depth of search
     * @param alpha Current Alpha value
     * @param beta Current Beta value
     * @param maxPlayer True if player is Max Player; Otherwise, false
     * @return int This is the number indicating score of the best next move
     */
    private double alphabeta(GameState state, int depth, double alpha, double beta, boolean maxPlayer) {
    	nodesVisited++;
        if(this.depth == depth || state.getSuccessors().size() == 0) {
            if (depth > depthReached)
                depthReached = depth;
            nodesEvaluated++;
            return state.evaluate();
        }
           else if(maxPlayer == false){
                double v = Double.POSITIVE_INFINITY;
                double bestv = Double.POSITIVE_INFINITY;
                    for(int i = 0; i<state.getSuccessors().size(); i++){
                        countfactor++;
                        v=Double.min(v, alphabeta(state.getSuccessors().get(i), depth + 1, alpha, beta, true));
                        GameState child = state.getSuccessors().get(i);
                        if(bestv > v && depth==0) {
                            bestv = v;
                            child = state.getSuccessors().get(i);
                            move = child.getLastMove();
                        }
                        if(v <= alpha) return v;
                        beta = Double.min(beta,v);
                    }
                    return v;
            }
            else{
            	double v = Double.NEGATIVE_INFINITY;
                double bestv = Double.NEGATIVE_INFINITY;
                for(int i = 0; i < state.getSuccessors().size(); i++) {
                    countfactor++;
                    GameState child = state.getSuccessors().get(i);
                    v = Double.max(v, alphabeta(state.getSuccessors().get(i), depth + 1, alpha, beta, false));
                    if(bestv < v && depth == 0) {
                        bestv = v;
                        child = state.getSuccessors().get(i);
                        move = child.getLastMove();
                    }
                    if(v >= beta) return v;
                    alpha = Double.max(alpha,v);
                    }
                return v;
            }
        }

    }

