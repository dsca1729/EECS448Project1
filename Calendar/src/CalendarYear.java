//EECS 448 Project 1
//Stephen Fulton, Shawn Parkes, and Rebekah Manweiler
//Java CalendarYear class

import java.io.*;

public class CalendarYear {
	
	private CalendarMonth[] Months = new CalendarMonth[10];
	private static String firstDayofWeek = "Monday";
	
	public CalendarYear(){
		Months[0] = new CalendarMonth(31, "August", 2016);
		Months[1] = new CalendarMonth(30, "September", 2016);
		Months[2] = new CalendarMonth(31, "October", 2016);
		Months[3] = new CalendarMonth(30, "November", 2016);
		Months[4] = new CalendarMonth(31, "December", 2016);
		Months[5] = new CalendarMonth(31, "January", 2017);
		Months[6] = new CalendarMonth(28, "February", 2017);
		Months[7] = new CalendarMonth(31, "March", 2017);
		Months[8] = new CalendarMonth(30, "April", 2017);
		Months[9] = new CalendarMonth(31, "May", 2017);
	}
}
