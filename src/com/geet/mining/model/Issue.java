package com.geet.mining.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author chandradasdipok 
 * This class is a model to store the information of
 * logs, healing action and the signature of issue Issue represents the
 * document vector in similar issue retrieval
 */
public class Issue implements Comparable<Issue> {


	public boolean isSigAvail = false;
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
	private Map<Event, Double> signatures;
	
	// latest cosine value with another issue
	private double cosine = -1;
	// set of events
	private Map<String,Event> events;
	
	public Issue() {
		transactions = new ArrayList<Transaction>();
		events = new HashMap<String,Event>();
		signatures = new HashMap<Event, Double>();
		healingAction = null;
	}
	
	public Issue(List<Transaction> trs){
		this();
		setTransactions(trs);
		for (Transaction transaction : transactions) {
			Event event = null;
			if (getEvents().containsKey(transaction.getEvent().getEventString())) {
				event = getEvents().get(transaction.getEvent().getEventString());
			//	System.out.println("Yes");
			} else {
			//	System.out.println("No");
				event = new Event(transaction.getEvent().getEventString());
			}
			if (transaction.getTransactionStatus() == Status.FAILURE) {
				event.setFailure(event.getFailure()+1);;
				setFail(getFail()+1);
			} else {
				event.setSuccess(event.getSuccess()+1);;
				setSucceed(getSucceed()+1);
			}
			//System.out.println(event);
			// set the transaction types
			getEvents().put(transaction.getEvent().getEventString(), event);
		}
		/*System.out.println(transactions.size());
		for (String key : getEvents().keySet()) {
			System.out.println(getEvents().get(key).getEventString()+","+getEvents().get(key).getFailure()+":"+getEvents().get(key).getSuccess());
		}*/
		generateSignatures();
	}
	
	
	// generate the signatures for the given issue
	public void generateSignatures() {
		isSigAvail=true;
		// Print the context table
		// System.out.println("Generating Signatures...");
		signatures = new HashMap<Event,Double>();
		for (String key : getEvents().keySet()) {
			signatures.put(getEvents().get(key), getEvents().get(key).getFailureProbabibilty());
		}
	}
	
	public Issue toClone(){
		return new Issue(Transaction.toCloneTransactions(getTransactions()));
	}
	
	//---------- Similar Issue Retrieval------------//
	
	// represent an issue as a document and a collection of terms
	public String toDocumentRepresentation() {
		String documentRepresentation = "";
		for (Event event : signatures.keySet()) {
			System.out.println(event + "\t"
					+ signatures.get(event));
		}
		return documentRepresentation;
	}

	// scalar value of issue as document vector
	public double scalarValue() {
		double scalarValue = 0;
		for (Event event : signatures.keySet()) {
			scalarValue += signatures.get(event)*signatures.get(event);
		}
		return Math.sqrt(scalarValue);
	}

	private double getDotProduct(Issue issue) {
		double dotProduct = 0;
		for (Event eventP : this.signatures.keySet()) {
			if (issue.signatures.containsKey(eventP)) {
					dotProduct += signatures.get(eventP)*issue.signatures.get(eventP);					
				}
			}
	
		return dotProduct/(this.scalarValue()*issue.scalarValue());
	}

	// set cosine similarity value between two documents
	public void setCosine(Issue issue) {
		this.cosine = getDotProduct(issue);
	}

	public double getCosine() {
		return cosine;
	}

	@Override
	public int compareTo(Issue issue) {
		double value = cosine-issue.cosine; 
		return (value !=0 )? (int)(value/Math.abs(value)):0; 
	}
	
	// setter-getter
	public List<Transaction> getTransactions() {
		return transactions;
	}
	public Map<String,Event> getEvents() {
		return events;
	}
	public void setEvents(Map<String,Event> events) {
		this.events = events;
	}
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
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
	public HealingAction getHealingAction() {
		return healingAction;
	}
	public void setHealingAction(HealingAction healingAction) {
		this.healingAction = healingAction;
	}

	public Map<Event, Double> getSignatures() {
		return signatures;
	}

	public void setSignatures(Map<Event, Double> signatures) {
		this.signatures = signatures;
	}


}
