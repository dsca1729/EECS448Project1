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
		
		//retrieve all the events from serial file when the eventgroup is initialized
		retrieveEventsFromFile();
	}
	
	/**
	 * Retrieves all events from the .ser file
	 * 
	 * @src http://www.tutorialspoint.com/java/java_serialization.htm
	 * This is where information on serialization in java was found.
	 * 
	 * @return Arraylist<Event2> of all events in file
	 */
	public void retrieveEventsFromFile(){
		
		try{
			
			//read in array of events from file and add them to eventgroup object
			FileInputStream fileIn = new FileInputStream(filename);
	        ObjectInputStream in = new ObjectInputStream(fileIn);
	        
	        ArrayList<Event2> list = (ArrayList<Event2>) in.readObject();
	        in.close();
	        fileIn.close();
	        
	        this.events = list;
		}
		catch(Exception e){
			
			this.events = new ArrayList<Event2>();
		}
	}
	
	/**
	 * Save events(in an arraylist) to ser file
	 * 
	 * @return none
	 */
	public void saveEvents(){
		
		try {
			
			 //writes the array of events to the serial file
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
		
		//update events from the file
		retrieveEventsFromFile();
		
		if(events.size() == 0){ //if the array is empty
			events.add(e);
			saveEvents();
			return;
		}
		
		for(int i =0; i<events.size();i++){
			
			if(e.isBefore(events.get(i))){ //if the added event comes before another event in the array
				
				events.add(i, e); //add it to the array at the right position
				saveEvents();
				return; //exit the function
			}
		}
		
		//if the loop is never exited add the event to the end of the array
		events.add(e);
		saveEvents();
		return;
		
		/*
		 * The serial file is always saved upon exit to prevent discrepancies
		 * between instances of EventGroup
		 */
		
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
			
			//if event date matches the day
			if( singleEvent.startMonth.equals(month) && singleEvent.startDay == day && singleEvent.startYear == year){
				
				matchedEvents.add(singleEvent);
			}
			//Check for multiday
			else if(singleEvent.isMultiday && singleEvent.multiDayApplies(month, day)){ 
				
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
				break;
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
			
			System.out.println("->"+events.get(i).eventDescription);
		}
	}
	
}
