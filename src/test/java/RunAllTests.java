import cucumber.RunCucumberTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ui.UiTestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({UiTestSuite.class, RunCucumberTest.class})
public class RunAllTests {
}

