package collectables;//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : collectables.Collectable.java
//  @ Date : 2022. 03. 23.
//  @ Author : 
//
//

import assets.Backpack;
import assets.virologist.Virologist;

/** Things the Virologists can store in their field.Backpack*/
public interface Collectable {
	/**
	 * Adds the collectables.Collectable to a field.Backpack
	 *
	 * @param v the field.virologist.Virologist the backpack belongs to(Only used sometimes)
	 * @param b the field.Backpack it gets added to
	 * @return true if added successfully, false if the Collectable cannot be added
	 */
	boolean AddToBackpack(Virologist v, Backpack b);

	/**
	 * Removes the collectables.Collectable from a field.Backpack
	 *
	 * @param v the field.virologist.Virologist the backpack belongs to(Only used sometimes)
	 * @param b the field.Backpack it gets removed from
	 */
	void RemoveFromBackpack(Virologist v, Backpack b);

	/** Applies the effect of the collectables.Collectable to the field.virologist.Virologist*/
	void Apply(Virologist v);

	/** Removes the effect of the collectables.Collectable from the field.virologist.Virologist*/
	void Remove(Virologist v);

	String getName();
}
