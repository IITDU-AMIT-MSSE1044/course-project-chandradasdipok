package com.geet.mining.experiment;

import com.geet.mining.model.Issue;

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
	protected Issue retrieveHistoricalIssues() {
		
		return null;
	}
}
