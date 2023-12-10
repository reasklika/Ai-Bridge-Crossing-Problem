/**
 * Represents a family member for the bridge crossing problem.
 * Implements Comparable interface to facilitate sorting based on member ID.
 */
public class FamilyMember implements Comparable<FamilyMember> {

    // Private member variables
    private int time;
    static int counter = 1;  // Static counter for assigning unique IDs to family members
    private int id;  // Unique ID for each family member

    /**
     * Constructor for creating a new family member with a given crossing time.
     *
     * @param time Crossing time for the family member.
     */
    public FamilyMember(int time) {
        this.time = time;
        id = counter++;  // Assign a unique ID and increment the counter
    }

    /**
     * Copy constructor for creating a new family member by copying another member.
     *
     * @param fm Another FamilyMember object to be copied.
     */
    public FamilyMember(FamilyMember fm) {
        this.id = fm.id;
        this.time = fm.time;
    }

    /**
     * Getter method to retrieve the ID of the family member.
     *
     * @return The unique ID of the family member.
     */
    public int getId() {
        return id;
    }

    /**
     * Getter method to retrieve the crossing time of the family member.
     *
     * @return The crossing time of the family member.
     */
    public int getTime() {
        return time;
    }

    /**
     * Overrides the equals method to compare two FamilyMember objects for equality.
     *
     * @param fm Object to compare with.
     * @return True if the objects are the same instance, false otherwise.
     */
    @Override
    public boolean equals(Object fm) {
        return fm == this;
    }

    /**
     * Custom equals method to compare two FamilyMember objects based on their IDs.
     *
     * @param fm Another FamilyMember object to compare with.
     * @return True if the IDs are equal, false otherwise.
     */
    public boolean equals(FamilyMember fm) {
        return fm.id == this.id;
    }

    /**
     * Overrides the hashCode method to generate a hash code based on the ID.
     *
     * @return The hash code of the family member.
     */
    @Override
    public int hashCode() {
        return id;
    }

    /**
     * Overrides the compareTo method to compare two FamilyMember objects based on their IDs.
     *
     * @param fm Another FamilyMember object to compare with.
     * @return A negative integer, zero, or a positive integer as this object is less than, equal to,
     *         or greater than the specified object.
     */
    @Override
    public int compareTo(FamilyMember fm) {
        return Integer.compare(this.id, fm.getId());
    }

    /**
     * Overrides the toString method to provide a string representation of the family member.
     *
     * @return A string representation of the family member, including its ID.
     */
    @Override
    public String toString() {
        return "*fm " + id + "* ";
    }
}