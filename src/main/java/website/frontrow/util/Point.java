package website.frontrow.util;

/**
 * A point or vector.
 */
public class Point
{

    private double x;
    private double y;

    /**
     * A simple point.
     * @param x x coordinate
     * @param y y coordinate
     */
    public Point(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Get the x value.
     * @return The value of x.
     */
    public double getX()
    {
        return x;
    }

    /**
     * Set the x value.
     * @param x The value to set x to.
     */
    public void setX(double x)
    {
        this.x = x;
    }

    /**
     * Get the y value.
     * @return The value of y.
     */
    public double getY()
    {
        return y;
    }

    /**
     * Set the y value.
     * @param y The value to set x to.
     */
    public void setY(double y)
    {
        this.y = y;
    }

    /**
     * Add two points together.
     * @param b The point to add.
     * @return The result.
     */
    public Point add(Point b)
    {
        return new Point(this.x + b.x, this.y + b.y);
    }

    /**
     * Divide both coordinates of a point by a divisor.
     * @param divisor The number to devide by.
     * @return A point with both coordinates devided by the divisor.
     */
    public Point divide(double divisor)
    {
        return new Point(this.x / divisor, this.y / divisor);
    }


    @Override
    public boolean equals(Object other)
    {
        if (other instanceof Point)
        {
            Point that = (Point) other;
            return  this.x == that.x
                    &  this.y == that.y;
        }
        return false;
    }

    @Override
    public String toString()
    {
        return "Point(" + x + ", " + y + ")";
    }

    @SuppressWarnings("checkstyle:magicnumber")
    private static int prime = 31;
    @Override
    public int hashCode()
    {
        return  prime * (prime + new Double(x).hashCode()) + new Double(y).hashCode();
    }
}