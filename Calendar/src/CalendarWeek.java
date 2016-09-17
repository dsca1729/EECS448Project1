
public class CalendarWeek {
	
	private CalendarDay[] currentWeek = new CalendarDay[7];
	
	public CalendarDay[] getWeek(){
		return currentWeek;
	}
	
	public void setDay(int index, CalendarDay day){
		currentWeek[index] = day;
	}
	
	public CalendarDay getDay(int index)
	{
		return currentWeek[index];
	}
	
	public void setupWeek(CalendarDay day, CalendarYear year)
	{
		int tempDate = day.getDate() - 1;
		int offset = day.getDate();
		int dayCount = 0;
		CalendarMonth tempMonth = year.getMonth(day.getMonth());
		String curDayOfWeek = day.getDayOfWeek();
		CalendarDay nullDay = new CalendarDay(0, "Null");
		nullDay.setDayOfWeek("");
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
			if(offset <= 0)
			{
				int temp = year.getMonthIndex(tempMonth.getMonth()) - 1;
				tempMonth = year.getMonth(year.monthNames[temp]);
				tempDate = tempMonth.getNumDays() - 1;
				tempDate = tempDate + offset;
			}
			else{tempDate = offset-1;}
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
