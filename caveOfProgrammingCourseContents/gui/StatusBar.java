package caveOfProgrammingCourseContents.gui;

import java.awt.FlowLayout;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatusBar extends JPanel
{
	private static final long serialVersionUID = 1L;
	private ResourceBundle rb;
	private JLabel loggedInLb = new JLabel();
	private JLabel downloadInProgress = new JLabel();
	private JLabel separator = new JLabel();

	public StatusBar(MainWindow mw)
	{
		super();
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		setDesc();
		separator.setText(" | ");
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

	protected void setDesc()
	{
		rb = RAO.getInstance();
		loggedInLb.setText(rb.getString("loggedIn"));
		downloadInProgress.setText(rb.getString("downloadInProgress"));

	}
}
