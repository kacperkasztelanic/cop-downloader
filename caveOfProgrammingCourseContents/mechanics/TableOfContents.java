package caveOfProgrammingCourseContents.mechanics;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TableOfContents
{
	private TableOfContents()
	{
	}

	public static void createTableOfContents(String url, String path, String prefix) throws IOException
	{
		saveContetnsReport(parseTableOfContents(url, prefix), path + "\\tableOfContents.txt", prefix);
	}

	private static String parseTableOfContents(String url, String prefix) throws IOException
	{
		Document document = Jsoup.connect(url).get();
		Elements titles = document.select(".lecture-name");
		int i = 0;
		StringBuilder contents = new StringBuilder();
		for (Element l : titles)
		{
			++i;
			contents.append(i).append(" - " + prefix).append(i).append(" - ").append(l.html())
					.append(System.getProperty("line.separator"));
		}
		return contents.toString();
	}

	private static void saveContetnsReport(String contents, String fileName, String prefix) throws IOException
	{
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName)))
		{
			bw.write(prefix + " - Cave of Programming");
			bw.newLine();
			bw.write("Table of Contents");
			bw.newLine();
			bw.write(contents);
		}
	}

	public static void main(String[] args)
	{
		System.out.println(System.getProperty("java.runtime.version"));
	}
}
