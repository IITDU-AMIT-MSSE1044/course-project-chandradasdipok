package com.geet.mining.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author chandradasdipok
 *
 *	
 */
public class HealingAction {

	/**
	 * This class is the healing action of a  issue
	 * stores in triple structure <verb,target,location>
	 * verb represents an action
	 * target represents a component or a device
	 * location is ignored now
	 * 
	 */
	
	
	private String verb;
	private String target;
	private String location;
	
	// description of a healing action of a historical issue
	private String description;
	
	public HealingAction(String description) {
		this.description = description;
	}
	
	// used when the historical issue's healing action is in 
	// triple structure<verb,target,location>
	public HealingAction(String verb,String target,String location){
		this.verb = verb;
		this.target = target;
		this.location = location;
	}
	// used when the historical issue's healing action in text
	public boolean setVerbAndTarget(String description){
		return false;
	}
	
	public static List<HealingAction> rules = new ArrayList<HealingAction>();
	static{
		rules.add(new HealingAction("v0","t0","l0"));
		rules.add(new HealingAction("v1","t1","l1"));
		rules.add(new HealingAction("v2","t2","l2"));
		rules.add(new HealingAction("v3","t3","l3"));
		rules.add(new HealingAction("v4","t4","l4"));
		rules.add(new HealingAction("v5","t5","l5"));
		rules.add(new HealingAction("v6","t6","l6"));
		rules.add(new HealingAction("v7","t7","l7"));
		rules.add(new HealingAction("v8","t8","l8"));
		rules.add(new HealingAction("v9","t9","l9"));
	}
	
	public String toTripleStructure(){
		return "<"+verb+","+target+","+location+">";
	}
	
	
	
	// getters 
	public String getVerb() {
		return verb;
	}
	public String getTarget() {
		return target;
	}
	public String getLocation() {
		return location;
	}
	public String getDescription() {
		return description;
	}
	
	
	public static void main(String[] args) {
		for (HealingAction healingAction : HealingAction.rules) {
			System.out.println(healingAction.toTripleStructure());
		}
	}
	
}