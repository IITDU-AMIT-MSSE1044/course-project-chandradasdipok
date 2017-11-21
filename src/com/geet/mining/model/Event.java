package com.geet.mining.model;

import java.util.HashSet;
import java.util.Set;

public class Event{

	private String eventString;

	private int failure;
	private int success;

	public Event(String eventString) {
		setEventString(eventString);
		setFailure(0);
		setSuccess(0);
	}

	public String getEventString() {
		return eventString;
	}

	private void setEventString(String eventString) {
		this.eventString = eventString;
	}


	@Override
	public int hashCode() {
		int hashcode = 0;
		hashcode += eventString.hashCode();
		return hashcode;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Event) {
			Event e = (Event) o;
			return e.eventString.equals(this.eventString);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return eventString+"["+failure+":"+success+"]";
	}


	public Event clone() {
		Event event = new Event(eventString);
		setFailure(getFailure());
		setSuccess(getSuccess());
		return event;
	}

	public static Set<Event> getClonedEvents(Set<Event> events) {
		Set<Event> clonedEvents = new HashSet<Event>();
		for (Event event : events) {
			clonedEvents.add(event.clone());
		}
		return clonedEvents;
	}

	public static Set<String> getEventsAsString(Set<Event> events) {
		Set<String> stringEvents = new HashSet<String>();
		for (Event event : events) {
			stringEvents.add(event.eventString);
		}
		return stringEvents;
	}
	
	public int getFailure() {
		return failure;
	}

	public void setFailure(int failure) {
		this.failure = failure;
	}

	public int getSuccess() {
		return success;
	}

	public void setSuccess(int success) {
		this.success = success;
	}
	
	public double getFailureProbabibilty(){
		return (double)getFailure()/(getFailure()+getSuccess());
	}
}
