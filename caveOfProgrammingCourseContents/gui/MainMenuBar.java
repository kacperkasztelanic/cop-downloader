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
	private ResourceBundle rb;
	private JMenu fileM = new JMenu();
	private JMenu settsM = new JMenu();
	private JMenu infoM = new JMenu();
	private JMenu lafM = new JMenu();
	private JMenu langM = new JMenu();
	private JCheckBoxMenuItem windowsMI = new JCheckBoxMenuItem();
	private JCheckBoxMenuItem metalMI = new JCheckBoxMenuItem();
	private JCheckBoxMenuItem nimbusMI = new JCheckBoxMenuItem();
	private JCheckBoxMenuItem defaultMI = new JCheckBoxMenuItem();
	private ButtonGroup langBG = new ButtonGroup();
	private JRadioButtonMenuItem englishMI = new JRadioButtonMenuItem();
	private JRadioButtonMenuItem polishMI = new JRadioButtonMenuItem();
	private JMenuItem aboutMI = new JMenuItem();
	private JMenuItem loginMI = new JMenuItem();
	private JMenuItem logoutMI = new JMenuItem();
	private JMenuItem quitMI = new JMenuItem();
	private JMenuItem saveLogMI = new JMenuItem();
	private MainWindow mw;
	protected LoginWindow lg;
	protected AboutWindow aw;

	protected void setDesc()
	{
		rb = RAO.getInstance();
		fileM.setText(rb.getString("fileM"));
		settsM.setText(rb.getString("settingsM"));
		langM.setText(rb.getString("languageM"));
		lafM.setText(rb.getString("lAndFM"));
		infoM.setText(rb.getString("infoM"));
		defaultMI.setText(rb.getString("defaultMI"));
		windowsMI.setText("Windows");
		nimbusMI.setText("Nimbus");
		metalMI.setText("Metal");
		englishMI.setText(rb.getString("englishMI"));
		polishMI.setText(rb.getString("polishMI"));
		aboutMI.setText(rb.getString("aboutMI"));
		loginMI.setText(rb.getString("loginMI"));
		logoutMI.setText(rb.getString("logoutMI"));
		saveLogMI.setText(rb.getString("saveLogMI"));
		quitMI.setText(rb.getString("quitMI"));
	}

	public MainMenuBar(MainWindow mw)
	{
		super();
		this.mw = mw;

		setDesc();

		defaultMI.setSelected(true);
		englishMI.setSelected(Locale.getDefault().getLanguage().equals(Locale.forLanguageTag("en").getLanguage()));
		polishMI.setSelected(Locale.getDefault().getLanguage().equals(Locale.forLanguageTag("pl").getLanguage()));

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
				JOptionPane.showMessageDialog(null, aw = new AboutWindow((JFrame) getRootPane().getParent()),
						rb.getString("aboutMI"), JOptionPane.PLAIN_MESSAGE);
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
