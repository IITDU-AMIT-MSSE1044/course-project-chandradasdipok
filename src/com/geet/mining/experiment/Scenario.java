package com.geet.mining.experiment;

import java.util.ArrayList;
import java.util.List;

import com.geet.mining.model.Issue;

public abstract class Scenario {

	protected List<Issue> historicalIssues;
	
	public Scenario() {
		readAllHistoricalIssues();
	}
	
	private  void readAllHistoricalIssues(){
		historicalIssues = new ArrayList<Issue>();
	}
	
	protected abstract Issue retrieveHistoricalIssues();
}
