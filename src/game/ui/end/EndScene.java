package game.ui.end;

import game.ui.SceneLauncher;

import javax.swing.*;

/**
 * JFrame which will contain a JPanel
 */
public class EndScene extends JFrame{
    /**
     * JFrames constructor that sets up the window's size and operations
     * @param sl SceneLauncher to change the game's states
     */
    public EndScene(SceneLauncher sl){
        this.setTitle("End Scene");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(SceneLauncher.Gamewidth, SceneLauncher.Gameheight);
        this.setLocationRelativeTo(null);
        this.add(new EndPanel(this, sl, sl.GetWinner()));
        this.pack();
        this.setVisible(true);
    }
}
