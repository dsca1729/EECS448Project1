import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class CalendarJFrame extends JFrame{
	
	public static CalendarDriver calDrive = new CalendarDriver();
	private static CalendarDay curDay;
	
	public static void main(String[] args)
	{
		curDay = calDrive.getCurrentDate();
		new CalendarJFrame();
	}
	
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
		JLabel panelInsert3 = new JLabel("This is the Month");
		panel3.add(panelInsert3);
		tabs.addTab("Month", panel3);
		panel3.setSize(600, 400);
		
		JPanel panel4 = new JPanel(false);
		JLabel panelInsert4 = new JLabel("This is the Year");
		panel4.add(panelInsert4);
		tabs.addTab("Year", panel4);
		panel4.setSize(600, 400);
		
		add(tabs);
		setVisible(true);
	}
	
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
		updateDayComboBox(31, dayList);
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
									updateDayComboBox(30, dayList);
								}
								else if(x.equals("February"))
								{
									updateDayComboBox(28, dayList);
								}
								else
								{
									updateDayComboBox(31, dayList);
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
		
		eventPanel.add(curDate);
		eventPanel.add(eventTitle);
		eventPanel.add(eventText);
		
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
		updateDayComboBox(curDay.getEventCount(), eventSelection);
		JButton removeEventButton = new JButton("Remove");
		removeEvent.add(remove);
		removeEvent.add(eventSelection);
		removeEvent.add(removeEventButton);
		
		addEventPanel.add(newEventPanel);
		addEventPanel.add(newEventText);
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
						updateDayComboBox(curDay.getEventCount(), eventSelection);
					}
				});
		
		newEventButton.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						curDay.addEvent(newEventText.getText());
						dayButton.doClick();
					}
				});
		
		removeEventButton.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						curDay.removeEvent((int)eventSelection.getSelectedItem());
						dayButton.doClick();
					}
				});
		
		panel.add(currentDayPanel, BorderLayout.NORTH);
		panel.add(eventPanel, BorderLayout.WEST);
		panel.add(addEventPanel, BorderLayout.EAST);
	}
	
	public static void updateDayComboBox(Integer i, JComboBox bx)
	{
		bx.removeAllItems();
		for(Integer j = 1; j <= i; j++)
		{
			bx.addItem(j);
		}
	}
	
	public static CalendarDay setCurrentDate(JComboBox monthBX, JComboBox dayBX)
	{
		String month = (String)monthBX.getSelectedItem();
		int day = (Integer)dayBX.getSelectedItem();
		return calDrive.setCurrentDate(month, day);
	}
}
