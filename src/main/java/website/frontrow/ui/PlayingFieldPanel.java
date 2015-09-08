package website.frontrow.ui;

import website.frontrow.level.Level;
import website.frontrow.sprite.ImageStore;

import javax.swing.JPanel;
import javax.swing.BorderFactory;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;

/**
 * A JPanel in which the playing field is going to be drawn.
 * @see javax.swing.JPanel
 */
public class PlayingFieldPanel extends JPanel
{

    private static final Color BACKGROUND_COLOR = Color.BLACK;

    private static final int BLOCK_SIZE = 32; //Pixels

    private static final int BORDER_WIDTH = 20; //Pixels

    private Level level;

    /**
     * Creates a playing field panel in which the game is drawn.
     * The panel is enclosed by a border.
     * @param level The level to display in the playing field.
     */
    public PlayingFieldPanel(Level level)
    {
        super();
        this.setBackground(Color.BLACK);
        this.setBorder(
                BorderFactory.createMatteBorder(BORDER_WIDTH, BORDER_WIDTH,
                        BORDER_WIDTH, BORDER_WIDTH, new ImageStore().getBorderImage())
        );

        Dimension size = new Dimension(
                BLOCK_SIZE * level.getCells().getWidth() + (2 * BORDER_WIDTH),
                BLOCK_SIZE * level.getCells().getHeight() + (2 * BORDER_WIDTH)
        );
        this.setPreferredSize(size);
        this.setMinimumSize(size);
        this.level = level;
    }

    @Override
    public void paint(Graphics graphics)
    {
        super.paint(graphics);

        level.draw(graphics, BORDER_WIDTH, BORDER_WIDTH,
                getSize().width - 2 * BORDER_WIDTH, getSize().height - 2 * BORDER_WIDTH);
    }
}