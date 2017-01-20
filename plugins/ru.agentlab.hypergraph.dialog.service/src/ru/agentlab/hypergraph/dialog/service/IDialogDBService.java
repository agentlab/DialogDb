package ru.agentlab.hypergraph.dialog.service;

import ru.agentlab.hypergraph.dialog.model.Dialog;
import ru.agentlab.hypergraph.dialog.model.Message;

public interface IDialogDBService {
	
	boolean addDialog(Dialog dialog);
	
	Message getMessage(long id);
	
	Dialog getDialog(long id);
	
	boolean deleteDialog(long id);
	
}
