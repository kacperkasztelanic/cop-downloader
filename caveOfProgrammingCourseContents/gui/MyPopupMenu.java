package caveOfProgrammingCourseContents.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JPopupMenu;

public class MyPopupMenu extends JPopupMenu
{
	private static final long serialVersionUID = 1L;
	private ResourceBundle rb = RAO.getInstance();
	JCheckBoxMenuItem showLogMI;

	public MyPopupMenu(MainWindow mw)
	{
		showLogMI = new JCheckBoxMenuItem(rb.getString("showLog"));
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
