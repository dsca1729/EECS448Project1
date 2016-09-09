import java.io.*;

public class CalendarDriver {
	
	public static void main(String[] args)
	{
		CalendarDay x = new CalendarDay(5,"September");
		x.addEvent("this sucks a lot");
		System.out.println(x.getEvents());
	}

}
