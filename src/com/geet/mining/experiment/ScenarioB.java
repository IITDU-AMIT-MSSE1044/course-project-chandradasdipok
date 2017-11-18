package com.geet.mining.experiment;

import java.util.ArrayList;
import java.util.List;

import com.geet.mining.model.Issue;
import com.geet.mining.model.Transaction;

/**
 * 
 * @author chandradasdipok
 * 
 *
 */
public class ScenarioB extends Scenario{

	/**
	 * We adopt the “leave-one-out” strategy (a  common  strategy  used  in  statistical  analysis)  by
	 * treating  all  the remaining issues  (other  than  the  “new issue”) as the “historical issues”. 
	 * Scenario II is used for building a knowledge base which manages all historical issues 
	 */
	
	@Override
	public List<Issue> getHistoricalIssues(int index) {
		List<Issue> historicalIssues = new ArrayList<Issue>();
		for (int i = 0; i < allIssues.size(); i++) {
			if (i==index) {
				continue;
			}
			historicalIssues.add(new Issue(Transaction.toCloneTransactions(allIssues.get(i).getTransactions())));
		}
		return historicalIssues;
	}
}
