package website.frontrow.board;

import org.junit.Test;
import website.frontrow.util.Point;

import static org.junit.Assert.assertEquals;

/**
 * Test the enemy class.
 */
public class EnemyTest extends UnitTest
{
    /**
     * Test the constructor of the Enemy class.
     */
    @Test
    public void constructorTest()
    {
        Enemy e = new Enemy(super.p);
        assertEquals(e.getLocation(), super.p);
    }

    @Override
    public Unit getTestUnit(boolean alive, Point start, Point end)
    {
        Enemy e = new Enemy(start);
        e.setMotion(end);
        if(!alive)
        {
            e.kill();
        }
        return e;
    }
}
