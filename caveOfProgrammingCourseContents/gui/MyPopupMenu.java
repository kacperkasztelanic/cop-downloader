package caveOfProgrammingCourseContents.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JPopupMenu;

public class MyPopupMenu extends JPopupMenu
{
	private static final long serialVersionUID = 1L;
	JCheckBoxMenuItem showLogMI;

	public MyPopupMenu(MainWindow mw)
	{
		showLogMI = new JCheckBoxMenuItem("Show log");
		showLogMI.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				mw.setProgressAreaVisible(showLogMI.isSelected());
			}
		});
		this.add(showLogMI);
	}
}
