package assets.virologist;//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : field.virologist.Virologist.java
//  @ Date : 2022. 03. 23.
//  @ Author : 
//
//

import assets.virologist.behavior.createbehavior.*;
import assets.virologist.behavior.dropbehavior.*;
import assets.virologist.behavior.getinfectedbehavior.*;
import assets.virologist.behavior.getstolenbehavior.*;
import assets.virologist.behavior.infectbehavior.*;
import assets.virologist.behavior.killbehavior.KillBehavior;
import assets.virologist.behavior.killbehavior.NotKill;
import assets.virologist.behavior.learnbehavior.*;
import assets.virologist.behavior.movebehavior.*;
import assets.virologist.behavior.pickupbehavior.*;
import assets.virologist.behavior.stealbehavior.*;
import assets.field.*;
import collectables.Collectable;
import collectables.agent.*;
import collectables.genome.*;
import collectables.equipment.*;

import java.util.ArrayList;

/**
 * The field.virologist.Virologist is the avatar that the player controls.
 * He can interact with Collectables on the field.field.Field and other Virologists.
 * He moves around the map and learns Genomes, of which he can create Agents.
 * */
public class Virologist implements java.io.Serializable{



	/** Constructor, creates the attributes of the field.virologist.Virologist and sets his field.virologist.behaviors to the default value*/
	public Virologist(){
		route = new Route();
		backPack = new VirologistBackpack();
		learnedGenomes = new ArrayList<>();
		DefaultBehaviors();
		SetState(State.NOT_IN_TURN);
	}
	/** The current state the field.virologist.Virologist is in*/
	private State state;

	private String name;

	private final VirologistBackpack backPack;

	private final Route route;
	
	/** The Genomes the field.virologist.Virologist knows*/
	private final ArrayList<Genome> learnedGenomes;

	/** The field.virologist.behaviors of the field.virologist.Virologist*/
	private MoveBehavior moveBehavior;

	private PickUpBehavior pickUpBehavior;

	private StealBehavior stealBehavior;

	private GetStolenBehavior getStolenBehavior;

	private DropBehavior dropBehavior;

	private LearnBehavior learnBehavior;

	private CreateBehavior createBehavior;

	private InfectBehavior infectBehavior;

	private GetInfectedBehavior getInfectedBehavior;

	private KillBehavior killBehavior;

	public VirologistBackpack getBackpack(){
		return backPack;
	}
	/** If the field.virologist.Virologist didn't move yet in his turn he calls his MoveBehavior*/
	public void Move(int d) {
		if(this.state == State.BEFORE_MOVE) {
			moveBehavior.MoveToField(this, d);
		}
	}

	public void MoveRandomOff(int d){
		if(this.state == State.BEFORE_MOVE) {
			moveBehavior.MoveRandomOff(this, d);
		}
	}
	/** If the field.virologist.Virologist didn't do action in his turn he calls his PickUpBehavior*/
	public void PickUpCollectable(ArrayList<Collectable> c) {
		if(state == State.BEFORE_ACTION){
			pickUpBehavior.PickUpCollectable(this, c);
		}
	}

	/** If the field.virologist.Virologist didn't do action in his turn he calls his StealBehavior*/
	public void Steal(Virologist v2, ArrayList<Collectable> c) {
		if(state == State.BEFORE_ACTION){
			stealBehavior.StealFromVirologist(this, v2, c);
		}
	}

	/** The field.virologist.Virologist calls his GetStolenBehavior and returns the result*/
	public boolean GetStolenFrom(ArrayList<Collectable> c) {
		return getStolenBehavior.GetStolenFrom(this, c);
	}

	/**
	 * If the field.virologist.Virologist is in turn, and he moved he calls his DropBehavior.
	 * If he is not in turn he just drops the items
	 * */
	public void DropCollectable(ArrayList<Collectable> c) {
		if(state == State.BEFORE_ACTION || state == State.AFTER_ACTION){
			dropBehavior.DropCollectable(this, c);
		}
		if(state == State.NOT_IN_TURN){
			Field f = GetRoute().GetLocation();
			for(Collectable collectable: c){
				collectable.RemoveFromBackpack(this, getBackpack());
				collectable.Remove(this);
				f.Add(this, collectable);
			}
		}
	}
	/** If the field.virologist.Virologist didn't do action in his turn he calls his LearnBehavior*/
	public void Learn() {
		if(state == State.BEFORE_ACTION){
			learnBehavior.LearnGenome(this);
		}
	}

	/** If the field.virologist.Virologist didn't do action in his turn he calls his CreateBehavior*/
	public void CreateAgent(Genome g) {
		if(state == State.BEFORE_ACTION){
			createBehavior.CreateAgent(this, g);
		}
	}

