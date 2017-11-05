package com.geet.mining.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author chandradasdipok
 * This class is a model to store the information of logs, healing action 
 * and the signature of issue 
 * Issue represents the document vector in similar issue retrieval
 */
public class Issue implements Comparable<Issue>{
	 
	
	public Issue(){
		transactions = new ArrayList<Transaction>();
		events = new HashSet<Event>();
		transactionModules = new HashMap<String,TransactionModule>();
		signatures = new HashMap<Term,Double>();
		healingAction = null;
	}
	
	// failed transaction and succeeded transactions given a issue
	int fail=0,succeed=0;
	// log messages of an issue
	private List<Transaction>transactions;
	// healing action of an issue
	private String  healingAction;
	// the signatures of an issue
	// the collection of terms
	// where term is also collection of events with weight in DMI
	// <Term,Double> := <Event Set, DMI weight>
	// and We
	private Map<Term,Double> signatures;
	
	// each issue is consists of some modules named 
	private Map<String,TransactionModule> transactionModules;	

	// latest cosine value with another issue
	private double cosine=-1;
	
	// set of events
	private Set<Event> events;
	public double getCosine() {
		return cosine;
	}

	// set cosine similarity value between two documents
	public void setCosine(Issue issue) {
		this.cosine = doubleDotProduct(issue)/(scalarValue()*issue.scalarValue());
	}

	public double doubleDotProduct(Issue issue){
		double dotProduct = 0;
		for (Term term : signatures.keySet()) {
			if (issue.signatures.containsKey(term)) {
				dotProduct += signatures.get(term)*issue.signatures.get(term);
			}
		}
		return dotProduct;
	}
	
	@Override
	public int compareTo(Issue issue) {
		if (cosine==-1) {
			setCosine(issue);
		} 
		return (cosine>issue.cosine)?1:0;
	}
	
	// scalar value of issue as document vector
	private double scalarValue(){
		double scalarValue = 0;
		for (Term term : signatures.keySet()) {
			scalarValue *= signatures.get(term);
		}
		return Math.sqrt(scalarValue);
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setLog(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public Map<String, TransactionModule> getTransactionModules() {
		return transactionModules;
	}

	public void setTransactionModules(Map<String, TransactionModule> transactionModules) {
		this.transactionModules = transactionModules;
	}

	public Set<Event> getEvents() {
		return events;
	}

	public void setEvents(Set<Event> events) {
		this.events = events;
	}

	
}
