package ui.game;

import game.ui.SceneLauncher;
import game.ui.game.GameScene;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ui.util.PlayerNames;
import ui.util.TestComponentNames;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class MoveUiTest {
    private static final String NEIGHBOR_FIELD = "10";
    private FrameFixture window;
    private GameScene gameScene;
    private SceneLauncher launcher;

    @Before
    public void setUp() {
        launcher = new SceneLauncher();
        ArrayList<String> players = new ArrayList<>();
        players.add(PlayerNames.VALID_PLAYER_NAME1);
        launcher.setPlayerNames(players);
        launcher.switchScenes(SceneLauncher.GLOBAL_GAME_STATES.GAME);
        gameScene = (GameScene) launcher.getCurrentScene(); //GuiActionRunner.execute(() -> (GameScene) launcher.getCurrentScene());
        window = new FrameFixture(gameScene);
        window.show();
    }

    @Test
    public void moveToNewField() {
        //ARRANGE

        //ACT
        window.button(NEIGHBOR_FIELD).click();
        window.button(TestComponentNames.MOVE_BUTTON).click();
        window.button(TestComponentNames.MOVE_BUTTON).requireDisabled();

        //ASSERT
        assertEquals(NEIGHBOR_FIELD, gameScene.getCurrentField().getId());
    }

    @After
    public void tearDown() {
        window.cleanUp();
    }
}
