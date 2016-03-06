package caveOfProgrammingCourseContents.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

import caveOfProgrammingCourseContents.mechanics.DownloadContents;
import caveOfProgrammingCourseContents.mechanics.TableOfContents;

public class MainWindow extends JFrame
{
	private static final long serialVersionUID = 1L;
	protected MainMenuBar mainMenu;
	protected StatusBar statusBar;
	private JButton downloadBt;
	private JButton stopBt;
	private JButton browseBt;
	private JButton tableOfContentsBt;
	private JCheckBox attachmentsCheck;
	private JCheckBox manualCheck;
	private JProgressBar progressBar;
	private JTextArea progressAreaRaw;
	private JScrollPane progressArea;
	private JTextField urlTF;
	private JTextField codeTF;
	private JTextField fromTF;
	private JTextField upToTF;
	private JTextField pathTF;
	private JTextField fileNamePatternTF;
	private JFileChooser folderDialog;
	private JLabel urlLb;
	private JLabel codeLb;
	private JLabel downloadLb;
	private JLabel upToLb;
	private JLabel progressLb;
	private JLabel pathLb;
	private JLabel fileNamePatternLb;
	private JPanel pathPl;
	private JPanel urlPl;
	private JPanel downloadPl;
	private JPanel buttonsPl;
	private JPanel progressPl;
	private JPanel mainPl;
	private List<Image> icons;
	protected LoginWindow lw;
	protected AboutWindow aw;
	protected DownloadContents dc;
	private volatile boolean stop = false;
	private boolean inProgress = false;
	private int advance;
	private SimpleDateFormat sdf;
	private MyPopupMenu popup;
	private Component space;

