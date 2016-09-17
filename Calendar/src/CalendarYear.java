//EECS 448 Project 1
//Stephen Fulton, Shawn Parkes, and Rebekah Manweiler
//Java CalendarYear class

import java.io.*;
/**
 * <h2>CalendarYear Class</h2> 
 * <p>Contains an array of CalendarMonth instances for each month (Aug-May) of this academic year (2016-2017)
 * @see CalendarMonth
 */
public class CalendarYear {
	
	private static CalendarMonth[] Months = new CalendarMonth[10];
	public final static String[] monthNames = {"August", "September", "October", "November", "December", "January", "February", "March", "April", "May"};
	public final static int[] daysInMonth = {31,30,31,30,31,31,28,31,30,31};
	
	/**
	 * <h3>Calendar Year Constructor</h3><p>
	 * Creates each month object in the array Months
	 * @see Calendarmonth
	 */
	public CalendarYear()
	{
		for (int i = 0; i < 10; i++)
		{
			Months[i] = new CalendarMonth(daysInMonth[i], monthNames[i]);
		}
		//loadEvents();
	}
	
	/**
	 * getMonthIndex(String month) returns the index of a month object given its name
	 * @param month
	 * @return int
	 */
	public static int getMonthIndex(String month)
	{
		for(int i = 0; i < Months.length; i++)
		{
			if(month.equals(monthNames[i]))
			{
				return i;
			}
		}
		return 0;
	}
	
	/**
	 * getMonth(String curMonth) returns a month object given its name
	 * @param curMonth
	 * @return CalendarMonth
	 */
	public CalendarMonth getMonth(String curMonth){
		for(int i = 0; i < Months.length; i++)
		{
			if(curMonth.equals(Months[i].getMonth())){
				return Months[i];
			}
		}
		return Months[0];
	}
	
	/**
	 * getMonths() returns the array of month objects
	 * @param none
	 * @return CalendarMonth[]
	 */
	public CalendarMonth[] getMonths(){
		return Months;
	}
}
