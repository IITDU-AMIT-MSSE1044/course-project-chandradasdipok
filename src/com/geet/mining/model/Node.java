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
	private double MI;
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
		return closedSet+"["+fail+","+succeed+"]"+getMI();
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
	
	// calculates the mutual information between the node i.e., concept and the issue where the node resides 
	// X={	1 if a sample transaction belongs to this node
	//		0 otherwise
	//	 }
	// Y={	1 if a sample transaction fails
	//		0 otherwise
	//	 }
	// n #failed transaction instance of an issue 
	// m #succeeded transaction instance of an issue
	// x #failed transaction instance of this node
	// y #succeeded transaction instance of this node
	public double getMutualInformationGivenIssue(Issue issue){
		// MI means Mutual Information
		System.out.println("MI ");
		System.out.println(this);
		double MI= 0.0;
		double n=issue.getFail(),m=issue.getSucceed(),x=getFail(),y=getSucceed();
		double pX1 = getProbablityOfRandomlySelectedTransactionBelongsTo(issue);
		double pX0 = getProbablityOfRandomlySelectedTransactionNotBelongsTo(issue);
		double pY1 = issue.getProbablityOfFailOfRandomlySelectedTransaction();
		double pY0 = issue.getProbablityOfSuccessOfRandomlySelectedTransaction();
		double pX1Y1 =  ((double) (x) / (n+m));
//		System.out.println(pX0 <= 0+","+pX0Y0+","+pX1+","+pX1Y1+","+pY0+","+pY1);
//		System.out.println("XOYO"+(m-y));
		double pX0Y0 =  ((double) (m-y) / (n+m));
//		System.out.println("Prbos");
//		System.out.println(pX0+","+pX0Y0+","+pX1+","+pX1Y1+","+pY0+","+pY1);
		/*System.out.println("Checking.....");
		System.out.println(pX1Y1/(pX1*pY1));
		System.out.println(pX0Y0/(pX0*pY0));
		*/
		if (pX0 <= 0 ) {
			System.out.println("pX0 "+pX0);
		}
		if (pX0Y0 <= 0 ) {
			System.out.println("pX0Y0 "+pX0Y0);
		}
		if (pX1<=0 ) {
			System.out.println("pX1 "+pX1);
		}
		if ( pX1Y1<=0) {
			System.out.println("pX1Y1 "+pX1Y1);
		}
		if (pY0<=0 ) {
			System.out.println("pY0 "+pY0);
		}
		if ( pY1<=0) {
			System.out.println("pY1 "+pY1);
		}
		MI += pX1Y1 * Math.log10(pX1Y1/(pX1*pY1));
		MI += pX0Y0 * Math.log10(pX0Y0/(pX0*pY0));
		return MI;
	}
	
	
	// clone of Node
	public Node toClone(Issue issue){
		Node clone = new Node();
		clone.setClosedSet(Event.getClonedEvents(closedSet));
		clone.setFail(fail);
		clone.setSucceed(succeed);
		clone.setMI(getMutualInformationGivenIssue(issue));
		return clone;
	}
	// check whether this node is child of the Node node
	public boolean isChildtOf(Node node){
		if (!getClosedSet().equals(node.getClosedSet()) && getClosedSet().containsAll(node.getClosedSet()) ) {
			return true;
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
	public double getMI() {
		return MI;
	}
	public void setMI(double mI) {
		MI = mI;
	}
}
