package com.geet.mining.dataset;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.geet.mining.concept_contrast_analysis.InputHandler;
import com.geet.mining.model.Event;
import com.geet.mining.model.TransactionModule;

public class DataSetGenerator {

	static Set<TransactionModule> modules;
	static Set<Event> allEvents = new HashSet<Event>();

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
	
	public static void main(String[] args) {
		initializeModules();
		for (TransactionModule module : modules) {
			System.out.println(module.toString());
		}
		
		List<TransactionModule> issueModules = new ArrayList<TransactionModule>();
		issueModules.add(setTransactionModuleData("T1", 36, 1));
		issueModules.add(setTransactionModuleData("T5", 187, 0));
		issueModules.add(setTransactionModuleData("T7", 0, 485));
		System.out.println("HHHHHHHHHHHH");
		for (TransactionModule transactionModule : issueModules) {
			System.out.println(transactionModule);
		}
		
		InputHandler inputHandler = new InputHandler();
		inputHandler.readIssueFromTransactionModules(issueModules);
		inputHandler.issue.generateSignatures();
		
	}

}
