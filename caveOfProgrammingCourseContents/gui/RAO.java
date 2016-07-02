package caveOfProgrammingCourseContents.gui;

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
			INSTANCE = ResourceBundle.getBundle("text");
		return INSTANCE;
	}
}
