package com.geet.mining.model;

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
			transaction.setTime(time);
			transaction.setEvent(event);
			transaction.setTransactionID(transactionID);;
			transaction.setLog(log);
			transaction.setTransactionStatus(transactionStatus);;
			return transaction;
		}
	}
	
	
	@Override
	public String toString() {
		return time+","+event+","+transactionID+","+log+","+transactionStatus;
	}
	
}
