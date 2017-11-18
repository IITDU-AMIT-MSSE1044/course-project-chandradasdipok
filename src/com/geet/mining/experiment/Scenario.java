package com.geet.mining.experiment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.geet.mining.dataset.DataSetGenerator;
import com.geet.mining.model.Issue;
import com.geet.mining.model.Transaction;

public abstract class Scenario {

	protected List<Issue> allIssues;
	DataSetGenerator dataSetGenerator = new DataSetGenerator();
	public Scenario() {
		readAllHistoricalIssues();
	}
	
	private  void readAllHistoricalIssues(){
		allIssues = new ArrayList<Issue>();
		InputHandler inputHandler = new InputHandler();
		for (int i = 0; i < 50; i++) {
			allIssues.add(inputHandler.readIssueFromDirectory("src/com/geet/mining/input/issue_"+i));
		}
	}
	
	public abstract List<Issue> getHistoricalIssues(int index);
	
	protected List<Issue> retrieveHistoricalIssues(Issue nIssue, List<Issue>hitoricalIssues){
		for (int i = 0; i < hitoricalIssues.size(); i++) {
			hitoricalIssues.get(i).setCosine(nIssue);
		}
		Collections.sort(hitoricalIssues);
		return hitoricalIssues;
	}
}
