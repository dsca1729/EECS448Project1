
public class CalendarWeek {
	
	private CalendarDay[] currentWeek = new CalendarDay[7];
	
	public CalendarDay[] getWeek(){
		return currentWeek;
	}
	
	public void setDay(int index, CalendarDay day){
		currentWeek[index] = day;
	}

}
