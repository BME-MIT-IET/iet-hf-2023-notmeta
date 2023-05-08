package assets.virologist.behavior.killbehavior;

import assets.virologist.State;
import assets.virologist.Virologist;
import collectables.equipment.Axe;

public class Kill implements KillBehavior, java.io.Serializable{
    final Axe axe;
    public Kill(Axe axe){
        this.axe=axe;

    }
    @Override
    public void KillVirologist(Virologist v1, Virologist v2) {
        if(axe.GetDurability() == 0){
            return;
        }
        axe.DecreaseEquipmentDurability();
        v2.SetState(State.KILLED);
        v1.SetState(State.AFTER_ACTION);
    }
}
