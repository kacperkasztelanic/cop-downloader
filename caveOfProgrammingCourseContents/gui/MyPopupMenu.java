package caveOfProgrammingCourseContents.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JPopupMenu;

public class MyPopupMenu extends JPopupMenu
{
	private static final long serialVersionUID = 1L;
	private ResourceBundle rb;
	JCheckBoxMenuItem showLogMI = new JCheckBoxMenuItem();

	public MyPopupMenu(MainWindow mw)
	{
		setDesc();
		showLogMI.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				mw.setProgressAreaVisible(showLogMI.isSelected());
			}
		});
		this.add(showLogMI);
	}

	protected void setDesc()
	{
		rb = RAO.getInstance();
		showLogMI.setText(rb.getString("showLog"));
	}
}
