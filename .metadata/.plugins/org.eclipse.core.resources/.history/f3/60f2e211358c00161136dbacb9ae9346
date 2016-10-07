/**
 * EECS 448 Project 1: Calendar
 * CalendarDriver.java
 * Authors: Rebekah Manweiler, Shawn Parkes, Stephen Fulton
 * Date: 2016-09-17
 */

import java.io.*;
/**
 * <h2>CalendarDriver Class</h2>
 * <p>Contains an instance "year" of the CalendarYear Class
 * Contains an instance "curDay" of the CalendarDay class
 * Contains the instance "curMonth" of the CalendarMonth class
 * @see CalendarYear
 * @see CalendarDay
 * @see CalendarMonth
 */
public class CalendarDriver {

	private static CalendarYear year;
	private static CalendarDay curDay;
	private static CalendarMonth curMonth;
	
	/**
	 * Sets CalendarDriver object up by setting
	 * <p>year object to new CalendarYear
	 * <p>currentDay object to currentDay read in from currentDay.txt
	 * <p>sets all days of the week in the year
	 */
	public CalendarDriver()
	{
		year = new CalendarYear();
		curDay = getCurrentDate();
		curMonth = getCurrentMonth();
		setDaysofWeek("Monday");
	}
	
	/**
	 * Returns the CalendarYear object
	 * @return CalendarYear 
	 */
	public CalendarYear getYear(){
		return year;
	}

	/**
	 * Gets the current month that the user is working within.
	 * @return CalendarMonth - month that curDay belongs to.
	 */
	public CalendarMonth getCurrentMonth() {
		return year.getMonth(curDay.getMonth());
	}
	
	/**
	 * Gets the name of the month that the user is currently working in.
	 * @return String - month name that curDay belongs to.
	 */
	public String getCurMonthName()
	{
		return year.getMonth(curDay.getMonth()).getMonth();
	}
	
	/**
	 * Gets the day of the week that curDay is.
	 * @return String - current day of week (Sunday, Monday, etc.).
	 */
	public String getCurrentDayOfWeek()
	{
		return curDay.getDayOfWeek();
	}
	
	/**
	 * Gets the name of the month after the one that is passed in - <br> Returns empty if month is may.
	 * @param month - name of month
	 * @return String - name of month that is after the given month
	 */
	public String getNextMonth(String month){
		try{
			for(int i = 0; i < 12; i++){
				if(year.monthNames[i] == month) return year.monthNames[i+1];
			}
		}catch(Exception e){}
		return "";
	}
	
	/**
	 * Gets the name of the month before the one that is passed in - <br> Returns empty if month is august.
	 * @param month - name of month
	 * @return String - name of month that is before the given month
	 */
	public String getPrevMonth(String month){
		try{
			for(int i = 0; i < 12; i++){
				if(year.monthNames[i] == month) return year.monthNames[i-1];
			}
		}catch(Exception e){}
		return "";
	}

	/**
	 * Gets an array of all the names of the months
	 * @return String[] - array of month names
	 */
	public static String[] getMonthNames()
	{
		return year.monthNames;
	}
	
	/**
	 * Returns the index of the month with the given name
	 * @param m - name of month
	 * @return int - index of month with given name
	 */
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
	
	/**
	 * Gets first day of the month with the given name.
	 * @param monthName - name of month
	 * @return String - name of first day of month (Sunday, Tuesday, etc.).
	 */
	public static String getFirstDayOfMonth(String monthName)
	{
		return year.getMonth(monthName).getFirstDayOfMonth();
	}
	
	/**
	 * Sets the current date by writing the current month and day that the user is looking at to the CurrentDate.txt file.
	 * @param m - name of month
	 * @param d - date in month user is looking at
	 * @return CalendarDay - day that has month m and date d
	 */
	public static CalendarDay setCurrentDate(String m, int d)
	{
		try{
			//opens current date tet file and writes the day (d) followed by the month (m)
			BufferedWriter bw = new BufferedWriter(new FileWriter("MonthFiles/CurrentDate.txt"));
			bw.write(Integer.toString(d));
			bw.newLine();
			bw.write(m);
			bw.close();
		}catch(IOException e){}
		curDay = year.getMonth(m).getDay(d - 1);
		curMonth = year.getMonth(curDay.getMonth());
		return curDay;
	}
	
	/**
	 * Sets the days of the week for every day in the year
	 * @param firstDayofWeek - day of week that the year starts on
	 */
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
	
