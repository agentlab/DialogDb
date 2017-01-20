package ru.agentlab.hypergraph.dialog.test;

import static com.codeaffine.osgi.test.util.ServiceCollector.collectServices;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ru.agentlab.hypergraph.dialog.model.Dialog;
import ru.agentlab.hypergraph.dialog.model.Message;
import ru.agentlab.hypergraph.dialog.model.User;
import ru.agentlab.hypergraph.dialog.service.IDialogDBService;
import ru.agentlab.hypergraph.dialog.service.impl.DialogDBService;

public class DialogDBTest {
    private static DialogDBService service;
	private Dialog dialog;
	private User sender1,sender2;
	private Message m1,m2;
    private static List services;

	@Before
	public void setUp() {

		dialog = new Dialog(123);
		sender1 = new User("Sender 1");
		sender2 = new User("Sender 2");

		m1 = new Message("Test_message_1", sender1, new Date(), 0);
		m2 = new Message("Test_message_2", sender2, new Date(), 1);

		dialog.addMessage(m1);
		dialog.addMessage(m2);
    }

    @BeforeClass
    public static void setUpDBService() {
        services = collectServices(IDialogDBService.class, DialogDBService.class);
        service = (DialogDBService)services.get(0);
	}

	@Test
    public void addDialogTest() {

        assertTrue(services.size() == 1);

        service.addDialog(dialog);
        Dialog dialog1 = service.getDialog(123);

        assertTrue(dialog1.equals(dialog));
        assertTrue(dialog1.getMessages().get(0).equals(m1) && dialog1.getMessages().get(1).equals(m2));
        assertTrue(dialog1.getMessages().get(0).getNext().equals(m2));
        assertTrue(dialog1.getUsers().contains(sender1) && dialog1.getUsers().contains(sender2));

        Message m3 = service.getMessage(123000000);
        Message m4 = service.getMessage(123000001);
        assertTrue(m3.equals(m1) && m4.equals(m2));

        service.deleteDialog(123);
	}

    @Test
    public void deleteDialogTest() {

    	service.addDialog(dialog);
    	service.deleteDialog(123);

    	assertTrue(service.getDialog(123) == null);
    	assertTrue(service.getMessage(123000000) == null);
    	assertTrue(service.getMessage(123000001) == null);
    }

    @AfterClass
    public static void setDown() {
        service.closeDB();
    }
}
