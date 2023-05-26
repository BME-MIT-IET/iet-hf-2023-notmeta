package java.ui;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import java.ui.game.MoveUiTest;
import java.ui.main_menu.MainMenuUiTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({MoveUiTest.class,MainMenuUiTest.class})
public class UiTestSuite {
}
