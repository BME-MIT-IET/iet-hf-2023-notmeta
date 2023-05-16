package ui;

import game.ui.SceneLauncher;
import game.ui.game.GameScene;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class MoveUiTest {
    private static final String STARTING_FIELD = "4";
    private static final String NEIGHBOR_FIELD = "10";
    private FrameFixture window;
    private GameScene scene;

    @Before
    public void setUp() {
        SceneLauncher launcher = new SceneLauncher();
        ArrayList<String> players = new ArrayList<>();
        players.add("alma");
        scene = GuiActionRunner.execute(() -> new GameScene(launcher, players));
        window = new FrameFixture(scene);
        window.show();
    }

    @Test
    public void moveToNewField() {
        assertEquals(STARTING_FIELD, scene.GetCurrentField().GetID());

        window.button(NEIGHBOR_FIELD).click();
        window.button("MoveActionBtn").click();
        window.button("MoveActionBtn").requireDisabled();

        assertEquals(NEIGHBOR_FIELD, scene.GetCurrentField().GetID());
    }

    @After
    public void tearDown() {
        window.cleanUp();
    }
}
