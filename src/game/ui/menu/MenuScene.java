package game.ui.menu;

import game.ui.SceneLauncher;

import javax.swing.*;

/**
 * JFrame which will contain a JPanel
 */
public class MenuScene extends JFrame {

    /**
     * JFrames constructor that sets up the window's size and operations
     * @param sl SceneLauncer to change the game's states
     */
    public MenuScene(SceneLauncher sl){
        this.setTitle("Menu");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(SceneLauncher.GAME_WIDTH, SceneLauncher.GAME_HEIGHT);
        this.setLocationRelativeTo(null);
        this.add(new MenuPanel(this, sl));
        this.pack();
        this.setVisible(true);
    }
}
