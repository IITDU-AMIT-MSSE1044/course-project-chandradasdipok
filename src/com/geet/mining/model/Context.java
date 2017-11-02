package com.geet.mining.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.geet.mining.model.Transaction.TransactionBuilder;

/**
 * @author chandradasdipok
 * This class maintains the context
 * Each context has an issue
 */
public class Context {

	private Issue issue;
	private boolean [][] CONTEXT_TABLE;

	// read issues from file in text format
	public void readAndSetIssueFromFile(String filepath){
		issue = new Issue();
		readTransactionsFromFile(filepath);
		setContextTable();
		printContextTable();
	}
	
	// read transactions
	private void readTransactionsFromFile(String filepath){
		File inputFile = new File(filepath);
		Scanner inputScanner=null;
		try {
			inputScanner = new Scanner(inputFile);
			while (inputScanner.hasNext()) {
				String tokens[] = inputScanner.nextLine().split(",");
				if (tokens.length==5) {
					TransactionBuilder transactionBuilder = new TransactionBuilder();
					if (tokens[4].startsWith("0")) {
						transactionBuilder.transactionStatus(Status.FAILURE);
					}else{
						transactionBuilder.transactionStatus(Status.SUCCESS);						
					}
					transactionBuilder.time(tokens[0]).event(tokens[1]).transactionID(tokens[2]).log(tokens[3]);
					Transaction transaction = transactionBuilder.build();
					issue.getTransactions().add(transaction);
					setTransactionModules(transaction);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally{
			inputScanner.close();
		}
	}
	
	// set transaction modules
	private  void setTransactionModules(Transaction transaction){
		TransactionModule transactionModule = null;
		if (issue.getTransactionModules().containsKey(transaction.getTransactionID())) {
			transactionModule = issue.getTransactionModules().get(transaction.getTransactionID());
		} else {
			transactionModule = new TransactionModule();
			transactionModule.transactionID = (transaction.getTransactionID());
		}
		transactionModule.eventSet.add(transaction.getEventID());
		if (transaction.getTransactionStatus() == Status.FAILURE) {
			transactionModule.fail++;
			issue.fail++;
		} else {
			transactionModule.succeed++;
			issue.succeed++;
		}
		// set the full event set
		if (!issue.getEvents().contains(transaction.getEventID())) {
			issue.getEvents().add(transaction.getEventID());
		}
		// set the transaction types
		issue.getTransactionModules().put(transaction.getTransactionID(), transactionModule);
	}
	
	@Deprecated
	// set transaction types
	private void setTransactionModules(List<Transaction>transactions){
		for (Transaction transaction : transactions) {
			TransactionModule transactionModule = null;
			if (issue.getTransactionModules().containsKey(transaction.getTransactionID())) {
				transactionModule = issue.getTransactionModules().get(transaction.getTransactionID());
			}else{
				transactionModule = new TransactionModule();
				transactionModule.transactionID=(transaction.getTransactionID());
				
			}
			transactionModule.eventSet.add(transaction.getEventID());
			if (transaction.getTransactionStatus()==Status.FAILURE) {
				transactionModule.fail++;
			}else{
				transactionModule.succeed++;
			}
			// set the full event set
			if (!issue.getEvents().contains(transaction.getEventID())) {
				issue.getEvents().add(transaction.getEventID());
			}			
			// set the transaction types
			issue.getTransactionModules().put(transaction.getTransactionID(), transactionModule);
			
		}
	}
	
	// build the context table
	private  void setContextTable(){
		CONTEXT_TABLE = new boolean [issue.getTransactionModules().size()][issue.getEvents().size()];
		// event set is converted to array
		String [] eventsArray = new String[issue.getEvents().size()]; 
		int flag =0;
		for (String event : issue.getEvents()) {
			eventsArray[flag] = event;
			flag++;
		}
		// transaction modules keys set is converted into an array
		flag = 0;
		String [] transactionTypeArray = new String[issue.getTransactionModules().keySet().size()]; 
		for (String transaction : issue.getTransactionModules().keySet()) {
			transactionTypeArray[flag] = transaction;
			flag++;
		}
		flag = 0;
		for (int i = 0; i < transactionTypeArray.length; i++) {
			for (int j = 0; j < eventsArray.length; j++) {
				if (issue.getTransactionModules().get(transactionTypeArray[i]).eventSet.contains(eventsArray[j])) {
					CONTEXT_TABLE[i][j] = true;
				}
			}
		}
	}
	
	private void printContextTable(){
		System.out.println(issue.getEvents().toString());
		System.out.println(issue.getTransactionModules().keySet().toString());
		for (int i = 0; i < CONTEXT_TABLE.length; i++) {
			for (int j = 0; j < CONTEXT_TABLE[i].length; j++) {
				// print 1 for true and 0 for false
				System.out.print((CONTEXT_TABLE[i][j]?1:0)+" ");
			}
			System.out.println();
		}
	}	
	
	// Here events are attributes of FCA
	// It takes the attribute set and the context table
	// returns the closures of given attributes
	private Set<String> closureOfEvents(Set<String> events, boolean[][] contextTable){
		Set<String> closure = new HashSet<String>();
		Set<String> transactionsID = new HashSet<String>();
		
		// collects the transactions which has common attributes i.e., events
		for (String moduleKey : issue.getTransactionModules().keySet()) {
			if (issue.getTransactionModules().get(moduleKey).eventSet.containsAll(events)) {
				transactionsID.add(moduleKey);
			}
		}
		
		// if the object set of common attributes i.e., events is empty then return all the attributes 
		if (transactionsID.size() == 0) {
			return issue.getEvents();
		} 
		//other wise take the intersection of all the events of objects
		else {
			for (String transactionKey : transactionsID) {
				if (closure.size() == 0) {
					// store first module's events as closure
					closure = issue.getTransactionModules().get(transactionKey).eventSet;
				} else {
					// intersection of module's events given transactions with closure 				
					closure.retainAll(issue.getTransactionModules().get(transactionKey).eventSet);
				}
			}
		}
		return closure;
	}
	public static void main(String[] args) {
		Context context = new Context();
		context.readAndSetIssueFromFile("src/com/geet/mining/input/coursera.txt");
		System.out.println(context.issue.fail+" "+context.issue.succeed);
		Set<String> events = new HashSet<String>();
		events.add("d");
		events.add("e");
		System.out.println(context.closureOfEvents(events, context.CONTEXT_TABLE));
	}
}
