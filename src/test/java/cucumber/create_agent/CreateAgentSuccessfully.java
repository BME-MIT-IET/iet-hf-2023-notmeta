package cucumber.create_agent;

import assets.virologist.Virologist;
import assets.virologist.VirologistBackpack;
import game.Controller;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;

public class CreateAgentSuccessfully {
    String fieldType = "normal";
    String agentType = "chorea";
    String startFieldId = "F1";
    String virologistId = "V1";
    Controller controller;
    VirologistBackpack backpack;

    @Given("virologist learned some genome, enough aminoacid and nucleotide")
    public void virologist_learned_some_genome_enough_aminoacid_and_nucleotide() {
        controller = new Controller();
        controller.CreateField(fieldType, startFieldId);
        controller.CreateVirologist(virologistId, startFieldId);
        controller.GiveAminoacidToVirologist(5, virologistId);
        controller.GiveNucleotideToVirologist(5, virologistId);
        controller.TeachGenome(agentType, virologistId);
        controller.Start();
    }

    @When("virologist tries to create agent")
    public void virologist_tries_to_create_agent() {
        Virologist virologist = controller.GetCurrentVirologist();
        backpack = virologist.GetBackpack();

        assertEquals(1, virologist.GetLearnedGenomes().size());
        assertEquals(agentType, virologist.GetLearnedGenomes().get(0).GetName());
        assertEquals(0, backpack.GetAgents().size());

        controller.MoveVirologist(0);
        controller.CreateAgent(agentType);
    }

    @Then("virologist receives an agent")
    public void virologist_receives_an_agent() {
        assertEquals(1, backpack.GetAgents().size());
        assertEquals(agentType, backpack.GetAgents().get(0).GetName());
    }
}
