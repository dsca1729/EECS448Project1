import java.time.*;
import java.time.format.*;
import java.time.temporal.*;

public class Event implements java.io.Serializable
{
	private String eventName;
	
	private int eventStartTime;
	private int eventEndTime;
	private int daySpan;

	//this integer will be used to sort events chronologically
	private int eventScaffoldId;

	private String eventStartDate;
	private String eventEndDate;
	
	private boolean eventOverlap;
	private boolean multidayStatus;

	//default constructor
	public Event()
	{
		eventName = "";
		eventStartTime = 0000;
		eventEndTime = 0000;
		eventStartDate = "";
		eventEndDate = "";
		eventOverlap = false;

		setMultidayStatus();

	}

//constructor to call in calendarDay class when generating events
//parameters will be user input fields.

/**
*	@pre: startTime and endTime parameters are 4-digit integers
*	      startDate and endDate are in form YYMMDD (Year/Month/Date)
*	@post: creates Event object and initializes member variables to 
*	       corresponding parameters
*	@returns: none
**/
	public Event(String name, int startTime, int endTime, String startDate, String endDate)
	{
		eventName = name;
		eventStartTime = startTime;
		eventEndTime = endTime;
		eventStartDate = startDate;
		eventEndDate = endDate;	
		eventOverlap = false;
		setMultidayStatus();
		generateDaySpan();
		generateEventScaffoldId();
	}
/**
*	@pre: Event object exists, String argument passed in
*	@post: eventName = name
*	@returns: none
**/
	public void setEventName(String name)
	{
		eventName = name;
	}

/**
*	@pre: Event object exists
*	@post: gets eventName
*	@returns: eventName
**/
	public String getEventName()
	{
		return eventName;
	}		

/**
*	@pre: Event object exists, 4 digit integer argument passed in
*	@post: eventStartTime = start
*	@returns: none
**/
	public void setEventStartTime(int start)
	{
		eventStartTime = start;
	}

/**
*	@pre: event object exists
*	@post: gets eventStartTime
*	@returns: eventStartTime
**/
	public int getEventStartTime()
	{
		return eventStartTime;
	}

/**
*	@pre: Event object exists, 4 digit integer argument passed in
*	@post: eventEndTime = end
*	@returns: none
**/
	public void setEventEndTime(int end)
	{
		eventEndTime = end;
	}

/**
*	@pre: Event object exists
*	@post: gets eventEndTime
*	@returns: eventEndTime
**/
	public int getEventEndTime()
	{
		return eventEndTime;
	}

/**
*	@pre: Event object exists, string argument passed in
*	@post: event
*	@returns: none
**/
	public void setStartDate(String startDate)
	{
		eventStartDate = startDate;
	}

/**
*	@pre: Event object exists
*	@post: gets eventStartDate
*	@returns: eventStartDate
**/
	public String getStartDate()
	{
		return eventStartDate;
	}

/**
*	@pre: Event object exists, string argument passed in
*	@post: eventEndDate = endDate
*	@returns: none
**/
	public void setEndDate(String endDate)
	{
		eventEndDate = endDate;
	}

/**
*	@pre: Event object exists
*	@post: checks eventEndDate
*	@returns: returns eventEndDate
**/
	public String getEndDate()
	{
		return eventEndDate;
	}

/**
*	@pre: Event object exists
*	@post: eventOverlap = true;
*	@returns: none
**/
	public void setOverlapTrue()
	{
		eventOverlap = true;
	}

/**
*	@pre: Event object exists
*	@post: eventOverlap = false;
*	@returns: none
**/
	public void setOverlapFalse()
	{
		eventOverlap = false;
	}

/**
*	@pre: Event object exists
*	@post: gets eventOverlap
*	@returns: eventOverlap
**/
	public boolean getOverlap()
	{
		return eventOverlap;
	}

/**
*	@pre: Event object exists, 10-integer scaffold value passed in
*	@post: eventScaffoldId = value
*	@returns: none
**/
	public void setEventScaffoldId(int value)
	{
		eventScaffoldId = value;
	}

/**
*	@pre: Event object exists
*	@post: gets eventScaffoldId
*	@returns: eventScaffoldId
**/
	public int getEventScaffoldId()
	{
		return eventScaffoldId;
	}

/**
*	@pre: Event object exists and has been initialized to non-default values
*	@post: generates eventScaffoldId by generating a 10-character integer
*	      of form  YYMMDDHHmm from Event member variables.
*	      eg an event starting on September 30 2016 at 11:35 would have
*	      an eventScaffoldId of 1609301135.
*	@returns: none
**/
	public void generateEventScaffoldId()
	{
		String tempScaffold = eventStartDate;
		String tempTime = Integer.toString(eventStartTime);

		tempScaffold = tempScaffold.concat(tempTime);

		eventScaffoldId = parseInt(tempScaffold);
	}
/**
*	@pre: Event object exists and has been initialized to non-default values
*	@post: determines if event spans multiple days using eventStartDate
*	       and eventEndDate variables
*	@returns: none
**/

	public void setMultidayStatus()
	{
		if(eventStartDate == eventEndDate)
		{
			multidayStatus = false; 
		}
		else
		{
			multidayStatus = true;
		}	
	}

/**
*	@pre: Event object exists
*	@post: checks multidayStatus value
*	@returns: multidayStatus
**/
	public boolean getMultidayStatus()
	{
		return multidayStatus;
	}

/**
*	@pre: Event object exists and has been initialized to non-default values
*	@post: parses eventStartDate and eventEndDate to integers
*	       
**/
	public void generateDaySpan()
	{
		int start = parseInt(eventStartDate);
		int end = parseInt(eventEndDate);
		int tempSpan = end - start;

		if (start == end)
		{
			daySpan = 1;
		}	
		else if(tempSpan <= 31)
		{
			daySpan = tempSpan;
		}
		else if (tempSpan >= 70 || tempSpan < 8000)
		{
			daySpan = tempSpan - 70 + 2;
		}
		else if (tempSpan > 8000)
		{
			daySpan = tempSpan - 8800 + 2;
		}
		else
		{
			daySpan = -1; //sentinel value denoting error
		}
	}
}
