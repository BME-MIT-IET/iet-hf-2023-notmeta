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
        controller.CreateField("normal", fieldId);
        controller.CreateVirologist("V1", fieldId);
        controller.CreateVirologist("V2", fieldId);
        var field = controller.GetField(fieldId);
        field.GetBackpack().Add(new Axe());
        controller.Start();
    }

    @When("the first virologist tries to kill the second virologist but has not picked up the axe")
    public void kill_virologist() {
        var virologist = controller.GetCurrentVirologist();
        var virologist2 = controller.GetVirologist("V2");
        virologist.KillVirologist(virologist2);
    }

    @Then("no virologists should be dead")
    public void check_virologist_is_not_dead() {
        controller.GetVirologists().forEach(virologist -> {
            assert(virologist.GetState() != State.KILLED);
        });
    }

    @And("the first virologist picks up the axe")
    public void pick_up_axe() {
        var virologist = controller.GetCurrentVirologist();
        controller.TakeEquipment(1);
        assert (virologist.GetBackpack().GetEquipments().size() == 1);
    }

    @Then("attempts to kill again")
    public void tries_to_kill_again() {
        var virologist = controller.GetCurrentVirologist();
        var virologist2 = controller.GetVirologist("V2");
        virologist.KillVirologist(virologist2);
    }

    @Then("the second virologist should be dead")
    public void check_virologist_is_dead() {
        var virologist = controller.GetVirologist("V2");
        assert(virologist.GetState() == State.KILLED);
    }
}
