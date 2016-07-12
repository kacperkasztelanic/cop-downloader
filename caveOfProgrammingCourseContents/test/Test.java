package caveOfProgrammingCourseContents.test;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import caveOfProgrammingCourseContents.mechanics.VideoMimeTypeExtension;

public class Test
{
	public static void ExtensionInference()
	{
		// String fullUrl =
		// "https://d2vvqscadf4c1f.cloudfront.net/h4kG0xrJRGuWOayDvtbP_009%20Primary%20Keys.mov";
		// String fullUrl =
		// "https://d2vvqscadf4c1f.cloudfront.net/btxI9KySQF2kJzeIsjrc_000%20Introducing%20MySQL.avi";
		String ext = null;
		String fullUrl = "https://www.filepicker.io/api/file/eALSowc6R82aodjKvr53";
		URL urlv;
		URLConnection connv;
		try
		{
			urlv = new URL(fullUrl);
			connv = urlv.openConnection();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return;
		}
		if (fullUrl.length() - fullUrl.lastIndexOf(".") < 6)
		{
			ext = fullUrl.substring(fullUrl.lastIndexOf(".") + 1);
			System.out.println(String.format(".%s", ext));
		}
		else
		{
			ext = VideoMimeTypeExtension.getExtension(connv.getContentType());
			if (ext == null)
				ext = "";
			System.out.println(String.format(".%s", ext));
		}
	}

	public static void main(String[] args)
	{
		Test.ExtensionInference();
	}
}
