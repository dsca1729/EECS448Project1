//EECS 448 Project 1
//Stephen Fulton, Shawn Parkes, and Rebekah Manweiler
//Java CalendaryMonth class

public class CalendarMonth {

		private int numDays = 0;
		private CalendarDay[] Days;
		private String month = "";
		private int year = 0;
		
		public CalendarMonth(String m, int y){
			month = m;
			year = y;
			numDays = setNumDays(month, y);
			Days = new CalendarDay[numDays];
			for(int i = 1; i <= numDays; i++){
				Days[i-1] = new CalendarDay(i, month);
			}
		}
		
		public int setNumDays(String name, int y){
			if(name == "April" || name == "June" || name == "September" || name == "November"){
				return 30;
			}
			else if(name == "February" && (y%4 == 0)){
				return 29;
			}
			else if(name == "February"){
				return 28;
			}
			else return 31;
		}
		
		public String getMonth(){
			return month;
		}
		
		public int getYear(){
			return year;
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
}
