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
import java.util.ResourceBundle;

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

import caveOfProgrammingCourseContents.gui.resources.Version;
import caveOfProgrammingCourseContents.mechanics.DownloadContents;
import caveOfProgrammingCourseContents.mechanics.TableOfContents;

public class MainWindow extends JFrame
{
	private static final long serialVersionUID = 1L;
	private final String TIME_PATTERN = "HH':'mm':'ss";
	private final String DEFAULT_FOLDER = "user.home";
	private ResourceBundle rb;
	protected MainMenuBar mainMenu;
	protected StatusBar statusBar;
	private JButton downloadBt = new JButton();
	private JButton stopBt = new JButton();
	private JButton browseBt = new JButton();
	private JButton tableOfContentsBt = new JButton();
	private JCheckBox attachmentsCheck = new JCheckBox();
	private JCheckBox attachmentsOnlyCheck = new JCheckBox();
	private JProgressBar progressBar = new JProgressBar();
	private JTextArea progressAreaRaw = new JTextArea();
	private JScrollPane progressArea;
	private JTextField urlTF = new JTextField();
	private JTextField fromTF = new JTextField();
	private JTextField upToTF = new JTextField();
	private JTextField pathTF = new JTextField();
	private JTextField fileNamePatternTF = new JTextField();
	private JFileChooser folderDialog;
	private JLabel urlLb = new JLabel();
	private JLabel downloadLb = new JLabel();
	private JLabel upToLb = new JLabel();
	private JLabel progressLb = new JLabel();
	private JLabel pathLb = new JLabel();
	private JLabel fileNamePatternLb = new JLabel();
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
	private SimpleDateFormat sdf = new SimpleDateFormat(TIME_PATTERN);
	private MyPopupMenu popup;
	private MyPopupMenuText popupText;
	private Component space;

