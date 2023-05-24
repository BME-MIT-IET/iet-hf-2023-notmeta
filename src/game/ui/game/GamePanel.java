package game.ui.game;

import game.Controller;
import game.ui.SceneLauncher;
import game.ui.game.map.DVirologist;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * JPanel that represents the Game. It contains 4 other JPanels.
 */
public class GamePanel extends JPanel {
    private ArrayList<String> players;

    private SceneLauncher sceneLauncher;
    private GameScene gameScene;
    private MapPanel mapPanel;
    private BackpackPanel backpackPanel;
    private FieldPanel fieldPanel;
    private ActionPanel actionPanel;

    /**
     *constructor of the GamenPanel
     */
    public GamePanel(GameScene gameScene, SceneLauncher sl, ArrayList<String> players, Controller controller){
        this.players = players;
        sceneLauncher = sl;
        this.gameScene = gameScene;
        this.setPreferredSize(new Dimension(SceneLauncher.Gamewidth, SceneLauncher.Gameheight));
        this.setBackground(Color.white);
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        mapPanel = new MapPanel(gameScene);
        backpackPanel = new BackpackPanel(gameScene, sceneLauncher, players, controller);
        fieldPanel = new FieldPanel(gameScene, sceneLauncher, players, controller);
        actionPanel = new ActionPanel(gameScene, sceneLauncher, players, controller, backpackPanel, mapPanel);
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(mapPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(backpackPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(fieldPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(actionPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(mapPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(backpackPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(fieldPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(actionPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))

        );

    }
}
