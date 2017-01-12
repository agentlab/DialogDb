import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
	
	private String content;
	private String sender;
	private Date date;
	private boolean isLast;
	private int messageID;
	
	public Message(){}
	
	public Message(String sender, Date date, String content){
		this.sender = sender;
		this.date = date;
		this.content = content;
		isLast = true;
		messageID = 0;
	}
	
	
	public String getContent(){return content;}
	public void setContent(String content){this.content=content;}
	
	public String getSender(){return sender;}
	public void setSender(String sender){this.sender=sender;}
	
	public Date getDate(){return date;}
	public void setDate(Date date){this.date=date;}
	
	public boolean getIsLast(){return isLast;}
	public void setIsLast(boolean isLast){this.isLast = isLast;}
	
	public int getMessageID(){return messageID;}
	public void setMessageID(int messageID){this.messageID=messageID;}
	
	public String toString(){
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");		
		return messageID + ": " + sender + "\t" + df.format(date) + '\n' + content;
	}	
	
}
