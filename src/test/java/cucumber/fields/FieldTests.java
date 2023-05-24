package cucumber.fields;

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

        controller.createField(shelter,shelter);
        controller.createField(normal,normal);
        controller.createField(warehouse,warehouse);
    }

    @When("having {int} amount of nucleotides and {int} aminoacids")
    public void set_materials(int nuc, int amino){
        var field = controller.getField(warehouse);
        for(int i=0; i< nuc; i++) {
            field.getBackpack().add(new Nucleotide());
        }
        for(int i=0; i< amino; i++) {
            field.getBackpack().add(new Aminoacid());
        }
    }

    @And("having {int} axes")
    public void set_equipments(int axes){
        var field = controller.getField(shelter);
        for(int i=0; i<axes; i++){
            field.getBackpack().add(new Axe());
        }
        field.getBackpack().add(new Cloak());
        field.getBackpack().add(new Sack());
        field.getBackpack().add(new Gloves());
    }

    @Then("check the {int}, {int} number of materials")
    public void check_materials(int nuc, int amino){
        var backpack = controller.getField(warehouse).getBackpack();

        assert(backpack.getNucleotide().size() == nuc);
        assert(backpack.getAminos().size() == amino);
    }

    @Then("check the {int} number of equipments")
    public void check_equipments(int axes){
        var backpack = controller.getField(warehouse).getBackpack();
        assert(backpack.getEquipments().size() == axes);
    }
}
