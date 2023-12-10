import java.util.*;

/**
 * Represents a state in the bridge crossing problem, including the current configuration
 * of family members on both sides of the bridge.
 * Implements Comparable interface for sorting based on the evaluation function (f).
 */
public class State implements Comparable<State> {

    // Evaluation function components
    private int f, h, g;

    // State properties
    private State father;
    private int totalTime;
    private int moves;
    private HashMap<Integer, FamilyMember> familyMembers;
    private ArrayList<FamilyMember> rightBank;
    private ArrayList<FamilyMember> leftBank;
    private boolean lanternOnRight;
    private String moveDescription = "       Starting State      |";

    /**
     * Constructor for creating an initial state.
     *
     * @param familyMembers Map of family members.
     * @param h             Heuristic value for evaluation.
     */
    public State(HashMap<Integer, FamilyMember> familyMembers, int h) {
        // Initialization of state properties
        this.f = 0;
        this.h = 0;
        this.g = 0;
        this.moves = 0;
        this.father = null;
        this.totalTime = 0;
        this.lanternOnRight = true;
        this.familyMembers = familyMembers;
        rightBank = new ArrayList<FamilyMember>();
        leftBank = new ArrayList<FamilyMember>();

        // Initializing banks with all family members on the right side
        for (FamilyMember familyMember : familyMembers.values()) {
            rightBank.add(familyMember);
        }

        // Evaluating the initial state
        this.evaluate(h);
    }

    /**
     * Copy constructor for creating a new state by copying another state.
     *
     * @param s Another State object to be copied.
     */
    public State(State s) {
        // Copying state properties
        this.f = s.getF();
        this.h = s.getH();
        this.g = s.getG();
        this.moves = s.getMoves();
        this.father = s.getFather();
        this.totalTime = s.getTotalTime();
        this.familyMembers = s.copyFamilyMembers();
        this.lanternOnRight = s.getLantern();
        rightBank = new ArrayList<FamilyMember>();
        leftBank = new ArrayList<FamilyMember>();

        // Copying family members to the right bank
        for (FamilyMember fm : s.getRightBank()) {
            rightBank.add(familyMembers.get(fm.getId()));
        }

        // Copying family members to the left bank
        for (FamilyMember fm : s.getLeftBank()) {
            leftBank.add(familyMembers.get(fm.getId()));
        }
    }

    /**
     * Creates a copy of the family members map.
     *
     * @return A copy of the family members map.
     */
    public HashMap<Integer, FamilyMember> copyFamilyMembers() {
        HashMap<Integer, FamilyMember> temp = new HashMap<Integer, FamilyMember>();
        for (FamilyMember fm : familyMembers.values()) {
            temp.put(fm.getId(), new FamilyMember(fm));
        }
        return temp;
    }

    // Getter methods for various state properties

    public HashMap<Integer, FamilyMember> getFamilyMembers() {
        return familyMembers;
    }

	public void setMoveDescription(String description)
	{
		moveDescription = description;
	}

    public ArrayList<FamilyMember> getLeftBank() {
        return this.leftBank;
    }

    public ArrayList<FamilyMember> getRightBank() {
        return this.rightBank;
    }

    public boolean getLantern() {
        return lanternOnRight;
    }

    public void moveLantern() {
        lanternOnRight = !lanternOnRight;
    }

    public int getMoves() {
        return moves;
    }

    // Increment the moves counter
    public void MoveIncrement() {
        moves++;
    }

    public int getF() {
        return this.f;
    }

    public int getG() {
        return this.g;
    }

    public int getH() {
        return this.h;
    }

    public State getFather() {
        return this.father;
    }

    public void setF(int f) {
        this.f = f;
    }

    public void setG(int g) {
        this.g = g;
    }

    public void setH(int h) {
        this.h = h;
    }

    public void setFather(State f) {
        this.father = f;
    }

    public int getTotalTime() {
        return this.totalTime;
    }

    // Moving family members between banks

    private void moveLeft(FamilyMember familyMember) {
        rightBank.remove(familyMember);
        leftBank.add(familyMember);
    }

    private void moveRight(FamilyMember familyMember) {
        leftBank.remove(familyMember);
        rightBank.add(familyMember);
    }

