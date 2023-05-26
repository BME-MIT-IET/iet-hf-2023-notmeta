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
        switch (equipmentTypeString) {
            case "Axe":
                return new Axe();
            case "Cloak":
                return new Cloak();
            case "Gloves":
                return new Gloves();
            case "Sack":
                return new Sack();
            default:
                throw new IllegalArgumentException("Unsupported equipment type: " + equipmentTypeString);
        }
    }

    @Given("a virologist on a normal field who has already moved")
    public void virologist_on_normal_field_who_has_already_moved() {
        controller = new Controller();
        controller.createField("normal", normalFieldId);
        controller.createVirologist(virologistId, normalFieldId);
        controller.start();
        //Move the virologist to the same field its standing on
        controller.moveVirologistRandomOff(0);
    }
    @When("normal field has one {equipmentType}")
    public void normal_field_has_one_equipment_of_given_type(Equipment equipment) {
        var field = controller.getField(normalFieldId);
        var bp = field.getBackpack();
        bp.add(equipment);
    }

    @And("virologist tries to collect the first equipment it finds on the ground")
    public void virologist_tries_to_collect_the_first_equipment_it_finds_on_the_ground() {
        // Only one virologist, so that should be the current player
        controller.takeEquipment(1);
    }

    @Then("the virologist should have one of {equipmentType}")
    public void the_virologist_should_have_one_of_equipmentType(Equipment equipment) {
        var virologist = controller.getCurrentVirologist();
        var bp = virologist.getBackpack();
        var equipments = bp.getEquipments();
        assertEquals(1, equipments.size());
        assertEquals(equipment.getName(), equipments.get(0).getName());
    }

    @And("the field will have zero of {equipmentType}")
    public void warehouse_has_quantity_amount_of_given_material(Equipment equipment) {
        var field = controller.getField(normalFieldId);
        var bp = field.getBackpack();
        var equipments = bp.getEquipments();
        assertEquals(0, equipments.size());
    }
}
