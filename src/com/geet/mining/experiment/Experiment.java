package com.geet.mining.experiment;

import java.util.List;

import com.geet.mining.model.Issue;

public class Experiment {

	public static void main(String[] args) {
		Scenario scenarioA = new ScenarioA();
		List<Issue> suggestedIssuesForA = scenarioA.retrieveHistoricalIssues(scenarioA.allIssues.get(1), scenarioA.getHistoricalIssues(1));
		
		Scenario scenarioB = new ScenarioB();
		List<Issue> suggestedIssuesForB = scenarioA.retrieveHistoricalIssues(scenarioA.allIssues.get(1), scenarioA.getHistoricalIssues(1));
		
		System.out.println(suggestedIssuesForA.size()!=0?suggestedIssuesForA.get(0):"Empty");
		System.out.println(suggestedIssuesForB.size()!=0?suggestedIssuesForB.get(0):"Empty");
			
	}
}