	/**If the field.virologist.Virologist didn't do action in his turn he calls his InfectBehavior*/
	public void Infect(Virologist v2, Agent a) {
		if(state == State.BEFORE_ACTION){
			infectBehavior.InfectVirologist(this, v2, a);
		}
	}

	public void InfectRandomOff(Virologist v2, Agent a){
		if(state == State.BEFORE_ACTION){
			infectBehavior.InfectRandomOff(this, v2, a);
		}
	}

	/**
	 * If the field.virologist.Virologist is not in his turn he calls his GetInfectedBehavior.
	 * If he is in his turn he will get infected regardless of his protection.
	 * */
	public void GetInfected(Virologist v1, Agent a) {
		if(state ==State.NOT_IN_TURN || state == State.BEFORE_MOVE){
			getInfectedBehavior.getInfected(v1, this, a);
		}
		else {
			a.Apply(this);
			backPack.AddApplied(a);

		}
	}

	public void GetInfectedRandomOff(Virologist v1, Agent a) {
		if(state ==State.NOT_IN_TURN || state == State.BEFORE_MOVE){
			getInfectedBehavior.getInfectedRandomOff(v1, this, a);
		}
		else {
			a.Apply(this);
			backPack.AddApplied(a);

		}
	}

	public void killVirologist(Virologist v){
		if(state == State.BEFORE_ACTION){
			killBehavior.KillVirologist(this,v);
		}
	}
	/**
	 * The field.virologist.Virologist Ends his turn. The warranty of all of his Agents will be decreased,
	 * as well as the durations of his applied Agents.
	 * */
	public void EndTurn() {
		backPack.DecreaseWarranties();
		SetState(State.NOT_IN_TURN);
	}
	//
	/** Sets all the effects to default. Then applies the effects of all collectables.equipment.Equipment and applies collectables.agent.Agent*/
	public void RefreshEffects() {
		DefaultBehaviors();
		ArrayList<Equipment> eqs = backPack.getEquipments();
		for(Equipment e : eqs){
			e.Apply(this);
		}
		ArrayList<Agent> ags = backPack.GetAppliedAgents();
		for(Agent a: ags){
			a.Apply(this);
		}
	}


	/** Sets the field.virologist.behaviors to the default value*/
	public void DefaultBehaviors(){
		SetMoveBehavior(new Move());
		SetPickUpBehavior(new PickUp());
		SetStealBehavior(new Steal());
		SetGetStolenBehavior(new NotGetStolen());
		SetDropBehavior(new Drop());
		SetLearnBehavior(new Learn());
		SetCreateBehavior(new Create());
		SetInfectBehavior(new Infect());
		SetGetInfectedBehavior(new GetInfected());
		SetKillBehavior(new NotKill());
	}


	/**
	 * Adds a collectables.genome.Genome to learnedGenomes if it wasn't learned yet
	 *
	 * @return true if it was added, false if it was not added
	 * */
	public boolean Add(Genome g) {
		if(learnedGenomes.contains(g)){
			return false;
		}
		learnedGenomes.add(g);
		return true;
	}

	/** Forgets all known genomes*/
	public void ForgetGenome() {
		int s = learnedGenomes.size();
		if (s > 0) {
			learnedGenomes.subList(0, s).clear();
		}
	}

	/** */
	public void SetState(State s) {
		state = s;
	}
	
	/** */
	public State getState() {
		return state;
	}
	
	/** */
	public Route GetRoute() {
		return route;
	}

	public ArrayList<Genome> GetLearnedGenomes(){
		return learnedGenomes;
	}
	


	public void SetMoveBehavior(MoveBehavior b){
		moveBehavior = b;
	}

	public void SetPickUpBehavior(PickUpBehavior b){
		pickUpBehavior = b;
	}

	public void SetStealBehavior(StealBehavior b){
		stealBehavior = b;
	}

	public void SetGetStolenBehavior(GetStolenBehavior b){
		getStolenBehavior = b;
	}

	public GetStolenBehavior GetGetStolenBehavior(){
		return getStolenBehavior;
	}

	public void SetDropBehavior(DropBehavior b){
		dropBehavior = b;
	}

	public void SetLearnBehavior(LearnBehavior b) {
		learnBehavior = b;
	}

	public void SetCreateBehavior(CreateBehavior b){
		createBehavior = b;
	}

	public void SetInfectBehavior(InfectBehavior b){
		infectBehavior = b;
	}

	public void SetGetInfectedBehavior(GetInfectedBehavior b){
		getInfectedBehavior = b;
	}

	public MoveBehavior GetMoveBehavior(){
		return this.moveBehavior;
	}

	public GetInfectedBehavior GetGetInfectedBehavior(){
		return this.getInfectedBehavior;
	}

	public void SetKillBehavior(KillBehavior k) {
		killBehavior = k;
	}

	public String GetName() {
		return name;
	}
	public void SetName(String name) {this.name = name;}

}
