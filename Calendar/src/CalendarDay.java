//EECS 448 Project 1
//Stephen Fulton, Shawn Parkes, and Rebekah Manweiler
//Java CalendarDay class

import java.io.*;

public class CalendarDay
{
	private int date = 0;
	private String month;
	private String dayOfWeek;
	private String[] dayEvents;
	
	public CalendarDay(int d, String m)
	{
		date = d;
		month = m;
		dayEvents = new String[0];
	}
	
	public int getDate()
	{
		return date;
	}
	
	public String getMonth()
	{
		return month;
	}
	
	public String getDayOfWeek()
	{
		return dayOfWeek;
	}
	
	public String getEvents()
	{
		String temp = "";
		for (int i = 0; i < dayEvents.length; i++)
		{
			temp += i + ". " + dayEvents[i] + "\n";
		}
		return temp;
	}
	
	public void addEvent(String event)
	{
		try
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter("MonthFiles/" + this.getMonth() + ".txt", true));
			bw.newLine();
			bw.write(date + " " + event);
			bw.close();
			
		}catch(IOException e){}
	}
}