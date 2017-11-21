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
		setTransactions(new ArrayList<Transaction>());
		if (readTransactionsFromFile(dirPath) ) {
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
}