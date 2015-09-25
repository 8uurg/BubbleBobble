package website.frontrow.util;

import java.util.ArrayList;

import website.frontrow.board.Bubble;
import website.frontrow.board.Enemy;
import website.frontrow.board.Player;
import website.frontrow.board.Unit;
import website.frontrow.board.Mover;
import website.frontrow.game.GameConstants;
import website.frontrow.level.Cell;
import website.frontrow.level.Level;
import website.frontrow.logger.Log;
import website.frontrow.logger.Logable;

/**
 * This Class is for handling collisions during the game.
 */
public class CollisionHandler
	implements Logable
{
	@SuppressWarnings("checkstyle:magicnumber")
	private static final double PRECISION = 0.0001d;
	private static final double ONE_DEV_PRECISION = 1 / PRECISION;
	private static final int SAMPLING = 64;
    private static final double LOC_OFFSET = 0.99d;
    private Level level;

    
    /**
     * Input a grid to read from.
     * @param level The level to handle the collisions for.
     * Input the grid to use.
     */
    public CollisionHandler(Level level)
    {
        this.level = level;
    }
    
    /**
     * Create an ArrayList with box values for a mover.
     * @param location
     * Input the location point of a mover
     * @return
     * Return an ArrayList of all box values.
     */
    public ArrayList<Point> buildBox(Point location)
    {   	
    	ArrayList<Point> aabb = new ArrayList<>();
        aabb.add(new Point(location.getX(), location.getY()));
        aabb.add(new Point(location.getX(), location.getY() + LOC_OFFSET));
        aabb.add(new Point(location.getX() + LOC_OFFSET, location.getY()));
        aabb.add(new Point(location.getX() + LOC_OFFSET, location.getY() + LOC_OFFSET));
        return aabb;
    }

    /**
     * Check if a mover collides with another mover. Call collisionApplier if it does.
     * The idea is that we check the movers projected location against the boxed
     * locations of all other movers in a loop.
     * If any of the box corners of the moving mover falls within
     * the box of another mover there is a collision.
     * There is also a check to filter whether the mover is colliding with itself.
	 * @param user The unit to check for.
	 */
    public void checkUnitsAABB(Unit user)
	{
		ArrayList<Unit> units = this.level.getUnits();
		AABB me = user.getAABB();
		for(Unit other: units)
		{
			if(other != user && other.getAABB().overlaps(me))
			{
				applyCollision(user, other);
			}
		}
	}

	/**
	 * Call the method that handles collisions.
	 * @param collider The mover that initiates the collision.
	 * @param colidee The mover that is being collided with.
	 */
	// TODO: Swap this out for something fancy that uses reflection. But this works fine for now.
	public void applyCollision(Unit collider, Unit colidee)
	{
		if(collider instanceof Player)
		{
			addToLog("[CH]\tPlayer collided with something.");
			playerCollision((Player) collider, colidee);
		}
		else if (colidee instanceof Player)
		{
			addToLog("[CH]\tSomething collided with a Player.");
			playerCollision((Player) colidee, collider);
		}

		if(collider instanceof Bubble)
		{
			bubbleCollision((Bubble) collider, colidee);
		}
		else if(colidee instanceof Bubble)
		{
			addToLog("[CH]\tSomething collided with a Bubble.");
			bubbleCollision((Bubble) colidee, collider);
		}
	}

	/**
	 * Called when a player collides with another mover.
	 * @param player Player which is currently colliding.
	 * @param other The mover that was collided with.
	 */
	public void playerCollision(Player player, Unit other)
	{
		// TODO: Make the player lose a life or something else.
	}

	/**
	 * Called when a bubble collides with another mover.
	 * @param bubble Bubble which is currently colliding.
	 * @param other The mover that was collided with.
	 */
	public void bubbleCollision(Bubble bubble, Unit other)
	{
		if(other instanceof Enemy)
		{
			bubble.capture((Enemy) other);
			// Kill the bubble for now.
			bubble.kill();
		}

	}

	/**
	 * Log actions from CollisionHandler.
	 * @param action Input a String that is the action performed.
	 */
	@Override
	public void addToLog(String action)
	{
		Log.add(action);
	}

	/**
	 * Check an AABB versus the level, to see if there are any collisions.
	 * @param aabb The aabb to check
	 * @param motion The motion to keep in mind
	 * @return True when there is a collision, false when not.
	 */
	public Cell checkLevelAABB(AABB aabb, Point motion)
	{
		// Find the cells we need to check.
		Grid<Cell> cells = level.getCells();
		int minx = (int) Math.max(Math.floor(aabb.getStart().getX()), 0);
		int miny = (int) Math.max(Math.floor(aabb.getStart().getY()), 0);
		int maxx = (int) Math.min(Math.ceil(aabb.getEnd().getX()), cells.getWidth());
		int maxy = (int) Math.min(Math.ceil(aabb.getEnd().getY()), cells.getHeight());
		// Check the cells.
		for(int y = miny; y < maxy; y++)
		{
			for(int x = minx; x < maxx; x++)
			{
				Point c = new Point(x, y);
				Cell cell = cells.get(x, y);
				Point aabbo = cell.getAABBOffset();
				AABB tile = new AABB(c.add(aabbo), c.add(aabbo).add(cell.getAABBDimensions()));
				if (cell.collides(motion) && aabb.overlaps(tile))
				{
					if(cell.getType() == Cell.WALL)
					{
						return Cell.WALL;
					}
					return Cell.PLATFORM;
				}
			}
		}

		return Cell.EMPTY;
	}

	/**
	 * Get the amount of steps for a mover.
	 * @param mover Mover to compute for.
	 * @param wh Width and height of movers AABB.
	 * @return amount of steps.
	 */
	private int getSteps(Mover mover, Point wh)
	{
		double stepsX = Math.abs(mover.getMotion().getX()) / wh.getX();
		double stepsY = Math.abs(mover.getMotion().getY()) / wh.getY();

		return (int) Math.ceil(Math.max(stepsX, stepsY) * SAMPLING);
	}

	// 8 is the max recusion depth.
	@SuppressWarnings("checkstyle:magicnumber")
	private static final int MAX_DEPTH = 8;
	/**
	 * Sweep from start until steps until you get a collision.
	 * @param start Starting position of the sweep.
	 * @param delta Delta between each step.
	 * @param wh Width and height of the unit to sweep.
	 * @param steps Amount of steps to do.
	 * @param level Recursion loop cap.
	 * @return Collision after sweep
	 */
	private Collision sweep(
			Mover mover, Point start, Point delta, Point wh, Point motion, int steps, int level)
	{
		if(level >= MAX_DEPTH)
		{
			return new Collision(start, true);
		}
		Point found = start;
		for(int i = 0; i <= steps; i++)
		{
			Point current = found.add(delta);
			Cell cell = checkLevelAABB(new AABB(current, current.add(wh)), motion);
			if(cell.getType() != Cell.EMPTY)
			{
				if(cell.getType() == Cell.WALL)
				{
					mover.onWallCollision();
				}
				return sweep(mover, found, delta.divide(steps), wh, motion, steps, level + 1);
			}

			found = current;
		}
		return new Collision(found, level != 0);
	}

	/**
	 * Find the next position for the given mover.
	 * @param mover Mover to get next position for.
	 * @return The next position of a given mover.
	 */
	public Collision findNextPosition(Mover mover)
	{
		Point wh = mover.getAABBDimensions();
		int steps = getSteps(mover, wh);

		if(steps == 0)
		{
			return new Collision(mover.getLocation(), false);
		}
		Point delta = mover.getMotion().divide(steps).divide(GameConstants.TICKS_PER_SEC);

		Collision collision = sweep(mover, mover.getLocation(), delta, mover.getAABBDimensions(),
				mover.getMotion(), steps, 0);

        double newXLocation
                = Math.round(collision.getPoint().getX() * ONE_DEV_PRECISION) * PRECISION;
        double newYLocation
                = Math.round(collision.getPoint().getY() * ONE_DEV_PRECISION) * PRECISION;

        Point newLocation = new Point(newXLocation, newYLocation);

        newLocation = checkLevelBounds(mover, newLocation);
		return new Collision(newLocation, collision.isCollided());
	}

	/**
	 * Checks whether the the mover is outside the bounds
	 * of the level and gives the correct new location.
     *
     * When the player goes through the bottom it appears at the top of the level.
     * When the player goes through the top of the level it appears at the bottom of the level.
     *
     * If the player tries to go through the sides of the level, nothing happens.
	 * @param mover the mover to check the level bounds for.
	 * @param newLocation the new location to check.
	 * @return The new location based on the bounds of the level.
	 */
	public Point checkLevelBounds(Mover mover, Point newLocation)
	{
        AABB boundingBox = mover.getAABB();
        double lowerLimit = this.level.getCells().getHeight() - boundingBox.getYRange().length();
        double upperLimit = 0;

        double leftLimit = 0;
        double rightLimit = this.level.getCells().getWidth() - boundingBox.getXRange().length();

        double newXLocation = Math.min(Math.max(leftLimit, newLocation.getX()), rightLimit);
        double newYLocation = newLocation.getY();

        if(newYLocation > lowerLimit)
        {
            newYLocation = upperLimit;
			addToLog("[CH]\tA mover went through the bottom of the level.");
        }
        if(newYLocation < upperLimit)
        {
            newYLocation = lowerLimit;
			addToLog("[CH]\tA mover went through the top of the level.");
        }

		return new Point(newXLocation, newYLocation);
	}
}
