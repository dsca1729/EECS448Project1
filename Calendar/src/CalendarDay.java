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
	
	public void removeEvent(int event)
	{
		if(event == 0 || event > dayEvents.length)
		{
			return;
		}
		try{
			File monthPath = new File(monthFilePath);
			File tempFile = new File("MonthFiles/temp.txt");
			BufferedReader br = new BufferedReader(new FileReader(monthPath));
			BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));
			String temp = date + " " + dayEvents[event-1];
			String x = br.readLine();
			while(x != null)
			{
				if(temp.equals(x) || x.equals("")){}
				else
				{
					bw.write(x);
					bw.newLine();
				}
				x = br.readLine();
			}
			br.close();
			bw.close();
			monthPath.delete();
			tempFile.renameTo(new File(monthFilePath));
		}catch(IOException e){
			System.out.println(e);
		}
	}
}
