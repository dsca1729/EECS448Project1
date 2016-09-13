
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
public class CalendarDriver {

	/**
	 * String firstDayofWeek - reset after every session as the oldest day with stored events
	 * 						 - is initialized in the CalendarDriver constructor
	 */
	private String firstDayofWeek = "Monday";//ok for now
	private CalendarYear[] Years;
	private CalendarDay[] currentWeek;
	
	/**
	 * reads in files and creates needed years, months, days, and events
	 */
	public CalendarDriver(){
		String initilizepath = "Initialize.txt";
		try {
			BufferedReader in = new BufferedReader(new FileReader(initilizepath));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * takes all years, months, days, and events and writes them to respective files 
	 * http://stackoverflow.com/questions/2361510/how-to-save-application-options-before-exit
	 */
	public void saveAndExitCalendar(){
		try{
			for(int y = 0; y < Years.length; y++){
				CalendarYear curyear = Years[y];
				CalendarMonth[] curmonths = curyear.getMonths();
				for(int m = 0; m < curmonths.length; m++){
					CalendarMonth curmonth = curmonths[m];
				}
			}
		}catch (IOException e){
			
		}
	}
	
	public void setDaysofWeek(String firstDayofWeek){
		
		String curday = firstDayofWeek;
		
		for(int y = 0; y < Years.length; y++){
			for(int m = 0; m < Years[y].getMonths().length; m++){
				for(int d = 0; d < (Years[y].getMonth(m)).getNumDays(); d++){
					CalendarMonth currentMonth = Years[y].getMonth(m);
					CalendarDay currentDay = currentMonth.getDay(d);
					currentDay.setDayOfWeek(curday);
					curday = setNextDayofWeek(curday);
				}
			}
		}
	}
	
	public String setNextDayofWeek(String curday){
		if(curday == "Sunday") return "Monday";
		else if(curday == "Monday") return "Tuesday";
		else if(curday == "Tuesday") return "Wednesday";
		else if(curday == "Wednesday") return "Thursday";
		else if(curday == "Thursday") return "Friday";
		else if(curday == "Friday") return "Saturday";
		else return "Sunday";
	}

}
