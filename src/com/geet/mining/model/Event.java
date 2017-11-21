package com.geet.mining.model;

import java.util.HashSet;
import java.util.Set;

public class Event implements Comparable<Event>,Cloneable{

	private String eventString;
	private int value;
	

	public Event(String eventString) {
		setEventString(eventString);
	}
	
	public String getEventString() {
		return eventString;
	}

	private void setEventString(String eventString) {
		this.eventString = eventString;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	 @Override
	    public int hashCode(){
	        int hashcode = 0;
	        hashcode += eventString.hashCode();
	        return hashcode;
	    }
	@Override
	public boolean equals(Object o) {
		if (o instanceof Event) {
			Event e = (Event) o;
			return e.eventString.equals(this.eventString);
		}else{
			return false;			
		}
	}
	
	@Override
	public String toString() {
		return eventString;
	}
	
	
	@Override
	public int compareTo(Event o) {
		return (value-o.value);
	}
	
	public Event clone(){
		Event event = new Event(eventString);
		event.setValue(value);
		return event;
	}
	
	public static Set<Event> getClonedEvents(Set<Event> events){
		Set<Event> clonedEvents = new HashSet<Event>();
		for (Event event : events) {
			clonedEvents.add(event.clone());
		}
		return clonedEvents;
	}
	
	public static Set<String> getEventsAsString(Set<Event>events){
		Set<String> stringEvents = new HashSet<String>();
		for (Event event : events) {
			stringEvents.add(event.eventString);
		}
		return stringEvents;
	}
}