	public MainWindow()
	{
		super("CaveOfProgramming Contents Downloader v1.0");
		this.setLayout(new BorderLayout());
		this.setLocationByPlatform(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setIcons();

		mainMenu = new MainMenuBar(this);
		this.setJMenuBar(mainMenu);

		// Buttons init
		String downloadTip = "<html>Click to begin downloading contents.<br />Ensure that you are logged in before attempting this.</html>";
		downloadBt = new JButton("Download");
		downloadBt.setToolTipText(downloadTip);
		downloadBt.setEnabled(false);
		String stopTip = "<html>Click this if you want to stop download after downloading the current file is accomplished.<br />This button is accessible only when download is pending.</html>";
		stopBt = new JButton("Stop");
		stopBt.setEnabled(false);
		stopBt.setToolTipText(stopTip);
		String browseTip = "Use convenient creator to select desired path for files.";
		browseBt = new JButton("Browse");
		browseBt.setToolTipText(browseTip);
		String tableOfContentsTip = "<html>Creates a simple text table of contents for the course.<br />File will be named: <i>tableOfContents.txt</i> and placed in the same folder specified in <code>Save folder</code> field.</html>";
		tableOfContentsBt = new JButton("Create table of contents");
		tableOfContentsBt.setToolTipText(tableOfContentsTip);
		// ProgressBar init
		progressBar = new JProgressBar();
		// CheckBoxes init
		attachmentsCheck = new JCheckBox("Download attachments");
		attachmentsCheck.setToolTipText("<html>Set this if you want to download attached <i>*.zip</i> files.</html>");
		manualCheck = new JCheckBox("Manual control");
		manualCheck.setToolTipText(
				"<html>If unchecked you need only to pass the number of the first movie. <br /> The next ones will be calculated automatically.</html>");
		// TextArea and ScrollPane init
		progressAreaRaw = new JTextArea();
		progressArea = new JScrollPane(progressAreaRaw);
		progressAreaRaw.setEditable(false);
		progressAreaRaw.setFont(progressArea.getFont().deriveFont(12f));
		progressArea.setVisible(false);
		// TextFields init
		String urlTip = "<html>Pass here generic url to the course - without <i>/lectures/xxxxx</i> sequence, where <i>xxxxx</i> is the number you need to provide to the <code>Start from</code> field.<br />Example: <i>http://courses.caveofprogramming.com/courses/the-java-spring-tutorial</i> for the Spring Tutorial.</html>";
		urlTF = new JTextField(50);
		urlTF.setToolTipText(urlTip);
		String codeTip = "<html>Pass here the number at the end of the first movie url.<br />If <code>Manual Control</code> is turned on you need to provide here the number of the first movie you want to download.</html>";
		codeTF = new JTextField(6);
		codeTF.setToolTipText(codeTip);
		String fromTip = "Number of the first movie you want to download.";
		fromTF = new JTextField(3);
		fromTF.setToolTipText(fromTip);
		String upToTip = "Number of the last movie you want to download.";
		upToTF = new JTextField(3);
		upToTF.setToolTipText(upToTip);
		String fileNamePatternTip = "<html>Enter here the filename pattern that downloaded files will named with.<br />Example: <i>somename</i> will produce filename similar to <i>somename.avi</i></html>";
		fileNamePatternTF = new JTextField(15);
		fileNamePatternTF.setToolTipText(fileNamePatternTip);
		String pathTip = "<html>Path to the desired download folder.<br />You can click <code>Browse</code> to set this.</html>";
		pathTF = new JTextField(28);
		pathTF.setToolTipText(pathTip);
		// FolderDialog init
		folderDialog = new JFileChooser(System.getProperty("user.home"));
		folderDialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		// Labels init
		pathLb = new JLabel("Save folder: ");
		pathLb.setToolTipText(pathTip);
		urlLb = new JLabel("URL: ");
		urlLb.setToolTipText(urlTip);
		codeLb = new JLabel("Start from: ");
		codeLb.setToolTipText(codeTip);
		downloadLb = new JLabel("Download from: ");
		downloadLb.setToolTipText(fromTip);
		upToLb = new JLabel(" to: ");
		upToLb.setToolTipText(upToTip);
		progressLb = new JLabel("Idle");
		fileNamePatternLb = new JLabel("File name: ");
		fileNamePatternLb.setToolTipText(fileNamePatternTip);

		advance = 0;
		sdf = new SimpleDateFormat("HH':'mm':'ss");

		// Buttons action listeners
		browseBt.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				browseDo();
			}
		});
		downloadBt.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				downloadDo();
			}
		});
		stopBt.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				stopDo();
			}
		});
		tableOfContentsBt.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				tableOfContentsDo();
			}
		});

		mainPl = new JPanel();

		urlPl = new JPanel(new FlowLayout(FlowLayout.LEFT));
		urlPl.add(urlLb);
		urlPl.add(urlTF);
		urlPl.add(codeLb);
		urlPl.add(codeTF);
		mainPl.add(urlPl);

		pathPl = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pathPl.add(pathLb);
		pathPl.add(pathTF);
		pathPl.add(browseBt);
		pathPl.add(fileNamePatternLb);
		pathPl.add(fileNamePatternTF);
		mainPl.add(pathPl);

		downloadPl = new JPanel(new FlowLayout(FlowLayout.LEFT));
		downloadPl.add(downloadLb);
		downloadPl.add(fromTF);
		downloadPl.add(upToLb);
		downloadPl.add(upToTF);
		downloadPl.add(attachmentsCheck);
		downloadPl.add(manualCheck);
		mainPl.add(downloadPl);

		buttonsPl = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonsPl.add(downloadBt);
		buttonsPl.add(stopBt);
		buttonsPl.add(tableOfContentsBt);
		mainPl.add(buttonsPl);

		progressPl = new JPanel(new FlowLayout(FlowLayout.LEFT));
		progressPl.add(progressLb);
		mainPl.add(progressPl);

		mainPl.add(progressBar);
		progressBar.setValue(0);
		progressBar.setBorderPainted(true);
		progressBar.setStringPainted(true);
		progressBar.setPreferredSize(new Dimension(progressBar.getWidth(), 25));
		space = Box.createRigidArea(new Dimension(0, 10));
		space.setVisible(false);
		mainPl.add(space);
		mainPl.add(progressArea);
		progressAreaRaw.setRows(5);

		mainPl.setBorder(new EmptyBorder(0, 10, 10, 10));
		mainPl.setLayout(new BoxLayout(mainPl, BoxLayout.PAGE_AXIS));
		this.add(mainPl, BorderLayout.CENTER);

		statusBar = new StatusBar(this);
		this.add(statusBar, BorderLayout.SOUTH);

		// Popup menu
		popup = new MyPopupMenu(this);
		this.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				checkPopup(e);
			}

			public void mousePressed(MouseEvent e)
			{
				checkPopup(e);
			}

			public void mouseReleased(MouseEvent e)
			{
				checkPopup(e);
			}

			private void checkPopup(MouseEvent e)
			{
				if (e.isPopupTrigger())
				{
					popup.show(MainWindow.this, e.getX(), e.getY());
				}
			}
		});
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public void displayInfo(String infoText)
	{
		displayInfo(infoText, "Info");
	}

	public void displayInfo(String infoText, String title)
	{
		JOptionPane.showMessageDialog(null, infoText, title, JOptionPane.INFORMATION_MESSAGE);
	}

	public void appendTextArea(String message)
	{
		progressAreaRaw.append(sdf.format(new Date()) + " | " + message + System.getProperty("line.separator"));
	}

	protected void setDownloadBtAccessible(boolean access)
	{
		downloadBt.setEnabled(access);
	}

	protected void setProgressAreaVisible(boolean visible)
	{
		progressArea.setVisible(visible);
		space.setVisible(visible);
		pack();
	}

	protected void saveLog(String fileName)
	{
		String contents = progressAreaRaw.getText();
		if (!contents.equals(""))
		{
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName)))
			{
				writer.write(contents);
				JOptionPane.showMessageDialog(null,
						"<html>Logfile <i>CoPDownloaderLog.txt</i> has been successfully created.</html>"
								+ pathTF.getText(),
						"Info", JOptionPane.INFORMATION_MESSAGE);
			}
			catch (IOException e)
			{
				JOptionPane.showMessageDialog(null, "Error saving log to file: " + fileName + ".", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		else
			JOptionPane.showMessageDialog(null,
					"Logfile has not been created as there is no content to write." + pathTF.getText(), "Info",
					JOptionPane.INFORMATION_MESSAGE);
	}

	private boolean validateForm(boolean all)
	{
		boolean ok = true;
		urlTF.setText(urlTF.getText().trim());
		if (!(urlTF.getText().length() > 0))
			return false;
		fileNamePatternTF.setText(fileNamePatternTF.getText().trim());
		if (!(fileNamePatternTF.getText().length() > 0))
			return false;
		pathTF.setText(pathTF.getText().trim());
		if (!(pathTF.getText().length() > 0))
			return false;
		codeTF.setText(codeTF.getText().trim());
		if (!(codeTF.getText().length() > 0))
			return false;
		for (int i = 0; i < codeTF.getText().length(); i++)
			if (codeTF.getText().charAt(i) < 48 || codeTF.getText().charAt(i) > 57)
				return false;
		if (all)
		{
			fromTF.setText(fromTF.getText().trim());
			upToTF.setText(upToTF.getText().trim());
			if (!(fromTF.getText().length() > 0))
				return false;
			for (int i = 0; i < fromTF.getText().length(); i++)
				if (fromTF.getText().charAt(i) < 48 || fromTF.getText().charAt(i) > 57)
					return false;
			if (!(upToTF.getText().length() > 0))
				return false;
			for (int i = 0; i < upToTF.getText().length(); i++)
				if (upToTF.getText().charAt(i) < 48 || upToTF.getText().charAt(i) > 57)
					return false;
		}
		return ok;
	}

	private void downloadDo()
	{
		boolean ok = validateForm(true);
		if (dc != null && !inProgress && ok)
		{
			statusBar.showDownloading();
			stop = false;
			inProgress = true;
			downloadBt.setEnabled(false);
			stopBt.setEnabled(true);
			progressLb.setText("Download in progress: ");
			dc.setAttachmentsDownload(attachmentsCheck.isSelected());
			dc.setFolderPath(pathTF.getText());
			dc.setPrefix(fileNamePatternTF.getText());
			dc.setUrlToWebsite(urlTF.getText());
			int from = Integer.parseInt(fromTF.getText());
			int upTo = Integer.parseInt(upToTF.getText());
			progressBar.setMaximum(upTo - from + 1);
			progressBar.setValue(0);
			dc.setFileNumber(from);
			if (manualCheck.isSelected())
				dc.setCode(Integer.parseInt(codeTF.getText()));
			else
				dc.setCode(Integer.parseInt(codeTF.getText()) + Integer.parseInt(fromTF.getText()) - 1);
			SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>()
			{
				boolean problem = false;

				protected Void doInBackground() throws Exception
				{
					try
					{
						while (dc.getFileNumber() <= upTo && !stop && !problem)
						{
							publish(dc.getFileNumber());
							dc.downloadNextPieceOfContents();
						}
					}
					catch (Exception e1)
					{
						problem = true;
					}
					return null;
				}

				protected void done()
				{
					downloadBt.setEnabled(true);
					stopBt.setEnabled(false);
					progressBar.setValue(100);
					statusBar.hideDownloading();
					progressLb.setText("Idle");
					inProgress = false;
					if (!problem)
					{
						progressBar.setValue(100);
						appendTextArea("Contents downloaded to: " + pathTF.getText());
						JOptionPane.showMessageDialog(null, "Content downloaded to: " + pathTF.getText(), "Info",
								JOptionPane.INFORMATION_MESSAGE);
					}
					else
						JOptionPane.showMessageDialog(null,
								"<html>There was a problem downloading contents.<br />Ensure data you input is correct.</html>",
								"Error", JOptionPane.ERROR_MESSAGE);
				}

				protected void process(List<Integer> number)
				{
					appendTextArea("Downloading file(s): " + dc.getPrefix() + number.get(number.size() - 1) + "...");
					incrementProgressBar();
				}

			};
			worker.execute();
		}
		else if (dc == null)
		{
			JOptionPane.showMessageDialog(null, "You have to log in before attempting to download contents.",
					"Log-in first", JOptionPane.INFORMATION_MESSAGE);
		}
		else
		{
			JOptionPane.showMessageDialog(null,
					"<html>There was a problem downloading contents.<br />Ensure data you input is correct.</html>",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void stopDo()
	{
		if (inProgress)
		{
			stop = true;
			stopBt.setEnabled(false);
			progressLb.setText("Stopping...");
			appendTextArea("Process will be stopped after this file download is accomplished.");
		}
	}

	private void browseDo()
	{
		int returnVal = folderDialog.showOpenDialog(MainWindow.this);
		if (returnVal == JFileChooser.APPROVE_OPTION)
			pathTF.setText(folderDialog.getSelectedFile().getAbsolutePath());
	}

	private void tableOfContentsDo()
	{
		boolean ok = validateForm(false);
		if (ok)
		{
			String url = urlTF.getText();
			String code = codeTF.getText();
			String fileNamePattern = fileNamePatternTF.getText();
			String path = pathTF.getText();
			SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>()
			{
				boolean error = false;

				protected Void doInBackground() throws Exception
				{
					try
					{
						TableOfContents.createTableOfContents(url + "/lectures/" + code, path, fileNamePattern);
					}

					catch (IOException e)
					{
						error = true;
					}
					return null;
				}

				protected void done()
				{
					if (!error)
					{
						appendTextArea("Table of contents created.");
						displayInfo("<html>Table of contents created in <i>tableOfContents.txt</i> and saved in <i>"
								+ pathTF.getText() + "</i></html>");
					}
					else
					{
						appendTextArea("Cannot create tableOfContents.txt");
						JOptionPane.showMessageDialog(null, "Error contacting server.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			};
			worker.execute();
		}
		else
		{
			JOptionPane.showMessageDialog(null,
					"<html>There was a problem creating table of contents.<br />Ensure data you input is correct.</html>",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void incrementProgressBar()
	{
		progressBar.setValue(advance++);
	}

	private void setIcons()
	{
		icons = new ArrayList<>();
		for (IconSizes ico : IconSizes.values())
		{
			URL iconURL = getClass().getResource("icon/" + ico + ".png");
			ImageIcon icon = new ImageIcon(iconURL);
			icons.add(icon.getImage());
		}
		this.setIconImages(icons);
	}
}