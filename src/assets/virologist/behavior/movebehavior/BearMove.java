package assets.virologist.behavior.movebehavior;

import assets.field.Field;
import assets.virologist.State;
import assets.virologist.Virologist;
import collectables.agent.Bear;

import java.util.ArrayList;

public class BearMove implements MoveBehavior, java.io.Serializable{
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    @Override
    public int GetPriority() {
        return 3;
    }

    /**
     * The field.virologist.Virologist moves to a random direction.
     *
     * @param v the field.virologist.Virologist who tries to move
     * @param d the direction he wants to move
     */

    @Override
    public void MoveToField(Virologist v, int d) {
        Field f = v.GetRoute().GetLocation();
        ArrayList<Integer> directions = f.GetDirections();
        int newD = getRandomNumber(0, directions.size());
        Field f2 = f.GetNeighbour(newD);
        f.Remove(v);
        f2.Accept(v);
        f2.DestroyMaterials();
        ArrayList<Virologist> virologistsOnField = f2.GetVirologists();
        for (Virologist vOnField: virologistsOnField) {
            if(!vOnField.GetName().equals(v.GetName()))
                vOnField.GetInfected(v,new Bear());
        }
        v.GetRoute().Add(f2);
        v.SetState(State.AFTER_ACTION);
    }

    @Override
    public void MoveRandomOff(Virologist v, int d) {
        Field f = v.GetRoute().GetLocation();
        int newD = 0;
        if(d != f.GetDirections().size() - 1)
            newD = d + 1;
        Field f2 = f.GetNeighbour(newD);
        f.Remove(v);
        f2.AcceptRandomOff(v);
        f2.DestroyMaterials();
        ArrayList<Virologist> virologistsOnField = f2.GetVirologists();
        for (Virologist vOnField: virologistsOnField) {
            vOnField.GetInfected(v,new Bear());
        }
        v.GetRoute().Add(f2);
        v.SetState(State.BEFORE_ACTION);
    }
}
