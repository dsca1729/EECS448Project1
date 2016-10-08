/**
 * EECS 448 Project 1: Calendar
 * CalendarFrame.java
 * Authors: Rebekah Manweiler, Shawn Parkes, Stephen Fulton
 * Date: 2016-09-17
 */

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
/**
 * <h2>CalendarJFrame Class extends JFrame</h2>
 * <p>Contains an instance "calDrive" of the CalendarDriver Class
 * Contains an instance "curDay" of the CalendarDay class
 * Contains the instance "curWeek" of the CalendarWeek class
 * @see CalendarDriver
 * @see CalendarDay
 * @see CalendarWeek
 */
public class CalendarJFrame extends JFrame{
	
	//These are the tools for the JFrame to interact with the Calendar class structure, primarily through calDrive
	public static CalendarDriver calDrive = new CalendarDriver();
	private static CalendarDay curDay;
	private static String monthShowing;
	private static CalendarWeek curWeek;
	
	//These are globals, as they are updated across multiple panels and thus need to be accessible by them.
	private static JLabel curMonth;
	private static JTable monthTable;
	private static JTabbedPane tabs = new JTabbedPane();
	
	//These arrays store the displays of the week panel, with each instance in dayOfWeekPan holding (1) dayOfWeek label, (1) dateLabel, and (1) eventArea
	private static final JLabel[] dayOfWeek = new JLabel[7];
	private static final JLabel[] dateLabels = new JLabel[7];
	private static final JTextArea[] eventAreas = new JTextArea[7];
	private static final JPanel[] dayOfWeekPan = new JPanel[7];
	
	private static final JLabel weekTitle = new JLabel();
	private static final JComboBox monthList = new JComboBox();
	private static final JComboBox dayList = new JComboBox();
	private static final JButton dayButton = new JButton("Go to this day");
	
	public static void main(String[] args)
	{
		curDay = calDrive.getCurrentDate();
		curWeek = calDrive.getWeek();
		new CalendarJFrame();
	}
	
	/**
	 * <h3>Calendar JFrame Constructor</h3><p>
	 * Creates a Calendar JFrame object that has 4 tabs with different panels inside of them to display day, week, month, and year.
	 * @param - None
	 */
	public CalendarJFrame()
	{
		super("Calendar");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setResizable(false);
		
		
		JPanel dayPanel = new JPanel();
		setDayPanel(dayPanel);
		tabs.addTab("Day", dayPanel);
		
		JPanel weekPanel = new JPanel();
		setWeekPanel(weekPanel);
		tabs.addTab("Week", weekPanel);
		
		JPanel monthPanel = new JPanel();
		setMonthPanel(monthPanel);
		tabs.addTab("Month", monthPanel);
		
		JPanel yearPanel = new JPanel(false);
		setYearPanel(yearPanel);
		tabs.addTab("Year", yearPanel);
		
		JPanel normalPanel = new JPanel();
		setNormalPanel(normalPanel);
		tabs.addTab("Add Normal", normalPanel);
		
		JPanel multiPanel = new JPanel();
		setMultiPanel(multiPanel);
		tabs.addTab("Add Multi-Day", multiPanel);
		
		JPanel recurPanel = new JPanel();
		setRecurPanel(recurPanel);
		tabs.addTab("Add Recurring", recurPanel);
		
		
		
		
		add(tabs);
		setVisible(true);
	}
	
