package caveOfProgrammingCourseContents.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MainMenuBar extends JMenuBar
{
	private static final long serialVersionUID = 1L;
	private JMenu fileM;
	private JMenu lafM;
	private JMenu infoM;
	private JCheckBoxMenuItem windowsMI;;
	private JCheckBoxMenuItem metalMI;;
	private JCheckBoxMenuItem nimbusMI;
	private JCheckBoxMenuItem defaultMI;
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
		fileM = new JMenu("File");
		lafM = new JMenu("L&F");
		infoM = new JMenu(" ? ");
		defaultMI = new JCheckBoxMenuItem("Default");
		defaultMI.setSelected(true);
		windowsMI = new JCheckBoxMenuItem("Windows");
		nimbusMI = new JCheckBoxMenuItem("Nimbus");
		metalMI = new JCheckBoxMenuItem("Metal");
		aboutMI = new JMenuItem("About");
		loginMI = new JMenuItem("Login");
		logoutMI = new JMenuItem("Logout");
		saveLogMI = new JMenuItem("Save log");
		quitMI = new JMenuItem("Quit");
		infoM.add(aboutMI);
		lafM.add(defaultMI);
		lafM.addSeparator();
		lafM.add(windowsMI);
		lafM.add(metalMI);
		lafM.add(nimbusMI);
		fileM.add(loginMI);
		fileM.add(logoutMI);
		fileM.addSeparator();
		fileM.add(saveLogMI);
		fileM.add(quitMI);
		this.add(fileM);
		this.add(lafM);
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
						JOptionPane.showMessageDialog(null, "You have been successfully logged out.", "Info",
								JOptionPane.INFORMATION_MESSAGE);
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Error contacting server.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
				else
					JOptionPane.showMessageDialog(null, "You are not logged-in.", "Info",
							JOptionPane.INFORMATION_MESSAGE);

			}
			catch (IOException e1)
			{
				JOptionPane.showMessageDialog(null, "Error contacting server.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, "You are not logged-in.", "Info", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
