package com.geet.mining.model;

import java.util.HashSet;
import java.util.Set;

public class Event {

	private String eventString;
	
	private int failure;
	private int success;

	public Event(String eventString) {
		setEventString(eventString);
		setFailure(0);
		setSuccess(0);
	}

	public double getAvgMutualInformation(Issue issue){
		double avgI = 0.0;
		
		double pY1 = issue.getFail();
		pY1 /= issue.getFail()+issue.getSucceed();
		
		double pY0 = issue.getSucceed();
		pY0 /= issue.getFail()+issue.getSucceed();
		int A=getFailure(), B=getSuccess(), C=issue.getFail()-getFailure(), D=issue.getSucceed()-getSuccess(),N=issue.getFail()+issue.getSucceed();
		
		/*
		 * -------------------------
		 * 		|  Y=1	 |  Y=0	   |
		 * -------------------------
		 * 	e=1	|  x(A)	 |  y(B)   |
		 * -------------------------
		 * 	e=0	| n-x(C) |  m-y(D) |
		 */
		double iEvt1Y1 = getMutualInformation(A, B, C, D, N);
		
		double IEvt0Y0 = getMutualInformation(D, C, B, A, N);
		
		avgI += pY1*iEvt1Y1;
		avgI += pY0*IEvt0Y0;
		
		return avgI;
	}
	
	private double getMutualInformation(int A, int B, int C, int D, int N){
		if (A ==0 || B==0 || C==0 || D==0 || N==0) {
			return 0.0;
		}
		double I = A*N;
		I /=(A+B);
		I /=(A+C);
		I = Math.log10(I);
		I /= Math.log10(2.0);
		return I;
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
		event.setFailure(getFailure());
		event.setSuccess(getSuccess());
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
	
	public String getEventString() {
		return eventString;
	}

	public void setEventString(String eventString) {
		this.eventString = eventString;
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

	
	
}
