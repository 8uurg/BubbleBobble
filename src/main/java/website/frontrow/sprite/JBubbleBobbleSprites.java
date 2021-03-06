package website.frontrow.sprite;

import website.frontrow.board.Direction;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains all the sprites for JBubbleBobble.
 */
public final class JBubbleBobbleSprites
    extends SpriteStore
{
    private static final JBubbleBobbleSprites INSTANCE = new JBubbleBobbleSprites();

    /**
     * Private Constructor due to being a Singleton.
     */
    private JBubbleBobbleSprites()
    {
    }

    /**
     * Get the instance of the Singleton Class JBubbleBobbleSprites.
     * @return Returns the instance of the Class.
     */
    public static JBubbleBobbleSprites getInstance()
    {
        return INSTANCE;
    }

    /**
     * The order of the directions of the sprites in an image file.
     */
    private static final Direction[] DIRECTIONS
            = {Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT};

    /**
     * The size of a single sprite frame in pixels.
     */
    private static final int SPRITE_SIZE = 16;

    private static final int FRAMES_DELAY = 300;


    /**
     * The wall sprite.
     * @return The sprite.
     */
    public Sprite getWallSprite()
    {
        return loadSprite("/sprites/wall.png");
    }

    /**
     * The bubble sprite.
     * @return The sprite.
     */
    @SuppressWarnings("magicnumber") // number of frames
    public Map<Direction, Sprite> getBubbleSprite()
    {
        return getDirectionalAnimatedSprite("/sprites/animated_projectile.png", 12);
    }

    /**
     * The platform sprites.
     * @return The sprite.
     */
    public Sprite getPlatformSprite()
    {
        return loadSprite("/sprites/platform.png");
    }

    /**
     * Creates a map with a sprite for each direction for the player.
     * @param index The index of the player in the game.
     * @return The map.
     */
    @SuppressWarnings("magicnumber") // number of frames
    public Map<Direction, Sprite> getPlayerSprite(int index)
    {
        switch(index)
        {
            case 0:
                return getDirectionalAnimatedSprite("/sprites/animated_player.png", 7);
            case 1:
                return getDirectionalAnimatedSprite("/sprites/animated_player_two.png", 7);
            default: return getDirectionalAnimatedSprite("/sprites/animated_player.png", 7);
        }
    }

    /**
     * Creates a map with a sprite for each direction for the enemies.
     * @return The map.
     */
    @SuppressWarnings("magicnumber") // number of frames
    public Map<Direction, Sprite> getEnemySprite()
    {
        return getDirectionalAnimatedSprite("/sprites/animated_zen.png", 4);
    }

    /**
     * Creates a map with a sprite for each direction for captured enemies.
     * @return The map.
     */
    @SuppressWarnings("magicnumber") // number of frames
    public Map<Direction, Sprite> getCapturedEnemySprite()
    {
        // TODO: Fix Frames for an animation of bubble.
        return getDirectionalAnimatedSprite("/sprites/animated_zen_captured.png", 12);
    }

    /**
     * Creates a map with sprites for all directions.
     * @param resource The resource to cut the sprites from.
     * @return The map with sprites for all directions.
     */
    @SuppressWarnings("unused") // We might want to use this in the future for non animated sprites.
    private Map<Direction, Sprite> getDirectionalSprite(String resource)
    {
        Sprite compoundSprites = loadSprite(resource);

        Map<Direction, Sprite> sprites = new HashMap<>();
        for(int i = 0; i < DIRECTIONS.length; i++)
        {
            Sprite directionSprite
                    = compoundSprites.slice(i * SPRITE_SIZE, 0, SPRITE_SIZE, SPRITE_SIZE);
            sprites.put(DIRECTIONS[i], directionSprite);
        }

        return sprites;
    }

    /**
     * Creates an animated sprite for multiple directions.
     * @param resource The source image.
     * @param numberOfFrames The number of frames for the animation.
     * @return The sprites.
     */
    private Map<Direction, Sprite> getDirectionalAnimatedSprite(String resource, int numberOfFrames)
    {
        Sprite allSprites = loadSprite(resource);

        Map<Direction, Sprite> sprites = new HashMap<>();
        for(int dirIndex = 0; dirIndex < DIRECTIONS.length; dirIndex++)
        {
            Sprite uncutFrames
                    = allSprites.slice(dirIndex * SPRITE_SIZE, 0,
                        SPRITE_SIZE, SPRITE_SIZE * numberOfFrames);
            Sprite[] frames = new Sprite[numberOfFrames];
            for(int frameIndex = 0; frameIndex < numberOfFrames; frameIndex++)
            {
                frames[frameIndex]
                        = uncutFrames.slice(0, frameIndex * SPRITE_SIZE, SPRITE_SIZE, SPRITE_SIZE);
            }
            Sprite sprite = new SpriteAnimation(frames, true, true, FRAMES_DELAY);
            sprites.put(DIRECTIONS[dirIndex], sprite);
        }

        return sprites;
    }
}
