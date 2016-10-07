import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class EventGroup{
	
	public ArrayList<Event> events;
	public static String filename = "Events.ser";
	
	public EventGroup(){
		
		events = getEvents();
	}
	
	private ArrayList<Event> getEvents(){
		
		try{
			
			FileInputStream fileIn = new FileInputStream(filename);
	        ObjectInputStream in = new ObjectInputStream(fileIn);
	        
	        ArrayList<Event> list = (ArrayList<Event>) in.readObject();
	        
	        in.close();
	        fileIn.close();
	        
	        return list;
		}
		catch(Exception e){
			
			return new ArrayList<Event>();
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
	
	public Event getCurrentDay(){
		return events.get(0);
	}
	
	public String getEventsForDate(String date){
		
		String events_string = new String("");
		Event single_event;
		
		for(int i=0;i<events.size();i++){
			
			single_event = events.get(i);
			if(single_event.getStartDate() == date){
				
				events_string = events_string + single_event.getEventName() + "\n";
			}
		}
		
		return events_string;
	}
	
}
