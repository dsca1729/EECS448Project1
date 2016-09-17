import java.io.*;

public class CalendarDriver {

	/**
	 * String firstDayofWeek - reset after every session as the oldest day with stored events
	 * 						 - is initialized in the CalendarDriver constructor
	 */
	private static CalendarYear year;
	private static CalendarDay curDay;
	private static CalendarMonth curMonth;
	
	public CalendarDriver()
	{
		year = new CalendarYear();
		curDay = getCurrentDate();
		curMonth = getCurrentMonth();
		setDaysofWeek("Monday");
	}
	
	public CalendarYear getYear(){
		return year;
	}

	public CalendarMonth getCurrentMonth() {
		return year.getMonth(curDay.getMonth());
	}
	
	public String getCurMonthName()
	{
		return year.getMonth(curDay.getMonth()).getMonth();
	}
	
	public String getCurrentDayOfWeek()
	{
		return curDay.getDayOfWeek();
	}
	
	public String getNextMonth(String month){
		try{
			for(int i = 0; i < 12; i++){
				if(year.monthNames[i] == month) return year.monthNames[i+1];
			}
		}catch(Exception e){}
		return "";
	}
	
	public String getPrevMonth(String month){
		try{
			for(int i = 0; i < 12; i++){
				if(year.monthNames[i] == month) return year.monthNames[i-1];
			}
		}catch(Exception e){}
		return "";
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
	
	public static String getFirstDayOfMonth(String monthName)
	{
		return year.getMonth(monthName).getFirstDayOfMonth();
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
		curMonth = year.getMonth(curDay.getMonth());
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
	
	public static int getCurDayOfMonth()
	{
		return curDay.getDate();
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
	
	public static CalendarWeek getPreviousWeek(CalendarWeek oldWeek)
	{
		CalendarDay tempDay = oldWeek.getDay(0);
		int tempDate = tempDay.getDate();
		String tempMonth = tempDay.getMonth();
		if((tempMonth.equals("August") && (tempDate <= 6)) || tempMonth.equals("Null"))
		{
			return oldWeek;
		}
		else if(tempMonth.equals("August") && tempDate == 7)
		{
			return setupWeek(year.getMonth("August").getDay(2));
		}
		else
		{
			if(tempDate >= 8)
			{
				return setupWeek(year.getMonth(tempMonth).getDay(tempDate - 8));
			}
			else
			{
				int offset = tempDate - 8;
				int temp = year.getMonthIndex(tempMonth) - 1;
				CalendarMonth m = year.getMonth(year.monthNames[temp]);
				tempDate = m.getNumDays() + offset;
				return setupWeek(m.getDay(tempDate));
				
			}
		}
		
	}
	
	public static CalendarWeek getWeek()
	{
		return setupWeek(curDay);
	}
	
	public static CalendarMonth getMonth(String month)
	{
		return year.getMonth(month);
	}
	
	public static CalendarWeek getNextWeek(CalendarWeek oldWeek)
	{
		int tempDate = oldWeek.getDay(0).getDate();
		int offset = tempDate + 7;
		CalendarMonth tempMonth = year.getMonth(oldWeek.getDay(0).getMonth());
		if(tempMonth.getMonth().equals("May") && tempDate >= 28)
		{
			return oldWeek;
		}
		if(offset > tempMonth.getNumDays())
		{
			offset = offset - tempMonth.getNumDays();
			tempMonth = year.getMonths()[year.getMonthIndex(tempMonth.getMonth())+1];
			tempDate = 1;
			tempDate = tempDate + offset;
			return setupWeek(tempMonth.getDay(tempDate-2));
		}
		else
		{
			return setupWeek(tempMonth.getDay(offset-1));
		}
	}
	
	public static CalendarWeek setupWeek(CalendarDay day)
	{
		CalendarWeek newWeek = new CalendarWeek();
		newWeek.setupWeek(day, year);
		return newWeek;
	}
}
