package cucumber.move_virologist;

import game.Controller;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.Assert.assertEquals;

public class TryToMoveWhenUnable {
    String fieldType = "normal";
    String effectType = "paralysis";
    String startFieldId = "F1";
    String endFieldId = "F2";
    String virologistId = "V1";
    Controller controller;

    @Given("virologist is on a field and is effected by paralysis")
    public void virologist_is_on_a_field_and_it_effected_by_paralysis() {
        controller = new Controller();
        controller.createField(fieldType, startFieldId);
        controller.createField(fieldType, endFieldId);
        controller.neighborFields(startFieldId, endFieldId);
        controller.createVirologist(virologistId, startFieldId);
        controller.effectVirologist(effectType, virologistId);
        controller.start();
    }

    @When("virologist tries to use the move action")
    public void virologist_tries_to_use_the_move_action() {
        assertEquals(startFieldId, controller.getCurrentField().getFieldID());
        controller.moveVirologist(1);
    }

    @Then("virologist does not move away")
    public void virologist_does_not_move_away() {
        assertEquals(startFieldId, controller.getCurrentField().getFieldID());
    }
}

