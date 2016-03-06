package caveOfProgrammingCourseContents.gui;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatusBar extends JPanel
{
	private static final long serialVersionUID = 1L;
	private JLabel loggedInLb;
	private JLabel downloadInProgress;
	private JLabel separator;

	public StatusBar(MainWindow mw)
	{
		super();
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		loggedInLb = new JLabel("Signed in");
		downloadInProgress = new JLabel("Download in progress");
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
