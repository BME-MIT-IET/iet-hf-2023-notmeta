package game.ui.end;

import game.ui.SceneLauncher;
import game.ui.StyledMenuButtonUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * JPanel that respenbles that tell who is the winner.
 */
public class EndPanel extends JPanel implements ActionListener {

    EndScene endScene;
    SceneLauncher sceneLauncher;
    JButton backToMenu;
    JLabel gif;

    /**
     * Constructor sets up the panel with winner's name
     * @param endScene the parent JFrame
     * @param sl SceneLauncher to change state to menu
     * @param winner the winners name
     */
    public EndPanel(EndScene endScene, SceneLauncher sl, String winner){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setPreferredSize(new Dimension(SceneLauncher.Gamewidth, SceneLauncher.Gameheight));
        this.setBackground(Color.white);
        this.endScene=endScene;
        this.sceneLauncher=sl;

        WinnerPanel winnerPanel = new WinnerPanel(winner);
        this.add(winnerPanel);

        backToMenu = new JButton("Go back to menu");
        backToMenu.setFont(new Font("Calibri", Font.PLAIN, 30));
        backToMenu.setBackground(new Color(0x2dce98));
        backToMenu.setUI(new StyledMenuButtonUI());
        backToMenu.addActionListener(this);
        gif=new JLabel(new ImageIcon("Images/brentrambo.gif"));
        gif.setAlignmentX(CENTER_ALIGNMENT);
        this.add(gif);
        this.add(Box.createRigidArea(new Dimension(0, 20)));
        backToMenu.setAlignmentX(CENTER_ALIGNMENT);
        this.add(backToMenu);

    }


    /**
     * Action to return to the menuscene
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == backToMenu){
            sceneLauncher.SwitchScenes(SceneLauncher.GLOBALGAMESTATES.Menu);
            sceneLauncher.players.clear();
            endScene.dispose();
        }
    }
}
