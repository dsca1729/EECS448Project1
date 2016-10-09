
public class Event2 implements java.io.Serializable{
	
	//Member Variables
	//Make Getters and Setters if time available
	
	public String startMonth;
	public String endMonth;
	
	public int startDay;
	public int endDay;
	
	public int startYear;
	public int endYear;
	
	public int startTime;
	public int endTime;
	
	public boolean isMultiday;
	public boolean isRecurring;
	
	public String eventDescription;
	
	//Constructors
	
	public Event2(String month, int day, int year, int startTime, int endTime, String eventDescription, boolean isRecurring){ //Normal Event Constructor
			
		this.startMonth = month;
		this.endMonth = month;
		
		this.startDay = day;
		this.endDay = day;
		
		this.startYear = year;
		this.endYear = year;
		
		this.startTime = startTime;
		this.endTime = endTime;
		
		this.eventDescription = eventDescription;
		
		this.isMultiday = false;
		this.isRecurring = isRecurring;
	}
	
	//This constructor is for multi-day events
	public Event2(String startMonth, String endMonth, int startDay, int endDay, int startYear, int endYear, int startTime, int endTime, String eventDescription){
		
		this.startMonth = startMonth;
		this.endMonth = endMonth;
		
		this.startDay = startDay;
		this.endDay = endDay;
		
		this.startYear = startYear;
		this.endYear = endYear;
		
		this.startTime = startTime;
		this.endTime = endTime;
		
		this.eventDescription = eventDescription;
		
		this.isMultiday = true;
		this.isRecurring = false;
	}
	
	// src: http://stackoverflow.com/questions/16069106/how-to-compare-two-java-objects
	@Override
	public boolean equals(Object other) {
	    if (!(other instanceof Event2)) {
	        return false;
	    }

	    Event2 that = (Event2) other;

	    if(this.startDay != that.startDay) return false;
	    if(this.endDay != that.endDay) return false;
	    if(this.startMonth != that.startMonth) return false;
	    if(this.endMonth != that.endMonth) return false;
	    if(this.startYear != that.startYear) return false;
	    if(this.endYear != that.endYear) return false;
	    if(this.startTime != that.startTime) return false;
	    if(this.endTime != that.endTime) return false;
	    if(this.eventDescription != that.eventDescription) return false;
	    
	    return true;

	}
	
	public boolean isBefore(Event2 other){
		
		if(this.startYear < other.startYear) return true;
		else if(this.startYear > other.startYear) return false;
		else{
			if(monthStringToInt(this.startMonth) < monthStringToInt(other.startMonth)) return true;
			else if(monthStringToInt(this.startMonth) > monthStringToInt(other.startMonth)) return false;
			else{
				if(this.startDay < other.startDay) return true;
				else if(this.startDay > other.startDay) return false;
				else{
					if(this.startTime <= other.startTime) return true;
					else return false;
				}
			}
		}
	}
	
	public int monthStringToInt(String month){
		
		switch(month){
			
			case "January":
				return 1;
			case "February":
				return 2;
			case "March":
				return 3;
			case "April":
				return 4;
			case "May":
				return 5;
			case "June":
				return 6;
			case "July":
				return 7;
			case "August":
				return 8;
			case "September":
				return 8;
			case "October":
				return 10;
			case "November":
				return 11;
			case "December":
				return 12;
			
			default:
				return 0;
		}
	}
	
}
