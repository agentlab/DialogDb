import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hypergraphdb.HGHandle;
import org.hypergraphdb.HGPlainLink;
import org.hypergraphdb.HyperGraph;
import org.hypergraphdb.atom.HGRel;
import org.hypergraphdb.atom.HGRelType;
import org.hypergraphdb.util.Pair;
import org.hypergraphdb.HGQuery.hg;
import org.hypergraphdb.algorithms.DefaultALGenerator;
import org.hypergraphdb.algorithms.HGDepthFirstTraversal;
import org.hypergraphdb.algorithms.HGTraversal;
import org.hypergraphdb.HGTypeSystem;

public class Dialog {
	public static void main(String[] args){		
		
		DialogDB db  = new DialogDB("db");
		db.add(new Message("X", new Date(), "������ �� ������ � ���������� ������� ����� �����, ��� ���� � ����.\n" 
										+"� ������, ��� �������� ��, � ����� ���� ��������. � ���� ���� ������� �������,\n"
										+"����������� ������"));
		db.add(new Message("Y", new Date(), "� �� ��� �� ����"));
		db.add(new Message("X", new Date(), "�����, �� �������! �� ���� �� ��� �� ����?"));
		db.add(new Message("Y", new Date(), "�� ��������. �� ���."));
		db.add(new Message("X", new Date(), "��� ����������� �� ������, ���������� �������, ��������� ��������� ����\n"
												+"� ��������� ����� � �� �� ����-�� �� �������"));
		db.add(new Message("Y", new Date(), "�� �����. �� �����."));
		db.add(new Message("X", new Date(), "�� ����, ��� �� ������� ������ ��� �����? �����, �� ������� ������ � �������,\n"
											+"��� � ����. ��� ��� ���������� ��������� � ������, ������� ��� ������ � ���,\n"
											+ "��� ������, ���������� � �����, ����� ���� ��� �����. � �������������,\n"
											+ " ��� ���� ������� ������� ������� � ����, � ������ ���� ��������,\n"
											+ " � ����� ��� ������� � �������. � ��?.. ��� �� �� �������?\n"
											+ " ���� �� �� ���� �� ��� �� ����. ��� ������� ���� �������� �����"));
		
		
		//db.Delete(3);
		
		//db.Delete(0);
		//db.Delete(0);
		//db.Delete(0);
		
		//db.add(new Message("Test", new Date(), "Test"));		
		
		//db.delete(5);		
		
		for(Message m : db.getAllMessages())
			System.out.println(m);
		
		
		//db.addMethodTest();
		//db.deleteMethodTest();
		//db.findMethodTest();
		//db.findAllMethodTest();
		
		//db.find(365);
		//db.find("�������");		
		
		/*System.out.println("��������� ���������:" +db.find(2));*/
		
		/*System.out.println("��������� �������:");
		for(Message m : db.find("�������"))
		System.out.println(m);*/
			
		db.closeDB();		
	}
	
	
	
	
	
	
	
	
	
	
	
}
