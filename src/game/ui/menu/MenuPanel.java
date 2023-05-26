package game.ui.menu;

import game.ui.SceneLauncher;
import game.ui.StyledMenuButtonUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * JPanel class represents the menu for the game
 */
public class MenuPanel extends JPanel implements ActionListener {

    private ArrayList<String> playernames = new ArrayList<>();

    private SceneLauncher sceneLauncher;
    private MenuScene menuScene;
    private PlayersPanel playersPanel;
    private JButton NewPlayer;
    private JButton StartGame;
    private JButton ResetGame;
    private JLabel Players;
    private JLabel Welcome;

    /**
     * JPanel's constructor that sets up the buttons and labels.
     * @param menuScene the parent JFrame class.
     * @param sl the main class that will be calles if there's a gamestate change.
     */
    public MenuPanel(MenuScene menuScene, SceneLauncher sl) {
        sceneLauncher = sl;
        this.menuScene = menuScene;

        this.setPreferredSize(new Dimension(SceneLauncher.GAME_WIDTH, SceneLauncher.GAME_HEIGHT));
        this.setBackground(Color.white);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        Welcome = new JLabel("Virologist The Game");
        Welcome.setFont(new Font("Calibri", Font.PLAIN, 60));
        Welcome.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(Welcome);

        StartGame = new JButton("Start Game");
        StartGame.setName("startGame");
        StartGame.setFont(new Font("Calibri", Font.PLAIN, 50));
        StartGame.setBackground(new Color(0x2dce98));
        StartGame.setUI(new StyledMenuButtonUI());
        StartGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        StartGame.addActionListener(this);
        this.add(StartGame);

        this.add(Box.createRigidArea(new Dimension(0, 20)));

        NewPlayer = new JButton("New player");
        NewPlayer.setName("newPlayer");
        NewPlayer.setFont(new Font("Calibri", Font.PLAIN, 50));
        NewPlayer.setBackground(new Color(0x2dce98));
        NewPlayer.setUI(new StyledMenuButtonUI());
        NewPlayer.setAlignmentX(Component.CENTER_ALIGNMENT);

        NewPlayer.addActionListener(this);
        this.add(NewPlayer);

        this.add(Box.createRigidArea(new Dimension(0, 20)));

        Players = new JLabel("Players:");
        Players.setFont(new Font("Calibri", Font.PLAIN, 60));
        Players.setName("players");
        Players.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(Players);



        playersPanel=new PlayersPanel();
        this.add(playersPanel);

        ResetGame = new JButton("Reset players");
        ResetGame.setName("resetPlayers");
        ResetGame.setFont(new Font("Calibri", Font.PLAIN, 10));
        ResetGame.addActionListener(this);
        ResetGame.setUI(new StyledMenuButtonUI());
        ResetGame.setBackground(new Color(0x2dce98));
        ResetGame.setLocation(0,0);
        ResetGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(ResetGame);
    }



    /**
     * If a button pressed this action will be performed.
     * Depending which button was pressed there will be a certain change on the screen.
     * @param e ActionEvent which holds the source.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == StartGame) {
            if(playernames.size() > 0){
                sceneLauncher.setPlayerNames(playernames);
                sceneLauncher.switchScenes(SceneLauncher.GLOBAL_GAME_STATES.GAME);
                menuScene.dispose();
                playernames.clear();
            }
            else{
                JOptionPane.showMessageDialog(null, "Please add az least one player", "0player", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (e.getSource() == NewPlayer) {
            if(playernames.size()==4){
                JOptionPane.showMessageDialog(null, "Reached max player count", "Gang", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("Images/gutyuligang.gif"));
            }
            else {
                String newPlayer = JOptionPane.showInputDialog("Please add a nickname max(10 character)", "New Player Name");
                if (newPlayer != null) {
                    if (newPlayer.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "You submitted an empty name");
                    } else if (newPlayer.length() > 10) {
                        JOptionPane.showMessageDialog(null, "Too long name");
                    } else {
                        playernames.add(newPlayer);
                        playersPanel.setPlayerNames(playernames.get(playernames.size() - 1));
                    }
                    repaint();
                }
            }
        }
        else if(e.getSource()==ResetGame){
            playernames.clear();
            playersPanel.resetPlayerNames();
            repaint();
        }
    }
}
