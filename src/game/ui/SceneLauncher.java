package game.ui;

import game.ui.end.EndScene;
import game.ui.game.GameScene;
import game.ui.menu.MenuScene;

import javax.swing.*;
import java.util.ArrayList;

/**
 * The main class which is responsible for the UI changes, playernames, and for the screen size;
 */
public class SceneLauncher {

    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 600;
    public ArrayList<String> players = new ArrayList<>();
    private String winner;

    /**
     * Main states for the game;
     */
    public enum GLOBAL_GAME_STATES {
        MENU,
        GAME,
        END
    }

    private JFrame currentScene;

    public JFrame getCurrentScene(){
        return currentScene;
    }

    /**
     * Constructor creates a MenuScene, pass itself as a parameter;
     */
    public SceneLauncher(){
        currentScene = new MenuScene(this);
    }

    /**
     * Function that responsible for changing the different states, and scenes for the game;
     * @param globalGameState the state that resembles the scene
     */
    public void SwitchScenes(GLOBALGAMESTATES globalgamestates){
        switch (globalgamestates){
            case Menu:
                currentScene = new MenuScene(this);
                break;
            case Game:
                currentScene = new GameScene(this,players);
                break;
            case End:
                currentScene = new EndScene(this);
                break;
        }
    }

    /**
     * Setter for players names
     * @param players array that holds the player's names
     */
    public void setPlayerNames(ArrayList<String> players){
        this.players = players;
    }

    public void setWinner(String winner){
        this.winner = winner;
    }

    public String getWinner(){
        return winner;
    }
}