	public static void setRecurPanel(JPanel panel)
	{
		final JLabel curDate = new JLabel(calDrive.getCurrentDayOfWeek() + ", " + calDrive.getCurMonthName() + " " + calDrive.getCurDayOfMonth());
		curDate.setAlignmentX(Component.CENTER_ALIGNMENT); 
		curDate.setFont(curDate.getFont().deriveFont(24.0f)); 
		
		dayButton.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						curDay = setCurrentDate(monthList, dayList);
						curDate.setText(curDay.getDayOfWeek() + ", " + curDay.getMonth() + " " + curDay.getDate());
						if(curDay.getEvents().equals("")){
							curDay.loadDayEvents();
						}
						curWeek = calDrive.getWeek();
						updateWeekDisplay(curWeek);
						weekTitle.setText("  Week of " + curDay.getMonth() + " " + curWeek.getDay(0).getDate() + "  ");
						if(curWeek.getDay(0).getDate() == 0)
						{
							weekTitle.setText("  Week of August 1  ");
						}
						
						updateMonthDisplay(curDay.getMonth());
					}
				});
		
		
		
		//Creates the Panel that contains the three radio button options
		JPanel typeRadio = new JPanel();
		
		final JRadioButton weekly = new JRadioButton("Weekly");
		final JRadioButton biWeekly = new JRadioButton("Bi-Weekly");
		final JRadioButton monthly = new JRadioButton("Monthly");
		
		//Groups the radio buttons, allows for just one selection
		ButtonGroup type = new ButtonGroup();
		type.add(weekly);
		type.add(biWeekly);
		type.add(monthly);
		
		//Adds the radio buttons to the panel
		typeRadio.add(weekly);
		typeRadio.add(biWeekly);
		typeRadio.add(monthly);
		
		
		//Creates the checkbox panel
		JPanel daysPanel = new JPanel();
		
		final JCheckBox sunday = new JCheckBox("Sunday");
		final JCheckBox monday = new JCheckBox("Monday");
		final JCheckBox tuesday = new JCheckBox("Tuesday");
		final JCheckBox wednesday = new JCheckBox("Wednesday");
		final JCheckBox thursday = new JCheckBox("Thursday");
		final JCheckBox friday = new JCheckBox("Friday");
		final JCheckBox saturday = new JCheckBox("Saturday");
		
		//Adds the checkboxes to the panel
		daysPanel.add(sunday);
		daysPanel.add(monday);
		daysPanel.add(tuesday);
		daysPanel.add(wednesday);
		daysPanel.add(thursday);
		daysPanel.add(friday);
		daysPanel.add(saturday);
	
		
		//Check boxes disappear when monthly radio button is selected
		monthly.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						if(monthly.isSelected() == true){
							
							sunday.setVisible(false);
							monday.setVisible(false);
							tuesday.setVisible(false);
							wednesday.setVisible(false);
							thursday.setVisible(false);
							friday.setVisible(false);
							saturday.setVisible(false);
							
							sunday.setSelected(false);
							monday.setSelected(false);
							tuesday.setSelected(false);
							wednesday.setSelected(false);
							thursday.setSelected(false);
							friday.setSelected(false);
							saturday.setSelected(false);
							
						}
					}
				});
		
		//Check Boxes reappear when switching from monthly to Bi-weekly
		biWeekly.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						if(biWeekly.isSelected() == true){
							
							sunday.setVisible(true);
							monday.setVisible(true);
							tuesday.setVisible(true);
							wednesday.setVisible(true);
							thursday.setVisible(true);
							friday.setVisible(true);
							saturday.setVisible(true);
							
						}
					}
				});
		
		//Check Boxes reappear when switching from monthly to Bi-weekly
		weekly.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						if(weekly.isSelected() == true){
							
							sunday.setVisible(true);
							monday.setVisible(true);
							tuesday.setVisible(true);
							wednesday.setVisible(true);
							thursday.setVisible(true);
							friday.setVisible(true);
							saturday.setVisible(true);
							
						}
					}
				});
		
		
		
		//Creates the start and end time panel
		JPanel startEndPanel = new JPanel();
		startEndPanel.setBorder(BorderFactory.createEmptyBorder(10,1,1,1));
		String[] timeStrings = { "12:00 am", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00 pm", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00"};
		JComboBox startTime = new JComboBox(timeStrings);
		JLabel start = new JLabel("Start Time:");
	
		startEndPanel.add(startTime);
		
		
		startEndPanel.setBorder(BorderFactory.createEmptyBorder(10,1,1,1));
		String[] endStrings = { "1:00 am", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00 pm", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00"};
		JComboBox endTime = new JComboBox(endStrings);
		JLabel end = new JLabel("End Time:");
		
		startEndPanel.add(endTime);
		
		//Creates the add event button
		final JButton newEventButton = new JButton("Add Event");
		startEndPanel.add(newEventButton);
		//Creates the panel that contains the text box
		JPanel addRecurPanel = new JPanel();
		addRecurPanel.setBorder(BorderFactory.createEmptyBorder(20,1,1,1));

		JPanel newEventPanel = new JPanel();
		JLabel newEventTitle = new JLabel("New Event:         ");
		newEventTitle.setFont(newEventTitle.getFont().deriveFont(20.0f));
		
		//Creates the text box
		final JTextArea newEventText = new JTextArea(20, 30);
		newEventText.setLineWrap(true);
		newEventText.setWrapStyleWord(true);
		newEventText.setEditable(true);
		
		newEventText.setBorder(BorderFactory.createLineBorder(Color.black));
		//KeyStroke tracking based off of http://stackoverflow.com/questions/2162170/jtextarea-new-line-on-shift-enter
		InputMap input = newEventText.getInputMap();
		KeyStroke enter = KeyStroke.getKeyStroke("ENTER");
		input.put(enter, "text submit");
				
		//File I/O can't handle JTextArea enter characters, so this forces the submission of the event
		ActionMap actions = newEventText.getActionMap();
		actions.put("text submit", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				newEventButton.doClick();
			}
	   });
			    
				

		//Gives the newEventText a scroll bar
		JScrollPane newEventScroll = new JScrollPane(newEventText);
				
		//Adds everything to the add and remove event panel
				
				
		//Adds teh text box to the addRecurPanel
		addRecurPanel.add(newEventScroll);
		addRecurPanel.add(newEventPanel);
		addRecurPanel.add(newEventText);
		
		
		
		
		
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		//Adds everything together
		panel.add(curDate);
		panel.add(typeRadio);
		panel.add(daysPanel);
		panel.add(addRecurPanel);
		panel.add(startEndPanel);
		
		
		 
	}
	
	public static void setMultiPanel(JPanel panel)
	{
		final JLabel curDate = new JLabel(calDrive.getCurrentDayOfWeek() + ", " + calDrive.getCurMonthName() + " " + calDrive.getCurDayOfMonth());
		curDate.setAlignmentX(Component.CENTER_ALIGNMENT); 
		curDate.setFont(curDate.getFont().deriveFont(24.0f)); 
		
		
		
		
		JPanel currentDayPanel = new JPanel();
		currentDayPanel.setLayout(new FlowLayout());
		JLabel currentDayLabel = new JLabel("Set End Date:");
		
		
		for (int i = 0; i < calDrive.getMonthNames().length; i++)
		{
			monthList.addItem(calDrive.getMonthNames()[i]);
		}
		updateComboBox(calDrive.getCurrentMonth().getNumDays(), dayList);
		
		
		monthList.setSelectedIndex(calDrive.getCurrentMonthIndex(calDrive.getCurMonthName()));
		dayList.setSelectedIndex(calDrive.getCurDayOfMonth()-1);
		
		//Sets how the monstList comboBox will work
		//When a month is selected in this comboBox, the dayList will update to accommodate for the month selected
		monthList.addActionListener(
				new ActionListener(){
						public void actionPerformed(ActionEvent e){
								JComboBox bx = (JComboBox)e.getSource();
								String x = (String)bx.getSelectedItem();
								if(x.equals("September") || x.equals("November") || x.equals("April"))
								{
									updateComboBox(30, dayList);
								}
								else if(x.equals("February"))
								{
									updateComboBox(28, dayList);
								}
								else
								{
									updateComboBox(31, dayList);
								}
						}
				});
		
		final JButton newEventButton = new JButton("Add Event");
		
		JPanel addEventPanel = new JPanel();
		addEventPanel.setBorder(BorderFactory.createEmptyBorder(20,1,1,1));

		JPanel newEventPanel = new JPanel();
		
		//Creates text area
		final JTextArea newEventText = new JTextArea(20, 30);
		newEventText.setLineWrap(true);
		newEventText.setWrapStyleWord(true);
		newEventText.setEditable(true);
		
		newEventText.setBorder(BorderFactory.createLineBorder(Color.black));
		//KeyStroke tracking based off of http://stackoverflow.com/questions/2162170/jtextarea-new-line-on-shift-enter
		InputMap input = newEventText.getInputMap();
		KeyStroke enter = KeyStroke.getKeyStroke("ENTER");
		input.put(enter, "text submit");
		
		//File I/O can't handle JTextArea enter characters, so this forces the submission of the event
		ActionMap actions = newEventText.getActionMap();
		actions.put("text submit", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
		    newEventButton.doClick();
		    }
		});
			    
				

		//Gives the newEventText a scroll bar
		JScrollPane newEventScroll = new JScrollPane(newEventText);
			
				
		//Adds everything to the add and remove event panel
			
			
		addEventPanel.add(newEventScroll);
		addEventPanel.add(newEventPanel);
		
		//Adds everything to the current day panel
		currentDayPanel.add(currentDayLabel);
		currentDayPanel.add(monthList);
		currentDayPanel.add(dayList);
		currentDayPanel.add(newEventButton);
		
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(curDate);	
		panel.add(addEventPanel);	
		panel.add(currentDayPanel);
		
		
		
	}
	
	public static void setNormalPanel(JPanel panel)
	{
		//Adds the Date to the top of page
		final JLabel curDate = new JLabel(calDrive.getCurrentDayOfWeek() + ", " + calDrive.getCurMonthName() + " " + calDrive.getCurDayOfMonth());
		curDate.setAlignmentX(Component.CENTER_ALIGNMENT); 
		curDate.setFont(curDate.getFont().deriveFont(24.0f)); 
		
		dayButton.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						curDay = setCurrentDate(monthList, dayList);
						curDate.setText(curDay.getDayOfWeek() + ", " + curDay.getMonth() + " " + curDay.getDate());
						if(curDay.getEvents().equals("")){
							curDay.loadDayEvents();
						}
						curWeek = calDrive.getWeek();
						updateWeekDisplay(curWeek);
						weekTitle.setText("  Week of " + curDay.getMonth() + " " + curWeek.getDay(0).getDate() + "  ");
						if(curWeek.getDay(0).getDate() == 0)
						{
							weekTitle.setText("  Week of August 1  ");
						}
						
						updateMonthDisplay(curDay.getMonth());
					}
				});
		
		//Creates the panel that contains the start and end time Combo Boxes
		JPanel startEndPanel = new JPanel();
		startEndPanel.setBorder(BorderFactory.createEmptyBorder(10,1,1,1));
		String[] timeStrings = { "12:00 am", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00 pm", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00"};
		JComboBox startTime = new JComboBox(timeStrings);
		JLabel start = new JLabel("Start Time:");
	
		startEndPanel.add(startTime);
		
		
		startEndPanel.setBorder(BorderFactory.createEmptyBorder(10,1,1,1));
		String[] endStrings = { "1:00 am", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00 pm", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00"};
		JComboBox endTime = new JComboBox(endStrings);
		JLabel end = new JLabel("End Time:");
		
		startEndPanel.add(endTime);
		
		//Creates the button that adds the event
		final JButton newEventButton = new JButton("Add Event");
		startEndPanel.add(newEventButton);
		//Creates the panel that contains the text box
		JPanel addEventPanel = new JPanel();
		addEventPanel.setBorder(BorderFactory.createEmptyBorder(20,1,1,1));

		JPanel newEventPanel = new JPanel();
		
		//Creates text area
		final JTextArea newEventText = new JTextArea(20, 30);
		newEventText.setLineWrap(true);
		newEventText.setWrapStyleWord(true);
		newEventText.setEditable(true);
		
		newEventText.setBorder(BorderFactory.createLineBorder(Color.black));
		//KeyStroke tracking based off of http://stackoverflow.com/questions/2162170/jtextarea-new-line-on-shift-enter
		InputMap input = newEventText.getInputMap();
		KeyStroke enter = KeyStroke.getKeyStroke("ENTER");
		input.put(enter, "text submit");
		
		//File I/O can't handle JTextArea enter characters, so this forces the submission of the event
		ActionMap actions = newEventText.getActionMap();
		actions.put("text submit", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
		    newEventButton.doClick();
		    }
		});
			    
				

		//Gives the newEventText a scroll bar
		JScrollPane newEventScroll = new JScrollPane(newEventText);
			
				
		//Adds everything to the add and remove event panel
			
			
		addEventPanel.add(newEventScroll);
		addEventPanel.add(newEventPanel);
		
		
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
				
		panel.add(curDate);
		panel.add(addEventPanel);
		panel.add(startEndPanel);
		
		
		 
	}
	
	/**
	 * Sets up the layout and appearance of the day panel
	 * @param panel - the panel that will display all the day components
	 */
	public static void setDayPanel(JPanel panel)
	{
		final JLabel curDate = new JLabel(calDrive.getCurrentDayOfWeek() + ", " + calDrive.getCurMonthName() + " " + calDrive.getCurDayOfMonth());
		final JTextArea eventText = new JTextArea(18, 35); //Today's events text box. Width, Height
	//	final JTextArea eventTextTest = new JTextArea(5, 30);
		//Set up the display for setting the current day
		panel.setLayout(new BorderLayout());
		JPanel currentDayPanel = new JPanel();
		currentDayPanel.setLayout(new FlowLayout());
		JLabel curDayLab = new JLabel("Current Day");
		
		curDayLab.setHorizontalTextPosition(SwingConstants.LEADING);
		for (int i = 0; i < calDrive.getMonthNames().length; i++)
		{
			monthList.addItem(calDrive.getMonthNames()[i]);
		}
		updateComboBox(calDrive.getCurrentMonth().getNumDays(), dayList);
		currentDayPanel.add(curDayLab);
		
		monthList.setSelectedIndex(calDrive.getCurrentMonthIndex(calDrive.getCurMonthName()));
		dayList.setSelectedIndex(calDrive.getCurDayOfMonth()-1);
		
		//Sets how the monstList comboBox will work
		//When a month is selected in this comboBox, the dayList will update to accommodate for the month selected
		monthList.addActionListener(
				new ActionListener(){
						public void actionPerformed(ActionEvent e){
								JComboBox bx = (JComboBox)e.getSource();
								String x = (String)bx.getSelectedItem();
								if(x.equals("September") || x.equals("November") || x.equals("April"))
								{
									updateComboBox(30, dayList);
								}
								else if(x.equals("February"))
								{
									updateComboBox(28, dayList);
								}
								else
								{
									updateComboBox(31, dayList);
								}
						}
				});
		
		//Adds everything to the current day panel
		currentDayPanel.add(monthList);
		currentDayPanel.add(dayList);
		currentDayPanel.add(dayButton);
		
		JPanel addEvent = new JPanel();
		
		
		final JButton normal = new JButton("Add Normal Event");
		final JButton multi = new JButton("Add Multi-Day Event");
		final JButton recur = new JButton("Add Recurring Event");
		
		addEvent.add(normal);
		addEvent.add(multi);
		addEvent.add(recur);
		
		
		//Sets up the display of the event panel which has the current day along with the events of it
		JPanel eventPanel = new JPanel();
		eventPanel.add(currentDayPanel);
		eventPanel.setLayout(new BoxLayout(eventPanel, BoxLayout.Y_AXIS));
		eventPanel.setBorder(BorderFactory.createEmptyBorder(10,26,26,20)); //Code from: http://stackoverflow.com/questions/8863062/add-space-between-jframe-and-jpanel
		
		JLabel eventTitle = new JLabel("Today's Events:");
		eventTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		eventTitle.setFont(eventTitle.getFont().deriveFont(16.0f));
		curDate.setAlignmentX(Component.CENTER_ALIGNMENT); 
		curDate.setFont(curDate.getFont().deriveFont(24.0f)); 
		curDay.loadDayEvents();
		
		eventText.setText(calDrive.getCurrentDate().getEvents());
		eventText.setLineWrap(true);
		eventText.setWrapStyleWord(true);
		eventText.setEditable(false);
		
		//Gives the eventText a scroll bar
		JScrollPane textBox = new JScrollPane(eventText);
		JPanel removeEvent = new JPanel();
		JLabel remove = new JLabel("Remove Event:   ");
		remove.setFont(remove.getFont().deriveFont(20.0f));
		final JComboBox eventSelection = new JComboBox();
		updateComboBox(curDay.getEventCount(), eventSelection);
		JButton removeEventButton = new JButton("Remove");
		removeEvent.add(remove);
		removeEvent.add(eventSelection);
		removeEvent.add(removeEventButton);
		
		//Adds everything to the event panel
		eventPanel.add(curDate);
		eventPanel.add(eventTitle);
		eventPanel.add(textBox);
		//eventPanel.add(eventTextTest);
		eventPanel.add(removeEvent);
		
		//Sets what dayButton does
		//When pressed, dayButton will update current date based on the input from the combo boxes
		//It will also update the display of the week and month panels based on the new date
		dayButton.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						curDay = setCurrentDate(monthList, dayList);
						curDate.setText(curDay.getDayOfWeek() + ", " + curDay.getMonth() + " " + curDay.getDate());
						if(curDay.getEvents().equals("")){
							curDay.loadDayEvents();
						}
						curWeek = calDrive.getWeek();
						updateWeekDisplay(curWeek);
						weekTitle.setText("  Week of " + curDay.getMonth() + " " + curWeek.getDay(0).getDate() + "  ");
						if(curWeek.getDay(0).getDate() == 0)
						{
							weekTitle.setText("  Week of August 1  ");
						}
						eventText.setText(curDay.getEvents());
						updateComboBox(curDay.getEventCount(), eventSelection);
						updateMonthDisplay(curDay.getMonth());
					}
				});
		
		//Sets what newEventButton does
		//When pressed, newEventButton will add in the new event from the text box to the event panel
		
		
		normal.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						tabs.setSelectedIndex(4);
					}
				});
		
		recur.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						tabs.setSelectedIndex(6);
					}
				});
		
		multi.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						tabs.setSelectedIndex(5);
					}
				});
		
		//Sets what removeEventButton does
		//When pressed, removeEventButton removes whatever the selected event is from the list of events for the current day
		removeEventButton.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						try{
							curDay.removeEvent((int)eventSelection.getSelectedItem());
							eventText.setText(curDay.getEvents());
							updateComboBox(curDay.getEventCount(), eventSelection);
							updateWeekDisplay(curWeek);
						}catch(Exception err){}
					}
				});
		
		//Adds everything to the day panel
		panel.add(eventPanel, BorderLayout.NORTH);
		panel.add(addEvent);
	}
	
	/**
	 * Sets up the layout and appearance of the month panel
	 * <p>Loads previous and next buttons, the month label, and the month table into the panel
	 * creates listeners for the previous and next buttons
	 * @param panel - the panel that will display all the day components
	 */
	public static void setMonthPanel(JPanel panel)
	{
		//setting static variables, buttons, and lables
		monthShowing = curDay.getMonth();
		curMonth = new JLabel("  " + monthShowing + "  ");
		JButton prevMonth = new JButton("Previous Month");
		JButton nextMonth = new JButton("Next Month");
		curMonth.setFont(curMonth.getFont().deriveFont(24.0f));
		prevMonth.setAlignmentX(Component.RIGHT_ALIGNMENT);
		curMonth.setAlignmentX(Component.CENTER_ALIGNMENT);
		nextMonth.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		//setting up the panels for the month view
		panel.setLayout(new GridLayout());
		JPanel monthLabelPanel = new JPanel();
		monthLabelPanel.setLayout(new BoxLayout(monthLabelPanel, BoxLayout.X_AXIS));
		monthLabelPanel.add(prevMonth);
		monthLabelPanel.add(curMonth);
		monthLabelPanel.add(nextMonth);
		JPanel currentMonthPanel = new JPanel();
		currentMonthPanel.setLayout(new BoxLayout(currentMonthPanel, BoxLayout.Y_AXIS));
		currentMonthPanel.setBorder(BorderFactory.createEmptyBorder(0,20,0,20));
		curMonth.setHorizontalTextPosition(SwingConstants.LEADING);
		
		//set up the month table
		String[] weekNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		String[][] data = new String[6][7];
		int numdays = (calDrive.getYear().getMonth(monthShowing)).getNumDays();
		int curnum = 1;
		int blankDays = 0;
		switch (calDrive.getFirstDayOfMonth(monthShowing)){
		case "Monday": blankDays = 1;
			break;
		case "Tuesday": blankDays = 2;
			break;
		case "Wednesday": blankDays = 3;
			break;
		case "Thursday": blankDays = 4;
			break;
		case "Friday": blankDays = 5;
			break;
		case "Saturday": blankDays = 6;
			break;
		default: blankDays = 0;
			break;
		}
		//create the table and add days in the correct columns
		for(int r = 0; r < 6; r++){
			for(int c = 0; c < 7; c++){
				if(r == 0 && blankDays >0){
					data[r][c] = "";
					blankDays--;
				}
				else if(curnum > numdays){
					data[r][c] = "";
				}
				else{
					data[r][c] = String.valueOf(curnum);
					curnum++;
				}
			}
		}
		
		//In grid text alignment code from http://stackoverflow.com/questions/2408541/align-the-values-of-the-cells-in-jtable
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.LEFT);
		rightRenderer.setVerticalAlignment(SwingConstants.TOP);
		TableModel model = new DefaultTableModel(data, weekNames)
		{
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};
		
		//sizes rows 
		monthTable = new JTable(model);
		for(int i = 0; i < 6; i++)
		{
			monthTable.setRowHeight(i, 70);
		}
		//sets day number to the top lefthand corner of the table entry
		for(int i = 0; i < 7; i++)
		{
			monthTable.getColumnModel().getColumn(i).setHeaderValue(weekNames[i]);
			monthTable.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
		}
		//add to the month panel
		currentMonthPanel.add(monthTable);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(monthLabelPanel, BorderLayout.NORTH);
		panel.add(currentMonthPanel, BorderLayout.SOUTH);
		
		//set up listeners for the buttons to update the month view
		prevMonth.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						String newMonth = calDrive.getPrevMonth(monthShowing);
						if(newMonth != "") updateMonthDisplay(newMonth);
					}
				});
		nextMonth.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						String newMonth = calDrive.getNextMonth(monthShowing);
						if(newMonth != "") updateMonthDisplay(newMonth);
					}
				});
		
		//set up listener for mouse click inside the month table
		monthTable.addMouseListener(new MouseAdapter() {
			  public void mousePressed(MouseEvent e) {
			      JTable target = (JTable)e.getSource();
			      int row = target.getSelectedRow();
			      int column = target.getSelectedColumn();
			      String targetDay = (String) target.getValueAt(row, column);
			      if(targetDay.equals("") == false)
			      {
			    	  int tempDay = Integer.parseInt(targetDay);
			    	  curWeek = calDrive.setupWeek(calDrive.getMonth(monthShowing).getDay(tempDay-1));
				      updateWeekDisplay(curWeek);
				      weekTitle.setText("  Week of " + curWeek.getDay(0).getMonth() + " " + curWeek.getDay(0).getDate() + "  ");
						if(curWeek.getDay(0).getDate() == 0)
						{
							weekTitle.setText("  Week of August 1  ");
						}
					  tabs.setSelectedIndex(1);
			      }
			  }
			});
		
		JLabel infoLab = new JLabel("*You can click on a day to go to the week that contains that day*");
		infoLab.setAlignmentX(Component.CENTER_ALIGNMENT);
		JScrollPane scrollTable = new JScrollPane(monthTable);
		//in order to add the week days header, the table had to be a scroll table
		scrollTable.setSize(monthTable.getSize());
		scrollTable.setBorder(BorderFactory.createEmptyBorder());
		
		//finalizing the month panel
		currentMonthPanel.add(infoLab);
		currentMonthPanel.add(scrollTable);
		panel.add(currentMonthPanel, BorderLayout.CENTER);
	}
	
	/**
	 * Sets up the layout and appearance of the week panel. <p>Loads previous and next buttons and date label into panel and events into panel, and places them into the overall week display panel.
	 * @param panel - The panel that will display all the week components.
	 */
	public static void setWeekPanel(JPanel panel)
	{
		//sets layout for overall week panel to be able to place sub-panels easily
		panel.setLayout(new FlowLayout());
		
		//weekHeader panel contains the previous and next buttons as well as the string stating what week the user is in
		JPanel weekHeader = new JPanel(new BorderLayout());
		weekHeader.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
		
		//weekPanel contains the title for each day in the week and the text areas for each of those day's events.
		JPanel weekPanel = new JPanel(new FlowLayout());
		
		//sets what prevweek button does
		//prev week calls calDrive to get the week prior to the one the user is on, then calls updateWeekDisplay to change box and label values according to new week.
		JButton prevWeek = new JButton("Previous Week");
		prevWeek.setHorizontalAlignment(SwingConstants.CENTER);
		prevWeek.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e)
					{
						curWeek = calDrive.getPreviousWeek(curWeek);
						updateWeekDisplay(curWeek);
						weekTitle.setText("  Week of " + curWeek.getDay(0).getMonth() + " " + curWeek.getDay(0).getDate() + "  ");
						
						//if the week being displayed is august 1st, then this overrides the setText for the week to accommodate not having a base Sunday to title from.
						if(curWeek.getDay(0).getDate() == 0)
						{
							weekTitle.setText("  Week of August 1  ");
						}
					}
				});
		
		//sets what nextweek button does
		//nextweek calls calDrive to get the week after the one the user is on, then calls updateWeekDisplay to change box and label values according to new week.
		JButton nextWeek = new JButton("Next Week");
		nextWeek.setHorizontalAlignment(SwingConstants.CENTER);
		nextWeek.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e)
					{
						curWeek = calDrive.getNextWeek(curWeek);
						updateWeekDisplay(curWeek);
						weekTitle.setText("  Week of " + curWeek.getDay(0).getMonth() + " " + curWeek.getDay(0).getDate() + "  ");
					}
				});
		
		//Initializes weekTitle based on whatever the current day the user open the program on is
		weekTitle.setText("  Week of " + curDay.getMonth() + " " + curWeek.getDay(0).getDate() + "  ");
		if(curWeek.getDay(0).getDate() == 0)
		{
			weekTitle.setText("  Week of August 1  ");
		}
		weekTitle.setFont(weekTitle.getFont().deriveFont(24.0f));
		weekTitle.setHorizontalAlignment(SwingConstants.CENTER);
		
		//add all components to the weekheader panel
		weekHeader.add(prevWeek, BorderLayout.WEST);
		weekHeader.add(weekTitle, BorderLayout.CENTER);
		weekHeader.add(nextWeek, BorderLayout.EAST);
		
		panel.add(weekHeader, BorderLayout.NORTH);
		
		//Adds components to weekPanel
		//This loop goes through for all 7 days in a week and using the current stored week adds titles and textAreas to individual panels to display each day and it's events, 
		//then adds those panels to one large display for that section.
		for(int i = 0; i < 7; i++)
		{
			dayOfWeekPan[i] = new JPanel(new BorderLayout());
			curWeek.getDay(i).loadDayEvents();
			
			//A day's month will be null if at beginning of august or end of may
			if(curWeek.getDay(i).getMonth().equals("Null"))
			{
				dayOfWeek[i] = new JLabel();
				dateLabels[i] = new JLabel();
				eventAreas[i] = new JTextArea(20, 9);
			}
			else
			{
				//Initializes dayofweek label, date label, and event area based on the given day's stored values
				dayOfWeek[i] = new JLabel(curWeek.getDay(i).getDayOfWeek());
				dateLabels[i] = new JLabel(curWeek.getDay(i).getMonth() + " " + curWeek.getDay(i).getDate());
				eventAreas[i] = new JTextArea(20, 9);
				eventAreas[i].setText(curWeek.getDay(i).getEvents());
			}
			
			//this formats the event area to display events properly
			eventAreas[i].setLineWrap(true);
			eventAreas[i].setWrapStyleWord(true);
			eventAreas[i].setEditable(false);
			eventAreas[i].setName(curWeek.getDay(i).getMonth() + " " + curWeek.getDay(i).getDate());
			
			//if user clicks event text area under label, go to the corresponding day in day view
			eventAreas[i].addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent e)
				{
					JTextArea target = (JTextArea)e.getSource();
					String month = target.getName();
					if(month.equals("")){}
					else
					{
						weekToDayView(month);
					}
				}
			});
			dateLabels[i].setName(curWeek.getDay(i).getMonth() + " " + curWeek.getDay(i).getDate());
			dateLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
			
			//if user clicks day label in week view, go to the corresponding day in day view
			dateLabels[i].addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent e){
						JLabel target = (JLabel)e.getSource();
						String month = target.getName();
						if(month.equals("")){}
						else
						{
							weekToDayView(month);
						}
				}
			});
			dayOfWeek[i].setHorizontalAlignment(SwingConstants.CENTER);
			
			//adds components to each of 7 day of week panels
			dayOfWeekPan[i].add(dayOfWeek[i], BorderLayout.NORTH);
			dayOfWeekPan[i].add(dateLabels[i], BorderLayout.CENTER);
			dayOfWeekPan[i].add(new JScrollPane(eventAreas[i]), BorderLayout.SOUTH);
			
			//if a panel is for a null day (<august 1 or >may 31), hide the panel
			if(curWeek.getDay(i).getMonth().equals("Null"))
			{
				dayOfWeekPan[i].setVisible(false);
			}
			weekPanel.add(dayOfWeekPan[i]);
		}
		
		JLabel infoLab = new JLabel("       *You can click an event box or date label to go to that day*       ");
		infoLab.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(weekPanel, BorderLayout.CENTER);
		panel.add(infoLab, BorderLayout.SOUTH);
	}
	
	/**
	 * Updates the day view based on the selected day in the week view
	 * @param input - Month and day string (i.e. September 13)
	 */
	public static void weekToDayView(String input)
	{
		//input is in format "month day" so it is parsed and broken up into the month and day individually
		int day = Integer.parseInt(input.substring(input.indexOf(" ")+1));
		input = input.substring(0, input.indexOf(" "));
		
		//this sets the combo boxes and then clicks the go to day button in the day panel to update all panels to that day
		monthList.setSelectedIndex(calDrive.getCurrentMonthIndex(input));
		dayList.setSelectedIndex(day-1);
		dayButton.doClick();
		tabs.setSelectedIndex(0);
	}
	
	/**
	 * Updates the month panel display based on the passed in month
	 * @param month - the month name the panel will pull data from to update display
	 */
	public static void updateMonthDisplay(String newMonth)
	{
		monthShowing = newMonth;
		String[] weekNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		String[][] data = new String[6][7];
		int numdays = (calDrive.getMonth(newMonth).getNumDays());
		int curnum = 1;
		int blankDays = 0;
		switch (calDrive.getFirstDayOfMonth(newMonth)){
		case "Monday": blankDays = 1;
			break;
		case "Tuesday": blankDays = 2;
			break;
		case "Wednesday": blankDays = 3;
			break;
		case "Thursday": blankDays = 4;
			break;
		case "Friday": blankDays = 5;
			break;
		case "Saturday": blankDays = 6;
			break;
		default: blankDays = 0;
			break;
		}
		for(int r = 0; r < 6; r++){
			for(int c = 0; c < 7; c++){
				if(r == 0 && blankDays >0){
					monthTable.setValueAt("", r, c);
					data[r][c] = "";
					blankDays--;
				}
				else if(curnum > numdays){
					monthTable.setValueAt("", r, c);
					data[r][c] = "";
				}
				else{
					monthTable.setValueAt(String.valueOf(curnum), r, c);
					data[r][c] = String.valueOf(curnum);
					curnum++;
				}
			}
		}
		curMonth.setText("  " + newMonth + "  ");
		
	}
	
	/**
	 * Updates the week Panel's display based on the passed in week
	 * @param week - the week the panel will pull data from to update display
	 */
	public static void updateWeekDisplay(CalendarWeek week)
	{
		//Loops through each day of the week, updating info on corresponding components
		for(int i = 0; i < 7; i++)
		{
			//Clears out eventArea, then reloads with the day's events
			eventAreas[i].setText("");
			
			//if the month is null, then hide the display (contains no relevant info)
			if(week.getDay(i).getMonth().equals("Null"))
			{
				dateLabels[i].setText(" ");
				dateLabels[i].setName("");
				eventAreas[i].setText("");
				eventAreas[i].setName("");
				dayOfWeekPan[i].setVisible(false);
			}
			else
			{
				//sets all non-null day panels to visible and updates their info
				dayOfWeekPan[i].setVisible(true);
				dayOfWeek[i].setText(week.getDay(i).getDayOfWeek());
				dateLabels[i].setText(week.getDay(i).getMonth() + " " + week.getDay(i).getDate());
				dateLabels[i].setName(week.getDay(i).getMonth() + " " + week.getDay(i).getDate());
				eventAreas[i].setText(week.getDay(i).getEvents());
				eventAreas[i].setName(week.getDay(i).getMonth() + " " + week.getDay(i).getDate());
			}
		}
	}

	/**
	 * Updates a combo box with numbers up to the specified Integer
	 * @param i - the Integer that the combo box will go up to
	 * @param bx - the combo box that will be updated
	 */
	public static void updateComboBox(Integer i, JComboBox<Integer> bx)
	{
		bx.removeAllItems();
		for(Integer j = 1; j <= i; j++)
		{
			bx.addItem(j);
		}
	}
	
	/**
	 * Sets the current Date that the say class will display
	 * @param monthBX - the combo box that has the month selected to be set
	 * @param dayBX - the combo box that has the day selected to be set
	 */
	public static CalendarDay setCurrentDate(JComboBox<Integer> monthBX, JComboBox<Integer> dayBX)
	{
		String month = (String)monthBX.getSelectedItem();
		int day = (Integer)dayBX.getSelectedItem();
		return calDrive.setCurrentDate(month, day);
	}

	
	/**
	 * Sets up the layout and appearance of the year panel
	 * @param panel - the panel that will display all the day components
	 */
	public static void setYearPanel(JPanel panel)
	{
		//Sets up the display of the Year Panel
		panel.setLayout(new BorderLayout());
		JPanel sixteenPanel = new JPanel();
		sixteenPanel.setLayout(new FlowLayout());
		sixteenPanel.setPreferredSize(new Dimension(400,600));
		sixteenPanel.setBorder(BorderFactory.createEmptyBorder(10,60,10,10));
		JPanel seventeenPanel = new JPanel();
		seventeenPanel.setLayout(new FlowLayout());
		seventeenPanel.setPreferredSize(new Dimension(400,600));
		seventeenPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,60));
		
		//Sets up the label and buttons for the 2016 panel
		JLabel firstYear = new JLabel("          2016          ");
		firstYear.setFont(firstYear.getFont().deriveFont(24.0f));
		firstYear.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton august = new JButton("August");
		august.setPreferredSize(new Dimension(150, 80));
		august.setFont(august.getFont().deriveFont(22.0f));
		august.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton september = new JButton("September");
		september.setPreferredSize(new Dimension(150, 80));
		september.setFont(august.getFont().deriveFont(22.0f));
		september.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton october = new JButton("October");
		october.setPreferredSize(new Dimension(150, 80));
		october.setFont(august.getFont().deriveFont(22.0f));
		october.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton november = new JButton("November");
		november.setPreferredSize(new Dimension(150, 80));
		november.setFont(august.getFont().deriveFont(22.0f));
		november.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton december = new JButton("December");
		december.setPreferredSize(new Dimension(150, 80));
		december.setFont(august.getFont().deriveFont(22.0f));
		december.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		//Adds everything to the 2016 panel
		sixteenPanel.add(firstYear);
		sixteenPanel.add(august);
		sixteenPanel.add(september);
		sixteenPanel.add(october);
		sixteenPanel.add(november);
		sixteenPanel.add(december);
		
		//Sets up the label and buttons for the 2017 panel
		JLabel secondYear = new JLabel("          2017          ");
		secondYear.setFont(firstYear.getFont().deriveFont(24.0f));
		JButton january = new JButton("January");
		january.setPreferredSize(new Dimension(150, 80));
		january.setFont(august.getFont().deriveFont(22.0f));
		january.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton february = new JButton("February");
		february.setPreferredSize(new Dimension(150, 80));
		february.setFont(august.getFont().deriveFont(22.0f));
		february.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton march = new JButton("March");
		march.setPreferredSize(new Dimension(150, 80));
		march.setFont(august.getFont().deriveFont(22.0f));
		march.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton april = new JButton("April");
		april.setPreferredSize(new Dimension(150, 80));
		april.setFont(august.getFont().deriveFont(22.0f));
		april.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton may = new JButton("May");
		may.setPreferredSize(new Dimension(150, 80));
		may.setFont(august.getFont().deriveFont(22.0f));
		may.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		//Adds everything to the 2017 panel
		seventeenPanel.add(secondYear);
		seventeenPanel.add(january);
		seventeenPanel.add(february);
		seventeenPanel.add(march);
		seventeenPanel.add(april);
		seventeenPanel.add(may);
		
		//Sets up what all the month buttons do
		//When a month button is pressed, it will update the month panel to that month
		//The user will then by taken to the month panel
		august.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						updateMonthDisplay("August");
						tabs.setSelectedIndex(2);
					}
				});
		
		september.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						updateMonthDisplay("September");
						tabs.setSelectedIndex(2);
					}
				});
		
		october.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						updateMonthDisplay("October");
						tabs.setSelectedIndex(2);
					}
				});
		
		november.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						updateMonthDisplay("November");
						tabs.setSelectedIndex(2);
					}
				});
		
		december.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						updateMonthDisplay("December");
						tabs.setSelectedIndex(2);
					}
				});
		
		january.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						updateMonthDisplay("January");
						tabs.setSelectedIndex(2);
					}
				});
		
		february.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						updateMonthDisplay("February");
						tabs.setSelectedIndex(2);
					}
				});
		
		march.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						updateMonthDisplay("March");
						tabs.setSelectedIndex(2);
					}
				});
		
		april.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						updateMonthDisplay("April");
						tabs.setSelectedIndex(2);
					}
				});
		
		may.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						updateMonthDisplay("May");
						tabs.setSelectedIndex(2);
					}
				});
		
		//Adds the 2 different year panels to the whole year panel
		panel.add(sixteenPanel, BorderLayout.WEST);
		panel.add(seventeenPanel, BorderLayout.EAST);
	}
}
