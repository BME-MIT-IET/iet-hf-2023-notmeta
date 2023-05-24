package cucumber.create_agent;

import assets.virologist.Virologist;
import assets.virologist.VirologistBackpack;
import game.Controller;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;

public class NotEnoughAminoacid {
    String fieldType = "normal";
    String agentType = "chorea";
    String startFieldId = "F1";
    String virologistId = "V1";
    Controller controller;
    VirologistBackpack backpack;

    @Given("virologist learned some genome, not enough aminoacid")
    public void virologist_learned_some_genome_not_enough_aminoacid() {
        controller = new Controller();
        controller.CreateField(fieldType, startFieldId);
        controller.CreateVirologist(virologistId, startFieldId);
        controller.GiveNucleotideToVirologist(5, virologistId);
        controller.TeachGenome(agentType, virologistId);
        controller.Start();
    }

    @When("virologist tries to create some agent")
    public void virologist_tries_to_create_some_agent() {
        Virologist virologist = controller.GetCurrentVirologist();
        backpack = virologist.GetBackpack();

        assertEquals(1, virologist.GetLearnedGenomes().size());
        assertEquals(agentType, virologist.GetLearnedGenomes().get(0).GetName());
        assertEquals(0, backpack.GetAgents().size());

        controller.MoveVirologist(0);
        controller.CreateAgent(agentType);
    }

    @Then("virologist receives no agent")
    public void virologist_receives_no_agent() {
        assertEquals(0, backpack.GetAgents().size());
    }
}
