package ui;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ui.game.MoveUiTest;
import ui.main_menu.MainMenuTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({MainMenuTest.class, MoveUiTest.class})
public class UiTestSuite {
}
