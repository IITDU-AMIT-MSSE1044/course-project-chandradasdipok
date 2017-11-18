package com.geet.mining.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author chandradasdipok
 * @date Oct 24, 2017
 * 
 * This class is the model of transactions of issues
 * A transaction  has  attributes namely transaction time @time, 
 * event @eventID, transaction ID @transactionID, log message @log,
 * status (success or failure) @status 
 */
public class Transaction {
	private Event event;
	private String time, transactionID, log;
	private Status transactionStatus;
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public String getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public Status getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(Status transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public Transaction toClone(){
		return new TransactionBuilder().event(event).time(time).transactionID(transactionID)
				.transactionStatus(transactionStatus).log(log).build();
	}
	
	public static List<Transaction> toCloneTransactions(List<Transaction> toCloneList){
		List<Transaction> transactions = new ArrayList<Transaction>();
		for (Transaction transaction : toCloneList) {
			transactions.add(transaction.toClone());
		}
		return transactions;
	}
	
	private Transaction(){};
	
	public static class TransactionBuilder{
		private Event event;
		private String time, transactionID, log;
		private Status transactionStatus;
		
		public TransactionBuilder time(String time){
			this.time = time;
			return this;
		}
		public TransactionBuilder event(Event event){
			this.event = event;
			return this;
		}public TransactionBuilder transactionID(String transactionID){
			this.transactionID = transactionID;
			return this;
		}public TransactionBuilder log(String log){
			this.log = log;
			return this;
		}public TransactionBuilder transactionStatus(Status transactionStatus){
			this.transactionStatus = transactionStatus;
			return this;
		}
		public Transaction build(){
			Transaction transaction = new Transaction();
			transaction.setTime(this.time);
			transaction.setEvent(this.event);
			transaction.setTransactionID(this.transactionID);;
			transaction.setLog(this.log);
			transaction.setTransactionStatus(this.transactionStatus);;
			return transaction;
		}
	}
	
	
	@Override
	public String toString() {
		return time+","+event+","+transactionID+","+log+","+transactionStatus;
	}
	
}
