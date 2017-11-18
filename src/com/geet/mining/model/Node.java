package com.geet.mining.model;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author chandradasdipok This class represents a node of FCA graph , i.e., a
 *         concept Each Node contains a event set which is a closed set, failed
 *         transactions and succeeded transactions
 * 
 */
public class Node {

	private Set<Event> closedSet;
	private double MI;
	private int fail;
	private int succeed;

	public Node() {
		setClosedSet(new HashSet<Event>());
		setMI(0);
		setFail(0);
		setSucceed(0);
	}

	public Set<Event> getClosedSet() {
		return closedSet;
	}

	public void setClosedSet(Set<Event> closedSet) {
		this.closedSet = closedSet;
	}

	public int getFail() {
		return fail;
	}

	public void setFail(int fail) {
		this.fail = fail;
	}

	public int getSucceed() {
		return succeed;
	}

	public void setSucceed(int succeed) {
		this.succeed = succeed;
	}

	@Override
	public String toString() {
		return closedSet + "[" + fail + "," + succeed + "]" + getMI();
	}

	// indicates the probability of randomly selected transaction
	// is belongs to this node i.e., concept
	// given the issue
	private double getProbablityOfRandomlySelectedTransactionBelongsTo(Issue issue) {
		return ((double) (fail + succeed)) / (issue.getFail() + issue.getSucceed());
	}

	// indicates the probability of randomly selected transaction
	// not belongs to this node
	private double getProbablityOfRandomlySelectedTransactionNotBelongsTo(Issue issue) {
		return 1.0 - getProbablityOfRandomlySelectedTransactionBelongsTo(issue);
	}

	// calculates the mutual information between the node i.e., concept and the
	// issue where the node resides
	// X={ 1 if a sample transaction belongs to this node
	// 0 otherwise
	// }
	// Y={ 1 if a sample transaction fails
	// 0 otherwise
	// }
	// n #failed transaction instance of an issue
	// m #succeeded transaction instance of an issue
	// x #failed transaction instance of this node
	// y #succeeded transaction instance of this node
	public double getAvgMutualInformation(Issue issue) {
		double avgI = 0.0;

		double pY1 = issue.getFail();
		pY1 /= issue.getFail() + issue.getSucceed();

		double pY0 = issue.getSucceed();
		pY0 /= issue.getFail() + issue.getSucceed();
		int A = getFail(), B = getSucceed(), C = issue.getFail() - getFail(),
		D = issue.getSucceed() - getSucceed(), N = issue.getFail() + issue.getSucceed();

		/*
		 * ------------------------- 
		 * 		| Y=1   | Y=0   | 
		 * -------------------------
		 *  X=1 | x(A)  | y(B)  | 
		 *  -------------------------
		 *  X=0 | n-x(C)| m-y(D)|
		 */
		double iNode1Y1 = getMutualInformation(A, B, C, D, N);

		double INode0Y0 = getMutualInformation(D, C, B, A, N);

		avgI += pY1 * iNode1Y1;
		avgI += pY0 * INode0Y0;

		return avgI;
	}

	private double getMutualInformation(int A, int B, int C, int D, int N) {
		if (A == 0 || B == 0 || C == 0 || D == 0 || N == 0) {
			return 0.0;
		}
		double I = A * N;
		I /= (A + B);
		I /= (A + C);
		I = Math.log10(I);
		I /= Math.log10(2.0);
		return I;
	}

	public static void main(String[] args) {
		double MI = 0.0;
		double n = 223, m = 486, x = 0, y = 0;
		double N = n + m;
		double pX1Y1 = (x) / N;
		double pX1 = (x + y) / N;
		double pY1 = (n) / N;

		double pX0Y0 = (m - y) / N;
		double pX0 = (N - (n + m)) / N;
		double pY0 = (m) / N;

		if (pX1Y1 != 0 && pX1 != 0 && pY1 != 0) {
			MI += Math.log10(pX1Y1 / (pX1 * pY1)) / Math.log10(2.0);
		}
		if (pX0Y0 != 0 && pX0 != 0 && pY0 != 0) {
			MI += pX0Y0 * Math.log10(pX0Y0 / (pX0 * pY0)) / Math.log10(2.0);
		}
		System.out.println(MI);
	}

	// clone of Node
	public Node toClone(Issue issue) {
		Node clone = new Node();
		clone.setClosedSet(Event.getClonedEvents(closedSet));
		clone.setFail(fail);
		clone.setSucceed(succeed);
		clone.setMI(getAvgMutualInformation(issue));
		return clone;
	}

	// check whether this node is child of the Node node
	public boolean isChildtOf(Node node) {
		if (!getClosedSet().equals(node.getClosedSet()) && getClosedSet().containsAll(node.getClosedSet())) {
			return true;
		}
		return false;
	}

	public static Set<Node> clonedNodes(Set<Node> nodes, Issue issue) {
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
