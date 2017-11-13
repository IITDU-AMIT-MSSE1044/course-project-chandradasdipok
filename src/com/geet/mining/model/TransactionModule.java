package com.geet.mining.model;

import java.util.HashSet;
import java.util.Set;

public class TransactionModule {
	public String transactionID;
	public int fail=0;
	public int succeed=0;
	public Set<Event> eventSet = new HashSet<Event>(); 
	
	@Override
	public String toString() {
		String str="";
		str += transactionID;
		str += ","+fail;
		str += ":"+succeed+",{";
		for (Event event : eventSet) {
			str+=event+" ";
		}
		str += "}";
		return str;
	}
	
	public TransactionModule toClone(){
		TransactionModule transactionModule = new TransactionModule();
		transactionModule.transactionID = transactionID;
		transactionModule.fail = fail;
		transactionModule.succeed = succeed;
		transactionModule.eventSet = Event.getClonedEvents(eventSet);
		return transactionModule;
	}
}
