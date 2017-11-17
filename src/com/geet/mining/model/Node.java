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
	
	// indicates the probability of randomly selected transaction
	// is belongs to this node i.e., concept
	// given the issue 
	private double getProbablityOfRandomlySelectedTransactionBelongsTo(Issue issue){
		return ((double) (fail+succeed))/(issue.getFail()+issue.getSucceed());
	}
	
	// indicates the probability of randomly selected transaction	
	// not belongs to this  node
	private double getProbablityOfRandomlySelectedTransactionNotBelongsTo(Issue issue){
		return 1.0-getProbablityOfRandomlySelectedTransactionBelongsTo(issue);
	}
		
	// clone of Node
	public Node toClone(Issue issue){
		Node clone = new Node();
		clone.setClosedSet(Event.getClonedEvents(closedSet));
		clone.setFail(fail);
		clone.setSucceed(succeed);
		return clone;
	}
	// check whether this node is child of the Node node
	public boolean isChildtOf(Node node){
		if (!getClosedSet().equals(node.getClosedSet()) && getClosedSet().containsAll(node.getClosedSet()) ) {
			return true;
		} 
		return false;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Node) {
			Node o = (Node) obj;
			return closedSet.equals(o.closedSet)? true:false;
		}
		return false;
	}
	
	public static Set<Node> clonedNodes(Set<Node> nodes,Issue issue){
		Set<Node> clonedNodes = new HashSet<Node>();
		for (Node node : nodes) {
			clonedNodes.add(node.toClone(issue));
		}
		return clonedNodes;
	}
	
	public static void main(String[] args) {
		double MI= 0.0;
		double n=223,m=486,x=0,y=0;
		double N = n+m;
		double pX1Y1 = (x)/N;
		double pX1= (x+y)/N;
		double pY1= (n)/N;

		double pX0Y0 = (m - y) / N;
		double pX0 = (N-(n+m)) / N;
		double pY0 = (m) / N;

		if (pX1Y1!=0 && pX1!=0 && pY1!=0) {
			MI += Math.log10(pX1Y1/(pX1*pY1))/Math.log10(2.0);
		}
		if (pX0Y0!=0 && pX0!=0 && pY0!=0) {
			MI += pX0Y0*Math.log10(pX0Y0/(pX0*pY0))/Math.log10(2.0);
		}
		System.out.println(MI);	
	}

}
