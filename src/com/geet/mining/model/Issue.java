package com.geet.mining.model;

import java.util.List;

/**
 * 
 * @author chandradasdipok
 * This class is a model to store the information of logs, healing action 
 * and the signature of issue 
 */
public class Issue {

	// log messages of an issue
	private String log;
	// healing action of an issue
	private String  healingAction;
	// the signatures of an issue
	// the collection of terms
	// where term is also collection of events with weight in DMI
	private List<Term> signatures;
		
	
	
	
	
}
