package com.geet.mining.experiment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.geet.mining.model.Event;
import com.geet.mining.model.Issue;
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

	private List<Transaction> transactions;
	
	// read each issue from an directory
	public Issue readIssueFromDirectory(String dirPath){
		transactions = new ArrayList<Transaction>();
		if (readTransactionsFromFile(dirPath)) {
			Issue issue = new Issue(Transaction.toCloneTransactions(getTransactions()));
			return issue;
		}else{
			System.err.println("Errrr");
			System.err.println(dirPath+"/logs.txt");
			System.exit(0);
		}
		return new Issue();
	}
	
	// read issue's healing action from file
	// Now the healing action file is ignored
	private boolean readHealingActionFromFile(String healFilePath){
		return true;
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
					if (tokens[4].startsWith("FAILURE")) {
						transactionBuilder.transactionStatus(Status.FAILURE);
					}else{
						transactionBuilder.transactionStatus(Status.SUCCESS);						
					}
					transactionBuilder.time(tokens[0]).event(new Event(tokens[1])).transactionID(tokens[2]).log(tokens[3]);
					Transaction transaction = transactionBuilder.build();
					getTransactions().add(transaction);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		finally{
			inputScanner.close();
		}
		return true;
	}
	
	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	/*
	 * Deprecated Fields and Methods
	 */
	
	@Deprecated
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
	@Deprecated
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
	@Deprecated
	private boolean [][] CONTEXT_TABLE;
	@Deprecated
	private Issue issue;
	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}

	@Deprecated
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
	
	@Deprecated
	// set transaction modules
	private void setTransactionModules(Transaction transaction) {
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
			issue.setFail(issue.getFail() + 1);
		} else {
			transactionModule.succeed++;
			issue.setSucceed(issue.getSucceed() + 1);
		}
		// set the full event set
		if (!issue.getEvents().contains(transaction.getEvent())) {
			issue.getEvents().add(transaction.getEvent());
		}
		// set the transaction types
		issue.getTransactionModules().put(transaction.getTransactionID(), transactionModule);

	}

	public static void main(String[] args) {
		InputHandler context = new InputHandler();
		context.readIssueFromDirectory("src/com/geet/mining/input/issue_1/");
	}

}
