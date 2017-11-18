package com.geet.mining.experiment;


import com.geet.mining.model.Issue;

public class Experiment {

	public static void main(String[] args) {
		/*Scenario scenarioA = new ScenarioA();
		List<Issue> suggestedIssuesForA = scenarioA.retrieveHistoricalIssues(scenarioA.allIssues.get(1), scenarioA.getHistoricalIssues(1));
		
		Scenario scenarioB = new ScenarioB();
		List<Issue> suggestedIssuesForB = scenarioA.retrieveHistoricalIssues(scenarioA.allIssues.get(1), scenarioA.getHistoricalIssues(1));
		
		System.out.println(suggestedIssuesForA.size()!=0?suggestedIssuesForA.get(0):"Empty");
		System.out.println(suggestedIssuesForB.size()!=0?suggestedIssuesForB.get(0):"Empty");
	*/
		InputHandler inputHandler = new InputHandler();
		Issue issue1 = inputHandler.readIssueFromDirectory("src/com/geet/mining/input/issue_0/logs.txt");
		Issue issue2 = inputHandler.readIssueFromDirectory("src/com/geet/mining/input/issue_3/logs.txt");
		
		System.out.println("Issue11111111111111111111");
		System.out.println(issue1.toDocumentRepresentation());
		System.out.println("Issue22222222222222222222");
		System.out.println(issue2.toDocumentRepresentation());
		issue1.setCosine(issue2);
		
		System.out.println(issue1.getCosine());
	}
}
