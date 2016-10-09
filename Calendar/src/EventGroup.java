import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class EventGroup{
	
	public ArrayList<Event2> events;
	public static String filename = "MonthFiles/Events.ser";
	
	/**
	 * Constructor
	 * 
	 * Retrieves events from the ser file using built in methods
	 */
	public EventGroup(){
		
		events = retrieveEventsFromFile();
	}
	
	/**
	 * Retrieves all events from the .ser file
	 * 
	 * @return Arraylist<Event2> of all events in file
	 */
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
	
	/**
	 * Save events(in an arraylist) to ser file
	 * 
	 * @return none
	 */
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
	
	/**
	 * Add an event to events, making sure that events are added in-order.
	 * 
	 * @param Event2 e the event that is being added
	 * @return none
	 */
	public void addEvent(Event2 e){
		
		for(int i =0; i<events.size();i++){
			
			if(e.isBefore(events.get(i))){ //if the added event comes before another event in the array
				
				events.add(i, e); //add it to the array at the right position
				return; //exit the function
			}
		}
		
		//if the event does not come before any other events, add it at the end
		events.add(e);
		saveEvents(); //This is just for safety to make sure we never lose events
	}
	
	/**
	 * Gets a list of events for a given day by checking year, day, and month and returns them
	 * 
	 * @param String month: month to check
	 * @param int day: day to check
	 * @param int year: year to check
	 * 
	 * @return ArrayList<Event2>: a list of events that match a given day
	 */
	public ArrayList<Event2> getEventsForDate(String month, int day, int year){
		
		ArrayList<Event2> matchedEvents = new ArrayList<Event2>();
		
		for(int i =0; i < events.size(); i++){
			
			Event2 singleEvent = events.get(i);
			
			if( singleEvent.startMonth.equals(month) && singleEvent.startDay == day && singleEvent.startYear == year){
				
				matchedEvents.add(singleEvent);
			}
		}
		
		return matchedEvents;
	}
	
	/**
	 * Remove an event from the events list
	 * 
	 * @param Event2 badEvent: event to check for an remove
	 * @return none
	 */
	public void removeEvent(Event2 badEvent){
		
		for(int i =0; i < events.size(); i++){
			
			if(events.get(i).equals(badEvent)){
				events.remove(i);
				return;
			}
		}
	}
	
	/**
	 * Print all the events to the console, just for debugging purposes
	 * 
	 * @return none
	 */
	public void printEvents(){
		
		for(int i=0; i< events.size();i++){
			
			System.out.println(events.get(i).eventDescription);
		}
	}
	
}