	/**
	 * Gets the current day that the user has selected - loads in current day at start of program
	 * @return CalendarDay - day object that the user last selected
	 */
	public static CalendarDay getCurrentDate()
	{
		int currentDay;
		String currentMonth;
		try{
			//opens file and reads in day followed by month
			BufferedReader br = new BufferedReader(new FileReader("MonthFiles/CurrentDate.txt"));
			currentDay = Integer.parseInt(br.readLine());
			currentMonth = br.readLine();
			br.close();
			curDay = year.getMonth(currentMonth).getDay(currentDay-1);
			
		}catch(IOException e){}
		return curDay;
	}
	
	/**
	 * Gets the date of the current day in the month
	 * @return int - date of curDay
	 */
	public static int getCurDayOfMonth()
	{
		return curDay.getDate();
	}
	
	/**
	 * Sets the next day of week based on the current day of the week
	 * @param curday - current day of the week
	 * @return String - next day of the week
	 */
	public static String setNextDayofWeek(String curday){
		if(curday == "Sunday") return "Monday";
		else if(curday == "Monday") return "Tuesday";
		else if(curday == "Tuesday") return "Wednesday";
		else if(curday == "Wednesday") return "Thursday";
		else if(curday == "Thursday") return "Friday";
		else if(curday == "Friday") return "Saturday";
		else return "Sunday";
	}
	
	/**
	 * Gets the week prior to the week that is passed in.<br> Returns passed in week if it is the first week of the year.
	 * @param oldWeek - CalendarWeek that is currently viewed.
	 * @return CalendarWeek - week prior to the one passed in
	 * @see setupWeek
	 */
	public static CalendarWeek getPreviousWeek(CalendarWeek oldWeek)
	{
		CalendarDay tempDay = oldWeek.getDay(0);
		int tempDate = tempDay.getDate();
		String tempMonth = tempDay.getMonth();
		
		//if it is the first week of the month, then returns the week that was passed in
		if((tempMonth.equals("August") && (tempDate <= 6)) || tempMonth.equals("Null"))
		{
			return oldWeek;
		}
		//if it is the 2nd week of the month, since the first week starts on a monday, it passes in that specific day to form the new week.
		else if(tempMonth.equals("August") && tempDate == 7)
		{
			return setupWeek(year.getMonth("August").getDay(2));
		}
		//otherwise, it finds the previous Sunday from the current week and sends that day to the setupWeek function in calendarWeek
		else
		{
			if(tempDate >= 8)
			{
				return setupWeek(year.getMonth(tempMonth).getDay(tempDate - 8));
			}
			else
			{
				//gets the previous month to the one that the oldweek is on and finds the day to start the week on and calls setupWeek
				int offset = tempDate - 8;
				int temp = year.getMonthIndex(tempMonth) - 1;
				CalendarMonth m = year.getMonth(year.monthNames[temp]);
				tempDate = m.getNumDays() + offset;
				return setupWeek(m.getDay(tempDate));
				
			}
		}
		
	}
	
	/**
	 * Calls setupWeek and returns the week that is based on curDay
	 * @return CalendarWeek - week including curDay
	 */
	public static CalendarWeek getWeek()
	{
		return setupWeek(curDay);
	}
	
	/**
	 * Returns the month based on the given name in the string.
	 * @param month - name of month
	 * @return CalendarMonth - month based on given string
	 */
	public static CalendarMonth getMonth(String month)
	{
		return year.getMonth(month);
	}
	
	/**
	 * Gets the next week based on the old week passed in.<br>Returns old week if user passes in the last week of the month.
	 * @param oldWeek - week before the desired week
	 * @return CalendarWeek - week after oldweek
	 */
	public static CalendarWeek getNextWeek(CalendarWeek oldWeek)
	{
		int tempDate = oldWeek.getDay(0).getDate();
		int offset = tempDate + 7;
		CalendarMonth tempMonth = year.getMonth(oldWeek.getDay(0).getMonth());
		//if oldweek is last week of year, return old week
		if(tempMonth.getMonth().equals("May") && tempDate >= 28)
		{
			return oldWeek;
		}
		//if the offset (7 days after first day of old week) is past the number of days in oldweek's month
		//then find correct day in next month and use that for new week basis day.
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
	
	/**
	 * Sets up week based on given day and returns that week
	 * @param day - day around which the new week is based
	 * @return CalendarWeek - new week based around given CalendarDay
	 */
	public static CalendarWeek setupWeek(CalendarDay day)
	{
		CalendarWeek newWeek = new CalendarWeek();
		newWeek.setupWeek(day, year);
		return newWeek;
	}
}
