package fifteensquare;

/**
 * Stores XY Coordinates in the form of short integers. 
 * @author Cade Reynoldson
 * @version 1.0
 */
public class XYPoint {
    
    /** The x value. */
    private short x;
    
    /** The y value. */
    private short y;
    
    /**
     * Instantiates this class with x and y values. 
     * @param x the x value. 
     * @param y the y value.
     */
    public XYPoint(int x, int y) {
        this.x = (short) x;
        this.y = (short) y;
    }
    
    /**
     * Returns the stored x value.
     * @return the stored x value.
     */
    public short getX() {
        return x;
    }
    
    /**
     * Sets the x value. 
     * @param x
     */
    public void setX(short x) {
        this.x = x;
    }
    
    /**
     * Returns the stored y value. 
     * @return the stored y value. 
     */
    public short getY() {
        return y;
    }
    
    /**
     * Sets the y value. 
     * @param y
     */
    public void setY(short y) {
        this.y = y;
    }
    
    /**
     * Returns the manhattan distance from the parameterized points. 
     * @param x1 the x value to compare. 
     * @param y1 the y value to compare. 
     * @return the manhattan distance from the parameterized points. 
     */
    public int manhattanDistance(short x1, short y1) {
        return Math.abs(x - x1) + Math.abs(y - y1);
    }
}
