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
		db.add(new Message("X", new Date(), "Стоишь на берегу и чувствуешь соленый запах ветра, что веет с моря.\n" 
										+"И веришь, что свободен ты, и жизнь лишь началась. И губы жжет подруги поцелуй,\n"
										+"пропитанный слезой…"));
		db.add(new Message("Y", new Date(), "Я не был на море…"));
		db.add(new Message("X", new Date(), "Ладно, не заливай! Ни разу не был на море?"));
		db.add(new Message("Y", new Date(), "Не довелось. Не был."));
		db.add(new Message("X", new Date(), "Уже постучались на небеса, накачались текилой, буквально проводили себя\n"
												+"в последний путь… А ты на море-то не побывал…"));
		db.add(new Message("Y", new Date(), "Не успел. Не вышло."));
		db.add(new Message("X", new Date(), "Не знал, что на небесах никуда без этого? Пойми, на небесах только и говорят,\n"
											+"что о море. Как оно бесконечно прекрасно… О закате, который они видели… О том,\n"
											+ "как солнце, погружаясь в волны, стало алым как кровь. И почувствовали,\n"
											+ " что море впитало энергию светила в себя, и солнце было укрощено,\n"
											+ " и огонь уже догорал в глубине. А ты?.. Что ты им скажешь?\n"
											+ " Ведь ты ни разу не был на море. Там наверху тебя окрестят лохом…"));
		
		
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
		//db.find("Алексей");		
		
		/*System.out.println("найденное сообщение:" +db.find(2));*/
		
		/*System.out.println("сообщения алексея:");
		for(Message m : db.find("Алексей"))
		System.out.println(m);*/
			
		db.closeDB();		
	}
	
	
	
	
	
	
	
	
	
	
	
}
