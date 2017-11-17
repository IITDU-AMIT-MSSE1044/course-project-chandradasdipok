package com.geet.mining.concept_analysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.geet.mining.model.Event;
import com.geet.mining.model.Issue;
import com.geet.mining.model.Node;
import com.geet.mining.model.Status;
import com.geet.mining.model.Transaction;
import com.geet.mining.model.TransactionModule;
import com.geet.mining.model.Transaction.TransactionBuilder;

/**
 * @author chandradasdipok
 * This class process the inputs
 * Reads issues from files
 */
public class InputHandler {

	public Issue issue;
	private boolean [][] CONTEXT_TABLE;

	
	public boolean readIssueFromTransactionModules(List<TransactionModule>modules){
		issue = new Issue();
		for (TransactionModule module : modules) {
			issue.getEvents().addAll(Event.getClonedEvents(module.eventSet));
			issue.setFail(issue.getFail()+module.fail);
			issue.setSucceed(issue.getSucceed()+module.succeed);
			issue.getTransactionModules().put(module.transactionID, module);
		}
		setContextTable();
		printContextTable();
		System.out.println(issue.getEvents());
		return false;
	}
	
	// read each issue from an directory
	public boolean readIssueFromDirectory(String dirPath){
		issue = new Issue();
		readTransactionsFromFile(dirPath+"/logs.txt");
		readHealingActionFromFile(dirPath+"/heal.txt");
		setContextTable();
		printContextTable();
		return false;
	}
	
	// read issue's healing action from file
	private boolean readHealingActionFromFile(String healFilePath){
		return false;
	}
	
	// read transactions
	private boolean readTransactionsFromFile(String logsfilepath){
		File inputFile = new File(logsfilepath);
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
					transactionBuilder.time(tokens[0]).event(new Event(tokens[1])).transactionID(tokens[2]).log(tokens[3]);
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
		return false;
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
		transactionModule.eventSet.add(transaction.getEvent());
		if (transaction.getTransactionStatus() == Status.FAILURE) {
			transactionModule.fail++;
			issue.setFail(issue.getFail()+1);
		} else {
			transactionModule.succeed++;
			issue.setSucceed(issue.getSucceed()+1);
		}
		// set the full event set
		if (!issue.getEvents().contains(transaction.getEvent())) {
			issue.getEvents().add(transaction.getEvent());
		}
		// set the transaction types
		issue.getTransactionModules().put(transaction.getTransactionID(), transactionModule);
		
	}
	
	
	
	// build the context table
	private  void setContextTable(){
		CONTEXT_TABLE = new boolean [issue.getTransactionModules().size()][issue.getEvents().size()];
		// event set is converted to array
		Event [] eventsArray = new Event[issue.getEvents().size()]; 
		int flag =0;
		for (Event event : issue.getEvents()) {
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
		System.out.println(transactionTypeArray.toString());
		System.out.println(eventsArray.toString());
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
	public static void main(String[] args) {
		InputHandler context = new InputHandler();
		context.readIssueFromDirectory("src/com/geet/mining/input/issue_01/");
		
	}

}
