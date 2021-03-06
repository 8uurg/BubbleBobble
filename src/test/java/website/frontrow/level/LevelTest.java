package website.frontrow.level;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import website.frontrow.board.Enemy;
import website.frontrow.board.Player;
import website.frontrow.board.Unit;
import website.frontrow.util.Grid;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test for level methods.
 */
@RunWith(MockitoJUnitRunner.class)
public class LevelTest
{
    private ArrayList<Unit> units = new ArrayList<>();
    private Grid<Cell> grid = new Grid<>(1, 1);

    @Mock
    private Player player;
    @Mock
    private Enemy enemy;
    private ArrayList<Unit> mockunits = new ArrayList<>();
    private ArrayList<Player> mockplayer = new ArrayList<>();

    /**
     * Set up.
     *  - Add items to mocklist.
     */
    @Before
    public void setUp()
    {
        mockunits.add(player);
        mockunits.add(enemy);
        mockunits.add(enemy);

        mockplayer.add(player);
    }

    /**
     * Test getCells().
     */
    @Test
    public void testGetCells()
    {
        Level level = new Level(mockplayer, units, grid);
        assertEquals(grid, level.getCells());
    }

    /**
     *  test getUnits().
     */
    @Test
    public void testGetUnits()
    {
        Level level = new Level(mockplayer, units, grid);
        assertEquals(units, level.getUnits());
    }

    /**
     * Test ticking the units.
     */
    @Test
    public void testTick()
    {
        Level level = new Level(mockplayer, mockunits, grid);
        level.tick();
        verify(player, times(1)).tick(level);
        verify(enemy, times(2)).tick(level);
    }

    /**
     * Tests the add method.
     */
    @Test
    public void testAddUnit()
    {
        Level level = new Level(mockplayer, mockunits, grid);
        Unit mockedUnit = mock(Unit.class);
        when(mockedUnit.isAlive()).thenReturn(true);
        level.addUnit(mockedUnit);

        assertFalse(level.getUnits().contains(mockedUnit));

        level.tick();
        assertTrue(level.getUnits().contains(mockedUnit));
    }

    /**
     * Test if we can set the number of enemies in the level.
     */
    @Test
    public void testSetEnemyNumber()
    {
        Level level = new Level(mockplayer, mockunits, grid);
        level.setNumberOfEnemies(1);
        assertEquals(level.getNumberOfEnemies(), 1);
    }

    /**
     * Test RemoveObserver.
     */
    @Test
    public void testRemoveObserver()
    {
        Level level = new Level(mockplayer, mockunits, grid);
        level.addObserver(null);

        assertTrue(level.getObservers().contains(null));

        level.removeObserver(null);
        assertFalse(level.getObservers().contains(null));
    }
}