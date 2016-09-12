import java.io.*;

public class CalendarDriver {

	/**
	 * String firstDayofWeek - reset after every session as the oldest day with stored events
	 * 						 - is initialized in the CalendarDriver constructor
	 */
	private static String firstDayofWeek = "Monday";//ok for now
	private static CalendarYear[] Years;
	
	public static void setDaysofWeek(String firstDayofWeek){
		
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
	
	public static void main(String[] args)
	{
		Years = new CalendarYear[1];
		CalendarDay x = new CalendarDay(5,"September");
		x.addEvent("this sucks a lot");
		x.addEvent("this, like, really sucks");
		System.out.println(x.getEvents());
		x.removeEvent(2);
		CalendarJFrame y = new CalendarJFrame();
	}
	
	public static String setNextDayofWeek(String curday){
		if(curday == "Sunday") return "Monday";
		else if(curday == "Monday") return "Tuesday";
		else if(curday == "Tuesday") return "Wednesday";
		else if(curday == "Wednesday") return "Thursday";
		else if(curday == "Thursday") return "Friday";
		else if(curday == "Friday") return "Saturday";
		else return "Sunday";
	}

}
