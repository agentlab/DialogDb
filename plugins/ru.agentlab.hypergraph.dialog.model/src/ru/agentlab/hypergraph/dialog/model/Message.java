package ru.agentlab.hypergraph.dialog.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {	
	private String content;
	private User sender;
	private Date date;
	private long messageID;
	private Message next;
	
	public Message(){}
	
	public Message(String content, User sender, Date date, long messageID) {
		super();
		this.content = content;
		this.sender = sender;
		this.date = date;
		this.messageID = messageID;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public long getMessageID() {
		return messageID;
	}

	public void setMessageID(long messageID) {
		this.messageID = messageID;
	}

	public Message getNext() {
		return next;
	}

	public void setNext(Message next) {
		this.next = next;
	}

	@Override
	public String toString() {
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");		
		return messageID + ": " + sender + "\t" + df.format(date) + '\n' + content;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (messageID ^ (messageID >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (messageID != other.messageID)
			return false;
		return true;
	}	
}
