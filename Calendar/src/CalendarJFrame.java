import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class CalendarJFrame extends JFrame{
  public CalendarJFrame()
	{
		super("Base");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabs = new JTabbedPane();
		JPanel panel1 = new JPanel(false);
		JLabel panelInsert1 = new JLabel("This is the Day");
		panel1.add(panelInsert1);
		tabs.addTab("Day", panel1);
		panel1.setPreferredSize(new Dimension(600, 400));
		
		JPanel panel2 = new JPanel(false);
		JLabel panelInsert2 = new JLabel("This is the Week");
		panel2.add(panelInsert2);
		tabs.addTab("Week", panel2);
		panel2.setPreferredSize(new Dimension(600, 400));
		
		JPanel panel3 = new JPanel(false);
		JLabel panelInsert3 = new JLabel("This is the Month");
		panel3.add(panelInsert3);
		tabs.addTab("Month", panel3);
		panel3.setPreferredSize(new Dimension(600, 400));
		
		JPanel panel4 = new JPanel(false);
		JLabel panelInsert4 = new JLabel("This is the Year");
		panel4.add(panelInsert4);
		tabs.addTab("Year", panel4);
		panel4.setPreferredSize(new Dimension(600, 400));
		
		add(tabs);
		pack();
		setVisible(true);
	}
}
