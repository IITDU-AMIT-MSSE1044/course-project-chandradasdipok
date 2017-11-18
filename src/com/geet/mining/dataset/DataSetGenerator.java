package com.geet.mining.dataset;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.geet.mining.model.Event;
import com.geet.mining.model.Issue;
import com.geet.mining.model.Status;
import com.geet.mining.model.Transaction;
import com.geet.mining.model.Transaction.TransactionBuilder;
import com.geet.mining.model.TransactionModule;

public class DataSetGenerator {

	static Set<TransactionModule> modules;
	static Set<Event> allEvents = new HashSet<Event>();
	static List<String> moduleString = new ArrayList<String>();

	public DataSetGenerator() {
		initializeModules();
	}
	
	private static void initializeEvents() {
		allEvents.add(new Event("a"));
		allEvents.add(new Event("b"));
		allEvents.add(new Event("c"));
		allEvents.add(new Event("d"));
		allEvents.add(new Event("e"));
		allEvents.add(new Event("x1"));
		allEvents.add(new Event("x2"));
		allEvents.add(new Event("x3"));
		allEvents.add(new Event("x4"));
		allEvents.add(new Event("x5"));
		allEvents.add(new Event("x6"));
		allEvents.add(new Event("x7"));
		allEvents.add(new Event("x8"));
		allEvents.add(new Event("y1"));
		allEvents.add(new Event("y2"));
		allEvents.add(new Event("p"));
		allEvents.add(new Event("q"));
		allEvents.add(new Event("k"));
		allEvents.add(new Event("z"));
	}

	private static Event getEvent(String evStr) {
		for (Event event : allEvents) {
			if (event.getEventString().equals(evStr)) {
				return event.clone();
			}
		}
		return null;
	}

	public Issue getRandomIssue(){
		Issue randomIssue=new Issue(executeRandomTransactions());		
		return randomIssue;
	}
	
	
	private static void initializeModules() {
		initializeEvents();
		TransactionModule module = new TransactionModule();
		Set<Event> events = new HashSet<Event>();
		modules = new HashSet<TransactionModule>();
		// New Instance Starts //
		module = new TransactionModule();
		events = new HashSet<Event>();
		events.add(getEvent("a"));
		events.add(getEvent("b"));
		events.add(getEvent("x1"));
		events.add(getEvent("x2"));
		events.add(getEvent("x3"));
		events.add(getEvent("x4"));
		events.add(getEvent("x5"));
		events.add(getEvent("x6"));
		events.add(getEvent("x7"));
		events.add(getEvent("x8"));
		events.add(getEvent("z"));
		module.eventSet = Event.getClonedEvents(events);
		module.transactionID = "T1";
		moduleString.add(module.transactionID);
		modules.add(module);
		// New Instance Ends //
		// New Instance Starts //
		module = new TransactionModule();
		events = new HashSet<Event>();
		events.add(getEvent("a"));
		events.add(getEvent("b"));
		events.add(getEvent("p"));
		events.add(getEvent("z"));
		module.eventSet = Event.getClonedEvents(events);
		module.transactionID = "T2";
		moduleString.add(module.transactionID);
		modules.add(module);
		// New Instance Ends //
		// New Instance Starts //
		module = new TransactionModule();
		events = new HashSet<Event>();
		events.add(getEvent("a"));
		events.add(getEvent("b"));
		events.add(getEvent("q"));
		events.add(getEvent("z"));
		module.eventSet = Event.getClonedEvents(events);
		module.transactionID = "T3";
		moduleString.add(module.transactionID);
		modules.add(module);
		// New Instance Ends //
		// New Instance Starts //
		module = new TransactionModule();
		events = new HashSet<Event>();
		events.add(getEvent("a"));
		events.add(getEvent("b"));
		events.add(getEvent("k"));
		events.add(getEvent("z"));
		module.eventSet = Event.getClonedEvents(events);
		module.transactionID = "T4";
		moduleString.add(module.transactionID);
		modules.add(module);
		// New Instance Ends //
		// New Instance Starts //
		module = new TransactionModule();
		events = new HashSet<Event>();
		events.add(getEvent("a"));
		events.add(getEvent("b"));
		events.add(getEvent("c"));
		events.add(getEvent("d"));
		events.add(getEvent("e"));
		events.add(getEvent("y1"));
		events.add(getEvent("z"));
		module.eventSet = Event.getClonedEvents(events);
		module.transactionID = "T5";
		moduleString.add(module.transactionID);
		modules.add(module);
		// New Instance Ends //
		// New Instance Starts //
		module = new TransactionModule();
		events = new HashSet<Event>();
		events.add(getEvent("a"));
		events.add(getEvent("b"));
		events.add(getEvent("c"));
		events.add(getEvent("d"));
		events.add(getEvent("e"));
		events.add(getEvent("y2"));
		events.add(getEvent("z"));
		module.eventSet = Event.getClonedEvents(events);
		module.transactionID = "T6";
		moduleString.add(module.transactionID);
		modules.add(module);
		// New Instance Ends //
		// New Instance Starts //
		module = new TransactionModule();
		events = new HashSet<Event>();
		events.add(getEvent("a"));
		events.add(getEvent("b"));
		events.add(getEvent("c"));
		events.add(getEvent("d"));
		events.add(getEvent("e"));
		events.add(getEvent("z"));
		module.eventSet = Event.getClonedEvents(events);
		module.transactionID = "T7";
		moduleString.add(module.transactionID);
		modules.add(module);
		// New Instance Ends //
	}
	
