import java.util.HashMap;

/**
 * The Main class serves as the entry point for the bridge crossing problem solver.
 */
public class Main {

    public static void main(String args[]) {
        // Set the heuristic to be used in the search
        int heuristic = 1;

        // Create a HashMap to represent family members with their respective crossing times
        HashMap<Integer, FamilyMember> familyMembers = new HashMap<Integer, FamilyMember>();
        familyMembers.put(0, new FamilyMember(1));
        familyMembers.put(1, new FamilyMember(3));
        familyMembers.put(2, new FamilyMember(6));
        familyMembers.put(3, new FamilyMember(8));
        familyMembers.put(4, new FamilyMember(12));

        // Create the initial state with the given family members and heuristic
        State initialState = new State(familyMembers, heuristic);

        // Create a search object and perform A* search to find the solution
        Search searcher = new Search();
        searcher.AStarSearch(initialState, heuristic);
    }
}