package website.frontrow.level;

import org.junit.Test;
import website.frontrow.board.Bubble;
import website.frontrow.board.Player;
import website.frontrow.board.Unit;
import website.frontrow.util.Point;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Test the cells.
 */
public class CellTest
{
    /**
     * Test collide with point 0,0.
     */
    @Test
    public void testEmptyCollision()
    {
        Unit unit = new Player(new Point(0, 0));
        assertFalse(Cell.EMPTY.collides(unit));
    }

    /**
     * Test platform collision 0,5.
     */
    @Test
    public void testPlatformCollision1()
    {
        Unit unit = new Bubble(new Point(0, 0), new Point(0, 5));
        assertTrue(Cell.PLATFORM.collides(unit));
    }

    /**
     * Test platform collision with 0,0.
     */
    @Test
    public void testPlatformCollision2()
    {
        Unit unit = new Bubble(new Point(0, 0), new Point(0, 0));
        assertFalse(Cell.PLATFORM.collides(unit));
    }

    /**
     * Test Wall Collision.
     */
    @Test
    public void testWallCollision()
    {
        Unit unit = new Player(new Point(0, 0));
        assertTrue(Cell.WALL.collides(unit));
    }
}