	private static TransactionModule setTransactionModuleData(String transactionID,int fail, int succ){
		TransactionModule transactionModule = new TransactionModule();
		for (TransactionModule module : modules) {
			if (module.transactionID.equals(transactionID)) {
				transactionModule = module.toClone();
				transactionModule.fail = fail;
				transactionModule.succeed = succ;
				return transactionModule;
			}
		}
		return null;
	}

	
	// randomly execute transactions and results
	private List<Transaction> executeRandomTransactions(){
		// transactionID to be random but belongs to transaction modules type
		// transaction event to random but belongs to that transaction module type
		// transaction status to be random either 0 or 1 referring success or failure 
		List<Transaction> transactions = new ArrayList<Transaction>();
		Random random = new Random();
		int moduleSize = modules.size();
		int counter = 0;
		while (counter < 100 || random.nextBoolean()) {
			// first transaction ID
			int moduleToPick = random.nextInt(moduleSize);
			TransactionModule transactionModule= setTransactionModuleData(moduleString.get(moduleToPick),0,0); 
			for (Event event:transactionModule.eventSet) {
				String transactionID = transactionModule.transactionID;
				Status transactionStatus = Status.SUCCESS;
				if (random.nextBoolean()) {
					transactionStatus = Status.FAILURE;
				}

				Transaction transaction = new TransactionBuilder().event(event).transactionID(transactionID)
						.time(System.currentTimeMillis()+"").log("log").transactionStatus(transactionStatus).build();
				System.out.println(transaction);
				transactions.add(transaction);
			}
			counter++;
		}
		return transactions;
	}
	
	public static void generateDataSet(){
		for (int i = 0; i < 50; i++) {
			try {
				File dir = new File("src/com/geet/mining/input/issue_"+i);
				if (!dir.exists()) {
					System.out.println(dir.mkdir());;
				}
				FileWriter fileWriter = new FileWriter(new File(dir.getAbsolutePath()+"/logs.txt"));
				List<Transaction> transactions = new DataSetGenerator().executeRandomTransactions();
				for (Transaction transaction : transactions) {
					System.out.println(transaction);
					fileWriter.write(transaction+"\n");
				}
				fileWriter.close();
				System.out.println(transactions.size());
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
	}
	public static void main(String[] args) {
		generateDataSet();
	}
}
