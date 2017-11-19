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
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				Issue issue1 = inputHandler.readIssueFromDirectory("src/com/geet/mining/input/issue_"+i+"/logs.txt");
				Issue issue2 = inputHandler.readIssueFromDirectory("src/com/geet/mining/input/issue_"+j+"/logs.txt");
				issue1.setCosine(issue2);
				System.out.print(issue1.getCosine()+",");
			}
			System.out.println();
		}
	}
}
