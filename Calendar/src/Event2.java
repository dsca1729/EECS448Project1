
public class Event2 implements java.io.Serializable{
	
	
	/*
	 * Member Variables
	 * 
	 */
	
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
	
	/**
	 * Normal event constructor
	 * 	
	 * @param month - month of event(start and end are the same)
	 * @param day - date of event
	 * @param year - year of event
	 * @param startTime - start time of event
	 * @param endTime - end time of event
	 * @param eventDescription - Description of event
	 * @param isRecurring - used to notate whether it's a recurring event(may or may not be used)
	 */
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
	
	/**
	 * Multiday Constructor
	 * @param startMonth - start Month of event
	 * @param endMonth - end month of the event
	 * @param startDay - start day of the event
	 * @param endDay - end day of the event
	 * @param startYear - start year of the event
	 * @param endYear - end year of the event
	 * @param eventDescription - actual text of the event
	 */
	public Event2(String startMonth, String endMonth, int startDay, int endDay, int startYear, int endYear, String eventDescription){
		
		this.startMonth = startMonth;
		this.endMonth = endMonth;
		
		this.startDay = startDay;
		this.endDay = endDay;
		
		this.startYear = startYear;
		this.endYear = endYear;
		
		this.startTime = 50;
		this.endTime = 50;
		
		this.eventDescription = eventDescription;
		
		this.isMultiday = true;
		this.isRecurring = false;
	}
	
	
	/**
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 * 
	 * src: http://stackoverflow.com/questions/16069106/how-to-compare-two-java-objects
	 */
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
	
	/**
	 * Used to compare two Events, to see if one comes before another
	 * 
	 * @param other: the other event that is being compared
	 * @return true if this event comes before the other
	 */
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
	/**
	 * Checks if multiday applies for an event for a given date
	 * @param month - month that is checked against multiday event
	 * @param date - date that is checked against multiday event
	 * @return true if the input date is part of the multiday event
	 */
	public boolean multiDayApplies(String month, int date){
		
		int newEndMonth = monthStringToInt(this.endMonth); //1-8
		int newCheckMonth = monthStringToInt(month);
		int newStartMonth = monthStringToInt(this.startMonth);
		
		/*
		 * Years were not considered in original project, Since it only applies
		 * for one school year, we can get away with only checking month by assuming
		 * January through May are in 2017
		 */
		
		if(newEndMonth < 8) newEndMonth += 12; //8-17
		if(newCheckMonth < 8) newCheckMonth += 12;
		if(newStartMonth < 8) newStartMonth += 12;
		
		/*
		 * The following statements return true if the input day falls within scope of
		 * the multiday event.
		 */
		if(newCheckMonth < newEndMonth && newCheckMonth > newStartMonth) return true;
		else if(newCheckMonth == newStartMonth && this.startDay <= date) return true;
		else if(newCheckMonth == newEndMonth && this.endDay >= date) return true;
		else return false;
	
	}
	
	/**
	 * Converts a month string to an integer value
	 * 
	 * @param String month: String representation of the month
	 * @return int: int representation of the month
	 */
	public static int monthStringToInt(String month){
		
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
