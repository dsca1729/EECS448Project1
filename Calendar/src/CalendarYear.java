//EECS 448 Project 1
//Stephen Fulton, Shawn Parkes, and Rebekah Manweiler
//Java CalendarYear class

import java.io.*;

public class CalendarYear {
	
	private static CalendarMonth[] Months = new CalendarMonth[10];
	public final static String[] monthNames = {"August", "September", "October", "November", "December", "January", "February", "March", "April", "May"};
	public final static int[] daysInMonth = {31,30,31,30,31,31,28,31,30,31};
	
	public CalendarYear()
	{
		for (int i = 0; i < 10; i++)
		{
			Months[i] = new CalendarMonth(daysInMonth[i], monthNames[i]);
		}
		//loadEvents();
	}
	
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
	
	public CalendarMonth getMonth(String curMonth){
		for(int i = 0; i < Months.length; i++)
		{
			if(curMonth.equals(Months[i].getMonth())){
				return Months[i];
			}
		}
		return Months[0];
	}
	
	public CalendarMonth[] getMonths(){
		return Months;
	}
}
