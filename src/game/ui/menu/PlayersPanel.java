package game.ui.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

/**
 * JPanel that holds and draw out the players name's
 */
public class PlayersPanel extends JPanel {
    ArrayList<String> players;
    public PlayersPanel(){
        players=new ArrayList<>();
        this.setVisible(true);
    }

    /**
     * Setter for a new player's name
     * @param player new player's name
     */
    public void setPlayerNames(String player){players.add(player);}

    /**
     * Clears the players names to set up new names.
     */
    public void resetPlayerNames(){
        players.clear();
    }

    /**
     * Paint function that is called by system
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        drawPlayerNames(g);
    }

    /**
     * Draw playernames to the screen
     * @param g
     */
    private void drawPlayerNames(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.BLACK);
        Font nicknameFont = new Font("Consolas", Font.PLAIN, 30);
        g2d.setFont(nicknameFont);
        FontMetrics fontMetrics = g2d.getFontMetrics(g2d.getFont());
        g2d.setStroke(new BasicStroke(5));
        g2d.draw(new Line2D.Float(0,0,800,0));
        if (!(players.isEmpty())) {
            for (int i = 0; i < players.size(); i++) {
                g2d.drawString(players.get(i), 400-fontMetrics.stringWidth(players.get(i))/2, fontMetrics.getHeight()+i * fontMetrics.getHeight());
            }
        }
    }

}
