package cucumber.move_virologist;

import game.Controller;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.Assert.assertEquals;

public class MoveVirologistToNormalField {
    String fieldType = "normal";
    String startFieldId = "F1";
    String endFieldId = "F2";
    String virologistId = "V1";
    Controller controller;

    @Given("virologist is on a field and it is his turn")
    public void virologist_is_on_a_field_and_it_is_his_turn() {
        controller = new Controller();
        controller.CreateField(fieldType, startFieldId);
        controller.CreateField(fieldType, endFieldId);
        controller.NeighborFields(startFieldId, endFieldId);
        controller.CreateVirologist(virologistId, startFieldId);
        controller.Start();
    }

    @When("virologist uses the move action")
    public void virologist_uses_the_move_action() {
        assertEquals(startFieldId, controller.GetCurrentField().GetFieldID());
        controller.MoveVirologist(1);
    }

    @Then("virologist moves to the next available normal field")
    public void virologist_moves_to_the_next_available_normal_field() {
        assertEquals(endFieldId, controller.GetCurrentField().GetFieldID());
    }
}
