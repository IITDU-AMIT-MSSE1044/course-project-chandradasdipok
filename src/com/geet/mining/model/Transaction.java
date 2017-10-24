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
	private String time, eventID, transactionID, log;
	private Status transactionStatus;
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getEventID() {
		return eventID;
	}

	public void setEventID(String eventID) {
		this.eventID = eventID;
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
		private String time, eventID, transactionID, log;
		private Status transactionStatus;
		
		public TransactionBuilder time(String time){
			this.time = time;
			return this;
		}
		public TransactionBuilder event(String event){
			this.eventID = event;
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
			transaction.setEventID(eventID);
			transaction.setTransactionID(transactionID);;
			transaction.setLog(log);
			transaction.setTransactionStatus(transactionStatus);;
			return transaction;
		}
	}
	
	
	@Override
	public String toString() {
		return time+","+eventID+","+transactionID+","+log+","+transactionStatus;
	}
	
}
