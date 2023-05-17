package cucumber.collect_equipment;

import collectables.equipment.*;
import game.Controller;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;

public class CollectEquipment {
    String normalFieldId = "F1";
    String virologistId = "V1";
    Controller controller;

    @ParameterType("Axe|Cloak|Gloves|Sack")
    public Equipment equipmentType(String equipmentTypeString) {
        return switch (equipmentTypeString) {
            case "Axe" -> new Axe();
            case "Cloak" -> new Cloak();
            case "Gloves" -> new Gloves();
            case "Sack" -> new Sack();
            default -> throw new IllegalArgumentException("Unsupported equipment type: " + equipmentTypeString);
        };
    }

    @Given("a virologist on a normal field who has already moved")
    public void virologist_on_normal_field_who_has_already_moved() {
        controller = new Controller();
        controller.CreateField("normal", normalFieldId);
        controller.CreateVirologist(virologistId, normalFieldId);
        controller.Start();
        //Move the virologist to the same field its standing on
        controller.MoveVirologistRandomOff(0);
    }
    @When("normal field has one {equipmentType}")
    public void normal_field_has_one_equipment_of_given_type(Equipment equipment) {
        var field = controller.GetField(normalFieldId);
        var bp = field.GetBackpack();
        bp.Add(equipment);
    }

    @And("virologist tries to collect the first equipment it finds on the ground")
    public void virologist_tries_to_collect_the_first_equipment_it_finds_on_the_ground() {
        // Only one virologist, so that should be the current player
        controller.TakeEquipment(1);
    }

    @Then("the virologist should have one of {equipmentType}")
    public void the_virologist_should_have_one_of_equipmentType(Equipment equipment) {
        var virologist = controller.GetCurrentVirologist();
        var bp = virologist.GetBackpack();
        var equipments = bp.GetEquipments();
        assertEquals(1, equipments.size());
        assertEquals(equipment.GetName(), equipments.get(0).GetName());
    }

    @And("the field will have zero of {equipmentType}")
    public void warehouse_has_quantity_amount_of_given_material(Equipment equipment) {
        var field = controller.GetField(normalFieldId);
        var bp = field.GetBackpack();
        var equipments = bp.GetEquipments();
        assertEquals(0, equipments.size());
    }
}
