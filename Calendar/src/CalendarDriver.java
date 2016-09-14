import java.io.*;

public class CalendarDriver {

	/**
	 * String firstDayofWeek - reset after every session as the oldest day with stored events
	 * 						 - is initialized in the CalendarDriver constructor
	 */
	private static CalendarYear year;
	private static CalendarDay curDay;
	
	public CalendarDriver()
	{
		year = new CalendarYear();
		getCurrentDate();
		setDaysofWeek("Monday");
	}
	
	public static void setDaysofWeek(String firstDayofWeek){
		
		String curday = firstDayofWeek;
			for(int m = 0; m < year.getMonths().length; m++){
				for(int d = 0; d < (year.getMonth(m)).getNumDays(); d++){
					CalendarMonth currentMonth = year.getMonth(m);
					CalendarDay currentDay = currentMonth.getDay(d);
					currentDay.setDayOfWeek(curday);
					curday = setNextDayofWeek(curday);
				}
			}
	}
	
	public static void getCurrentDate(){
		int currentDay;
		int currentMonth;
		try{
			BufferedReader br = new BufferedReader(new FileReader("MonthFiles/CurrentDate.txt"));
			currentDay = Integer.parseInt(br.readLine());
			currentMonth = Integer.parseInt(br.readLine());
			br.close();
			curDay = year.getMonth(currentMonth -1).getDay(currentDay -1);
		}catch(IOException e){}
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
