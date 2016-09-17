/**
 * EECS 448 Project 1: Calendar
 * CalendarMonth.java
 * Authors: Rebekah Manweiler, Shawn Parkes, Stephen Fulton
 * Date: 2016-09-17
 */


import java.io.*;
/**
 * <h2>CalendarMonth Class</h2> 
 * <p>Contains an array of CalendarDay instances with size equal to the number of days of the month
 * @see CalendarDay
 */
public class CalendarMonth {

		private int numDays = 0;
		private CalendarDay[] Days;
		private String month = "";
		private int year = 0;
		
		/**
		 * CalendarMonth(int nd, String m) takes in the name of the month and the number of days
		 * <p>creates a CalendarDay object for each day of the month 
		 * @param nd
		 * @param m
		 */
		public CalendarMonth(int nd, String m){
			numDays = nd;
			month = m;
			Days = new CalendarDay[nd];
			for(int i = 1; i <= nd; i++){
				Days[i-1] = new CalendarDay(i, month);
			}
		}
		
		/**
		 * getMonth() returns the name of the month
		 * @return String
		 */
		public String getMonth(){
			return month;
		}
		
		/**
		 * getNumDays() returns the number of days of the month
		 * @return int
		 */
		public int getNumDays(){
			return numDays;
		}
		
		/**
		 * getDays() returns the array of day objects in the month
		 * @return CalendarDay[]
		 */
		public CalendarDay[] getDays(){
			return Days;
		}	
		
		/**
		 * getDay(int index) returns a day object at the specified index of the Days array
		 * @param index
		 * @return CalendarDay
		 */
		public CalendarDay getDay(int index){
			return Days[index];
		}
		
		/**
		 * getFirstDayOfMonth() returns the day of the week of the first day of the month
		 * @return String
		 */
		public String getFirstDayOfMonth()
		{
			return Days[0].getDayOfWeek();
		}
}
