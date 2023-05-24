package assets;//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : field.Backpack.java
//  @ Date : 2022. 03. 23.
//  @ Author : 
//
//

import collectables.equipment.Equipment;
import collectables.material.Aminoacid;
import collectables.material.Nucleotide;
import java.util.ArrayList;

/** Stores materials and Equipments*/
public class Backpack implements java.io.Serializable{
	/** Constructor*/
	public Backpack(){
		nucleotids = new ArrayList<>();
		aminoacids = new ArrayList<>();
		equipments = new ArrayList<>();
	}

	protected final ArrayList<Nucleotide> nucleotids;

	protected final ArrayList<Aminoacid> aminoacids;

	protected final ArrayList<Equipment> equipments;

	/** The Backpack can always store the Equipment*/
	public boolean add(Equipment e) {
		equipments.add(e);
		return true;
	}


	/** The Backpack can always store the Aminoacid*/
	public boolean add(Aminoacid a) {
		aminoacids.add(a);
		return true;
	}
	/** The Backpack can always store the Nucleotide*/
	public boolean add(Nucleotide n) {
		nucleotids.add(n);
		return true;
	}

	public ArrayList<Equipment> getEquipments() {
		return equipments;
	}

	public ArrayList<Aminoacid> getAminos() {
		return  aminoacids;
	}

	public ArrayList<Nucleotide> getNucleotide() {
		return nucleotids;
	}

	public void Remove(Equipment e) {
		equipments.remove(e);
	}

	public void Remove(Aminoacid a) {
		aminoacids.remove(a);
	}

	public void Remove(Nucleotide n) {
		nucleotids.remove(n);
	}
}
