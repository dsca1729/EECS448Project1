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
		curDay = getCurrentDate();
		setDaysofWeek("Monday");
	}
	
	public static String[] getMonthNames()
	{
		return year.monthNames;
	}
	
	public static int getCurrentMonthIndex(String m)
	{
		for(int i = 0; i < year.getMonths().length; i++)
		{
			if(m.equals(year.getMonths()[i].getMonth())){
				return i;
			}
		}
		return 0;
	}
	
	public static CalendarDay setCurrentDate(String x, int y)
	{
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter("MonthFiles/CurrentDate.txt"));
			bw.write(Integer.toString(y));
			bw.newLine();
			bw.write(x);
			bw.close();
		}catch(IOException e){}
		curDay = year.getMonth(x).getDay(y - 1);
		return curDay;
	}
	
	public static void setDaysofWeek(String firstDayofWeek){
		
		String curday = firstDayofWeek;
		for(int m = 0; m < year.getMonths().length; m++){
			for(int d = 0; d < (year.getMonths())[m].getNumDays(); d++){
				CalendarMonth currentMonth = year.getMonths()[m];
				CalendarDay currentDay = currentMonth.getDay(d);
				currentDay.setDayOfWeek(curday);
				curday = setNextDayofWeek(curday);
			}
		}
	}
	
	public static CalendarDay getCurrentDate()
	{
		int currentDay;
		String currentMonth;
		try{
			
			BufferedReader br = new BufferedReader(new FileReader("MonthFiles/CurrentDate.txt"));
			currentDay = Integer.parseInt(br.readLine());
			currentMonth = br.readLine();
			br.close();
			curDay = year.getMonth(currentMonth).getDay(currentDay-1);
			
		}catch(IOException e){}
		return curDay;
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
	
	/*public static CalendarWeek getWeek()
	{
		int tempDate = curDay.getDate() - 1;
		CalendarMonth tempMonth = year.getMonth(curDay.getMonth());
		String curDayOfWeek = curDay.getDayOfWeek();
		switch(curDayOfWeek)
		{
			case "Sunday":
			
		}
		CalendarDay nullDay = new CalendarDay(0, ""); // used to fill gaps at beginning of august and end of may
		int dayCount = 0; // keeps track of how many days have been put into the week
		CalendarWeek newWeek = new CalendarWeek();
		//checks if week is at beginning of august
		if(tempMonth.equals("August"))
		{
			if(tempDate == 1 || tempDate == 2 || tempDate == 3 || tempDate == 4 || tempDate == 5 || tempDate == 6)
			{
				newWeek.setDay(0, nullDay);
				dayCount++;
			}
		}
	}*/
	
	

}