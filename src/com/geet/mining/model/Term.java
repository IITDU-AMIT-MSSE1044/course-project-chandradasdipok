package com.geet.mining.model;

import java.util.Set;

/**
 * 
 * @author chandradasdipok
 * This class represents the term of issue
 * This store the events as value a of term
 * and the DMI as the weight of the term 
 */

public class Term implements Comparable<Term>{
	// event set
	private Set<String>eventsAsValue;
	//weight of term
	private double DMIAsWeight;
	
	public Term(Set<String> events,double weight) {
		eventsAsValue = events;
		DMIAsWeight = weight;
	}
	// return true if the event set is same
	public boolean IsSame(Term term) {		
		return eventsAsValue.equals(term.eventsAsValue)?true:false;
	}
	
	public Set<String> getEventsAsValue() {
		return eventsAsValue;
	}

	public void setEventsAsValue(Set<String> eventsAsValue) {
		this.eventsAsValue = eventsAsValue;
	}

	public double getDMIAsWeight() {
		return DMIAsWeight;
	}

	public void setDMIAsWeight(double dMIAsWeight) {
		DMIAsWeight = dMIAsWeight;
	}
	
	@Override
	public int compareTo(Term o) {
		return eventsAsValue.equals(o.eventsAsValue)?1:0;
	}

}
