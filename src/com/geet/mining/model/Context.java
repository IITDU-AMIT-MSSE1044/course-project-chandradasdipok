package com.geet.mining.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
			issue.fail++;
		} else {
			transactionModule.succeed++;
			issue.succeed++;
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
	
	// Here events are attributes of FCA
	// It takes the attribute set and the context table
	// returns the closures of given attributes
	private Set<Event> closureOfEvents(Set<Event> events, boolean[][] contextTable){
		Set<Event> closure = new HashSet<Event>();
		Set<String> transactionsID = new HashSet<String>();
		
		// collects the transactions which has common attributes i.e., events
		for (String moduleKey : issue.getTransactionModules().keySet()) {
			if (issue.getTransactionModules().get(moduleKey).eventSet.containsAll(events)) {
				transactionsID.add(moduleKey);
				System.out.println("Module Key"+moduleKey);
			}
		}
		
		// if the object set of common attributes i.e., events is empty then return all the attributes 
		if (transactionsID.size() == 0) {
			return Event.getClonedEvents(issue.getEvents());
		} 
		//other wise take the intersection of all the events of objects
		else {
			for (String transactionKey : transactionsID) {
				if (closure.size() == 0) {
					// store first module's events as closure
					closure = Event.getClonedEvents(issue.getTransactionModules().get(transactionKey).eventSet);
				} else {
					// intersection of module's events given transactions with closure 				
					closure.retainAll(Event.getClonedEvents(issue.getTransactionModules().get(transactionKey).eventSet));
				}
			}
		}
		return closure;
	}
	
	// next closure algorithms
	private Set<Event> getNextClosedSet(Set<Event> closedSet, List<Event> attributes){
		for (int i = attributes.size()-1; i >=0; i--) {
			Set<Event> nextClosedSet = new HashSet<Event>();
			Event m = attributes.get(i);
			System.out.println("Element "+m);
			if (closedSet.contains(m)) {
				closedSet.remove(m);
				System.out.println("Closed Set after remove "+closedSet);
			} else {
				nextClosedSet.addAll(closedSet);
				nextClosedSet.add(m);
				System.out.println("Next Closed Set "+nextClosedSet);
				System.out.println("Total Events "+issue.getEvents());
				System.out.println("Closures: "+closureOfEvents(nextClosedSet, CONTEXT_TABLE));
				nextClosedSet = closureOfEvents(nextClosedSet, CONTEXT_TABLE);
				System.out.println("Closures of Next Closed Set "+nextClosedSet);
				if (!hasLessThanElementM(nextClosedSet, closedSet, m)) {
					return nextClosedSet;
				}
			}
		}
		return new HashSet<Event>();
	}
	
	// detect whether there is difference between closed set and 
	// next closed set less than m
	private boolean hasLessThanElementM(Set<Event> nextClosedSet, Set<Event>closedSet, Event eventM){
		System.out.println("Element Check "+eventM);
		System.out.println(nextClosedSet);
		System.out.println(closedSet);
		Set<Event> diff = nextClosedSet;
		diff.removeAll(closedSet);
		System.out.println(diff);
		// if has elements less than eventM
		// return true
		for (Event event : diff) {
			if (eventM.getEventString().compareTo(event.getEventString())> 0) {
				System.out.println("Smallest New Element "+ event);
				return true;
			}
		}
		return false;
	}
	
	private Set<Event> getFirstClosure(){
		return new HashSet<Event>();
	}
	
	private void getAllClosures(List<Event> attributes){
		Set<Event> closedSet = getFirstClosure();
		int i=0;
		while (i<50) {
			System.out.println("No. "+i+": Closed Sets "+closedSet);
			closedSet = Event.getClonedEvents(getNextClosedSet(Event.getClonedEvents(closedSet), attributes));
			i++;
		}
		return ;
	}
	
	public static void main(String[] args) {
		Context context = new Context();
		context.readAndSetIssueFromFile("src/com/geet/mining/input/coursera.txt");
		System.out.println(context.issue.fail+" "+context.issue.succeed);
		context.getAllClosures(new ArrayList<Event>(Event.getClonedEvents(context.issue.getEvents())));
		//System.out.println(context.getNextClosedSet(new HashSet<Event>(), new ArrayList<>(context.issue.getEvents())));;
		/*System.out.println();
		Set<Event> events = new HashSet<Event>();
		events.add(new Event("c"));
		events.add(new Event("e"));
		System.out.println(context.closureOfEvents(events,context.CONTEXT_TABLE));*/
	}

}
