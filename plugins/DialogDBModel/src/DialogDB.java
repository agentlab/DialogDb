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
		
		HGHandle currentLastMessageHandle = hg.findOne(graph, hg.and(hg.type(Message.class),hg.eq("isLast", true))); //поиск handle последнего сообщения
		HGHandle messageHandle = graph.add(message);							//добавление нашего сообщения
		try{
			Message currentLastMessage = graph.get(currentLastMessageHandle); 	//по handle получаем текущее последнее сообщение
			currentLastMessage.setIsLast(false);								//полученное сообщение больше не последнее
			graph.replace(currentLastMessageHandle, currentLastMessage);
			graph.add(new HGPlainLink(currentLastMessageHandle,messageHandle));	//создаем связь между сообщениями
			message.setMessageID(currentLastMessage.getMessageID()+1);			//нумерация сообщений
			graph.replace(messageHandle, message);
		}
		catch(NullPointerException e){} 										//сообщение, которое мы добавили, является первым		
	}
	
	public Message find(int id){
		try{
			Message message = (Message) hg.getOne(graph, hg.and(hg.type(Message.class),	 //поиск сообщения по id
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
														 hg.eq("sender",sender))); 	//список всех сообщений адресата
		messages.get(0);
		Collections.sort(messages, new MessageCompare());							//сортировка
		return messages;
		}
		catch(IndexOutOfBoundsException e){
			System.out.println("No messages were found");
			return null;
		}
	}
	
	public void delete(int id){
		HGHandle messageHandle = hg.findOne(graph, hg.and(hg.type(Message.class),
														  hg.eq("messageID", id))); //поиск handle сообщения				
		List<HGHandle> links = hg.findAll(graph, hg.incident(messageHandle));	//получение handle всех ребер, связанных с данным сообщением
				
		HGHandle[] neighborMessageHandles = new HGHandle[2];	//массив handle "соседних" сообщений (макс. 2)
		
		try{
			for(int i=0;i<2;i++){
				neighborMessageHandles[i] = hg.findOne(graph, hg.and(hg.type(Message.class), 
																	 hg.not(hg.eq("messageID",id)),	
																	 hg.target(links.get(i))));
				graph.remove(links.get(i));														//удалить ребро
			}			
			graph.add(new HGPlainLink(neighborMessageHandles[0],neighborMessageHandles[1])); 	//создать связь между "соседними" сообщениями
			
		}
			catch(IndexOutOfBoundsException e){											//"соседнее" сообщение всего одно
				Message message = graph.get(messageHandle);
				if (message.getIsLast()) {												//если удаляемое сообщение последнее
					try{
							Message penultimateMessage = graph.get(neighborMessageHandles[0]);	
							penultimateMessage.setIsLast(true);								//сделать последним предпоследнее сообщение
							graph.replace(neighborMessageHandles[0], penultimateMessage);
						}
					catch(NullPointerException ex){}				//сообщение в графе вообще одно
					}					
				}
		
		List<HGHandle> messages = hg.findAll(graph, hg.and(hg.type(Message.class),		//всех handle сообщений, 
															hg.gt("messageID", id)));	//у которых id больше удаляемого
		try{
			for(HGHandle handle : messages){
				Message m = graph.get(handle);
				m.setMessageID(m.getMessageID()-1);	
				graph.replace(handle, m);
				}	
			}
		catch(NullPointerException e){}													//таких сообщений нет
		graph.remove(messageHandle);	//удаление сообщения		
	}
	
	public List<Message> getAllMessages(){
		List<Message> messages = hg.getAll(graph, hg.type(Message.class));		//список всех сообщений
		Collections.sort(messages, new MessageCompare());						//сортировка
		return messages;
	}
	

	
}
