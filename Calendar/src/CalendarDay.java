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
	private String monthFilePath;
	
	public CalendarDay(int d, String m)
	{
		date = d;
		month = m;
		dayEvents = new String[0];
		monthFilePath = "MonthFiles/" + m + ".txt";
	}

	public void setDayOfWeek(String dow)
	{
		dayOfWeek = dow;
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
			temp += (i+1) + ". " + dayEvents[i] + "\n";
		}
		return temp;
	}
	
	public void addEvent(String event)
	{
		try
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(monthFilePath, true));
			bw.newLine();
			bw.write(date + " " + event);
			bw.close();
			
		}catch(IOException e){}
		addEventToArray(event);
	}

	public void addEventToArray(String event)
	{
		String[] temp = new String[dayEvents.length + 1];
		for(int i = 0; i < dayEvents.length; i++)
		{
			temp[i] = dayEvents[i];
		}
		temp[dayEvents.length] = event;
		dayEvents = temp;	
	}
}
