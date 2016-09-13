import java.io.*;

public class CalendarDay
{
	private int date = 0;
	private String month;
	private String dayOfWeek;
	private String[] dayEvents;
	private String monthFilePath;
	
	/**
	 * <h3>Calendar Day Constructor</h3><p>
	 * Creates a Calendar Day object based on the given date and month that stores its day of the week and events for the day
	 * @param d - int: date of month (1,2...31)
	 * @param m - String: name of CalendarMonth that CalendarDay belongs to
	 * @see Calendarmonth
	 */
	public CalendarDay(int d, String m)
	{
		date = d;
		month = m;
		dayEvents = new String[0];
		monthFilePath = "MonthFiles/" + m + ".txt";
	}

	/**
	 * Sets the dayOfWeek variable in the CalendarDay object.
	 * <p>Returns nothing.
	 * @param dow - String: Day of week (Sunday...Saturday)
	 */
	public void setDayOfWeek(String dow)
	{
		dayOfWeek = dow;
	}
	
	/**
	 * Returns the date as an integer from the CalendarDay object.
	 * @return the date of the given CalendarDay
	 */
	public int getDate()
	{
		return date;
	}
	
	/**
	 * Returns the month as a string from the CalendarDay object.
	 * @return the month that the given CalendarDay belongs to.
	 */
	public String getMonth()
	{
		return month;
	}
	
	/**
	 * Returns the day of the week (Sunday...Saturday) from the CalendarDay object.
	 * @return day of week as string of the given CalendarDay
	 */
	public String getDayOfWeek()
	{
		return dayOfWeek;
	}
	
	/**
	 * Returns the events of the given day as a string to be displayed as a list.
	 * @return A string of all events concatenated together.
	 */
	public String getEvents()
	{
		String temp = "";
		for (int i = 0; i < dayEvents.length; i++)
		{
			temp += (i+1) + ". " + dayEvents[i] + "\n";
		}
		return temp;
	}
	
	/**
	 * Adds a new event to the CalendarDay's array of events and writes the event to specific CalendarDay's month file. Event  can be any String the user wants to put.
	 * @param event - Any String the user wants stored to the specific CalendarDay
	 */
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

	/**
	 * Adds the event to the CalendarDay's array to be used for display and record purposes.
	 * @param event - The given string from addEvent that is to be added to the day
	 * @see addEvent
	 */
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
	
	/**
	 * Removes event from the CalendarDay's events array and from its corresponding month file.
	 * <p>
	 * Function always ends if user requests to remove an event that is not within the list of events, either 0 or a value greater than the known number of events.
	 * @param event - Number from list of events user wants to remove.
	 */
	public void removeEvent(int event)
	{
		if(event <= 0 || event > dayEvents.length)
		{
			return;
		}else{event = event - 1;} //This statement is to catch if we forget to decrement the user's input to get correct index and decrements it itself.
		try{
			File monthPath = new File(monthFilePath);
			File tempFile = new File("MonthFiles/temp.txt");
			BufferedReader br = new BufferedReader(new FileReader(monthPath));
			BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));
			String temp = date + " " + dayEvents[event];
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
		removeEventFromArray(event);
	}
	/**
	 * Removes the event at the given index from the CalendarDay's event array.
	 * @param event - index of the event to be removed from array. Will always be valid.
	 */
	public void removeEventFromArray(int event)
	{
		String[] temp = new String[dayEvents.length - 1];
		int tempCount = 0;
		for (int i = 0; i < dayEvents.length; i++)
		{
			if(i == event){}
			else
			{
				temp[tempCount] = dayEvents[i];
				tempCount++;
			}
		}
		dayEvents = temp;
	}
	
	public void loadDayEvents()
	{
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(monthFilePath));
			String temp = br.readLine();
			while(temp != null)
			{
				String temp2 = temp.substring(0, 2);
				temp2.trim();
				if(Integer.parseInt(temp2) == date)
				{
					addEventToArray(temp);
				}
				temp = br.readLine();
			}
			br.close();
		}catch(IOException e)
		{System.out.println(e);}
	}
}
