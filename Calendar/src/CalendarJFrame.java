import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import java.io.*;

public class CalendarJFrame extends JFrame{
	
	public static CalendarDriver calDrive = new CalendarDriver();
	private static CalendarDay curDay;
	private static JLabel curMonth;
	private static JTable monthTable;
	
	public static void main(String[] args)
	{
		curDay = calDrive.getCurrentDate();
		new CalendarJFrame();
		//calDrive.getWeek();
	}
	
	/**
	 * <h3>Calendar JFrame Constructor</h3><p>
	 * Creates a Calendar JFrame object that has 4 tabs with different panels inside of them
	 */
	public CalendarJFrame()
	{
		super("Calendar");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 400);
		setResizable(false);
		
		
		JTabbedPane tabs = new JTabbedPane();
		JPanel dayPanel = new JPanel();
		setDayPanel(dayPanel);
		tabs.addTab("Day", dayPanel);
		
		
		JPanel panel2 = new JPanel(false);
		JLabel panelInsert2 = new JLabel("This is the Week");
		panel2.add(panelInsert2);
		tabs.addTab("Week", panel2);
		panel2.setSize(600, 400);
		
		JPanel panel3 = new JPanel(false);
		JPanel monthPanel = new JPanel();
		setMonthPanel(monthPanel);
		tabs.addTab("Month", monthPanel);
		
		JPanel panel4 = new JPanel(false);
		JLabel panelInsert4 = new JLabel("This is the Year");
		panel4.add(panelInsert4);
		tabs.addTab("Year", panel4);
		panel4.setSize(600, 400);
		
		add(tabs);
		setVisible(true);
	}
	
	/**
	 * Sets up the layout and appearance of the day panel
	 * @param panel - the panel that will display all the day components
	 */
	public static void setDayPanel(JPanel panel)
	{
		final JLabel curDate = new JLabel(curDay.getMonth() + " " + curDay.getDate());
		final JTextArea eventText = new JTextArea(15, 20);
		
		panel.setLayout(new BorderLayout());
		JPanel currentDayPanel = new JPanel();
		currentDayPanel.setLayout(new FlowLayout());
		JLabel curDayLab = new JLabel("Current Day");
		
		curDayLab.setHorizontalTextPosition(SwingConstants.LEADING);
		final JComboBox monthList = new JComboBox(calDrive.getMonthNames());
		final JComboBox dayList = new JComboBox();
		updateComboBox(31, dayList);
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
		eventPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,0)); //Code from: http://stackoverflow.com/questions/8863062/add-space-between-jframe-and-jpanel
		
		
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
		addEventPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		JPanel newEventPanel = new JPanel();
		JLabel newEventTitle = new JLabel("New Event");
		JButton newEventButton = new JButton("Add Event");
		newEventPanel.add(newEventTitle);
		newEventPanel.add(newEventButton);
		
		final JTextArea newEventText = new JTextArea(15, 20);
		newEventText.setLineWrap(true);
		newEventText.setWrapStyleWord(true);
		newEventText.setEditable(true);
		
		JPanel removeEvent = new JPanel();
		JLabel remove = new JLabel("Remove Event:");
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
						curDate.setText(curDay.getMonth() + " " + curDay.getDate());
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
	
	public static void updateMonthDisplay(String newMonth)
	{
		String[] weekNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		String[][] data = new String[6][7];
		int numdays = (calDrive.getCurrentMonth()).getNumDays();
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
		curMonth.setText(curDay.getMonth());
		
	}

	/**
	 * Updates a combo box with numbers up to the specified Integer
	 * @param i - the Integer that the combo box will go up to
	 * @param bx - the combo box that will be updated
	 */
	public static void updateComboBox(Integer i, JComboBox bx)
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
	public static CalendarDay setCurrentDate(JComboBox monthBX, JComboBox dayBX)
	{
		String month = (String)monthBX.getSelectedItem();
		int day = (Integer)dayBX.getSelectedItem();
		return calDrive.setCurrentDate(month, day);
	}
}
