package fifteensquare;

import java.util.Arrays;
import java.util.HashMap;

/**
 * The state class. Handles calculating heuristics and storing data of a given state. 
 * @author Cade Reynoldson
 * @version 1.0
 */
public class State {
    
    /** The first goal state stored in the form of a 2D char array. */
    public static final State goalState_1 = new State(parseArray("123456789ABCDEF "));
    
    /** The second goal state stored in the form of a 2D char array. */
    public static final State goalState_2 = new State(parseArray("123456789ABCDFE "));
    
    /** The values of the goal state one mapped to their coordinates in the array. */
    public static final HashMap<Character, XYPoint> goalStateCoordinates_g1 = getGoalStateCoords_g1();
    
    /** The values of the goal state two mapped to their coordinates in the array. */
    public static final HashMap<Character, XYPoint> goalStateCoordinates_g2 = getGoalStateCoords_g2();
    
    /** The current stored state. */
    private char[][] state;
    
    /**
     * Instantiates a state given a pre parsed 2d character array.
     * @param state the state to store.
     */
    public State(char[][] state) {
        this.state = state;
    }
    
    /**
     * Instantiates a state given a string to parse. 
     * @param arr the array to parese and create a state. 
     */
    public State(String arr) {
        this.state = State.parseArray(arr);
    }
    
    /**
     * Hashcode for storing a state. 
     */
    @Override
    public int hashCode() {
        int hash = 1;
        for (int i = 0; i < state.length; i++)
            hash += Arrays.hashCode(state[i]);
        return hash;
    }
    
    /**
     * Copies the current state. 
     * @return a deep copy of the current state. 
     */
    public State copy() {
        char[][] newState = new char[4][4];
        for (int i = 0; i < 4; i++) 
            for (int j = 0; j < 4; j++)
                newState[i][j] = state[i][j];
        return new State(newState);
    }
    
    /**
     * Returns the character contained in the given x and y coordinates. 
     * @param x the x coord.
     * @param y the y coord.
     * @return the character contained in the given x and y coordinates. 
     */
    public char getChar_xy(int x, int y) {
        return state[y][x];
    }
    
    /**
     * Returns the character in the given position with normal array subscript notation.
     * @param x the first index.
     * @param y the second index.
     * @return the character in the given position with normal array subscript notation. 
     */
    public char getChar_arr(int x, int y) {
        return state[x][y];
    }
    
    /**
     * Sets a charater in the state given its graph based (x, y) coordinates. 
     * @param val the value to set. 
     * @param x the x index.
     * @param y the y index. 
     */
    public void setChar_xy(char val, int x, int y) {
        state[y][x] = val;
    }
    
    /**
     * Sets a character in the state given its array subscript poistion. 
     * @param val the value to set. 
     * @param x the first index.
     * @param y the second index. 
     */
    public void setChar_arr(char val, int x, int y) {
        state[x][y] = val;
    }
    
    /**
     * Checks whether this state is equal to another state. 
     * @param state the state to check for equality. 
     * @return true if the states are equal, false otherwise. 
     */
    public boolean equals(State state) {
        for (int i = 0; i < 4; i++) 
            for (int j = 0; j < 4; j++)
                if (this.state[i][j] != state.getChar_arr(i, j))
                    return false;
        return true;
    }
    
    /**
     * Prints a state of the char array.
     * @param state
     */
    public void printState() {
        for (int i = 0; i < 4; i++) { //Y index
            for (int j = 0; j < 4; j++) {
                if (j < 3) 
                    System.out.print(state[i][j] + " ");
                else
                    System.out.println(state[i][j]);
            }
        }
    }
    
    /**
     * Calculates the number of tiles in the "wrong" position at the given state.  
     * @param state the state.
     * @return 
     */
    public int heuristicOne(State state) {
        int total = 0;
        for (int i = 0; i < 4; i++) 
            for (int j = 0; j < 4; j++)
                if (this.state[i][j] != state.getChar_arr(i, j))
                    total++;
        return total;
    }
    