    /**
     * Generates and returns a list of possible child states resulting from moving family members
     * from the right bank to the left bank.
     *
     * @param heuristic Heuristic value for evaluation.
     * @return List of child states.
     */
    public ArrayList<State> getChildrenRL(int heuristic) {
        ArrayList<State> children = new ArrayList<State>();

        // Generating child states for all pairs of family members on the right bank
        for (int i = 0; i < rightBank.size(); i++) {
            for (int j = 0; j < rightBank.size(); j++) {
                State childState = new State(this);
                FamilyMember familyMember = childState.getRightBank().get(i);
                FamilyMember secondFamilyMember = childState.getRightBank().get(j);

                // Moving the selected pair to the left bank
                if (!familyMember.equals(secondFamilyMember)) {
                    childState.moveLeft(childState.getFamilyMembers().get(familyMember.getId()));
                    childState.moveLeft(childState.getFamilyMembers().get(secondFamilyMember.getId()));

                    // Updating total time based on the family members' crossing times
                    if (familyMember.getTime() >= secondFamilyMember.getTime()) {
                        childState.totalTime += familyMember.getTime();
                    } else {
                        childState.totalTime += secondFamilyMember.getTime();
                    }
                    childState.setMoveDescription(familyMember + "& " + secondFamilyMember + " moved left |");
                } else {
                    // Moving a single family member to the left bank
                    childState.setMoveDescription("     " + familyMember + " moved left      |");
                    childState.moveLeft(childState.getFamilyMembers().get(familyMember.getId()));
                    childState.totalTime += familyMember.getTime();
                }

                // Updating the state properties and adding the child state to the list
                childState.moveLantern();
                childState.MoveIncrement();
                childState.evaluate(heuristic);
                childState.setFather(this);
                children.add(childState);
            }
        }
        return children;
    }

    /**
     * Generates and returns a list of possible child states resulting from moving family members
     * from the left bank to the right bank.
     *
     * @param heuristic Heuristic value for evaluation.
     * @return List of child states.
     */
    public ArrayList<State> getChildrenLR(int heuristic) {
        ArrayList<State> children = new ArrayList<State>();

        // Generating child states for all pairs of family members on the left bank
        for (int i = 0; i < leftBank.size(); i++) {
            for (int j = 0; j < leftBank.size(); j++) {
                State childState = new State(this);
                FamilyMember familyMember = childState.getLeftBank().get(i);
                FamilyMember secondFamilyMember = childState.getLeftBank().get(j);

                // Moving the selected pair to the right bank
                if (!familyMember.equals(secondFamilyMember)) {
                    childState.moveRight(familyMember);
                    childState.moveRight(secondFamilyMember);

                    // Updating total time based on the family members' crossing times
                    if (familyMember.getTime() >= secondFamilyMember.getTime()) {
                        childState.totalTime += familyMember.getTime();
                    } else {
                        childState.totalTime += secondFamilyMember.getTime();
                    }

                    childState.setMoveDescription(familyMember + "& " + secondFamilyMember + " moved right| ");
                } else {
                    // Moving a single family member to the right bank
                    childState.setMoveDescription("    " + familyMember + " moved right     |");
                    childState.moveRight(childState.getFamilyMembers().get(familyMember.getId()));
                    childState.totalTime += familyMember.getTime();
                }

                // Updating the state properties and adding the child state to the list
                childState.moveLantern();
                childState.setFather(this);
                childState.MoveIncrement();
                childState.evaluate(heuristic);
                children.add(childState);
            }
        }
        return children;
    }

    /**
     * Generates and returns a list of possible child states based on the current state and
     * the position of the lantern.
     *
     * @param heuristic Heuristic value for evaluation.
     * @return List of child states.
     */
    public ArrayList<State> getChildren(int heuristic) {
        if (lanternOnRight) {
            return getChildrenRL(heuristic);
        } else {
            return getChildrenLR(heuristic);
        }
    }

    /**
     * Evaluates the state based on the selected heuristic.
     *
     * @param heuristic Heuristic type.
     */
    public void evaluate(int heuristic) {
        switch (heuristic) {
            case 1:
                this.familyMembersRemaining();
                break;
            case 2:
                this.movesRemaining();
                break;
            case 3:
                this.noBridgeLimit();
                break;
            default:
                break;
        }
    }

    /**
     * Overrides the toString method to provide a string representation of the state.
     *
     * @return A string representation of the state.
     */
	@Override
	public String toString()
	{

		String output ="";
		output+= moveDescription;
		for(FamilyMember familyMember:leftBank)
		{
			output += familyMember.toString();
		}
		output+="~~~~~~~~~~~~";
		for(FamilyMember familyMember:rightBank)
		{
			output += familyMember.toString();
		}
		output+= "| Total time: "+ this.totalTime + " Moves: "+this.getMoves()+"|";
		output+=" Heuristic: "+getF();
		return output;
	}
/**
     * Heuristic function based on the number of family members remaining on the right bank.
     */
    private void familyMembersRemaining() {
        h = rightBank.size();
        g = totalTime;
        f = h + g;
    }

