package assets.field;

import assets.virologist.Virologist;
import assets.virologist.behavior.learnbehavior.Learn;
import collectables.agent.Bear;
import collectables.genome.Genome;
public class BearLaboratory extends Laboratory{


    /**
     * Constructor
     *
     * @param g genome
     */
    public BearLaboratory(Genome g) {
        super(g);
    }

    @Override
    /**
     *  If the field.virologist.Virologist steps on a field.field.BearLaboratory he got infected with bear agent
     *
     * @param v virologist
     **/
    public void Accept(Virologist v) {
        v.SetLearnBehavior(new Learn());
        v.GetInfected(new Virologist(),new Bear());
        virologists.add(v);
    }

    @Override
    public void AcceptRandomOff(Virologist v){
        v.GetInfectedRandomOff(v,new Bear());
        virologists.add(v);
    }

    @Override
    public String GetType(){
        return "bearlaboratory";
    }
}
