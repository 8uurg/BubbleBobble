package website.frontrow.board;

import org.junit.Test;

import website.frontrow.game.GameConstants;
import website.frontrow.util.Point;
import static org.junit.Assert.assertEquals;

/**
 * Test the enemy class.
 */
public class EnemyTest
        extends MoverTest
{
    /**
     * Test the constructor of the Enemy class.
     */
    @Test
    public void constructorTest()
    {
        Enemy e = new Enemy(super.p, null);
        assertEquals(e.getLocation(), super.p);
    }

    /**
     * Test whether the caught time is returned properly.
     */
    @Test
    public void getChaughtTimeTest()
    {
        Enemy e = new Enemy(super.p, null);

        assertEquals(Enemy.TIME_TO_ESCPAPE, e.getChaughtTime());
    }

    @Override
    public Mover getTestMover(boolean alive, Point start, Point end)
    {
        Enemy e = new Enemy(start, null);
        e.setMotion(end);
        if(!alive)
        {
            e.kill();
        }
        return e;
    }
    
    /**
     * Test the tick method.
     */
    @Override
    public void testTick()
    {
        Mover u = getTestMover(true, new Point(0, 0), new Point(GameConstants.TICKS_PER_SEC, 0));

        u.tick(emptyLevel);
        
        //An enemy might move
        assertEquals(0.25, u.getLocation().getX(), 2);
    }
}
