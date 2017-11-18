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
public class ScenarioA extends Scenario{

	/**
	 * Here, we  reflect  real  usage  of  our  approach in practice by treating the
	 * previously encountered issues (i.e.,  those  that  occurred  before  “new  issue”)
	 * as  the “historical issues”. We then apply our approach for each combination  of  
	 * “new  issue”  +  “historical  issues”  and then measure the accuracy of our approach’s
	 * effective-ness in suggesting a correct healing action for the “new issue” 
	 */
	
	@Override
	public List<Issue> getHistoricalIssues(int index) {
		List<Issue> historicalIssues = new ArrayList<Issue>();
		for (int i = 0; i<index && i < allIssues.size(); i++) {
			historicalIssues.add(new Issue(Transaction.toCloneTransactions(allIssues.get(i).getTransactions())));
		}
		return historicalIssues;
	}
	
}
