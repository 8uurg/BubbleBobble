package website.frontrow.ui;

import java.awt.GridLayout;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import website.frontrow.board.Player;
import website.frontrow.logger.Log;
import website.frontrow.logger.Logable;

/**
 * A Panel containing the score for the player.
 * In case of multiplayer, scores of both players are shown seperately.
 * Created by Remi Flinterman on 8-9-2015.
 */
public class ScorePanel
        extends JPanel
        implements Logable
{
    /**
     * The map of players and their labels with their scores.
     */
    private final Map<Player, JLabel> labels;

    /**
     * Creates a new score panel for the player.
     * When playing multiplayer, a score panel is created for each player.
     * @param players The list of players (max. 2 players).
     */
    ScorePanel(List<Player> players)
    {
        super();
        assert players != null;

        setLayout(new GridLayout(2, players.size()));

        for(int c = 0; c < players.size(); c++)
        {
            add(new JLabel("Player" + c + 1, JLabel.CENTER));
        }

        labels = new LinkedHashMap<>();

        for (Player player : players)
        {
            JLabel jLabel = new JLabel("0", JLabel.CENTER);
            labels.put(player, jLabel);
            add(jLabel);
        }

        addToLog("[ScP]\tScore Panel Created Successfully.");
    }

    /**
     * Returns the labels.
     * @return labels Map<Player, JLabel>
     */
    public Map<Player, JLabel> getLabels()
    {
        return labels;
    }

    /**
     * Log actions in ScorePanel.
     * @param action Input a String that is the action performed.
     */
    @Override
    public void addToLog(String action)
    {
        Log.add(action);
    }
}
