package com.geet.mining.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author chandradasdipok This class is a model to store the information of
 *         logs, healing action and the signature of issue Issue represents the
 *         document vector in similar issue retrieval
 */
public class Issue implements Comparable<Issue> {

	public Issue() {
		transactions = new ArrayList<Transaction>();
		events = new HashSet<Event>();
		transactionModules = new HashMap<String, TransactionModule>();
		signatures = new HashMap<Term, Double>();
		nodes = new HashSet<Node>();
		healingAction = null;
	}

	// nodes of FCA graph representation of an issue
	private Set<Node> nodes;

	// failed transaction and succeeded transactions given a issue
	int fail = 0, succeed = 0;
	// log messages of an issue
	private List<Transaction> transactions;
	// healing action of an issue
	private String healingAction;
	// the signatures of an issue
	// the collection of terms
	// where term is also collection of events with weight in DMI
	// <Term,Double> := <Event Set, DMI weight>
	// and We
	private Map<Term, Double> signatures;

	// each issue is consists of some modules named
	private Map<String, TransactionModule> transactionModules;

	// latest cosine value with another issue
	private double cosine = -1;

	// set of events
	private Set<Event> events;

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setLog(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public Map<String, TransactionModule> getTransactionModules() {
		return transactionModules;
	}

	public void setTransactionModules(
			Map<String, TransactionModule> transactionModules) {
		this.transactionModules = transactionModules;
	}

	public Set<Event> getEvents() {
		return events;
	}

	public void setEvents(Set<Event> events) {
		this.events = events;
	}

	// indicates the probability of fail of randomly selected transaction
	// given the issue
	public double getProbablityOfFailOfRandomlySelectedTransaction() {
		return (double) getFail() / ((double) getFail() + getSucceed());
	}

	// indicates the probability of succeed of randomly selected transaction
	// given the issue
	public double getProbablityOfSuccessOfRandomlySelectedTransaction() {
		return (double) getSucceed() / ((double) getFail() + getSucceed());
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

	public Set<Node> getNodes() {
		return nodes;
	}

	public void setNodes(Set<Node> nodes) {
		this.nodes = nodes;
	}

	// generate the signatures for the given issue
	public void generateSignatures() {
		// traverse all the nodes
		// store the traversed nodes in previousNodes
		for (Node currentNode : Node.clonedNodes(getNodes())) {
			// retrieve the nodes those are super concept of the current Node
			// A node is super of current Node if the node is sub set of current
			// Node
			// store all the super nodes as candidate nodes from the previous
			// nodes
			System.out.println("Current Node :" + currentNode);
			Set<Node> parentNodes = new HashSet<Node>();
			for (Node storedNode : Node.clonedNodes(getNodes())) {
				if (currentNode.isChildtOf(storedNode)) {
					// add stored node to parent nodes
					// System.out.println(currentNode+" is child of "+storedNode);
					// // check whether stored node is present as subset in
					// parent nodes
					boolean isStoredNodePresentAsParent = false;
					// delete the nodes from parent nodes which are parent of
					// stored node
					Set<Node> toDeletenodes = new HashSet<Node>();
					for (Node node : parentNodes) {
						if (storedNode.isChildtOf(node)) {
							toDeletenodes.add(node);
						}
						if (node.isChildtOf(storedNode)) {
							isStoredNodePresentAsParent = true;
						}
					}
					for (Node node : toDeletenodes) {
						parentNodes.remove(node);
						// System.out.println("Removed "+node);
					}
					if (!isStoredNodePresentAsParent) {
						// System.out.println("added "+storedNode);
						parentNodes.add(storedNode.toClone());
					} else {
						// System.out.println("ignored "+storedNode);
					}
				}
			}
			System.out.println("Parents :" + parentNodes);

			// calculate the mutual information between current nodes and parent
			// node
			// and store if full fill certain criteria
			// if the mutual information of current node is greater than zero
			// and the difference between current node and child node is non
			// zero
			double MICurrentNode = currentNode
					.getMutualInformationGivenIssue(this);
			if (MICurrentNode > 0) {
				for (Node node : parentNodes) {
					// DMI - Delta Mutual Information
					double MIParentNode = node
							.getMutualInformationGivenIssue(this);
					double DMI = MICurrentNode - MIParentNode;
					if (DMI > 0) {
						Set<String> events = new HashSet<String>();
						Set<Event> suspectEvents = Event
								.getClonedEvents(currentNode.getClosedSet());
						suspectEvents.retainAll(Event.getClonedEvents(node
								.getClosedSet()));
						for (Event event : suspectEvents) {
							events.add(event.getEventString());
						}
						Term term = new Term(events, DMI);
						signatures.put(term, term.getDMIAsWeight());
					}
				}
			}
		}
	}

	//---------- Similar Issue Retrieval------------//
	
	// represent an issue as a document and a collection of terms
	public String toDocumentRepresentation() {
		String documentRepresentation = "";
		for (Term signatureTerm : signatures.keySet()) {
			System.out.println(signatureTerm + "\t"
					+ signatures.get(signatureTerm));
		}
		return documentRepresentation;
	}

	// scalar value of issue as document vector
	private double scalarValue() {
		double scalarValue = 0;
		for (Term term : signatures.keySet()) {
			scalarValue *= signatures.get(term);
		}
		return Math.sqrt(scalarValue);
	}

	public double getDotProduct(Issue issue) {
		double dotProduct = 0;
		for (Term termP : this.signatures.keySet()) {
			for (Term termQ : issue.signatures.keySet()) {
				dotProduct = termP.getDMIAsWeight()*termQ.getDMIAsWeight();
				Term p = termP.toClone();
				Term q = termQ.toClone();
				p.getEventsAsValue().retainAll(q.getEventsAsValue());
				dotProduct *= p.getEventsAsValue().size();
			}
		}
		return dotProduct/(this.scalarValue()*issue.scalarValue());
	}

	// set cosine similarity value between two documents
	public void setCosine(Issue issue) {
		this.cosine = getDotProduct(issue)
				/ (scalarValue() * issue.scalarValue());
	}

	public double getCosine() {
		return cosine;
	}

	@Override
	public int compareTo(Issue issue) {
		if (cosine == -1) {
			setCosine(issue);
		}
		return (cosine > issue.cosine) ? 1 : 0;
	}

}
