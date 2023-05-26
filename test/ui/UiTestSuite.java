package ui;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ui.game.MoveUiTest;
import ui.main_menu.MainMenuUiTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({MoveUiTest.class,MainMenuUiTest.class})
public class UiTestSuite {
}
