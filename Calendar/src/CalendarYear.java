//EECS 448 Project 1
//Stephen Fulton, Shawn Parkes, and Rebekah Manweiler
//Java CalendarYear class

import java.io.*;

public class CalendarYear {
	
	private CalendarMonth[] Months;
	private int Year = 0;
	
	public CalendarYear(int y){
		Year = y;
		Months[0] = new CalendarMonth("January", Year);
		Months[1] = new CalendarMonth("February", Year);
		Months[2] = new CalendarMonth("March", Year);
		Months[3] = new CalendarMonth("April", Year);
		Months[4] = new CalendarMonth("May", Year);
		Months[5] = new CalendarMonth("June", Year);
		Months[6] = new CalendarMonth("July", Year);
		Months[7] = new CalendarMonth("August", Year);
		Months[8] = new CalendarMonth("September", Year);
		Months[9] = new CalendarMonth("October", Year);
		Months[10] = new CalendarMonth("November", Year);
		Months[11] = new CalendarMonth("December", Year);
	}
	
	public CalendarMonth getMonth(int index){
		return Months[index];
	}
	
	public CalendarMonth[] getMonths(){
		return Months;
	}
	
}
