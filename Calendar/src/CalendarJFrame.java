import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class CalendarJFrame extends JFrame{
	
	public static CalendarYear cy = new CalendarYear();
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
	
	public static void setDayPanel(JPanel panel1)
	{
		panel1.setLayout(new BorderLayout());
		JPanel currentDayPanel = new JPanel();
		currentDayPanel.setLayout(new FlowLayout());
		JLabel curDayLab = new JLabel("Current Day");
		
		curDayLab.setHorizontalTextPosition(SwingConstants.LEADING);
		JComboBox monthList = new JComboBox(cy.monthNames);
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
		
		
		currentDayPanel.add(monthList);
		currentDayPanel.add(dayList);
		currentDayPanel.add(dayButton);
		
		JPanel eventPanel = new JPanel();
		eventPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,0)); //Code from: http://stackoverflow.com/questions/8863062/add-space-between-jframe-and-jpanel
		JLabel curDay = new JLabel();
		JTextArea eventText = new JTextArea(15, 20);
		eventText.setLineWrap(true);
		eventText.setWrapStyleWord(true);
		eventText.setEditable(false);
		
		panel1.add(currentDayPanel, BorderLayout.NORTH);
		eventPanel.add(eventText);
		panel1.add(eventPanel, BorderLayout.WEST);
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
			System.out.println(currentDay + currentMonth);
			br.close();
			
		}catch(IOException e){}
	}
}
