package ui.main_menu;

import game.ui.SceneLauncher;
import game.ui.game.GameScene;
import game.ui.menu.MenuScene;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ui.util.PlayerNames;
import ui.util.TestComponentNames;

import static org.junit.Assert.assertEquals;

public class MainMenuTest {
    private FrameFixture window;
    private MenuScene menuScene;
    private SceneLauncher launcher;
    private JButtonFixture startGameButton;
    private JButtonFixture resetPlayersButton;
    private JButtonFixture addNewPlayerButton;
    @Before
    public void setUp() {
        launcher = new SceneLauncher();
        menuScene = (MenuScene) launcher.getCurrentScene();
        window = new FrameFixture(menuScene);
        startGameButton = window.button(TestComponentNames.START_GAME);
        resetPlayersButton = window.button(TestComponentNames.RESET_PLAYERS);
        addNewPlayerButton = window.button(TestComponentNames.NEW_PLAYER);
        window.show();
    }

    @Test
    public void startGameWithZeroPlayers() {
        //ARRANGE

        //ACT
        startGameButton.click();

        //ASSERT
        assertEquals(launcher.getCurrentScene(), menuScene);
    }

    @Test
    public void startGameWithAddingPlayerWithTooLongName() {
        //ARRANGE

        //ACT
        addPlayerToTest(PlayerNames.INVALID_PLAYER_NAME);

        var optionPaneTooLong = window.optionPane();
        optionPaneTooLong.okButton().click();

        startGameButton.click();

        //ASSERT
        assertEquals(launcher.getCurrentScene().getClass(), MenuScene.class);
    }

    @Test
    public void startGameWithAddingPlayers() {
        //ARRANGE

        //ACT
        addPlayerToTest(PlayerNames.VALID_PLAYER_NAME1);
        startGameButton.click();

        //ASSERT
        assertEquals(launcher.getCurrentScene().getClass(), GameScene.class);
    }

    @Test
    public void reachedMaxPlayerCount() {
        //ARRANGE

        for (int i = 0; i < 4; i++) {
            addPlayerToTest(PlayerNames.VALID_PLAYER_NAME1);
        }

        //ACT
        addNewPlayerButton.click();
        var maxPlayerCount = window.optionPane();

        //ASSERT
        maxPlayerCount.requireMessage("Reached max player count");
    }

    @Test
    public void addPlayersAndResetPlayers() {
        //ARRANGE

        //ACT
        addPlayerToTest(PlayerNames.VALID_PLAYER_NAME1);
        addPlayerToTest(PlayerNames.VALID_PLAYER_NAME2);

        //Reset Added players
        resetPlayersButton.click();
        //Trying to start game, should fail
        startGameButton.click();

        //ASSERT
        assertEquals(launcher.getCurrentScene().getClass(), MenuScene.class);
    }

    @After
    public void tearDown() {
        window.cleanUp();
    }

    private void addPlayerToTest(String playerName) {
        addNewPlayerButton.click();
        var player1ModalWindowForName = window.optionPane();
        player1ModalWindowForName.textBox().enterText(playerName);
        player1ModalWindowForName.okButton().click();
    }
}
