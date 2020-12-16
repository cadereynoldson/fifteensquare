package fifteensquare;

/**
 * An implementation of a search node for the Fifteen Squares problem. 
 * @author Cade Reynoldson
 * @version 1.0
 */
public class SearchNode {
    
    /** The state of this node. */
    private State state;
    
    /** The parent of this node. */
    private SearchNode parent;
    
    /** The heuristic of this node to it's goal. */
    private int heuristic;
    
    /** The depth of this node */
    private int depth;
    
    /** The coordinates of the blank space in the state. */
    private XYPoint blankCoords;
    
    /**
     * Instantiates a new version of a search node. 
     * @param state the state of the node.
     * @param parent the parent of the node. 
     * @param depth the depth of the node. 
     * @param x the x value of the blank position of the state. 
     * @param y the y value of the blank position of the state. 
     */
    public SearchNode(State state, SearchNode parent, int depth) {
        this.state = state;
        this.parent = parent;
        this.depth = depth;
        this.blankCoords = state.getCoordinates(' ');
        heuristic = -1;
    }
    
    /**
     * Instantiates a new version of a search node. 
     * @param state the state of the node.
     * @param parent the parent of the node. 
     * @param depth the depth of the node. 
     * @param x the x value of the blank position of the state. 
     * @param y the y value of the blank position of the state. 
     */
    public SearchNode(State state, SearchNode parent, int depth, XYPoint blankCoords) {
        this.state = state;
        this.parent = parent;
        this.depth = depth;
        this.blankCoords = blankCoords;
        heuristic = -1;
    }
    
    /**
     * Sets the heuristic of the node. Returns a second node with
     * its heuristic set to the second goal. 
     * @param heuristic the heuristic to use. 
     */
    public SearchNode setHeuristics(String heuristic) {
        SearchNode secondHeuristic = new SearchNode(state, parent, depth, blankCoords);
        if (heuristic.equals("h1")) {
            this.heuristic = state.heuristicOne(State.goalState_1);
            secondHeuristic.setHeuristic(state.heuristicOne(State.goalState_2));
        } else {
            this.heuristic = state.heuristicTwo((short) 1);
            secondHeuristic.setHeuristic(state.heuristicTwo((short) 2));
        }
        return secondHeuristic;
    }
    
    /**
     * Sets the heuristic value. 
     * @param heuristic the new heuristic value. 
     */
    public void setHeuristic(int heuristic) {
        this.heuristic = heuristic;
    }
    
    /**
     * Returns the heuristic to goal 1 of this node. 
     * @return the heuristic to goal 1 of this node. 
     */
    public int getHeuristic() {
        return heuristic;
    }
    
    /**
     * Returns the depth of this node. 
     * @return the depth of this node. 
     */
    public int getDepth() {
        return depth;
    }
    
    /**
     * Returns a new search node with the blank space "moved to the right"
     * @return a new search node with the blank space "moved to the right", null if node cannot move to the right.
     */
    public SearchNode moveRight() {
        if (blankCoords.getX() == 3) //If blank x value is equal to 3, cannot move right.
            return null;
        char val = state.getChar_xy(blankCoords.getX() + 1, blankCoords.getY());
        State newState = state.copy();
        newState.setChar_xy(' ', blankCoords.getX() + 1, blankCoords.getY());
        newState.setChar_xy(val , blankCoords.getX(), blankCoords.getY());
        return new SearchNode(newState, this, depth + 1, new XYPoint(blankCoords.getX() + 1, blankCoords.getY()));
    }
    
    /**
     * Returns a new search node with the blank space "moved to the left"
     * @return a new search node with the blank space "moved to the left", null if node cannot move to the left.
     */
    public SearchNode moveLeft() {
        if (blankCoords.getX() == 0) //If blank x value is equal to 0, cannot move left.
            return null;
        char val = state.getChar_xy(blankCoords.getX() - 1, blankCoords.getY());
        State newState = state.copy();
        newState.setChar_xy(' ', blankCoords.getX() - 1, blankCoords.getY());
        newState.setChar_xy(val , blankCoords.getX(), blankCoords.getY());
        return new SearchNode(newState, this, depth + 1, new XYPoint(blankCoords.getX() - 1, blankCoords.getY()));
    }
    
    /**
     * Returns a new search node with the blank space "moved up"
     * @return a new search node with the blank space "moved up", null if node cannot move up.
     */
    public SearchNode moveUp() {
        if (blankCoords.getY() == 0) //If blank y value is equal to 0, cannot move up.
            return null;
        char val = state.getChar_xy(blankCoords.getX(), blankCoords.getY() - 1);
        State newState = state.copy();
        newState.setChar_xy(' ', blankCoords.getX(), blankCoords.getY() - 1);
        newState.setChar_xy(val , blankCoords.getX(), blankCoords.getY());
        return new SearchNode(newState, this, depth + 1, new XYPoint(blankCoords.getX(), blankCoords.getY() - 1));
    }
    
    /**
     * Returns a new search node with the blank space "moved down"
     * @return a new search node with the blank space "moved down", null if node cannot move down.
     */
    public SearchNode moveDown() {
        if (blankCoords.getY() == 3) //If blank y value is equal to 3, cannot move down.
            return null;
        char val = state.getChar_xy(blankCoords.getX(), blankCoords.getY() + 1);
        State newState = state.copy();
        newState.setChar_xy(' ', blankCoords.getX(), blankCoords.getY() + 1);
        newState.setChar_xy(val , blankCoords.getX(), blankCoords.getY());
        return new SearchNode(newState, this, depth + 1, new XYPoint(blankCoords.getX(), blankCoords.getY() + 1));
    }
    
    /**
     * Expands the search node. Returns an array of size 4, with null values for
     * nodes which could not be expanded in a certian direction. Calling this method counts as
     * ONE expansion (the expansion of this node). 
     * Indexes and their corresponding directions:
     * - 0: right
     * - 1: down
     * - 2: left
     * - 3: up
     * @return An array of search nodes, with null values in direcitons that couldn't be expanded. 
     */
    public SearchNode[] expand() {
        SearchNode[] expansion = new SearchNode[4];
        expansion[3] = moveRight();
        expansion[2] = moveDown();
        expansion[1] = moveLeft();
        expansion[0] = moveUp();
        return expansion;
    }
    
    
    /**
     * Returns the current state of this search node.
     * @return the current state of this search node. 
     */
    public State getState() {
        return state;
    }
    
    /**
     * Returns a string representation of the contained state.
     * @return a string representation of the contained state. 
     */
    public String getStateString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                s.append(state.getChar_arr(i, j));
        return s.toString();
    }
    
    /**
     * Prints the values of the node. Mainly used for debugging. 
     */
    public void printNode() {
        System.out.printf("Node - Depth %d - Blank Value: (%d, %d) - Heuristic: %d -"
                + " State Hash: %d \n", depth, blankCoords.getX(), blankCoords.getY(), heuristic, state.hashCode());
        state.printState();
    }
    
    /**
     * Prints the path taken from this node to the root node. 
     */
    public void printPath() {
        if (parent != null) {
            parent.printPath();
        }
        printNode();
    }
}
