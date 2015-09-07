package website.frontrow.board;

import website.frontrow.util.Point;

<<<<<<< HEAD
import website.frontrow.util.CollisionHandler;
import website.frontrow.level.Square;
=======
import website.frontrow.util.Point;
>>>>>>> dev-mapreader

/**
 * A Unit, or Entity is something that is part of a level, but not restricted to grid cells.
 */
public class Unit
{
    private Direction direction;
    private Direction faceLeft;
    /**
     * Amount of lives an entity has
     */
    private boolean alive;

    /**
     * Current location of an entity.
     */
    private Point location;
    /**
     * Current direction of motion.
     */
    private Point motion;

    /**
     * Constructor of the Unit Class.
     * @param alive Whether this entity is alive
     * Input the amount of lives the Unit has.
     * @param direction
     * Set the direction the unit is going in.
     * @param faceLeft
     * A unit always starts facing the right.
     */
    public Unit(boolean alive, Point location, Point motion)
    {
        this.direction = Direction.RIGHT;
        this.faceLeft  = Direction.RIGHT;
        this.alive = alive;
        this.location = location;
        this.motion = motion;
    }

    /**
     * Get the status of the unit, whether it's dead or alive.
     * @return
     * Return true if the Unit is alive, false if it's dead.
     */
    public boolean isAlive()
    {
        return alive;
    }

    /**
     * Get the location of a Unit.
     * @return Location of a unit.
     */
    public Point getLocation() {
        return location;
    }

    /**
     * Set the location of a unit.
     * Warning: Does not care about walls.
     * @param location New location of a unit.
     */
    public void setLocation(Point location) {
        this.location = location;
    }

    /**
     * Set direction of motion.
     * @return The direction of motion of this unit.
     */
    public Point getMotion() {
        return motion;
    }

    /**
     * Set direction of motion.
     * @param motion The new direction of motion of this unit.
     */
    public void setMotion(Point motion) {
        this.motion = motion;
    }
    
    /**
     * Gets the direction the Unit is facing.
     * @Return 
     * Returns a Direction: either Left or Right.
     */
    public Direction faceLeft(){
    	return this.faceLeft;
    }
    
    /**
     * Set the direction the Unit is facing. Either left or right.
     * @param dir
     * Set the Direction value for the direction the Unit faces.
     */
    public void setFace(Direction dir){
    	if(dir == Direction.LEFT || dir == Direction.RIGHT){
    		this.faceLeft = dir;
    	}
    	else{
    		throw new IllegalArgumentException(
    				"Direction given is invalid, it should be either LEFT or RIGHT.");
    	}
    }
    
    /**
     * Get the direction the Unit is facing.
     * @return
     * Returns a Direction value.
     */
    public Direction getDirection(){
    	return this.direction;
    }
    
    /**
     * Set the direction the Unit is facing.
     * Checks whether the direction is a movement along the x-axis 
     * and adjusts the 'faceLeft' value accordingly
     * @param dir
     * Sets the Direction value.
     */
    public void setDirection(Direction dir){
    	this.direction = dir;
    	
    	if(dir == Direction.RIGHT){
    		setFace(Direction.RIGHT);
    	}
    	else if(dir == Direction.LEFT){
    		setFace(Direction.LEFT);
    	}
    }
}
