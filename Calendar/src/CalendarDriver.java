import java.io.*;

public class CalendarDriver {

	/**
	 * String firstDayofWeek - reset after every session as the oldest day with stored events
	 * 						 - is initialized in the CalendarDriver constructor
	 */
	private static CalendarYear year;
	private static CalendarDay curDay;
	private static CalendarMonth curMonth;
	
	public CalendarDriver()
	{
		year = new CalendarYear();
		curDay = getCurrentDate();
		curMonth = getCurrentMonth();
		setDaysofWeek("Monday");
	}
	
	public CalendarYear getYear(){
		return year;
	}

	public CalendarMonth getCurrentMonth() {
		return year.getMonth(curDay.getMonth());
	}
	
	public String getNextMonth(String month){
		try{
			for(int i = 0; i < 12; i++){
				if(year.monthNames[i] == month) return year.monthNames[i+1];
			}
		}catch(Exception e){}
		return "";
	}
	
	public String getPrevMonth(String month){
		try{
			for(int i = 0; i < 12; i++){
				if(year.monthNames[i] == month) return year.monthNames[i-1];
			}
		}catch(Exception e){}
		return "";
	}

	public static String[] getMonthNames()
	{
		return year.monthNames;
	}
	
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
	
	public static String getFirstDayOfMonth(String monthName)
	{
		return year.getMonth(monthName).getFirstDayOfMonth();
	}
	
	public static CalendarDay setCurrentDate(String x, int y)
	{
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter("MonthFiles/CurrentDate.txt"));
			bw.write(Integer.toString(y));
			bw.newLine();
			bw.write(x);
			bw.close();
		}catch(IOException e){}
		curDay = year.getMonth(x).getDay(y - 1);
		curMonth = year.getMonth(curDay.getMonth());
		return curDay;
	}
	
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
	
	public static CalendarDay getCurrentDate()
	{
		int currentDay;
		String currentMonth;
		try{
			
			BufferedReader br = new BufferedReader(new FileReader("MonthFiles/CurrentDate.txt"));
			currentDay = Integer.parseInt(br.readLine());
			currentMonth = br.readLine();
			br.close();
			curDay = year.getMonth(currentMonth).getDay(currentDay-1);
			
		}catch(IOException e){}
		return curDay;
	}
	
	public static String setNextDayofWeek(String curday){
		if(curday == "Sunday") return "Monday";
		else if(curday == "Monday") return "Tuesday";
		else if(curday == "Tuesday") return "Wednesday";
		else if(curday == "Wednesday") return "Thursday";
		else if(curday == "Thursday") return "Friday";
		else if(curday == "Friday") return "Saturday";
		else return "Sunday";
	}
	
	public static CalendarWeek getPreviousWeek(CalendarWeek oldWeek)
	{
		CalendarDay tempDay = oldWeek.getWeek()[0];
		int tempDate = tempDay.getDate();
		String tempMonth = tempDay.getMonth();
		if(tempMonth.equals("August") && (tempDate <= 6))
		{
			return oldWeek;
		}
		else if(tempMonth.equals("May") && (tempDate >= 28))
		{
			return oldWeek;
		}
		else
		{
			if(tempDate >= 8)
			{
				return setupWeek(year.getMonth(tempMonth).getDay(tempDate - 8));
			}
			else
			{
				int offset = tempDate - 8;
				int temp = year.getMonthIndex(tempMonth) - 1;
				CalendarMonth m = year.getMonth(year.monthNames[temp-1]);
				tempDate = m.getNumDays() - 1 + offset;
				return setupWeek(m.getDay(tempDate));
				
			}
		}
		
	}
	
	public static CalendarWeek getWeek()
	{
		return setupWeek(curDay);
	}
	
	public static CalendarMonth getMonth(String month)
	{
		return year.getMonth(month);
	}
	
	public static CalendarWeek setupWeek(CalendarDay day)
	{
		int tempDate = day.getDate() - 1;
		int offset = day.getDate();
		int dayCount = 0;
		CalendarMonth tempMonth = year.getMonth(day.getMonth());
		String curDayOfWeek = day.getDayOfWeek();
		CalendarDay nullDay = new CalendarDay(0, "Null");
		nullDay.setDayOfWeek("");
		CalendarWeek newWeek = new CalendarWeek();
		if(tempMonth.getMonth().equals("August") && curDay.getDate() <= 6)
		{
			newWeek.setDay(0, nullDay);
			newWeek.setDay(1, tempMonth.getDay(0));
			newWeek.setDay(2, tempMonth.getDay(1));
			newWeek.setDay(3, tempMonth.getDay(2));
			newWeek.setDay(4, tempMonth.getDay(3));
			newWeek.setDay(5, tempMonth.getDay(4));
			newWeek.setDay(6, tempMonth.getDay(5));
			return newWeek;
		}
		else if(tempMonth.getMonth().equals("May") && curDay.getDate() >= 28)
		{
			newWeek.setDay(0, tempMonth.getDay(27));
			newWeek.setDay(1, tempMonth.getDay(28));
			newWeek.setDay(2, tempMonth.getDay(29));
			newWeek.setDay(3, tempMonth.getDay(30));
			newWeek.setDay(4, nullDay);
			newWeek.setDay(5, nullDay);
			newWeek.setDay(6, nullDay);
			return newWeek;
		}
		switch(curDayOfWeek)
		{
		case "Monday":
			offset = offset - 1;
			break;
		case "Tuesday":
			offset = offset - 2;
			break;
		case "Wednesday":
			offset = offset - 3;
			break;
		case "Thursday":
			offset = offset - 4;
			break;
		case "Friday":
			offset = offset - 5;
			break;
		case "Saturday":
			offset = offset - 6;
			break;
		}
		if(offset < 0)
		{
			int temp = year.getMonthIndex(tempMonth.getMonth()) - 1;
			tempMonth = year.getMonth(year.monthNames[temp]);
			tempDate = tempMonth.getNumDays() - 1;
			tempDate = tempDate + offset;
		}
		else{tempDate = offset - 1;}
		while(dayCount < 7)
		{
			newWeek.setDay(dayCount, tempMonth.getDays()[tempDate]);
			dayCount++;
			tempDate++;
			if(tempDate == tempMonth.getNumDays()){
				int temp = year.getMonthIndex(tempMonth.getMonth()) + 1;
				tempMonth = year.getMonth(year.monthNames[temp]);
				tempDate = 0;
			}
		}
		return newWeek;
	}
	
	

}