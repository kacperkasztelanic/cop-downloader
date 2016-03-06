package caveOfProgrammingCourseContents.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class AboutWindow extends JPanel
{
	private static final long serialVersionUID = 1L;

	private JLabel author;
	private JLabel year;
	private JLabel title;
	private JLabel version;
	private JLabel explanation;
	private JLabel image;

	public AboutWindow(Frame parent)
	{
		author = new JLabel("Written by: Kacper Kasztelanic");
		author.setAlignmentX(Component.CENTER_ALIGNMENT);
		year = new JLabel("Created in 2016");
		year.setAlignmentX(Component.CENTER_ALIGNMENT);
		version = new JLabel("Version: 1.0");
		version.setAlignmentX(Component.CENTER_ALIGNMENT);
		title = new JLabel("CaveOfProgramming Contents Downloader");
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		explanation = new JLabel(
				"<html><p align=center>Utility that helps to download course contents<br> from caveofprogramming.com</p></html>",
				SwingConstants.CENTER);
		explanation.setAlignmentX(Component.CENTER_ALIGNMENT);
		Font font = title.getFont();
		title.setFont(new Font(font.getFontName(), Font.BOLD, 15));
		ImageIcon icon = new ImageIcon(getClass().getResource("icon/icon128.png"));
		image = new JLabel(icon);
		image.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(title);
		this.add(version);
		this.add(Box.createRigidArea(new Dimension(0, 10)));
		this.add(image);
		this.add(Box.createRigidArea(new Dimension(0, 10)));
		this.add(explanation);
		this.add(Box.createRigidArea(new Dimension(0, 10)));
		this.add(author);
		this.add(year);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
}
