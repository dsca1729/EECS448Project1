
public class CalendarDriver {
	
	public static void main(String[] args)
	{
		CalendarDay x = new CalendarDay(5,"September");
		System.out.println(x.getEvents());
		x.addEvent("this sucks a lot");
		System.out.println(x.getEvents());
	}

}
