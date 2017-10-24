package com.geet.mining.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.geet.mining.model.Transaction.TransactionBuilder;

/**
 * 
 * @author chandradasdipok
 * This class maintains the context table of transactions and events
 */
public class Context {

	static Set<String> events;
	static Map<String,TransactionType> transactionTypesMap = new HashMap<String,TransactionType>();
	static  boolean [][] CONTEXT_TABLE;
	
	// read transactions
	public static List<Transaction> readTransactionsFromFile(String filepath){
		List<Transaction> transactions = new ArrayList<Transaction>();
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
					transactions.add(transactionBuilder.build());
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally{
			inputScanner.close();
		}
		return transactions;
	}
	
	// set transaction types
	public static void setTransactionTypes(List<Transaction>transactions){
		for (Transaction transaction : transactions) {
			TransactionType transactionType = null;
			if (transactionTypesMap.containsKey(transaction.getTransactionID())) {
				transactionType = transactionTypesMap.get(transaction.getTransactionID());
			}else{
				transactionType = new TransactionType();
				transactionType.transactionID=(transaction.getTransactionID());
				
			}
			transactionType.eventSet.add(transaction.getEventID());
			if (transaction.getTransactionStatus()==Status.FAILURE) {
				transactionType.fail++;
			}else{
				transactionType.succeed++;
			}
			transactionTypesMap.put(transaction.getTransactionID(), transactionType);
		}
	}
	
	// build the context table
	
	
	public static void main(String[] args) {
		
		
/*		for (Transaction transaction : readTransactionsFromFile("src/com/geet/mining/input/input.txt")) {
			System.out.println(transaction);
		}
*/	
		setTransactionTypes(readTransactionsFromFile("src/com/geet/mining/input/input.txt"));
		for (String key : transactionTypesMap.keySet()) {
			System.out.println(transactionTypesMap.get(key));
		}
	}
	

}
