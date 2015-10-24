package website.frontrow.game;

import java.awt.event.KeyEvent;
import website.frontrow.board.Player;
import website.frontrow.board.UnitFactory;
import website.frontrow.util.keymap.KeyAction;
import website.frontrow.util.keymap.KeyCodeKey;
import website.frontrow.util.keymap.KeyRegistry;
import website.frontrow.util.keymap.SingleKeyAction;

/**
 * Player actions.
 */
public class PlayerActions
{
    private KeyAction jump;
    private KeyAction left;
    private KeyAction right;
    private KeyAction shoot;

    /**
     * Create a PlayerActions object.
     * @param player The player for which these actions are meant.
     * @param game The current game.
     * @param unitFactory The factory to create bubbles with.
     */
    public PlayerActions(Player player, Game game, UnitFactory unitFactory)
    {
        this.jump = new SingleKeyAction(() -> player.jump(), true);
        this.left = new SingleKeyAction(() -> player.goLeft(), false);
        this.right = new SingleKeyAction(() -> player.goRight(), false);
        this.shoot = new SingleKeyAction(() -> player.shoot(game, unitFactory), true);
    }

    /**
     * Get the jump action.
     * (For example to rebind)
     * @return The jump action.
     */
    public KeyAction getJump()
    {
        return jump;
    }

    /**
     * Get the go left action.
     * (For example to rebind)
     * @return The go left action.
     */
    public KeyAction getLeft()
    {
        return left;
    }

    /**
     * Get the go right action.
     * (For example to rebind)
     * @return The go right action.
     */
    public KeyAction getRight()
    {
        return right;
    }

    /**
     * Get the shoot action.
     * (For example to rebind)
     * @return The shoot action.
     */
    public KeyAction getShoot()
    {
        return shoot;
    }

    /**
     * Bind using default bindings.
     * This method may need a better spot than here.
     * @param registry Registry to register to.
     */
    public void rebindDefaults(KeyRegistry registry)
    {
        registry.register(new KeyCodeKey(KeyEvent.VK_UP), jump);
        registry.register(new KeyCodeKey(KeyEvent.VK_LEFT), left);
        registry.register(new KeyCodeKey(KeyEvent.VK_RIGHT), right);
        registry.register(new KeyCodeKey(KeyEvent.VK_SPACE), shoot);
    }
}
