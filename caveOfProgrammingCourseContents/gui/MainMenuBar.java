package caveOfProgrammingCourseContents.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MainMenuBar extends JMenuBar
{
	private static final long serialVersionUID = 1L;
	private ResourceBundle rb = RAO.getInstance();
	private JMenu fileM;
	private JMenu settsM;
	private JMenu infoM;
	private JMenu lafM;
	private JMenu langM;
	private JCheckBoxMenuItem windowsMI;;
	private JCheckBoxMenuItem metalMI;;
	private JCheckBoxMenuItem nimbusMI;
	private JCheckBoxMenuItem defaultMI;
	private ButtonGroup langBG;
	private JRadioButtonMenuItem englishMI;
	private JRadioButtonMenuItem polishMI;
	private JMenuItem aboutMI;
	private JMenuItem loginMI;
	private JMenuItem logoutMI;
	private JMenuItem quitMI;
	private JMenuItem saveLogMI;
	private MainWindow mw;
	protected LoginWindow lg;
	protected AboutWindow aw;

	public MainMenuBar(MainWindow mw)
	{
		super();
		this.mw = mw;
		fileM = new JMenu(rb.getString("fileM"));
		settsM = new JMenu(rb.getString("settingsM"));
		langM = new JMenu(rb.getString("languageM"));
		lafM = new JMenu(rb.getString("lAndFM"));
		infoM = new JMenu(rb.getString("infoM"));
		defaultMI = new JCheckBoxMenuItem(rb.getString("defaultMI"));
		defaultMI.setSelected(true);
		windowsMI = new JCheckBoxMenuItem("Windows");
		nimbusMI = new JCheckBoxMenuItem("Nimbus");
		metalMI = new JCheckBoxMenuItem("Metal");
		englishMI = new JRadioButtonMenuItem(rb.getString("englishMI"));
		polishMI = new JRadioButtonMenuItem(rb.getString("polishMI"));
		englishMI.setSelected(Locale.getDefault().getLanguage().equals(Locale.forLanguageTag("en").getLanguage()));
		polishMI.setSelected(Locale.getDefault().getLanguage().equals(Locale.forLanguageTag("pl").getLanguage()));
		aboutMI = new JMenuItem(rb.getString("aboutMI"));
		loginMI = new JMenuItem(rb.getString("loginMI"));
		logoutMI = new JMenuItem(rb.getString("logoutMI"));
		saveLogMI = new JMenuItem(rb.getString("saveLogMI"));
		quitMI = new JMenuItem(rb.getString("quitMI"));
		langBG = new ButtonGroup();
		infoM.add(aboutMI);
		lafM.add(defaultMI);
		lafM.addSeparator();
		lafM.add(windowsMI);
		lafM.add(metalMI);
		lafM.add(nimbusMI);
		langBG.add(englishMI);
		langBG.add(polishMI);
		langM.add(englishMI);
		langM.add(polishMI);
		settsM.add(lafM);
		settsM.add(langM);
		fileM.add(loginMI);
		fileM.add(logoutMI);
		fileM.addSeparator();
		fileM.add(saveLogMI);
		fileM.add(quitMI);
		this.add(fileM);
		this.add(settsM);
		this.add(infoM);

		loginMI.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				lg = new LoginWindow((JFrame) getRootPane().getParent());
			}
		});

		logoutMI.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				logOutDo();
			}
		});

		saveLogMI.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				mw.saveLog("CoPDownloaderLog.txt");
			}
		});

		quitMI.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});

		aboutMI.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane.showMessageDialog(null, aw = new AboutWindow((JFrame) getRootPane().getParent()), "About",
						JOptionPane.PLAIN_MESSAGE);
			}
		});

		defaultMI.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					SwingUtilities.updateComponentTreeUI(mw.getRootPane());
					mw.pack();
					unselectCheckBoxMenuItems();
					defaultMI.setSelected(true);
				}
				catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e1)
				{
					e1.printStackTrace();
				}

			}
		});

		windowsMI.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					SwingUtilities.updateComponentTreeUI(mw.getRootPane());
					mw.pack();
					unselectCheckBoxMenuItems();
					windowsMI.setSelected(true);
				}
				catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e1)
				{
					e1.printStackTrace();
				}
			}
		});

		metalMI.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
					SwingUtilities.updateComponentTreeUI(mw.getRootPane());
					mw.pack();
					unselectCheckBoxMenuItems();
					metalMI.setSelected(true);
				}
				catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e1)
				{
					e1.printStackTrace();
				}
			}
		});

		nimbusMI.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
					SwingUtilities.updateComponentTreeUI(mw.getRootPane());
					mw.pack();
					unselectCheckBoxMenuItems();
					nimbusMI.setSelected(true);
				}
				catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e1)
				{
					e1.printStackTrace();
				}
			}
		});

		englishMI.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				setCurrentLocale(Locale.forLanguageTag("en"));
			}
		});

		polishMI.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				setCurrentLocale(Locale.forLanguageTag("pl"));
			}
		});
	}

	private void setCurrentLocale(Locale locale)
	{
		RAO.makeInstanceNull();
		Locale.setDefault(locale);
		mw.updateDesc();
	}

	private void unselectCheckBoxMenuItems()
	{
		defaultMI.setSelected(false);
		windowsMI.setSelected(false);
		nimbusMI.setSelected(false);
		metalMI.setSelected(false);
	}

	private void logOutDo()
	{
		if (mw.dc != null)
		{
			try
			{
				if (mw.dc.isLoggedInCorrectly())
				{
					if (mw.dc.signOut())
					{
						mw.statusBar.hideLogged();
						mw.setDownloadBtAccessible(false);
						mw.appendTextArea("Logged out.");
						JOptionPane.showMessageDialog(null, rb.getString("loggedOutDialog"), rb.getString("info"),
								JOptionPane.INFORMATION_MESSAGE);
					}
					else
					{
						JOptionPane.showMessageDialog(null, rb.getString("errorContactingServer"),
								rb.getString("error"), JOptionPane.ERROR_MESSAGE);
					}
				}
				else
					JOptionPane.showMessageDialog(null, rb.getString("notLoggedInDialog"), rb.getString("info"),
							JOptionPane.INFORMATION_MESSAGE);

			}
			catch (IOException e1)
			{
				JOptionPane.showMessageDialog(null, rb.getString("errorContactingServer"), rb.getString("error"),
						JOptionPane.ERROR_MESSAGE);
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, rb.getString("notLoggedInDialog"), rb.getString("info"),
					JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
