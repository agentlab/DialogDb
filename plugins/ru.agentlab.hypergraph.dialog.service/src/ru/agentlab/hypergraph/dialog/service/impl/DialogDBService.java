package ru.agentlab.hypergraph.dialog.service.impl;

import java.util.List;

import org.hypergraphdb.HGHandle;
import org.hypergraphdb.HGPlainLink;
import org.hypergraphdb.HGQuery.hg;
import org.hypergraphdb.HyperGraph;
import org.osgi.service.component.annotations.Component;

import ru.agentlab.hypergraph.dialog.model.Dialog;
import ru.agentlab.hypergraph.dialog.model.Message;
import ru.agentlab.hypergraph.dialog.service.IDialogDBService;

@Component(enabled = true, immediate = true,
    name = "DialogDBService")
public class DialogDBService implements IDialogDBService{
	private HyperGraph graph;

    public DialogDBService() {
        graph = new HyperGraph("db");
    }

    protected DialogDBService(String path) {
		graph = new HyperGraph(path);
    }

	public void closeDB(){
		graph.close();
	}

	@Override
    public boolean addDialog(Dialog dialog){
		HGHandle handle1 = hg.findOne(graph, hg.and(hg.type(Dialog.class),hg.eq("dialogID", dialog.getDialogID())));
		HGHandle handle2 = hg.assertAtom(graph, dialog);
		if (handle2.equals(handle1))
        {
            return false;
        }

		HGHandle penultimateMessageHandle = null;
		for (Message m : dialog.getMessages()){
			HGHandle currentMessageHandle = graph.add(m);
			graph.add(new HGPlainLink(handle2,currentMessageHandle)); // dialog - message link
			try{graph.add(new HGPlainLink(currentMessageHandle, penultimateMessageHandle));}
				catch(NullPointerException e){}
			penultimateMessageHandle = currentMessageHandle;
		}
		return true;
	}

	@Override
    public Message getMessage(long id){
		return hg.getOne(graph, hg.and(hg.type(Message.class),
			   hg.eq("messageID", id)));
	}

	@Override
    public Dialog getDialog(long id){
		return hg.getOne(graph, hg.and(hg.type(Dialog.class),
								hg.eq("dialogID", id)));
	}

	@Override
    public boolean deleteDialog(long id){
		HGHandle handle1 = hg.findOne(graph, hg.and(hg.type(Dialog.class),hg.eq("dialogID", id)));
		if (handle1==null)
        {
            return false;
        }

		Dialog dialog = graph.get(handle1);
		for(Message m : dialog.getMessages()){
			HGHandle messageHandle = hg.findOne(graph, hg.and(hg.type(Message.class),hg.eq("messageID", m.getMessageID())));
			List<HGHandle> links = hg.findAll(graph, hg.incident(messageHandle));
			for(HGHandle linkHandle : links)
            {
                graph.remove(linkHandle);
            }
			graph.remove(messageHandle);
		}
		graph.remove(handle1);
		return true;
	}

}
