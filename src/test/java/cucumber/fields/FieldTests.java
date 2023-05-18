package cucumber.fields;

import assets.field.Normal;
import assets.field.Shelter;
import assets.field.WareHouse;
import collectables.equipment.Axe;
import collectables.equipment.Cloak;
import collectables.equipment.Gloves;
import collectables.equipment.Sack;
import collectables.material.Aminoacid;
import collectables.material.Nucleotide;
import game.Controller;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class FieldTests {

    String normal = "normal";
    String  shelter = "shelter";
    String warehouse = "warehouse";
    Controller controller;

    @Given("initialized fields")
    public void init_fields() {
        controller = new Controller();

        controller.CreateField(normal,normal);
        controller.CreateField(shelter,shelter);
        controller.CreateField(warehouse,warehouse);
    }

    @When("having {int} amount of nucleotides and {int} aminoacids")
    public void set_materials(int nuc, int amino){
        var field = controller.GetField(warehouse);
        for(int i=0; i< nuc; i++) {
            field.GetBackpack().Add(new Nucleotide());
        }
        for(int i=0; i< amino; i++) {
            field.GetBackpack().Add(new Aminoacid());
        }
    }

    @And("having {int} axes")
    public void set_equipments(int axes){
        var field = controller.GetField(shelter);
        for(int i=0; i<axes; i++){
            field.GetBackpack().Add(new Axe());
        }
        field.GetBackpack().Add(new Cloak());
        field.GetBackpack().Add(new Sack());
        field.GetBackpack().Add(new Gloves());
    }

    @Then("check the {int}, {int} number of materials")
    public void check_materials(int nuc, int amino){
        var backpack = controller.GetField(warehouse).GetBackpack();

        assert(backpack.GetNucleotide().size() == nuc);
        assert(backpack.GetAminos().size() == amino);
    }

    @Then("check the {int} number of equipments")
    public void check_equipments(int axes){
        var backpack = controller.GetField(warehouse).GetBackpack();
        assert(backpack.GetEquipments().size() == axes);
    }
}
