package com.geet.mining.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.geet.mining.concept_analysis.ConceptAnalyzer;

/**
 * 
 * @author chandradasdipok 
 * This class is a model to store the information of
 * logs, healing action and the signature of issue Issue represents the
 * document vector in similar issue retrieval
 */
public class Issue implements Comparable<Issue> {

	boolean isSigAvail = false;
	
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
	private int fail = 0, succeed = 0;
	// log messages of an issue
	private List<Transaction> transactions;
	// healing action of an issue
	private HealingAction healingAction;
	// the signatures of an issue
	// the collection of terms
	// where term is also collection of events with weight in DMI
	// <Term,Double> := <Event Set,  weight>
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
		return (double) (getFail()) / ((double) getFail() + getSucceed());
	}

	// indicates the probability of succeed of randomly selected transaction
	// given the issue
	public double getProbablityOfSuccessOfRandomlySelectedTransaction() {
		return (double) (getSucceed()) / ((double) getFail() + getSucceed());
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

	private Set<Node> getNodes() {
		return nodes;
	}

	private void setNodes(Set<Node> nodes) {
		this.nodes = nodes;
	}


	public HealingAction getHealingAction() {
		return healingAction;
	}

	public void setHealingAction(HealingAction healingAction) {
		this.healingAction = healingAction;
	}

	// generate the signatures for the given issue
	public void generateSignatures() {
		ConceptAnalyzer conceptAnalyzer = new ConceptAnalyzer(this);
		// generate  the nodes of a FCA graph
		setNodes(conceptAnalyzer.generateNodesOfGraph());
		// traverse all the nodes
		// store the traversed nodes in previousNodes
/*		System.out.println("Nodesssssssssssssssssssss");
		System.out.println(getNodes().toString());
		System.out.println("Nodesssssssssssssssssssss");
		
*/		// calculate all the MI
		
		System.out.println("Generating Signatures...");
		for (Node node : getNodes()) {
			node.setMI(node.getMutualInformationGivenIssue(this));
			System.out.println(node);
		}
		
		for (Node currentNode : Node.clonedNodes(getNodes(),this)) {
			// retrieve the nodes those are super concept of the current Node
			// A node is super of current Node if the node is sub set of current Node
			// store all the super nodes as candidate nodes from the previous
			// nodes
			System.out.println("Current Node :" + currentNode);
			Set<Node> parentNodes = new HashSet<Node>();
			for (Node storedNode : Node.clonedNodes(getNodes(),this)) {
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
						parentNodes.add(storedNode.toClone(this));
					} else {
						// System.out.println("ignored "+storedNode);
					}
				}
			}
			System.out.println("Parents :" + parentNodes);	
			//n App2 , we do not address the weak-discrimination  phenomenon:
			//we  first  apply  FCA and use delta events between parent and child concepts 
			//to  define  terms  (using  grouping  information),  use  TF-IDF  as  
			//the  weight  of  each  term,  and  finally  calculate the cosine core
			//as the similarity metric value
			for (Node node : parentNodes) {
				Set<Event> setA = Event.getClonedEvents(currentNode.getClosedSet());
				Set<Event> setB = Event.getClonedEvents(currentNode.getClosedSet());
				setA.retainAll(setB);
				Term term = new Term(Event.getEventsAsString(setA), 1.0);
				if (signatures.containsKey(term)) {
					term.setTFWeight(term.getTFWeight()+1.0);
				}
				signatures.put(term, term.getTFWeight());
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
				dotProduct = termP.getTFWeight()*termQ.getTFWeight();
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

	public Issue(List<TransactionModule> modules){
		transactions = new ArrayList<Transaction>();
		events = new HashSet<Event>();
		transactionModules = new HashMap<String, TransactionModule>();
		signatures = new HashMap<Term, Double>();
		nodes = new HashSet<Node>();
		healingAction = null;
		for (TransactionModule module : modules) {
			transactionModules.put(module.transactionID, module);
			events.addAll(Event.getClonedEvents(module.eventSet));
		}
	}
	
}
