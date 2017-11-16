package com.geet.mining.experiment;

import java.util.List;

import com.geet.mining.model.Issue;

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
	protected Issue retrieveHistoricalIssues() {
		Issue historicalIssue;
		historicalIssue = new Issue();
		return historicalIssue;
	}
}
