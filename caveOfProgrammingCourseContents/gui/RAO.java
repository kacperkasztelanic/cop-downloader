package caveOfProgrammingCourseContents.gui;

import java.util.Locale;
import java.util.ResourceBundle;

public class RAO
{
	private static ResourceBundle INSTANCE;

	private RAO()
	{

	}

	protected static void makeInstanceNull()
	{
		INSTANCE = null;
	}

	public static ResourceBundle getInstance()
	{
		if (INSTANCE == null)
			INSTANCE = ResourceBundle.getBundle("caveOfProgrammingCourseContents.gui.resources.translations.text",
					Locale.getDefault());
		return INSTANCE;
	}
}
