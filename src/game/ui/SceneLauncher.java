package game.ui;

import game.ui.end.EndScene;
import game.ui.game.GameScene;
import game.ui.menu.MenuScene;

import java.util.ArrayList;

/**
 * The main class which is responsible for the UI changes, playernames, and for the screen size;
 */
public class SceneLauncher {

    public static final int Gamewidth = 800;
    public static final int Gameheight = 600;
    public ArrayList<String> players = new ArrayList<>();
    private String winner;

    /**
     * Main states for the game;
     */
    public enum GLOBALGAMESTATES{
        Menu,
        Game,
        End
    }

    /**
     * Constructor creates a MenuScene, pass itself as a parameter;
     */
    public SceneLauncher(){
        new MenuScene(this);
    }

    /**
     * Function that responsible for changing the different states, and scenes for the game;
     * @param globalgamestates the state that resembles the scene
     */
    public void SwitchScenes(GLOBALGAMESTATES globalgamestates){
        switch (globalgamestates){
            case Menu:
                new MenuScene(this);
                break;
            case Game:
                new GameScene(this,players);
                break;
            case End:
                new EndScene(this);
                break;
        }
    }

    /**
     * Setter for players names
     * @param players array that holds the player's names
     */
    public void SetPlayerNames(ArrayList<String> players){
        this.players = players;
    }

    public void SetWinner(String winner){
        this.winner = winner;
    }
    //TODO
    public String GetWinner(){
        return winner;
    }
}
