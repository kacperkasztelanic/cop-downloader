package caveOfProgrammingCourseContents.mechanics;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DownloadContents
{
	private Map<String, String> cookies;
	private List<String> urls;
	private Document doc;
	private String urlToWebsite;
	private String prefix;
	private String folderPath;
	private int fileNumber;
	private int code;
	private boolean attachmentsDownload;

	public DownloadContents()
	{

	}

	public DownloadContents(String urlToWebsite, int code, String prefix, String folderPath, int fileNumber,
			boolean attachmentsDownload)
	{
		this.prefix = prefix;
		this.code = code;
		this.folderPath = folderPath;
		this.fileNumber = fileNumber;
		this.attachmentsDownload = attachmentsDownload;
	}

	public void downloadNextPieceOfContents() throws IOException
	{
		this.retrieveDocument();
		this.getLinks();
		this.downloadContents();
		this.fileNumber++;
		this.code++;
	}

	public void retrieveDocument() throws IOException
	{
		this.doc = Jsoup.connect(this.urlToWebsite + "/lectures/" + this.code).cookies(cookies).get();
	}

	public void getLinks()
	{
		String vlink = "";
		String alink = "";
		Elements el = this.doc.select("a.download");
		int size = el.size();
		Element vid = null;
		Element attach = null;
		List<String> urls = new ArrayList<>();
		if (size > 0)
		{
			vid = el.get(0);
			vlink = vid.attr("href");
			urls.add(vlink);
			if (size > 1)
			{
				attach = el.get(1);
				alink = attach.attr("href");
				urls.add(alink);
			}
		}
		this.urls = urls;
	}

	public void downloadContents() throws IOException
	{
		int size = this.urls.size();
		String vidurl = null;
		String ext = null;
		URL urlv = null;
		URL urla = null;
		if (size > 0)
		{
			vidurl = this.urls.get(0);
			ext = vidurl.substring(vidurl.length() - 3);
			urlv = new URL(this.urls.get(0));
			File vid = new File(String.format("%s\\%s%d.%s", this.folderPath, this.prefix, this.fileNumber, ext));
			FileUtils.copyURLToFile(urlv, vid);
			if (size > 1 && this.attachmentsDownload)
			{
				urla = new URL(this.urls.get(1));
				File att = new File(String.format("%s\\%s%d.zip", this.folderPath, this.prefix, this.fileNumber));
				FileUtils.copyURLToFile(urla, att);
			}
		}
	}

	public boolean signOut() throws IOException
	{
		Response response = Jsoup.connect("http://courses.caveofprogramming.com/sign_out").method(Method.GET).execute();
		this.cookies = response.cookies();
		String text = this.cookies.get("site_preview");
		if (text != null)
			return text.contains("logged_out");
		return false;
	}

	public void getSession(String useremail, String password) throws IOException
	{
		Response response = Jsoup.connect("https://sso.teachable.com/secure/1086/users/sign_in").method(Method.POST)
				.data("utf8", "✓")
				.data("authenticity_token",
						"2KsHY3OP7KYYUms7i2Iq9a0hOybylJfWdVCewQKB4zjUjD6o2HPpJGHi7iRclr7GkbpQXsB69K6kgaPa+VRN7A==")
				.data("user[school_id]", "1086").data("user[email]", useremail).data("user[password]", password)
				.userAgent("Mozilla").execute();
		this.cookies = response.cookies();
	}

	public boolean isLoggedInCorrectly()
	{
		String text = this.cookies.get("signed_in");
		if (text != null)
			return text.contains("true");
		return false;
	}

	public void setAttachmentsDownload(boolean bool)
	{
		this.attachmentsDownload = bool;
	}

	public boolean getAttachemntsDownload()
	{
		return this.attachmentsDownload;
	}

	public void setFileNumber(int number)
	{
		this.fileNumber = number;
	}

	public int getFileNumber()
	{
		return this.fileNumber;
	}

	public void setPrefix(String prefix)
	{
		this.prefix = prefix;
	}

	public String getPrefix()
	{
		return this.prefix;
	}

	public void setFolderPath(String folderPath)
	{
		this.folderPath = folderPath;
	}

	public String getFolderPath()
	{
		return this.folderPath;
	}

	public void setUrlToWebsite(String urlToWebsite)
	{
		this.urlToWebsite = urlToWebsite;
	}

	public String getUrlToWebsite()
	{
		return urlToWebsite;
	}

	public void setCode(int code)
	{
		this.code = code;
	}

	public int getCode()
	{
		return code;
	}

	public String toString()
	{
		return "DownloadContents [urlToWebsite=" + urlToWebsite + ", prefix=" + prefix + ", folderPath=" + folderPath
				+ ", fileNumber=" + fileNumber + ", code=" + code + ", attachmentsDownload=" + attachmentsDownload
				+ "]";
	}

	// public static void main(String[] args) throws IOException
	// {
	// // DownloadContents instance = new DownloadContents();
	// // Map<String, String> cookies = (Map<String, String>)
	// // instance.getSession();
	// // int begin = 38418;
	// // for (int i = 70; i < 91; i++)
	// // {
	// // System.out.println("Pobieranie pliku: Android" + i);
	// // String link =
	// //
	// "http://courses.caveofprogramming.com/courses/learn-android-40-programming-in-java/lectures/"
	// // + begin++;
	// // Document document = Jsoup.connect(link).cookies(cookies).get();
	// // List<String> urls = DownloadContents.getLinks(document);
	// // DownloadContents.downloadContents(urls, i);
	// // }
	// // DownloadContents.signOut();
	// // System.out.println(cookies.size());
	// // System.out.println("-----");
	// // for (Map.Entry<String, String> entry : cookies.entrySet())
	// // {
	// // System.out.println(entry.getKey());
	// // System.out.println(entry.getValue());
	// // System.out.println("-----");
	// // }
	// // String test = "";
	// // if ((test = cookies.get("signed_in")) != null)
	// // System.out.println(test.contains("true"));
	// // System.out.println(instance.signOut());
	//
	// DownloadContents dc = new DownloadContents();
	// dc.setAttachmentsDownload(true);
	// dc.setCode(38020);
	// dc.setFileNumber(1);
	// dc.setFolderPath("sdf");
	// dc.setPrefix("MyConsole");
	// dc.setUrlToWebsite("fdgfd");
	// // dc.setFileNumber(1);
	// // dc.setFolderPath("C:\\Users\\Kacper\\Desktop");
	// // dc.setPrefix("MyConsole");
	// //
	// dc.setUrlToWebsite("http://courses.caveofprogramming.com/courses/the-java-spring-tutorial");
	// dc.getSession("kcpr51@hotmail.com", "11*3k@cper");
	// dc.downloadNextPieceOfContents();
	// dc.signOut();
	// }
}
