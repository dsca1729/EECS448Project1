
public class CalendarWeek {
	
	//array of days in the calendarweek
	private CalendarDay[] currentWeek = new CalendarDay[7];
	
	/**
	 * Gets an array of CalendarDays found within the week
	 * @return CalendarDay[] - array of CalendarDays in week
	 */
	public CalendarDay[] getWeek(){
		return currentWeek;
	}
	
	/**
	 * Sets position (index) in array to the passed in CalendarDay (day)
	 * @param index - position in day (day in week) to set day to
	 * @param day - day to store in the week
	 */
	public void setDay(int index, CalendarDay day){
		currentWeek[index] = day;
	}
	
	/**
	 * Gets a CalendarDay from currentWeek array based on given index
	 * @param index - Position in CalendarDay array that is the desired day
	 * @return CalendarDay - CalendarDay object at given index in current week
	 */
	public CalendarDay getDay(int index)
	{
		return currentWeek[index];
	}
	
	/**
	 * Sets up week based on the given day.<p>
	 * Also takes in a year object to be able to reference months when weeks span multiple months
	 * @param day - CalendarDay object that the week will have to include
	 * @param year - year object that week references to get months
	 */
	public void setupWeek(CalendarDay day, CalendarYear year)
	{
		//tempDate stores the day's date, offset stores date to calculate how many days to include before it
		int tempDate = day.getDate();
		int offset = day.getDate();
		
		//dayCount is position in currentWeek to which the next day will be added
		int dayCount = 0;
		
		//tempMonth stores the CalendarMonth the day is in
		CalendarMonth tempMonth = year.getMonth(day.getMonth());
		String curDayOfWeek = day.getDayOfWeek();
		
		//Nullday is used when there is a day at beginning or end of week that is outside of the year scope (august and may)
		CalendarDay nullDay = new CalendarDay(0, "Null");
		nullDay.setDayOfWeek("");
		
		//checks if week is first week of august or last week of may and automatically loads them to format correctly.
		if(tempMonth.getMonth().equals("August") && day.getDate() <= 6)
		{
			setDay(0, nullDay);
			setDay(1, tempMonth.getDay(0));
			setDay(2, tempMonth.getDay(1));
			setDay(3, tempMonth.getDay(2));
			setDay(4, tempMonth.getDay(3));
			setDay(5, tempMonth.getDay(4));
			setDay(6, tempMonth.getDay(5));
			for(int i = 0; i < 6; i++)
			{
				tempMonth.getDay(i).loadDayEvents();
			}
		}
		else if(tempMonth.getMonth().equals("May") && day.getDate() >= 28)
		{
			setDay(0, tempMonth.getDay(27));
			setDay(1, tempMonth.getDay(28));
			setDay(2, tempMonth.getDay(29));
			setDay(3, tempMonth.getDay(30));
			setDay(4, nullDay);
			setDay(5, nullDay);
			setDay(6, nullDay);
			for(int i = 27; i < 31; i++)
			{
				tempMonth.getDay(i).loadDayEvents();
			}
		}
		else
		{
			//this calculates where to start the week (where the next closest sunday is)
			switch(curDayOfWeek)
			{
			case "Sunday":
				offset = offset - 0;
				break;
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
			
			//offset <= 0 means week spans the currentmonth and previousmonth, adjusts tempdate to start of week in previous month
			if(offset <= 0)
			{
				int temp = year.getMonthIndex(tempMonth.getMonth()) - 1;
				tempMonth = year.getMonth(year.monthNames[temp]);
				tempDate = tempMonth.getNumDays() - 1;
				tempDate = tempDate + offset;
			}
			else{tempDate = offset-1;}
			//loops through and adds CalendarDays to week, if date reaches end of month then 
			//sets it to first day of next month to continue loading week
			while(dayCount < 7)
			{
				setDay(dayCount, tempMonth.getDay(tempDate));
				tempMonth.getDay(tempDate).loadDayEvents();
				dayCount++;
				tempDate++;
				if(tempDate >= tempMonth.getNumDays()){
					int temp = year.getMonthIndex(tempMonth.getMonth()) + 1;
					tempMonth = year.getMonth(year.monthNames[temp]);
					tempDate = 0;
				}
			}
		}
	}
}
