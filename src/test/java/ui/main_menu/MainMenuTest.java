package ui.main_menu;

import game.ui.SceneLauncher;
import game.ui.game.GameScene;
import game.ui.menu.MenuScene;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ui.util.TestComponentNames;

import static org.junit.Assert.assertEquals;

public class MainMenuTest {
    private FrameFixture window;
    private MenuScene menuScene;
    private SceneLauncher launcher;

    @Before
    public void setUp() {
        launcher = new SceneLauncher();
        menuScene = (MenuScene)launcher.getCurrentScene();
        window = new FrameFixture(menuScene);
        window.show();
    }

    @Test
    public void startGameWithZeroPlayers(){
        //ARRANGE

        //ACT
        window.button(TestComponentNames.START_GAME).click();

        //ASSERT
        assertEquals( launcher.getCurrentScene(), menuScene);
    }

    @Test
    public void startGameWithAddingPlayerWithTooLongName(){
        //ARRANGE
        var TOO_LONG_NAME = "12345678901";

        //ACT
        window.button(TestComponentNames.NEW_PLAYER).click();
        var optionPaneFixture = window.optionPane();
        optionPaneFixture.textBox().enterText(TOO_LONG_NAME);
        optionPaneFixture.okButton().click();
        var optionPaneTooLong = window.optionPane();
        optionPaneTooLong.okButton().click();
        window.button(TestComponentNames.START_GAME).click();

        //ASSERT
        assertEquals(launcher.getCurrentScene().getClass(), MenuScene.class);
    }

    @Test
    public void startGameWithAddingPlayers(){
        //ARRANGE
        var PLAYER_NAME = "testPlayer";

        //ACT
        window.button(TestComponentNames.NEW_PLAYER).click();
        var optionPaneFixture = window.optionPane();
        optionPaneFixture.textBox().enterText(PLAYER_NAME);
        optionPaneFixture.okButton().click();
        window.button(TestComponentNames.START_GAME).click();

        //ASSERT
        assertEquals(launcher.getCurrentScene().getClass(), GameScene.class);
    }

    @Test
    public void reachedMaxPlayerCount(){
        //ARRANGE
        var PLAYER_NAME = "testPlayer";

        for(int i=0;i<4;i++){
            window.button(TestComponentNames.NEW_PLAYER).click();
            var optionPaneFixture = window.optionPane();
            optionPaneFixture.textBox().enterText(PLAYER_NAME);
            optionPaneFixture.okButton().click();
        }

        //ACT
        window.button(TestComponentNames.NEW_PLAYER).click();
        var maxPlayerCount = window.optionPane();

        //ASSERT
        maxPlayerCount.requireMessage("Reached max player count");
    }

    @Test
    public void addPlayersAndResetPlayers(){
        //ARRANGE
        var PLAYER1_NAME = "Player1";
        var PLAYER2_NAME = "Player2";

        //ACT
        window.button(TestComponentNames.NEW_PLAYER).click();
        var player1ModalWindowForName = window.optionPane();
        player1ModalWindowForName.textBox().enterText(PLAYER1_NAME);
        player1ModalWindowForName.okButton().click();
        window.button(TestComponentNames.NEW_PLAYER).click();
        var player2ModalWindowForName = window.optionPane();
        player2ModalWindowForName.textBox().enterText(PLAYER2_NAME);
        player2ModalWindowForName.okButton().click();

        //Reset Added players
        window.button(TestComponentNames.RESET_PLAYERS).click();
        //Trying to start game, should fail
        window.button(TestComponentNames.START_GAME).click();
        //ASSERT
        assertEquals(launcher.getCurrentScene().getClass(), MenuScene.class);
    }

    @After
    public void tearDown() {
        window.cleanUp();
    }
}
