import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class CalendarJFrame extends JFrame{
	
	public static CalendarYear cy = new CalendarYear();
	private static CalendarDay overallCurDay;
	private static int currentDay;
	private static String currentMonth;
	
	public static void main(String[] args)
	{
		getCurrentDate();
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
		final JLabel curDate = new JLabel(cy.getMonth(currentMonth).getMonth() + " " + currentDay);
		final JTextArea eventText = new JTextArea(15, 20);
		
		panel.setLayout(new BorderLayout());
		JPanel currentDayPanel = new JPanel();
		currentDayPanel.setLayout(new FlowLayout());
		JLabel curDayLab = new JLabel("Current Day");
		
		curDayLab.setHorizontalTextPosition(SwingConstants.LEADING);
		final JComboBox monthList = new JComboBox(cy.monthNames);
		final JComboBox dayList = new JComboBox();
		updateDayComboBox(31, dayList);
		JButton dayButton = new JButton("Go to this Day");
		currentDayPanel.add(curDayLab);
		
		monthList.setSelectedIndex(0);
		
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
		
		dayButton.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						setCurrentDate(monthList, dayList);
						curDate.setText(overallCurDay.getMonth() + " " + overallCurDay.getDate());
						overallCurDay.loadDayEvents();
						eventText.setText(overallCurDay.getEvents());
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
		cy.getMonth(currentMonth).getDay(currentDay-1).loadDayEvents();
		
		eventText.setText(cy.getMonth(currentMonth).getDay(currentDay-1).getEvents());
		eventText.setLineWrap(true);
		eventText.setWrapStyleWord(true);
		eventText.setEditable(false);
		
		eventPanel.add(curDate);
		eventPanel.add(eventTitle);
		eventPanel.add(eventText);
		
		panel.add(currentDayPanel, BorderLayout.NORTH);
		panel.add(eventPanel, BorderLayout.WEST);
	}
	
	public static void updateDayComboBox(Integer i, JComboBox bx)
	{
		bx.removeAllItems();
		for(Integer j = 1; j <= i; j++)
		{
			bx.addItem(j);
		}
	}
	
	public static void getCurrentDate()
	{
		try{
			
			BufferedReader br = new BufferedReader(new FileReader("MonthFiles/CurrentDate.txt"));
			currentDay = Integer.parseInt(br.readLine());
			currentMonth = br.readLine();
			br.close();
			overallCurDay = cy.getMonth(currentMonth).getDay(currentDay-1);
			
		}catch(IOException e){}
	}
	
	public static void setCurrentDate(JComboBox monthBX, JComboBox dayBX)
	{
		String x = (String)monthBX.getSelectedItem();
		int y = (Integer)dayBX.getSelectedItem();
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter("MonthFiles/CurrentDate.txt"));
			bw.write(y);
			bw.newLine();
			bw.write(x);
		}catch(IOException e){}
		overallCurDay = cy.getMonth(x).getDay(y - 1);
	}
	
}
