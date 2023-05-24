package cucumber.move_virologist;

import assets.virologist.behavior.movebehavior.RandomMove;
import game.Controller;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class MoveRandomly {
    String fieldType = "normal";
    String effectType = "chorea";
    String startFieldId = "F1";
    String endFieldId = "F2";
    String virologistId = "V1";
    Controller controller;

    @Given("virologist is on a field and is effected by chorea")
    public void virologist_is_on_a_field_and_is_effected_by_chorea() {
        controller = new Controller();
        controller.CreateField(fieldType, startFieldId);
        controller.CreateField(fieldType, endFieldId);
        controller.NeighborFields(startFieldId, endFieldId);
        controller.CreateVirologist(virologistId, startFieldId);
        controller.EffectVirologist(effectType, virologistId);
        controller.Start();
    }

    @When("virologist uses move")
    public void virologist_uses_move() {
        assertEquals(startFieldId, controller.GetCurrentField().GetFieldID());
        assertThat(controller.GetCurrentVirologist().GetMoveBehavior(), instanceOf(RandomMove.class));
        controller.MoveVirologistRandomOff(0);
    }

    @Then("virologist moves randomly")
    public void virologist_moves_randomly() {
        assertEquals(endFieldId, controller.GetCurrentField().GetFieldID());
    }
}
