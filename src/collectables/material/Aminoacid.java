package collectables.material;//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : collectables.material.Aminoacid.java
//  @ Date : 2022. 03. 23.
//  @ Author : 
//
//

import assets.Backpack;
import assets.virologist.Virologist;
/** Aminoacids are used to create Agents*/
public class Aminoacid extends Materials{
    /**
     * Adds the collectables.material.Aminoacid to a field.Backpack.
     *
     * @param v the field.virologist.Virologist the backpack belongs to(Only used sometimes)
     * @param b the field.Backpack it gets added to
     * @return true if added successfully, false if the Aminoacid cannot be added
     */
    @Override
    public boolean AddToBackpack(Virologist v, Backpack b) {
        return b.add(this);
    }

    /**
     * Removes the collectables.material.Aminoacid from a field.Backpack.
     *
     * @param v the field.virologist.Virologist the backpack belongs to(Only used sometimes)
     * @param b the field.Backpack it gets removed from
     */
    @Override
    public void RemoveFromBackpack(Virologist v, Backpack b) {
        b.Remove(this);
    }

    @Override
    public String getName(){ return "Aminoacid";}
}
