import java.io.*;

public class CalendarDriver {
	
	public static void main(String[] args)
	{
		CalendarDay x = new CalendarDay(5,"September");
		x.addEvent("this sucks a lot");
		x.addEvent("this, like, really sucks");
		System.out.println(x.getEvents());
		x.removeEvent(1);
		System.out.println(x.getEvents());
		//CalendarJFrame y = new CalendarJFrame();
	}

}
