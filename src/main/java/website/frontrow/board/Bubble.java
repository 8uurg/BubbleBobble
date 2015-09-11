package website.frontrow.board;

import website.frontrow.sprite.Sprite;
import website.frontrow.sprite.SpriteStore;
import website.frontrow.util.Point;

/**
 * A bubble!
 */
public class Bubble extends Unit
{
    public static final SpriteStore SS = new SpriteStore();

    // A bubble can contain an enemy.
    private Enemy contains;

    /**
     * Constructor of the Bubble Unit.
     * Input a byte to specify the amount of lives this unit has.
     * @param position The starting position of the bubble.
     * @param motion The starting motion of the bubble.
     */
    public Bubble(Point position, Point motion)
    {
        super(true, position, motion);
    }

    /**
     * Get the enemy that is contained within the bubble.
     * @return
     * Return the enemy that is in the bubble.
     */
    public Enemy getContains()
    {
        return contains;
    }

    /**
     * Capture other.
     * @param other The enemy to capture.
     */
    public void capture(Enemy other)
    {
        this.contains = other;
        // Kill the enemy for good measure.
        // (Do not forget to revive and re-add to the level when he escapes.)
        other.kill();
    }

    /**
     * Set an enemy to be contained by a bubble.
     * @param contains
     * Sets the enemy as contained by the bubble.
     */
    public void setContains(Enemy contains)
    {
        this.contains = contains;
    }

    @Override
    public Sprite getSprite()
    {
        return SS.getBubbleSprite();
    }

    @Override
    public void onWallCollision()
    {
        this.kill();
    }

    @Override
    public void applyGravity()
    {
        // Ignore gravity.
    }
}