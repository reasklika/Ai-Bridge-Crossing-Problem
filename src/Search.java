import java.util.*;

/**
 * The Search class performs A* search on the state space to solve the bridge crossing problem.
 */
public class Search {

    public boolean lanternOnLeft = false;
    private ArrayList<State> frontier = new ArrayList<State>();
    private int nodeExpandCount = 0;
    private int nodeExploreCount = 0;

    /**
     * Performs A* search on the state space to find a solution to the bridge crossing problem.
     *
     * @param initialState The initial state of the problem.
     * @param heuristic    The heuristic to be used in the search.
     * @return The final state representing the solution.
     */
    public State AStarSearch(State initialState, int heuristic) {
        long startTime = System.currentTimeMillis();
        HashSet<State> closedSet = new HashSet<State>();

        // Check if the initial state is already the final state.
        if (initialState.isFinal()) return initialState;

        // Put the initial state in the frontier.
        this.frontier.add(initialState);

        // Check for an empty frontier.
        while (this.frontier.size() > 0) {
            // Get the first node out of the frontier.
            State currentState = this.frontier.remove(0);

            // If the current state is the final state, return it.
            if (currentState.isFinal()) {
                long endTime = System.currentTimeMillis();
                System.out.println("Time Elapsed: " + currentState.getTotalTime() +
                        " Nodes Expanded: " + nodeExpandCount +
                        " Nodes explored: " + nodeExploreCount +
                        " Search time: " + (double) (endTime - startTime) / 1000 + " sec");
                printPathFollowed(currentState);
                return currentState;
            }

            // If the current state is not in the closed set, add it and expand its children.
            if (!closedSet.contains(currentState)) {
                closedSet.add(currentState);
                this.frontier.addAll(currentState.getChildren(heuristic));
                nodeExpandCount++;
            }

            // Sort the frontier based on the heuristic score to get the best state first.
            Collections.sort(this.frontier);
            nodeExploreCount++;
        }

        // Reset node counts in case no solution is found.
        nodeExpandCount = 0;
        nodeExploreCount = 0;
        return null;
    }

    /**
     * Prints the path followed to reach the final state.
     *
     * @param finalState The final state representing the solution.
     */
    private void printPathFollowed(State finalState) {
        Stack<String> moves = new Stack<String>();
        moves.push("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        moves.push(finalState.toString());
        State father = finalState.getFather();

        // Trace back the path from the final state to the initial state.
        while (father != null) {
            moves.push(father.toString());
            father = father.getFather();
        }

        moves.push("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Path Followed~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        // Print the path in reverse order.
        while (!moves.isEmpty()) {
            System.out.println(moves.pop());
        }
    }
}
