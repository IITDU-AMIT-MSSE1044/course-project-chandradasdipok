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

	private Set<String> events;
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
		events = new HashSet<String>();
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
		if (!events.contains(transaction.getEventID())) {
			events.add(transaction.getEventID());
		}
		// set the transaction types
		issue.getTransactionModules().put(transaction.getTransactionID(), transactionModule);
	}
	
	@Deprecated
	// set transaction types
	private void setTransactionModules(List<Transaction>transactions){
		events = new HashSet<String>();
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
			if (!events.contains(transaction.getEventID())) {
				events.add(transaction.getEventID());
			}			
			// set the transaction types
			issue.getTransactionModules().put(transaction.getTransactionID(), transactionModule);
			
		}
	}
	
	// build the context table
	private  void setContextTable(){
		CONTEXT_TABLE = new boolean [issue.getTransactionModules().size()][events.size()];
		// event set is converted to array
		String [] eventsArray = new String[events.size()]; 
		int flag =0;
		for (String event : events) {
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
		System.out.println(events.toString());
		System.out.println(issue.getTransactionModules().keySet().toString());
		for (int i = 0; i < CONTEXT_TABLE.length; i++) {
			for (int j = 0; j < CONTEXT_TABLE[i].length; j++) {
				// print 1 for true and 0 for false
				System.out.print((CONTEXT_TABLE[i][j]?1:0)+" ");
			}
			System.out.println();
		}
	}	
	public static void main(String[] args) {
		Context context = new Context();
		context.readAndSetIssueFromFile("src/com/geet/mining/input/input.txt");
		System.out.println(context.issue.fail+" "+context.issue.succeed);
	}

}
