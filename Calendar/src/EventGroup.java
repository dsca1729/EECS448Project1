import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class EventGroup{
	
	public ArrayList events;
	public static String filename = "Events.ser";
	
	public EventGroup(){
		
		events = getEvents();
	}
	
	private ArrayList getEvents(){
		
		try{
			
			FileInputStream fileIn = new FileInputStream(filename);
	        ObjectInputStream in = new ObjectInputStream(fileIn);
	        
	        ArrayList list = (ArrayList) in.readObject();
	        in.close();
	        fileIn.close();
	        
	        return list;
		}
		catch(Exception e){
			
			return new ArrayList();
		}
	}
	
	public void writeEvents(){
		
		try {
	         FileOutputStream fileOut = new FileOutputStream(filename);
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         
	         out.writeObject(events);
	         out.close();
	         fileOut.close();
	    }
		catch(Exception e) {
		     e.printStackTrace();
		}
	}
	
	public void addEvent(Event e){
		events.add(e);
	}
	
}
