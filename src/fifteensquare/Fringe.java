package fifteensquare;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 * An abstract representation of a fringe. Contains various helper methods for fetching and adding data 
 * to the contained fringe. 
 * @author Cade Reynoldson
 */
public class Fringe {
    
    /** The fringe of the search algorithms in the form of a collection. */
    private Collection<SearchNode> fringe;
    
    /** A secondary fringe of the search algorithms, used as secondary storage for iterative deeping search. */
    private Collection<SearchNode> secondaryFringe;
    
    /** The search method this fringe is initialized to handle. */
    public String searchMethod;
    
    /** The maximum size this fringe reached. */
    private int maxFringe;
    
    /** The number of nodes created and added to this fringe. */
    private int numCreated;
    
    /** 
     * The maximum depth of nodes to put in the primary field. This field is used by iterative
     * deepening search and depth limited search.  
     */
    private int maxDepth;
    
    /** The heuristic or option this fringe uses. */
    private String option;
    
    /** Set of visited SearchNodes in the form of a hashset for quick containment check. */
    public HashSet<SearchNode> visitedNodes; 
    
    /** A set of visited states in the form of a hashset for quick containment check. */
    public HashSet<String> visitedStates;
    
    /**
     * Instantiates the fringe with a given search method. 
     * @param searchMethod the search method. 
     */
    public Fringe(String searchMethod, String option) {
        maxFringe = Integer.MIN_VALUE;
        numCreated = 0;
        this.searchMethod = searchMethod;
        this.option = option;
        visitedNodes = new HashSet<SearchNode>();
        visitedStates = new HashSet<String>();
        if (searchMethod.equals("BFS")) //BFS uses a linked list (queue).
            fringe = new LinkedList<SearchNode>();
        else if (searchMethod.equals("DFS") || searchMethod.equals("DLS")) { //DFS and DLS use a stack. 
            fringe = new Stack<SearchNode>();
            if (searchMethod.equals("DLS"))
                maxDepth = Integer.parseInt(option);
        }
        else if (searchMethod.equals("GBFS")) { //Custom comparator in a priority queue for GBFS.
            fringe = new PriorityQueue<SearchNode>(50, (Comparator<? super SearchNode>) new Comparator<SearchNode>() {
                @Override
                public int compare(SearchNode s1, SearchNode s2) {
                    return Integer.compare(s1.getHeuristic(), s2.getHeuristic());
                }
            });
        } else if (searchMethod.equals("AStar")) { //Custom comparator in a priority queue for A*.
            fringe = new PriorityQueue<SearchNode>(50, (Comparator<? super SearchNode>) new Comparator<SearchNode>() {
                @Override
                public int compare(SearchNode s1, SearchNode s2) {
                    return Integer.compare(s1.getHeuristic() + s1.getDepth(), s2.getHeuristic() + s2.getDepth());
                }
            });
        } else if (searchMethod.equals("ID")) {
            fringe = new Stack<SearchNode>();
            secondaryFringe = new Stack<SearchNode>();
            maxDepth = 1;
        }
    }
    
    /**
     * Adds a search node to this fringe. 
     * @param node the search node to add. 
     */
    public void add(SearchNode node) {
        if (visitedStates.contains(node.getStateString())) { //If this state has been visited, don't add to fringe. (Cycle check)  
            return;
        }
        if (searchMethod.equals("DLS") && node.getDepth() > maxDepth) //If DLS is used and node depth is larger than max depth, return. 
            return;
        else if (searchMethod.equals("ID") && node.getDepth() > maxDepth) {
            secondaryFringe.add(node);
            return;
        }
        if (option.equals("h1")) { //Add node with heuristics in context to both goals. 
            SearchNode n = node.setHeuristics("h1");
            fringe.add(n);
        } else if (option.equals("h2")) {
            SearchNode n = node.setHeuristics("h2");
            fringe.add(n);
        }
        fringe.add(node);
        if (fringe.size() > maxFringe)
            maxFringe = fringe.size();
        numCreated++;
    }
    
    /**
     * Marks a given search node as visited. 
     * @param node the node to mark as visited. 
     */
    public void markVisited(SearchNode node) {
        visitedNodes.add(node);
        visitedStates.add(node.getStateString());
    }
    
    /**
     * Returns the next search node in this fringe. Returns null if no node exists in fringe. 
     * @return the next search node in this fringe. 
     */
    public SearchNode getNext() {
        SearchNode theNode = null; 
        try {
            if (searchMethod.equals("BFS")) {
                theNode = ((LinkedList<SearchNode>) fringe).pop();
            } else if (searchMethod.equals("DFS") || searchMethod.equals("DLS")) {
                theNode = ((Stack<SearchNode>) fringe).pop();
            } else if (searchMethod.equals("GBFS") || searchMethod.equals("AStar")) {
                theNode = ((PriorityQueue<SearchNode>) fringe).remove();
            } else if (searchMethod.equals("ID")) {
                if (fringe.size() - 1 == 0) { //If the current fringe is about to be empty, pop and swap. 
                    theNode = ((Stack<SearchNode>) fringe).pop();
                    fringe = secondaryFringe;
                    secondaryFringe = new Stack<SearchNode>();
                    maxDepth++;
                    return theNode;
                }
                theNode = ((Stack<SearchNode>) fringe).pop();
            }
            if (visitedStates.contains(theNode.getStateString()) && theNode != null) {
                return getNext();
            }
        } catch (Exception e) { //Exception thrown, means no more nodes exist to be fetched. 
            return null;
        }
        return theNode;
    }
    
    /**
     * Returns the number of nodes created by this fringe. 
     * @return the number of nodes created by this fringe.
     */
    public int getNumCreated() {
        return numCreated; 
    }
    
    /**
     * Returns the maximum size this fringe reached. 
     * @return the maximum size this fringe reached. 
     */
    public int getMaxFringe() {
        return maxFringe; 
    }
    
    /**
     * Returns true/false based on whether this fringe contains values. 
     * @return true/false based on whether this fringe contains values. 
     */
    public boolean hasNext() {
        return fringe.size() > 0;
    }
    
    public void printFringeStats() {
        System.out.printf("Fringe stats - Contained %d \n", fringe.size());
    }
}
