package com.geet.mining.model;

import java.util.Map;

/**
 * 
 * @author chandradasdipok
 * This class is a model to store the information of logs, healing action 
 * and the signature of issue 
 * Issue represents the document vector in similar issue retrieval
 */
public class Issue implements Comparable<Issue>{
	 
	// log messages of an issue
	private String log;
	// healing action of an issue
	private String  healingAction;
	// the signatures of an issue
	// the collection of terms
	// where term is also collection of events with weight in DMI
	// <Term,Double> := <Event Set, DMI weight>
	// and We
	private Map<Term,Double> signatures;
	
	// latest cosine value with another issue
	private double cosine=-1;
	public double getCosine() {
		return cosine;
	}

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
}
