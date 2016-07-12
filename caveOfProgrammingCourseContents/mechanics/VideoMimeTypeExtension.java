package caveOfProgrammingCourseContents.mechanics;

import java.util.HashMap;
import java.util.Map;

public class VideoMimeTypeExtension
{
	private static final Map<String, String> types;
	static
	{
		types = new HashMap<>();
		types.put("video/x-flv", "flv");
		types.put("video/mp4", "mp4");
		types.put("application/x-mpegurl", "m3u8");
		types.put("video/mp2t", "ts");
		types.put("video/3gpp", "3gp");
		types.put("video/quicktime", "mov");
		types.put("video/x-msvideo", "avi");
		types.put("video/x-ms-wmv", "wmv");
	}

	public static String getExtension(String mimeType)
	{
		return types.get(mimeType.toLowerCase());
	}

	public static int size()
	{
		return types.size();
	}
}
