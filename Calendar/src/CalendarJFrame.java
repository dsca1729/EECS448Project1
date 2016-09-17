import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import java.io.*;

public class CalendarJFrame extends JFrame{
	
	public static CalendarDriver calDrive = new CalendarDriver();
	private static CalendarDay curDay;
	private static CalendarWeek curWeek;
	private static JLabel curMonth;
	private static JTable monthTable;
	private static JTabbedPane tabs = new JTabbedPane();
	private static final JLabel[] dayOfWeek = new JLabel[7];
	private static final JLabel[] dateLabels = new JLabel[7];
	private static final JTextArea[] eventAreas = new JTextArea[7];
	private static final JPanel[] dayOfWeekPan = new JPanel[7];
	
	public static void main(String[] args)
	{
		curDay = calDrive.getCurrentDate();
		curWeek = calDrive.getWeek();
		new CalendarJFrame();
	}
	
	/**
	 * <h3>Calendar JFrame Constructor</h3><p>
	 * Creates a Calendar JFrame object that has 4 tabs with different panels inside of them
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
		
		add(tabs);
		setVisible(true);
	}
	
	/**
	 * Sets up the layout and appearance of the day panel
	 * @param panel - the panel that will display all the day components
	 */
	public static void setDayPanel(JPanel panel)
	{
		final JLabel curDate = new JLabel(curDay.getDayOfWeek() + ", " + curDay.getMonth() + " " + curDay.getDate());
		final JTextArea eventText = new JTextArea(5, 35);
		
		panel.setLayout(new BorderLayout());
		JPanel currentDayPanel = new JPanel();
		currentDayPanel.setLayout(new FlowLayout());
		JLabel curDayLab = new JLabel("Current Day");
		
		curDayLab.setHorizontalTextPosition(SwingConstants.LEADING);
		final JComboBox monthList = new JComboBox(calDrive.getMonthNames());
		final JComboBox dayList = new JComboBox();
		updateComboBox(calDrive.getCurrentMonth().getNumDays(), dayList);
		final JButton dayButton = new JButton("Go to this Day");
		currentDayPanel.add(curDayLab);
		
		monthList.setSelectedIndex(calDrive.getCurrentMonthIndex(curDay.getMonth()));
		dayList.setSelectedIndex(curDay.getDate()-1);
		
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
		
		
		currentDayPanel.add(monthList);
		currentDayPanel.add(dayList);
		currentDayPanel.add(dayButton);
		
		JPanel eventPanel = new JPanel();
		eventPanel.setLayout(new BoxLayout(eventPanel, BoxLayout.Y_AXIS));
		eventPanel.setBorder(BorderFactory.createEmptyBorder(10,26,26,0)); //Code from: http://stackoverflow.com/questions/8863062/add-space-between-jframe-and-jpanel
		
		
		JLabel eventTitle = new JLabel("Today's Events:");
		eventTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		eventTitle.setFont(eventTitle.getFont().deriveFont(16.0f));
		curDate.setAlignmentX(Component.CENTER_ALIGNMENT);
		curDate.setFont(curDate.getFont().deriveFont(24.0f));  //Code from: http://stackoverflow.com/questions/17884843/change-jlabel-font-size
		curDay.loadDayEvents();
		
		eventText.setText(curDay.getEvents());
		eventText.setLineWrap(true);
		eventText.setWrapStyleWord(true);
		eventText.setEditable(false);
		
		JScrollPane textBox = new JScrollPane(eventText);
		
		eventPanel.add(curDate);
		eventPanel.add(eventTitle);
		eventPanel.add(textBox);
		
		JPanel addEventPanel = new JPanel();
		addEventPanel.setLayout(new BoxLayout(addEventPanel, BoxLayout.Y_AXIS));
		addEventPanel.setBorder(BorderFactory.createEmptyBorder(60,0,10,26));
		
		JPanel newEventPanel = new JPanel();
		JLabel newEventTitle = new JLabel("New Event:         ");
		newEventTitle.setFont(newEventTitle.getFont().deriveFont(20.0f));
		final JButton newEventButton = new JButton("Add Event");
		newEventPanel.add(newEventTitle);
		newEventPanel.add(newEventButton);
		
		final JTextArea newEventText = new JTextArea(20, 30);
		newEventText.setLineWrap(true);
		newEventText.setWrapStyleWord(true);
		newEventText.setEditable(true);
		
		//KeyStroke tracking based off of http://stackoverflow.com/questions/2162170/jtextarea-new-line-on-shift-enter
		InputMap input = newEventText.getInputMap();
		KeyStroke enter = KeyStroke.getKeyStroke("ENTER");
		input.put(enter, "text submit");
		
		ActionMap actions = newEventText.getActionMap();
	    actions.put("text submit", new AbstractAction() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            newEventButton.doClick();
	        }
	    });
		
		
		JPanel removeEvent = new JPanel();
		JLabel remove = new JLabel("Remove Event:   ");
		remove.setFont(remove.getFont().deriveFont(20.0f));
		final JComboBox eventSelection = new JComboBox();
		updateComboBox(curDay.getEventCount(), eventSelection);
		JButton removeEventButton = new JButton("Remove");
		removeEvent.add(remove);
		removeEvent.add(eventSelection);
		removeEvent.add(removeEventButton);
		JScrollPane newEventScroll = new JScrollPane(newEventText);
		
		addEventPanel.add(newEventPanel);
		addEventPanel.add(newEventScroll);
		addEventPanel.add(removeEvent);
		
		dayButton.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						curDay = setCurrentDate(monthList, dayList);
						curDate.setText(curDay.getDayOfWeek() + ", " + curDay.getMonth() + " " + curDay.getDate());
						if(curDay.getEvents().equals("")){
							curDay.loadDayEvents();
						}
						eventText.setText(curDay.getEvents());
						updateComboBox(curDay.getEventCount(), eventSelection);
						updateMonthDisplay(curDay.getMonth());
					}
				});
		
		newEventButton.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						curDay.addEvent(newEventText.getText());
						eventText.setText(curDay.getEvents());
						newEventText.setText("");
						updateComboBox(curDay.getEventCount(), eventSelection);
					}
				});
		
		removeEventButton.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						try{
							curDay.removeEvent((int)eventSelection.getSelectedItem());
							eventText.setText(curDay.getEvents());
							updateComboBox(curDay.getEventCount(), eventSelection);	
						}catch(Exception err){}
					}
				});
		
		panel.add(currentDayPanel, BorderLayout.NORTH);
		panel.add(eventPanel, BorderLayout.WEST);
		panel.add(addEventPanel, BorderLayout.EAST);
	}
	
	public static void setMonthPanel(JPanel panel)
	{
		curMonth = new JLabel(curDay.getMonth());
		curMonth.setFont(curMonth.getFont().deriveFont(24.0f));
		curMonth.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.setLayout(new GridLayout());
		JPanel currentMonthPanel = new JPanel();
		currentMonthPanel.setLayout(new BoxLayout(currentMonthPanel, BoxLayout.Y_AXIS));
		currentMonthPanel.setBorder(BorderFactory.createEmptyBorder(0,20,0,20));
		curMonth.setHorizontalTextPosition(SwingConstants.LEADING);
		currentMonthPanel.add(curMonth);
		
		JLabel columnHeads = new JLabel("Sunday          Monday          Tuesday       Wednesday       Thursday           Friday          Saturday");
		columnHeads.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		String[] weekNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		String[][] data = new String[6][7];
		int numdays = (calDrive.getCurrentMonth()).getNumDays();
		int curnum = 1;
		int blankDays = 0;
		switch (calDrive.getFirstDayOfMonth(curDay.getMonth())){
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
		
		monthTable = new JTable(data, weekNames);
		for(int i = 0; i < 6; i++)
		{
			monthTable.setRowHeight(i, 40);
		}
		for(int i = 0; i < 7; i++)
		{
			monthTable.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
		}
		currentMonthPanel.add(columnHeads);
		currentMonthPanel.add(monthTable);
		panel.add(currentMonthPanel, BorderLayout.CENTER);
	}
	
	public static void setWeekPanel(JPanel panel)
	{
		panel.setLayout(new BorderLayout());
		JPanel weekHeader = new JPanel(new BorderLayout());
		weekHeader.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
		JButton prevWeek = new JButton("Previous Week");
		prevWeek.setHorizontalAlignment(SwingConstants.CENTER);
		prevWeek.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e)
					{
						curWeek = calDrive.getPreviousWeek(curWeek);
						updateWeekDisplay(curWeek);
					}
				});
		JButton nextWeek = new JButton("Next Week");
		nextWeek.setHorizontalAlignment(SwingConstants.CENTER);
		nextWeek.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e)
					{
						curWeek = calDrive.getNextWeek(curWeek);
						updateWeekDisplay(curWeek);
					}
				});
		JLabel weekTitle = new JLabel("Week of " + curDay.getMonth() + " " + curDay.getDate());
		weekHeader.add(prevWeek, BorderLayout.WEST);
		weekHeader.add(weekTitle, BorderLayout.CENTER);
		weekHeader.add(nextWeek, BorderLayout.EAST);
		weekTitle.setFont(weekTitle.getFont().deriveFont(24.0f));
		weekTitle.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(weekHeader, BorderLayout.NORTH);
		JPanel weekPanel = new JPanel(new FlowLayout());
		for(int i = 0; i < 7; i++)
		{
			dayOfWeekPan[i] = new JPanel(new BorderLayout());
			curWeek.getDay(i).loadDayEvents();
			if(curWeek.getDay(i).getMonth().equals("Null"))
			{
				dayOfWeek[i] = new JLabel();
				dateLabels[i] = new JLabel();
				eventAreas[i] = new JTextArea(20, 9);
			}
			else
			{
				dayOfWeek[i] = new JLabel(curWeek.getDay(i).getDayOfWeek());
				dateLabels[i] = new JLabel(curWeek.getDay(i).getMonth() + " " + curWeek.getDay(i).getDate());
				eventAreas[i] = new JTextArea(20, 9);
				eventAreas[i].setText(curWeek.getDay(i).getEvents());
			}
			eventAreas[i].setLineWrap(true);
			eventAreas[i].setWrapStyleWord(true);
			eventAreas[i].setEditable(false);
			dateLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
			dayOfWeek[i].setHorizontalAlignment(SwingConstants.CENTER);
			dayOfWeekPan[i].add(dayOfWeek[i], BorderLayout.NORTH);
			dayOfWeekPan[i].add(dateLabels[i], BorderLayout.CENTER);
			dayOfWeekPan[i].add(new JScrollPane(eventAreas[i]), BorderLayout.SOUTH);
			if(curWeek.getDay(i).getMonth().equals("Null"))
			{
				dayOfWeekPan[i].setVisible(false);
			}
			weekPanel.add(dayOfWeekPan[i]);
		}
		panel.add(weekPanel, BorderLayout.CENTER);
	}
	
	public static void updateMonthDisplay(String newMonth)
	{
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
		curMonth.setText(newMonth);
		
	}
	
	public static void updateWeekDisplay(CalendarWeek week)
	{
		for(int i = 0; i < 7; i++)
		{
			eventAreas[i].setText("");
			if(week.getDay(i).getMonth().equals("Null"))
			{
				dateLabels[i].setText(" ");
				eventAreas[i].setText("");
				dayOfWeekPan[i].setVisible(false);
			}
			else
			{
				dateLabels[i].setVisible(true);
				dayOfWeekPan[i].setVisible(true);
				dayOfWeek[i].setText(week.getDay(i).getDayOfWeek());
				dateLabels[i].setText(week.getDay(i).getMonth() + " " + week.getDay(i).getDate());
				eventAreas[i].setText(week.getDay(i).getEvents());
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

	
	public static void setYearPanel(JPanel panel)
	{
		panel.setLayout(new BorderLayout());
		JPanel sixteenPanel = new JPanel();
		sixteenPanel.setLayout(new BoxLayout(sixteenPanel, BoxLayout.Y_AXIS));
		sixteenPanel.setBorder(BorderFactory.createEmptyBorder(10,60,10,10));
		JPanel seventeenPanel = new JPanel();
		seventeenPanel.setLayout(new BoxLayout(seventeenPanel, BoxLayout.Y_AXIS));
		seventeenPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,60));
		JLabel firstYear = new JLabel("2016");
		firstYear.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton august = new JButton("August");
		august.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton september = new JButton("September");
		september.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton october = new JButton("October");
		october.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton november = new JButton("November");
		november.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton december = new JButton("December");
		december.setAlignmentX(Component.CENTER_ALIGNMENT);
		sixteenPanel.add(firstYear);
		sixteenPanel.add(august);
		sixteenPanel.add(september);
		sixteenPanel.add(october);
		sixteenPanel.add(november);
		sixteenPanel.add(december);
		JLabel secondYear = new JLabel("2017");
		secondYear.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton january = new JButton("January");
		january.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton february = new JButton("February");
		february.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton march = new JButton("March");
		march.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton april = new JButton("April");
		april.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton may = new JButton("May");
		may.setAlignmentX(Component.CENTER_ALIGNMENT);
		seventeenPanel.add(secondYear);
		seventeenPanel.add(january);
		seventeenPanel.add(february);
		seventeenPanel.add(march);
		seventeenPanel.add(april);
		seventeenPanel.add(may);
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
		panel.add(sixteenPanel, BorderLayout.WEST);
		panel.add(seventeenPanel, BorderLayout.EAST);
	}
}
