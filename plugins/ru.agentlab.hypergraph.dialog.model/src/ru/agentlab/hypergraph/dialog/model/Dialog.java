package ru.agentlab.hypergraph.dialog.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Dialog {
	private Set<User> users;
	private List<Message> messages;
	private long dialogID;
	
	public Dialog(){
		users = new HashSet<User>();
		messages = new ArrayList<Message>();
	}
	
	public Dialog(long dialogID){
		users = new HashSet<User>();
		messages = new ArrayList<Message>();
		this.dialogID = dialogID;
	}	
	
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}	

	public long getDialogID() {
		return dialogID;
	}

	public void setDialogID(long dialogID) {
		this.dialogID = dialogID;
	}

	@Override
	public String toString() {
		String s="Users:";
		for(User user : users)
			s+=user+",";
		s=s.substring(0,s.length()-1);
		s+="\nMessages:";
		for(Message message : messages)
			s+='\n'+message.toString();
		
		return s;
	}
	
	public void addMessage(Message message){
		message.setMessageID(getDialogID()*1000000 + messages.size());
		users.add(message.getSender());
		try{messages.get(messages.size()-1).setNext(message);}
		catch(IndexOutOfBoundsException e){}
		messages.add(message);
	}
	
	public void removeMessage(int index){
		Message message = messages.get(index);
		try{
			Message nextMessage = message.getNext();
			messages.get(index-1).setNext(nextMessage);
		}
		catch(IndexOutOfBoundsException e){}
		messages.remove(index);
	}
}
