package com.geet.mining.model;

import java.util.HashSet;
import java.util.Set;

public class TransactionModule {
	String transactionID;
	int fail=0;
	int succeed=0;
	Set<Event> eventSet = new HashSet<Event>(); 
	
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
}
