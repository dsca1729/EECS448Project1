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
	}
	
	public CalendarMonth getMonth(int index){
		return Months[index];
	}
	
	public CalendarMonth[] getMonths(){
		return Months;
	}
	
	public static void loadEvents()
	{
		for(int i = 0; i < Months.length; i++)
		{
			for(int j = 0; j < (Months[i]).getDays().length; j++)
			{
				((Months[i]).getDays())[j].loadDayEvents();
			}
		}
	}
}
