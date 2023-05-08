package game.ui.end;

import game.ui.SceneLauncher;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

/**
 * JPanel that writes out the winners name
 */
public class WinnerPanel extends JPanel {

    private String winner;

    /**
     * constructor of WinnerPanel
     * @param winner
     */
    public WinnerPanel(String winner){
        this.winner=winner;
        this.setVisible(true);
    }

    /**
     * Paint function that is called by system
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        drawOutWinner(g);
    }

    /**
     * Method that draws out the winners name
     * @param g
     */
    private void drawOutWinner(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.BLACK);
        Font nicknameFont = new Font("Consolas", Font.BOLD, 40);
        g2d.setFont(nicknameFont);
        FontMetrics fontMetrics = g2d.getFontMetrics(g2d.getFont());
        g2d.drawString(winner + " won!", SceneLauncher.Gamewidth/2-fontMetrics.stringWidth(winner+" won!")/2,150);
    }
}
