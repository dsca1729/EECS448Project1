
public class CalendarDriver {

	/**
	 * String firstDayofWeek - reset after every session as the oldest day with stored events
	 * 						 - is initialized in the CalendarDriver constructor
	 */
	private String firstDayofWeek = "Monday";//ok for now
	private CalendarYear[] Years;
	private CalendarDay[] currentWeek;
	
	public CalendarDriver(){
		
	}
	
	public void setDaysofWeek(String firstDayofWeek){
		
		String curday = firstDayofWeek;
		
		for(int y = 0; y < Years.length; y++){
			for(int m = 0; m < Years[y].getMonths().length; m++){
				for(int d = 0; d < (Years[y].getMonth(m)).getNumDays(); d++){
					CalendarMonth currentMonth = Years[y].getMonth(m);
					CalendarDay currentDay = currentMonth.getDay(d);
					currentDay.setDayOfWeek(curday);
					curday = setNextDayofWeek(curday);
				}
			}
		}
	}
	
	public String setNextDayofWeek(String curday){
		if(curday == "Sunday") return "Monday";
		else if(curday == "Monday") return "Tuesday";
		else if(curday == "Tuesday") return "Wednesday";
		else if(curday == "Wednesday") return "Thursday";
		else if(curday == "Thursday") return "Friday";
		else if(curday == "Friday") return "Saturday";
		else return "Sunday";
	}

}
