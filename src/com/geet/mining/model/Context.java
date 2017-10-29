package com.geet.mining.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
		events = new HashSet<String>();
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
			// set the full event set
			if (!events.contains(transaction.getEventID())) {
				events.add(transaction.getEventID());
			}			
			// set the transaction types
			transactionTypesMap.put(transaction.getTransactionID(), transactionType);
			
		}
	}
	// build the context table
	public static void setContextTable(){
		CONTEXT_TABLE = new boolean [transactionTypesMap.size()][events.size()];
		// event set is converted to array
		String [] eventsArray = new String[events.size()]; 
		int flag =0;
		for (String event : events) {
			eventsArray[flag] = event;
			flag++;
		}
		// transactiontypes keys set is converted into an array
		flag = 0;
		String [] transactionTypeArray = new String[transactionTypesMap.keySet().size()]; 
		for (String transaction : transactionTypesMap.keySet()) {
			transactionTypeArray[flag] = transaction;
			flag++;
		}
		flag = 0;
		for (int i = 0; i < transactionTypeArray.length; i++) {
			for (int j = 0; j < eventsArray.length; j++) {
				if (transactionTypesMap.get(transactionTypeArray[i]).eventSet.contains(eventsArray[j])) {
					CONTEXT_TABLE[i][j] = true;
				}
			}
		}
	}
	
	static void printContextTable(){
		System.out.println(events.toString());
		System.out.println(transactionTypesMap.keySet().toString());
		for (int i = 0; i < CONTEXT_TABLE.length; i++) {
			for (int j = 0; j < CONTEXT_TABLE[i].length; j++) {
				// print 1 for true and 0 for false
				System.out.print((CONTEXT_TABLE[i][j]?1:0)+" ");
			}
			System.out.println();
		}
	}	
	public static void main(String[] args) {
		
		
/*		for (Transaction transaction : readTransactionsFromFile("src/com/geet/mining/input/input.txt")) {
			System.out.println(transaction);
		}
*/	
		setTransactionTypes(readTransactionsFromFile("src/com/geet/mining/input/input.txt"));
		for (String key : transactionTypesMap.keySet()) {
			System.out.println(transactionTypesMap.get(key));
		}
		setContextTable();
		printContextTable();
	}

}