    /**
     * Calculates the total manhattan distance from each tile in it's given state. 
     * @param state the state 
     * @return
     */
    public int heuristicTwo(short goalState) {
        int total = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (goalState == 1)
                    total += goalStateCoordinates_g1.get(state[i][j]).manhattanDistance((short) j, (short) i);
                else 
                    total += goalStateCoordinates_g2.get(state[i][j]).manhattanDistance((short) j, (short) i);
            }
        }
        return total;
    }
    
    /**
     * Parses an 2d array (state) from command line arguments. 
     * @param arr the array to parse. 
     * @return a 2d array in the form of a state. 
     */
    public static char[][] parseArray(String arr) { 
        if (arr.length() > 16) 
            return null;
        char[][] state = new char[4][4];
        int total = 0;
        for (int i = 0; i < 4; i++) 
            for (int j = 0; j < 4; j++) 
                state[i][j] = arr.charAt(total++);
        return state;
    }
    
    /**
     * Returns the coordinates of the blank space of this state in the form of an XYPoint. Runs in O(16). 
     * @param state the state to locate the blank space of. 
     * @return the coordinates of the blank space of a state. 
     */
    public XYPoint getCoordinates(char target) {
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                if (state[i][j] == target)
                    return new XYPoint(j, i);
        return null;
    }
    
    /**
     * Returns whether this state is a goal state or not. 
     * @return true if this state is a goal state, false otherwise. 
     */
    public boolean isGoalState() {
        return equals(goalState_1) || equals(goalState_2);
    }
    
    /**
     * Initializes goal state 1's values mapped to their coordinates. 
     * @return a hash map with the goal state values mapped to their coordinates.
     */
    public static HashMap<Character, XYPoint> getGoalStateCoords_g1() {
        HashMap<Character, XYPoint> points = new HashMap<Character, XYPoint>();
        points.put('1', new XYPoint(0, 0));
        points.put('2', new XYPoint(1, 0));
        points.put('3', new XYPoint(2, 0));
        points.put('4', new XYPoint(3, 0));
        points.put('5', new XYPoint(0, 1));
        points.put('6', new XYPoint(1, 1));
        points.put('7', new XYPoint(2, 1));
        points.put('8', new XYPoint(3, 1));
        points.put('9', new XYPoint(0, 2));
        points.put('A', new XYPoint(1, 2));
        points.put('B', new XYPoint(2, 2));
        points.put('C', new XYPoint(3, 2));
        points.put('D', new XYPoint(0, 3));
        points.put('E', new XYPoint(1, 3));
        points.put('F', new XYPoint(2, 3));
        points.put(' ', new XYPoint(3, 3));
        return points;
    }
    
    /**
     * Initializes goal state 2's values mapped to their coordinates. 
     * @return a hash map with the goal state values mapped to their coordinates.
     */
    public static HashMap<Character, XYPoint> getGoalStateCoords_g2() {
        HashMap<Character, XYPoint> points = new HashMap<Character, XYPoint>();
        points.put('1', new XYPoint(0, 0));
        points.put('2', new XYPoint(1, 0));
        points.put('3', new XYPoint(2, 0));
        points.put('4', new XYPoint(3, 0));
        points.put('5', new XYPoint(0, 1));
        points.put('6', new XYPoint(1, 1));
        points.put('7', new XYPoint(2, 1));
        points.put('8', new XYPoint(3, 1));
        points.put('9', new XYPoint(0, 2));
        points.put('A', new XYPoint(1, 2));
        points.put('B', new XYPoint(2, 2));
        points.put('C', new XYPoint(3, 2));
        points.put('D', new XYPoint(0, 3));
        points.put('F', new XYPoint(1, 3));
        points.put('E', new XYPoint(2, 3));
        points.put(' ', new XYPoint(3, 3));
        return points;
    }
}
