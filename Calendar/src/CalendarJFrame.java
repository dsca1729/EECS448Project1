import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class CalendarJFrame extends JFrame{
	
	private CalendarYear yearOne;
	
	public CalendarJFrame(CalendarYear x)
	{
		super("Calendar");
		yearOne = x;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 400);
		setResizable(false);
		String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		
		JTabbedPane tabs = new JTabbedPane();
		JPanel panel1 = new JPanel();
		JPanel textPanel = new JPanel();
		JLabel label1 = new JLabel("This is the Day");
		label1.setHorizontalTextPosition(SwingConstants.LEADING);
		textPanel.add(label1);
		textPanel.setSize(300, 400);
		JPanel datePanel = new JPanel();
		JComboBox monthList = new JComboBox(months);
		monthList.setSelectedIndex(0);
		datePanel.add(monthList);
		JComboBox dayList = new JComboBox();
		//dayList.setSelectedIndex(0);
		datePanel.add(dayList);
		JButton dayButton = new JButton("Go to this Day");
		datePanel.add(dayButton);
		datePanel.setSize(200, 300);
		
		JPanel eventPanel = new JPanel();
		JTextArea eventText = new JTextArea(10, 10);
		eventText.setLineWrap(true);
		eventText.setWrapStyleWord(true);
		
		panel1.add(textPanel);
		panel1.add(datePanel);
		panel1.add(eventText);
		tabs.addTab("Day", panel1);
		
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
}
