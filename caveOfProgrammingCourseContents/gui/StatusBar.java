package caveOfProgrammingCourseContents.gui;

import java.awt.FlowLayout;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatusBar extends JPanel
{
	private static final long serialVersionUID = 1L;
	private ResourceBundle rb = RAO.getInstance();
	private JLabel loggedInLb;
	private JLabel downloadInProgress;
	private JLabel separator;

	public StatusBar(MainWindow mw)
	{
		super();
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		loggedInLb = new JLabel(rb.getString("loggedIn"));
		downloadInProgress = new JLabel(rb.getString("downloadInProgress"));
		separator = new JLabel(" | ");
		this.add(loggedInLb);
		loggedInLb.setVisible(false);
		downloadInProgress.setVisible(false);
		separator.setVisible(false);
		this.add(separator);
		this.add(downloadInProgress);
	}

	public void hideLogged()
	{
		loggedInLb.setVisible(false);
		separator.setVisible(false);
	}

	public void showLogged()
	{
		loggedInLb.setVisible(true);
		separator.setVisible(downloadInProgress.isVisible());
	}

	public void hideDownloading()
	{
		downloadInProgress.setVisible(false);
		separator.setVisible(false);
	}

	public void showDownloading()
	{
		downloadInProgress.setVisible(true);
		separator.setVisible(loggedInLb.isVisible());
	}
}
