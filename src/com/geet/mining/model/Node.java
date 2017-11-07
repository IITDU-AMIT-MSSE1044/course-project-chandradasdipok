package com.geet.mining.model;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author chandradasdipok
 * This class represents a node of FCA graph , i.e., a concept
 * Each Node contains a event set which is a closed set,
 * failed transactions and succeeded transactions 
 * 
 */
public class Node {

	private Set<Event> closedSet;
	
	private long fail;
	private long succeed;
	
	public Node() {
		setClosedSet(new HashSet<Event>());
		setFail(0);
		setSucceed(0);
	}
	public Set<Event> getClosedSet() {
		return closedSet;
	}
	public void setClosedSet(Set<Event> closedSet) {
		this.closedSet = closedSet;
	}
	public long getFail() {
		return fail;
	}
	public void setFail(long fail) {
		this.fail = fail;
	}
	public long getSucceed() {
		return succeed;
	}
	public void setSucceed(long succeed) {
		this.succeed = succeed;
	}
	
	@Override
	public String toString() {
		return closedSet+"["+fail+","+succeed+"]";
	}
	
	// indicates the probability of fail of randomly selected transaction 
	// given the node i.e., concept 
	private double getProbablityOfFail(){
		return (double) fail /((double) fail+succeed);
	}
	
	// indicates the probability of succeed of randomly selected transaction	
	// given the node i.e., concept
	private double getProbablityOfSuccess(){
		return (double) succeed /((double) fail+succeed);
	}
	// calculates the mutual information between the node i.e., concept and the issue where the node resides 
	private double getMutualInformationGivenComponentOfIssue(Issue issue){
		// MI means Mutual Information
		double MI= 0.0;
		
		return MI;
	}
	
}
