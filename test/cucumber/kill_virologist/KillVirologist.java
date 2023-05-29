package cucumber.kill_virologist;

import assets.virologist.State;
import collectables.equipment.Axe;
import game.Controller;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class KillVirologist {


    private Controller controller;

    @Given("two virologists on one field containing an axe")
    public void initialize_scenario() {
        var fieldId = "F1";
        controller = new Controller();
        controller.createField("normal", fieldId);
        controller.createVirologist("V1", fieldId);
        controller.createVirologist("V2", fieldId);
        var field = controller.getField(fieldId);
        field.getBackpack().add(new Axe());
        controller.start();
    }

    @When("the first virologist tries to kill the second virologist but has not picked up the axe")
    public void kill_virologist() {
        var virologist = controller.getCurrentVirologist();
        var virologist2 = controller.getVirologist("V2");
        virologist.killVirologist(virologist2);
    }

    @Then("no virologists should be dead")
    public void check_virologist_is_not_dead() {
        controller.getVirologists().forEach(virologist -> {
            assert(virologist.getState() != State.KILLED);
        });
    }

    @And("the first virologist picks up the axe")
    public void pick_up_axe() {
        var virologist = controller.getCurrentVirologist();
        controller.takeEquipment(1);
        assert (virologist.getBackpack().getEquipments().size() == 1);
    }

    @Then("attempts to kill again")
    public void tries_to_kill_again() {
        var virologist = controller.getCurrentVirologist();
        var virologist2 = controller.getVirologist("V2");
        virologist.killVirologist(virologist2);
    }

    @Then("the second virologist should be dead")
    public void check_virologist_is_dead() {
        var virologist = controller.getVirologist("V2");
        assert(virologist.getState() == State.KILLED);
    }
}
