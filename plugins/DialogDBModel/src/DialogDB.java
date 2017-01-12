import java.util.Date;
import java.util.Collections;
import java.util.List;

import org.hypergraphdb.HGHandle;
import org.hypergraphdb.HGPlainLink;
import org.hypergraphdb.HyperGraph;
import org.hypergraphdb.HGQuery.hg;

public class DialogDB {
	private HyperGraph graph;
	
	public DialogDB(String s){
		graph = new HyperGraph(s);
	}
	
	public void closeDB(){
		graph.close();
	}
	
	public void add(Message message){	
		
		HGHandle currentLastMessageHandle = hg.findOne(graph, hg.and(hg.type(Message.class),hg.eq("isLast", true))); //����� handle ���������� ���������
		HGHandle messageHandle = graph.add(message);							//���������� ������ ���������
		try{
			Message currentLastMessage = graph.get(currentLastMessageHandle); 	//�� handle �������� ������� ��������� ���������
			currentLastMessage.setIsLast(false);								//���������� ��������� ������ �� ���������
			graph.replace(currentLastMessageHandle, currentLastMessage);
			graph.add(new HGPlainLink(currentLastMessageHandle,messageHandle));	//������� ����� ����� �����������
			message.setMessageID(currentLastMessage.getMessageID()+1);			//��������� ���������
			graph.replace(messageHandle, message);
		}
		catch(NullPointerException e){} 										//���������, ������� �� ��������, �������� ������		
	}
	
	public Message find(int id){
		try{
			Message message = (Message) hg.getOne(graph, hg.and(hg.type(Message.class),	 //����� ��������� �� id
																hg.eq("messageID", id)));
			message.getMessageID();		
			return message;
		}catch(NullPointerException e){
			System.out.println("No message was found");
			return null;
		}
	}
	
	public List<Message> findAll(String sender){
		try{List<Message> messages = hg.getAll(graph, hg.and(hg.type(Message.class),
														 hg.eq("sender",sender))); 	//������ ���� ��������� ��������
		messages.get(0);
		Collections.sort(messages, new MessageCompare());							//����������
		return messages;
		}
		catch(IndexOutOfBoundsException e){
			System.out.println("No messages were found");
			return null;
		}
	}
	
	public void delete(int id){
		HGHandle messageHandle = hg.findOne(graph, hg.and(hg.type(Message.class),
														  hg.eq("messageID", id))); //����� handle ���������				
		List<HGHandle> links = hg.findAll(graph, hg.incident(messageHandle));	//��������� handle ���� �����, ��������� � ������ ����������
				
		HGHandle[] neighborMessageHandles = new HGHandle[2];	//������ handle "��������" ��������� (����. 2)
		
		try{
			for(int i=0;i<2;i++){
				neighborMessageHandles[i] = hg.findOne(graph, hg.and(hg.type(Message.class), 
																	 hg.not(hg.eq("messageID",id)),	
																	 hg.target(links.get(i))));
				graph.remove(links.get(i));														//������� �����
			}			
			graph.add(new HGPlainLink(neighborMessageHandles[0],neighborMessageHandles[1])); 	//������� ����� ����� "���������" �����������
			
		}
			catch(IndexOutOfBoundsException e){											//"��������" ��������� ����� ����
				Message message = graph.get(messageHandle);
				if (message.getIsLast()) {												//���� ��������� ��������� ���������
					try{
							Message penultimateMessage = graph.get(neighborMessageHandles[0]);	
							penultimateMessage.setIsLast(true);								//������� ��������� ������������� ���������
							graph.replace(neighborMessageHandles[0], penultimateMessage);
						}
					catch(NullPointerException ex){}				//��������� � ����� ������ ����
					}					
				}
		
		List<HGHandle> messages = hg.findAll(graph, hg.and(hg.type(Message.class),		//���� handle ���������, 
															hg.gt("messageID", id)));	//� ������� id ������ ����������
		try{
			for(HGHandle handle : messages){
				Message m = graph.get(handle);
				m.setMessageID(m.getMessageID()-1);	
				graph.replace(handle, m);
				}	
			}
		catch(NullPointerException e){}													//����� ��������� ���
		graph.remove(messageHandle);	//�������� ���������		
	}
	
	public List<Message> getAllMessages(){
		List<Message> messages = hg.getAll(graph, hg.type(Message.class));		//������ ���� ���������
		Collections.sort(messages, new MessageCompare());						//����������
		return messages;
	}
	

	
}
