package caveOfProgrammingCourseContents;

import java.util.Locale;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import caveOfProgrammingCourseContents.gui.MainWindow;

public class App
{
	public static void main(String[] args)
	{
		Locale.setDefault(Locale.forLanguageTag("en"));
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		}

		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new MainWindow();
			}
		});
	}
}