	public MainWindow()
	{
		super("CaveOfProgramming Contents Downloader v" + Version.getVer());
		this.setLayout(new BorderLayout());
		this.setLocationByPlatform(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainMenu = new MainMenuBar(this);
		this.setJMenuBar(mainMenu);

		// Buttons init
		downloadBt.setEnabled(false);
		stopBt.setEnabled(false);
		// CheckBoxes init
		attachmentsOnlyCheck.setEnabled(false);
		// TextArea and ScrollPane init
		progressAreaRaw = new JTextArea();
		progressArea = new JScrollPane(progressAreaRaw);
		progressAreaRaw.setEditable(false);
		progressAreaRaw.setFont(progressArea.getFont().deriveFont(12f));
		progressArea.setVisible(false);
		// TextFields init
		urlTF.setColumns(65);
		fromTF.setColumns(3);
		upToTF.setColumns(3);
		fileNamePatternTF.setColumns(15);
		pathTF.setColumns(28);
		// FolderDialog init
		folderDialog = new JFileChooser(System.getProperty(DEFAULT_FOLDER));
		folderDialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		setIcons();
		setDesc();

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
		attachmentsCheck.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (attachmentsOnlyCheck.isEnabled())
				{
					attachmentsOnlyCheck.setEnabled(false);
					attachmentsOnlyCheck.setSelected(false);
				}
				else
					attachmentsOnlyCheck.setEnabled(true);
			}
		});

		mainPl = new JPanel();

		urlPl = new JPanel(new FlowLayout(FlowLayout.LEFT));
		urlPl.add(urlLb);
		urlPl.add(urlTF);
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
		downloadPl.add(attachmentsOnlyCheck);
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
		popupText = new MyPopupMenuText(this);
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
		urlTF.setComponentPopupMenu(popupText);
		fromTF.setComponentPopupMenu(popupText);
		upToTF.setComponentPopupMenu(popupText);
		pathTF.setComponentPopupMenu(popupText);
		fileNamePatternTF.setComponentPopupMenu(popupText);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public void displayInfo(String infoText)
	{
		displayInfo(infoText, rb.getString("info"));
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
						"<html>" + rb.getString("logFileCreatedDialog") + "</html>" + pathTF.getText(),
						rb.getString("info"), JOptionPane.INFORMATION_MESSAGE);
			}
			catch (IOException e)
			{
				JOptionPane.showMessageDialog(null, rb.getString("errorSavingLogDialog") + fileName + ".",
						rb.getString("error"), JOptionPane.ERROR_MESSAGE);
			}
		}
		else
			JOptionPane.showMessageDialog(null, rb.getString("noContentDialog") + pathTF.getText(),
					rb.getString("info"), JOptionPane.INFORMATION_MESSAGE);
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
			progressLb.setText(rb.getString("downloadInProgressEntry"));
			dc.setAttachmentsDownload(attachmentsCheck.isSelected());
			dc.setAttachmentsOnlyDownload(attachmentsOnlyCheck.isSelected());
			dc.setFolderPath(pathTF.getText());
			dc.setPrefix(fileNamePatternTF.getText());
			dc.setUrlToWebsite(urlTF.getText());
			int from = Integer.parseInt(fromTF.getText());
			int upTo = Integer.parseInt(upToTF.getText());
			progressBar.setMaximum(upTo - from + 1);
			progressBar.setValue(0);
			advance = 0;
			dc.setFileNumber(from);
			SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>()
			{
				boolean problem = false;

				protected Void doInBackground() throws Exception
				{
					try
					{
						for (int i = 1; i < from; i++)
							dc.omitLesson();
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
					progressLb.setText(rb.getString("idle"));
					inProgress = false;
					if (!problem)
					{
						progressBar.setValue(100);
						appendTextArea(rb.getString("contentDownloaded") + pathTF.getText());
						JOptionPane.showMessageDialog(null, rb.getString("contentDownloaded") + pathTF.getText(),
								rb.getString("info"), JOptionPane.INFORMATION_MESSAGE);
					}
					else
					{
						JOptionPane.showMessageDialog(null,
								"<html>" + rb.getString("problemDownloadingDialog") + "</html>", rb.getString("error"),
								JOptionPane.ERROR_MESSAGE);
						appendTextArea(rb.getString("problemDownloadingTA"));
					}
				}

				protected void process(List<Integer> number)
				{
					appendTextArea(rb.getString("downloadingFilesTA") + dc.getPrefix() + number.get(number.size() - 1)
							+ "...");
					incrementProgressBar();
				}

			};
			worker.execute();
		}
		else if (dc == null)
		{
			JOptionPane.showMessageDialog(null, rb.getString("logInFirstDialog"), rb.getString("logInFirst"),
					JOptionPane.INFORMATION_MESSAGE);
		}
		else
		{
			JOptionPane.showMessageDialog(null, rb.getString("problemDownloadingDialog"), rb.getString("error"),
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void stopDo()
	{
		if (inProgress)
		{
			stop = true;
			stopBt.setEnabled(false);
			progressLb.setText(rb.getString("stoppingLb"));
			appendTextArea(rb.getString("stoppingTA"));
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
			String fileNamePattern = fileNamePatternTF.getText();
			String path = pathTF.getText();
			SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>()
			{
				boolean error = false;

				protected Void doInBackground() throws Exception
				{
					try
					{
						TableOfContents.createTableOfContents(url, path, fileNamePattern);
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
						appendTextArea(rb.getString("tableOfContentsCreated"));
						displayInfo(
								"<html>" + rb.getString("tableOfContentsCreatedTA") + pathTF.getText() + "</i></html>");
					}
					else
					{
						appendTextArea(rb.getString("cannotCreateTOCTA"));
						JOptionPane.showMessageDialog(null, rb.getString("errorContactingServer"),
								rb.getString("error"), JOptionPane.ERROR_MESSAGE);
					}
				}
			};
			worker.execute();
		}
		else
		{
			JOptionPane.showMessageDialog(null, "<html>" + rb.getString("cannotCreateTOCDialog") + "</html>",
					rb.getString("error"), JOptionPane.ERROR_MESSAGE);
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
			URL iconURL = getClass().getResource("resources/icon/" + ico + ".png");
			ImageIcon icon = new ImageIcon(iconURL);
			icons.add(icon.getImage());
		}
		this.setIconImages(icons);
	}

	protected void setDesc()
	{
		rb = RAO.getInstance();
		// Buttons init
		downloadBt.setText(rb.getString("downloadBt"));
		downloadBt.setToolTipText("<html>" + rb.getString("downloadTip") + "</html>");
		stopBt.setText(rb.getString("stopBt"));
		stopBt.setToolTipText("<html>" + rb.getString("stopTip") + "</html>");
		browseBt.setText(rb.getString("browseBt"));
		browseBt.setToolTipText(rb.getString("browseTip"));
		tableOfContentsBt.setText(rb.getString("tableOfContentsBt"));
		tableOfContentsBt.setToolTipText("<html>" + rb.getString("tableOfContentsTip") + "</html>");
		// CheckBoxes init
		attachmentsCheck.setText(rb.getString("attachmentsCheck"));
		attachmentsCheck.setToolTipText("<html>" + rb.getString("attachmentsCheckTip") + "</html>");
		attachmentsOnlyCheck.setText(rb.getString("attachmentsOnlyCheck"));
		attachmentsOnlyCheck.setToolTipText("<html>" + rb.getString("attachmentsOnlyCheckTip") + "</html>");
		// TextFields init
		urlTF.setToolTipText("<html>" + rb.getString("urlTip") + "</html>");
		fromTF.setToolTipText(rb.getString("fromTip"));
		upToTF.setToolTipText(rb.getString("upToTip"));
		fileNamePatternTF.setToolTipText("<html>" + rb.getString("fileNamePatternTip") + "</html>");
		pathTF.setToolTipText("<html>" + rb.getString("pathTip") + "</html>");
		// Labels init
		pathLb.setText(rb.getString("path"));
		pathLb.setToolTipText("<html>" + rb.getString("pathTip") + "</html>");
		urlLb.setText(rb.getString("url"));
		urlLb.setToolTipText("<html>" + rb.getString("urlTip") + "</html>");
		downloadLb.setText(rb.getString("from"));
		downloadLb.setToolTipText(rb.getString("fromTip"));
		upToLb.setText(rb.getString("to"));
		upToLb.setToolTipText(rb.getString("upToTip"));
		progressLb.setText(rb.getString("idle"));
		fileNamePatternLb.setText(rb.getString("fileNamePattern"));
		fileNamePatternLb.setToolTipText("<html>" + rb.getString("fileNamePatternTip") + "</html>");
		pack();
	}

	protected void updateDesc()
	{
		this.setDesc();
		mainMenu.setDesc();
		popup.setDesc();
		popupText.setDesc();
		statusBar.setDesc();
	}
}
