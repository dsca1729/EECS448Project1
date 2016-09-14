//EECS 448 Project 1
//Stephen Fulton, Shawn Parkes, and Rebekah Manweiler
//Java CalendaryMonth class

import java.io.*;

public class CalendarMonth {

		private int numDays = 0;
		private CalendarDay[] Days;
		private String month = "";
		private int year = 0;
		
		public CalendarMonth(int nd, String m){
			numDays = nd;
			month = m;
			Days = new CalendarDay[nd];
			for(int i = 1; i <= nd; i++){
				Days[i-1] = new CalendarDay(i, month);
			}
		}
		
		public String getMonth(){
			return month;
		}
		
		public int getNumDays(){
			return numDays;
		}
		
		public CalendarDay[] getDays(){
			return Days;
		}	
		
		public CalendarDay getDay(int index){
			return Days[index];
		}
		
		public String getFirstDayOfMonth()
		{
			return Days[0].getDayOfWeek();
		}
}
