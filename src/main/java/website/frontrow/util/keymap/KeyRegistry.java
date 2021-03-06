package website.frontrow.util.keymap;

import java.util.HashMap;

/**
 * Keeps track of events registered to certain keys.
 */
public class KeyRegistry
{

    private HashMap<Key, KeyAction> registry = new HashMap<>();

    /**
     * Checks whether a given key is already in use.
     *
     * @param key The key to check
     * @return Whether the key is used.
     */
    public boolean isUsed(Key key)
    {
        return registry.containsKey(key);
    }

    /**
     * Register a key with a given action.
     *
     * @param key    The key to bind
     * @param action The action to execute on this key
     */
    public void register(Key key, KeyAction action)
    {
        if (this.isUsed(key))
        {
            registry.get(key).unbind(key, this);
        }

        action.bind(key, this);
        registry.put(key, action);
    }

    /**
     * Register a key with a given action.
     *
     * @param keyCode The key to bind.
     * @param action The action to execute on this key.
     */
    public void register(int keyCode, KeyAction action)
    {
        register(new KeyCodeKey(keyCode), action);
    }

    /**
     * Unregisters a key.
     *
     * @param key Key to unregister
     */
    public void unregister(Key key)
    {
        if (!isUsed(key))
        {
            return;
        }

        registry.get(key).unbind(key, this);
        registry.remove(key);
    }

    /**
     * Execute the action for a given key.
     *
     * @param key The pressed key.
     * @return Whether to reset the key or not.
     */
    public boolean handle(Key key)
    {
        KeyAction action = registry.get(key);

        if (action == null)
        {
            // No action, so release the key immediately.
            return true;
        }

        action.execute();
        return action.resetKey();
    }
}
