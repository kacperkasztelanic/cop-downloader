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
		saveContentsReport(parseTableOfContents(getDocumentFromUrl(url), prefix), path + "\\tableOfContents.txt",
				prefix);
	}

	private static Document getDocumentFromUrl(String url) throws IOException
	{
		return Jsoup.connect(url).get();
	}

	private static String parseTableOfContents(Document doc, String prefix)
	{
		Elements titles = doc.select(".lecture-name");
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

	private static void saveContentsReport(String contents, String fileName, String prefix) throws IOException
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
}