    /**
     * Heuristic function based on the remaining moves to be made.
     */
    private void movesRemaining() {
        h = 2 * familyMembers.size() - 3 - moves;
        g = totalTime;
        f = h + g;
    }

    /**
     * Heuristic function without a bridge limit, considering the maximum time taken by a family member
     * and the time of the family member closest to the lantern on the left bank.
     */
    private void noBridgeLimit() {
        int maxTime = 0;
        int minTime = 0;

        // Finding the maximum time among family members on the right bank
        if (!rightBank.isEmpty()) {
            maxTime = rightBank.get(0).getTime();
        }

        for (FamilyMember fm : rightBank) {
            if (maxTime < fm.getTime()) {
                maxTime = fm.getTime();
            }
        }

        h = 2 * maxTime;

        // Considering the family member closest to the lantern on the left bank
        if (!lanternOnRight) {
            if (!leftBank.isEmpty()) {
                minTime = leftBank.get(0).getTime();
            }

            for (FamilyMember fm : leftBank) {
                if (minTime > fm.getTime()) {
                    minTime = fm.getTime();
                }
            }
            h = h / 2;
            h += minTime;
        }

        // Adjusting heuristic if there are only two family members on the right bank
        if (rightBank.size() <= 2) {
            h = maxTime;
        }

        g = totalTime;
        f = h + g;
    }

    /**
     * Checks if the state is a final state (all family members on the left bank).
     *
     * @return True if the state is a final state, false otherwise.
     */
    public boolean isFinal() {
        return rightBank.isEmpty();
    }

    /**
     * Overrides the equals method to compare two State objects.
     *
     * @param obj Object to compare with.
     * @return Always returns true.
     */
    @Override
    public boolean equals(Object obj) {
        return true;
    }

    /**
     * Custom equals method to compare two State objects based on relevant properties.
     *
     * @param state Another State object to compare with.
     * @return True if the states are considered equal, false otherwise.
     */
    public boolean equals(State state) {
        return (this.lanternOnRight == state.lanternOnRight)
                && (this.totalTime == state.getTotalTime())
                && listEqualsIgnoreOrder(this.rightBank, state.rightBank)
                && listEqualsIgnoreOrder(this.leftBank, state.leftBank);
    }

    /**
     * Compares two lists for equality, ignoring the order of elements.
     *
     * @param list1 First list to compare.
     * @param list2 Second list to compare.
     * @return True if the lists are considered equal, false otherwise.
     */
    public boolean listEqualsIgnoreOrder(List<FamilyMember> list1, List<FamilyMember> list2) {
        return new HashSet<>(list1).equals(new HashSet<>(list2));
    }

    /**
     * Overrides the hashCode method to generate a hash code based on relevant properties.
     *
     * @return The hash code of the state.
     */
    @Override
    public int hashCode() {
        int hash = 0;

        // Handling cases with a large number of family members
        if (familyMembers.size() > 8) {
            FamilyMember[] tempRightBankArray = rightBank.toArray(new FamilyMember[rightBank.size()]);
            FamilyMember[] tempLeftBankArray = leftBank.toArray(new FamilyMember[leftBank.size()]);

            return Objects.hash(Arrays.hashCode(tempRightBankArray),
                    Arrays.hashCode(tempLeftBankArray), totalTime, lanternOnRight);
        } else {
            // Handling cases with a smaller number of family members
            ArrayList<FamilyMember> tempRightBank = new ArrayList<FamilyMember>(this.rightBank);
            ArrayList<FamilyMember> tempLeftBank = new ArrayList<FamilyMember>(this.leftBank);
            Collections.sort(tempRightBank);
            Collections.sort(tempLeftBank);
            int counter = this.familyMembers.size() + 1;

            // Calculating the hash code based on family members' IDs and lantern position
            for (FamilyMember fm : tempLeftBank) {
                hash += (fm.getId()) * Math.pow(10, counter);
                counter -= 1;
            }
            hash += (this.familyMembers.size() + 1) * Math.pow(10, counter);
            counter -= 1;

            for (FamilyMember fm : tempRightBank) {
                hash += (fm.getId()) * Math.pow(10, counter);
                counter -= 1;
            }

            if (lanternOnRight) {
                hash += (0) * Math.pow(10, counter);
            } else {
                hash += (familyMembers.size()) * Math.pow(10, counter);
            }
        }

        return hash;
    }

    /**
     * Overrides the compareTo method to enable sorting based on the evaluation function (f).
     *
     * @param s Another State object to compare with.
     * @return Result of the comparison based on the evaluation function.
     */
    @Override
    public int compareTo(State s) {
        return Double.compare(this.f, s.getF());
    }
}