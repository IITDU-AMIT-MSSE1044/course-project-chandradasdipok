package com.geet.mining.model;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author chandradasdipok
 * This class represents the term of issue
 * This store the events as value a of term
 * and the DMI as the weight of the term 
 * Term represents the component of Vector Space 
 */

public class Term {
	// event set
	private Set<String>eventsAsValue;
	//weight of term
	private double TF_Weight;
	
	public Term(Set<String> events,double weight) {
		eventsAsValue = events;
		TF_Weight = weight;
	}
	
	@Override
	public int hashCode() {
		String hash="";
		for (String str : eventsAsValue) {
			hash+=str;
		}
		return hash.hashCode();
	}
	
	// return true if the event set is same
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Term) {
			Term t = (Term) obj;
			return eventsAsValue.equals(t.eventsAsValue)?true:false;
		}
		return false;
	}
	public Set<String> getEventsAsValue() {
		return eventsAsValue;
	}

	public void setEventsAsValue(Set<String> eventsAsValue) {
		this.eventsAsValue = eventsAsValue;
	}

	public double getTFWeight() {
		return TF_Weight;
	}

	public void setTFWeight(double TFWeight) {
		TF_Weight = TFWeight;
	}
	
	@Override
	public String toString() {
		String toString = "";
		for (String event : getEventsAsValue()) {
			toString+=event+",";
		}
		toString+=TF_Weight;
		return toString;
	}
	public static void main(String[] args) {
		Set<String> events1 = new HashSet<String>();
		events1.add("a");
		events1.add("c");
		Set<String> events2 = new HashSet<String>();
		events2.add("b");
		events2.add("a");
		Term t1 = new Term(events1, 0.0);
		Term t2 = new Term(events2, 1.0);
		System.out.println(t1.equals(t2));
	}
	
	public Term toClone() {
		return new Term(getEventsAsValue(), TF_Weight);
	}
}
