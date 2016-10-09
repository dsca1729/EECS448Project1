import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class EventGroup{
	
	public ArrayList<Event2> events;
	public static String filename = "MonthFiles/Events.ser";
	
	public EventGroup(){
		
		events = retrieveEventsFromFile();
	}
	
	private ArrayList<Event2> retrieveEventsFromFile(){
		
		try{
			
			FileInputStream fileIn = new FileInputStream(filename);
	        ObjectInputStream in = new ObjectInputStream(fileIn);
	        
	        ArrayList<Event2> list = (ArrayList<Event2>) in.readObject();
	        in.close();
	        fileIn.close();
	        
	        return list;
		}
		catch(Exception e){
			
			return new ArrayList<Event2>();
		}
	}
	
	public void saveEvents(){
		
		try {
			
			printEvents();
			
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
	
	public void addEvent(Event2 e){
		
		for(int i =0; i<events.size();i++){
			if(e.isBefore(events.get(i))){
				events.add(i, e);
				break;
			}
		}
	}
	
	public ArrayList<String> getEventsForDate(String month, int day, int year){
		
		ArrayList<String> matchedEvents = new ArrayList<String>();
		
		for(int i =0; i < events.size(); i++){
			
			Event2 singleEvent = events.get(i);
			
			if( singleEvent.startMonth.equals(month) && singleEvent.startDay == day && singleEvent.startYear == year){
				
				matchedEvents.add(singleEvent.eventDescription);
			}
		}
		
		return matchedEvents;
	}
	
	public void removeEvent(Event2 badEvent){
		
		for(int i =0; i < events.size(); i++){
			
			if(events.get(i).equals(badEvent)){
				events.remove(i);
			}
		}
	}
	
	public void printEvents(){
		for(int i=0; i< events.size();i++){
			System.out.println(events.get(i).eventDescription);
		}
	}
	
}
