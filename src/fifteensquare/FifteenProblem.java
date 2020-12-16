package fifteensquare;

/**
 * An object oriented design for implementation of the Fifteen Problem. Contians a main function which
 * provided arguments in the following order will either run:
 * - Depth First Search: "DFS"
 * - Breadth First Search: "BFS"
 * - Greedy Best First Search: "GBFS"
 * - A Star: "AStar"
 * - Iterative Deeping Search: "ID" 
 * - Depth Limited Search: "DLS"
 * Arugments should be provided in the following format: 
 * [initialState], [searchMethod], [options]
 * Valid values for [options]:
 * - "h1" heuristic one, Total squares out of place.
 * - "h2" heuristic two, Total manhattan distance of all squares relative to their goal posion.
 * - Heuristics are ONLY taken into account for GBFS and AStar!
 * - "integerValue" the maximum depth or iteration depth per each iteration in ID or DLS.
 * Example argument inputs:
 * "123456789AB DEFC" BFS
 * "123456789AB DEFC" DLS 2
 * "123456789AB DEFC" AStar h2
 * @author Cade Reynoldson
 * @version 1.0
 */
public class FifteenProblem {
    
    /** Breadth first search string key. */
    public static final String BFS = "BFS";
    
    /** Depth first search string key. */
    public static final String DFS = "DFS";
    
    /** Greedy best first search string key. */
    public static final String GBFS = "GBFS";
    
    /** A Star string search key */
    public static final String ASTAR = "AStar";
    
    /** Iterative deepening string search key. */
    public static final String ID = "ID";
    
    /** Depth limited search string key. */
    public static final String DLS = "DLS";
    
    /** Heuristic one search key. (Number of tiles in the "wrong" position at the given state) */
    public static final String H1 = "H1";
    
    /** Heurisitc two search key. (Calculates the total manhattan distance from each tile in it's given state.) */
    public static final String H2 = "H2";
    
    /** Initial state one for running the program. */
    public static final String inputOne = "123456789AB DEFC";
    
    /** Initial state two for running the program. */
    public static final String inputTwo = "123456789ABC DFE";
    
    /** The number of nodes expanded. */
    private int numExpanded = 0;
    
    /** The fringe of a the search algorithms in the form of a collection. */
    private Fringe fringe; 
    
    /** The head node of the search tree. */
    private SearchNode headNode;
    
    /**
     * Instantiates a new instance of the fifteen squares problem. 
     * @param arr the array representation of the original state. 
     * @param searchMethod the search method to use. 
     * @param options the options for running the search algorithm.
     */
    public FifteenProblem(String arr, String searchMethod, String options) {
        headNode = new SearchNode(new State(arr), null, 0);
        if (options.length() > 0) 
            fringe = new Fringe(searchMethod, options);
        else 
            fringe = new Fringe(searchMethod, "");
        fringe.add(headNode);
    }
    
    /**
     * Expands a given search node, and adds its non null children to the fringe. 
     * @param node the node to expand. 
     */
    public void expandNode(SearchNode node) {
        SearchNode[] expanded = node.expand();
        for (int i = 0; i < expanded.length; i++)
            if (expanded[i] != null) {
                fringe.add(expanded[i]);
            }
        numExpanded++;
    }
    
    /**
     * Conducts a search to the goal state using one of these search methods specified in the 
     * creation of the class.
     * - Breadth First Search
     * - Depth First Search
     * - Greedy Best First Search
     * - AStar
     * - Depth Limited Search
     * - Iterative Deepening Search
     * @param headNode the node to search for a solution. 
     */
    public void search() {
        SearchNode current = fringe.getNext();
        expandNode(current);
        while (!current.getState().isGoalState() && fringe.hasNext()) { //While the current node is not the goal state
            expandNode(current);
            fringe.markVisited(current);
            current = fringe.getNext();
        }
        if (current.getState().isGoalState())  //If this node is equal to the goal state:
            printConclusion(current, current.getDepth(), fringe.getNumCreated(), numExpanded, fringe.getMaxFringe());
        else  //Else, no solution was found. 
            printConclusion(null, -1, 0, 0, 0);
    }
    
    /**
     * Prints the head node of the search tree. 
     */
    public void printHeadNode() {
        System.out.println("Head node:");
        headNode.getState().printState();
    }
    
    /**
     * Prints the conclusion derived by the search strategy. 
     * @param depth the depth the solution was discovered at. 
     * @param numCreated the number of nodes the fringe created. 
     * @param numExpanded the number of nodes expanded. 
     * @param maxFringe the maximum size of the fringe. 
     */
    public void printConclusion(SearchNode node, int depth, int numCreated, int numExpanded, int maxFringe) {
        if (node != null) {
            node.getState().printState();
            System.out.printf("\nProblem Solved!\n- Max Depth: %d\n- Number of nodes created: %d\n- Number of nodes expanded: %d\n- Max size of the fringe:%d\n", depth, numCreated, numExpanded, maxFringe);
        } else 
            System.out.printf("\nCould not solve problem\n- Max Depth: %d\\n- Number of nodes created: %d\\n- Number of nodes expanded: %d\\n- Max size of the fringe:%d\n", depth, numCreated, numExpanded, maxFringe);
    }
    
    /**
     * Main Method. Runs the program based on the passed arguments. 
     * @param args the arguments to run the program with. 
     */
    public static void main(String[] args) {
        FifteenProblem problem;
        if (args.length == 0) {
            System.out.println("No arguments passed. Using default state. (A-Star, H1)");
            problem = new FifteenProblem(FifteenProblem.inputTwo, FifteenProblem.ASTAR, FifteenProblem.H1);
        } else 
            problem = new FifteenProblem(args[0], args[1], args[2]);
        problem.printHeadNode();
        System.out.println();
        problem.search();
    }
}
