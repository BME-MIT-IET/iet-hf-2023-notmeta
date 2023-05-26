package cucumber.collect_material;

import assets.Backpack;
import collectables.material.Aminoacid;
import collectables.material.Nucleotide;
import game.Controller;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;

public class CollectMaterial {
    String normalFieldId = "F1";
    String warehouseId = "W1";
    String virologistId = "V1";
    Controller controller;

    public enum MaterialType {
        AminoAcid,
        Nucleotide
    }

    @ParameterType("AminoAcid|Nucleotide")
    public MaterialType materialType(String materialTypeString) {
        return MaterialType.valueOf(materialTypeString);
    }

    @Given("a virologist on a normal field with a neighboring warehouse")
    public void virologist_on_normal_field_with_neighboring_warehouse() {
        controller = new Controller();
        controller.createField("normal", normalFieldId);
        controller.createField("warehouse", warehouseId);
        controller.neighborFields(normalFieldId, warehouseId);
        controller.createVirologist(virologistId, normalFieldId);
        controller.start();
    }

    private Backpack getWarehouseBackPack() {
        var warehouse = controller.getField(warehouseId);
        return warehouse.getBackpack();
    }

    @When("virologist moves to the neighboring warehouse with {int} amount of {materialType}")
    public void virologistMovesToWarehouseWithAmountOfMaterialType(int quantity, MaterialType materialType) {
        var bp = getWarehouseBackPack();
        if (materialType == MaterialType.AminoAcid) {
            for (int i = 0; i < quantity; i++) {
                bp.add(new Aminoacid());
            }
        } else if (materialType == MaterialType.Nucleotide) {
            for (int i = 0; i < quantity; i++) {
                bp.add(new Nucleotide());
            }
        }
        controller.moveVirologistRandomOff(1);
    }

    @And("virologist tries to collect {int} {materialType}")
    public void virologistTriesToCollectMaterial(int quantity, MaterialType materialType) {
        // Only one virologist, so that should be the current player
        if (materialType == MaterialType.AminoAcid) {
            controller.takeAminoacid(quantity);
        } else if (materialType == MaterialType.Nucleotide) {
            controller.takeNucleotide(quantity);
        }
    }

    @Then("the virologist should have {int} amount of {materialType}")
    public void theVirologistShouldHaveQuantityAmountOfMaterial(int quantity, MaterialType materialType) {
        var virologist = controller.getCurrentVirologist();
        var backpack = virologist.getBackpack();
        if (materialType == MaterialType.AminoAcid) {
            assertEquals(quantity, backpack.getAminos().size());
        } else if (materialType == MaterialType.Nucleotide) {
            assertEquals(quantity, backpack.getNucleotide().size());
        }
    }

    @And("the warehouse will have {int} amount of {materialType}")
    public void warehouse_has_quantity_amount_of_given_material(int quantity, MaterialType materialType) {
        var bp = getWarehouseBackPack();
        if (materialType == MaterialType.AminoAcid) {
            assertEquals(quantity, bp.getAminos().size());
        } else if (materialType == MaterialType.Nucleotide) {
            assertEquals(quantity, bp.getNucleotide().size());
        }
    }
}