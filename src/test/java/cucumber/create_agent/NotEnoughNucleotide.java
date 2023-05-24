package cucumber.create_agent;

import assets.virologist.Virologist;
import assets.virologist.VirologistBackpack;
import game.Controller;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;

public class NotEnoughNucleotide {
    String fieldType = "normal";
    String agentType = "chorea";
    String startFieldId = "F1";
    String virologistId = "V1";
    Controller controller;
    VirologistBackpack backpack;

    @Given("virologist learned some genome, not enough nucleotide")
    public void virologist_learned_some_genome_not_enough_nucleotide() {
        controller = new Controller();
        controller.createField(fieldType, startFieldId);
        controller.createVirologist(virologistId, startFieldId);
        controller.giveAminoacidToVirologist(5, virologistId);
        controller.teachGenome(agentType, virologistId);
        controller.start();
    }

    @When("virologist tries to create new agent")
    public void virologist_tries_to_create_new_agent() {
        Virologist virologist = controller.getCurrentVirologist();
        backpack = virologist.GetBackpack();

        assertEquals(1, virologist.GetLearnedGenomes().size());
        assertEquals(agentType, virologist.GetLearnedGenomes().get(0).GetName());
        assertEquals(0, backpack.GetAgents().size());

        controller.moveVirologist(0);
        controller.createAgent(agentType);
    }

    @Then("virologist receives no new agent")
    public void virologist_receives_no_new_agent() {
        assertEquals(0, backpack.GetAgents().size());
    }
}
