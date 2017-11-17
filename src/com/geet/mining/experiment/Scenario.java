package com.geet.mining.experiment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.geet.mining.dataset.DataSetGenerator;
import com.geet.mining.model.Issue;

public abstract class Scenario {

	protected List<Issue> allIssues;
	DataSetGenerator dataSetGenerator = new DataSetGenerator();
	public Scenario() {
		readAllHistoricalIssues();
	}
	
	private  void readAllHistoricalIssues(){
		allIssues = new ArrayList<Issue>();
		for (int i = 0; i < 10; i++) {
			allIssues.add(dataSetGenerator.getRandomIssue());
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
	public static void main(String[] args) {
		ScenarioA scenarioA = new ScenarioA();
		for (Issue issue : scenarioA.allIssues) {
			System.out.println(issue.toDocumentRepresentation());
		}
	}
}
