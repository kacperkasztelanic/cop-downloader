package caveOfProgrammingCourseContents.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

import caveOfProgrammingCourseContents.mechanics.DownloadContents;

public class LoginWindow extends JDialog
{
	private static final long serialVersionUID = 1L;
	private JTextField usernameTF;
	private JPasswordField passwordPF;
	private JButton loginBt;
	private JButton cancelBt;
	private JCheckBox showPasswordCheck;
	private JLabel usernameLb;
	private JLabel passwordLb;
	private MainWindow mw;

	public LoginWindow(Frame parent)
	{
		super(parent, "Login", true);
		this.mw = (MainWindow) parent;
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		setLocationRelativeTo(parent);

		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBorder(new EmptyBorder(10, 10, 5, 10));

		GridBagConstraints cs = new GridBagConstraints();

		cs.insets = new Insets(3, 3, 3, 3);

		usernameLb = new JLabel("Username: ");
		cs.gridx = 0;
		cs.gridy = 0;
		cs.gridwidth = 1;
		panel.add(usernameLb, cs);

		usernameTF = new JTextField(20);
		cs.gridx = 1;
		cs.gridy = 0;
		cs.gridwidth = 2;
		panel.add(usernameTF, cs);

		passwordLb = new JLabel("Password: ");
		cs.gridx = 0;
		cs.gridy = 1;
		cs.gridwidth = 1;
		panel.add(passwordLb, cs);

		passwordPF = new JPasswordField(20);
		cs.gridx = 1;
		cs.gridy = 1;
		cs.gridwidth = 2;
		panel.add(passwordPF, cs);

		loginBt = new JButton("Login");
		loginBt.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>()
				{
					boolean error = false;
					String username = getUsername();
					String password = getPassword();

					protected Boolean doInBackground() throws Exception
					{
						try
						{
							mw.dc = new DownloadContents();
							mw.dc.getSession(username, password);
							return mw.dc.isLoggedInCorrectly();
						}
						catch (IOException e1)
						{
							error = true;
						}
						return false;
					}

					protected void done()
					{
						try
						{
							if (get())
							{
								mw.statusBar.showLogged();
								mw.setDownloadBtAccessible(true);
								mw.appendTextArea("Successfully logged in as: " + username + ".");
								JOptionPane.showMessageDialog(null, "Logged in correctly.", "Info",
										JOptionPane.INFORMATION_MESSAGE);
								dispose();
							}
							else
							{
								mw.statusBar.hideLogged();
								mw.setDownloadBtAccessible(false);
								passwordPF.setText("");
								JOptionPane.showMessageDialog(null, "Invalid username or password.",
										"Invalid log-in credentials.", JOptionPane.ERROR_MESSAGE);
							}
						}
						catch (HeadlessException | InterruptedException | ExecutionException e)
						{
							JOptionPane.showMessageDialog(null, "Error contacting server.", "Error",
									JOptionPane.ERROR_MESSAGE);
						}
						if (error)
							JOptionPane.showMessageDialog(null, "Error contacting server.", "Error",
									JOptionPane.ERROR_MESSAGE);
					}

				};
				worker.execute();
			}
		});
		cancelBt = new JButton("Cancel");
		cancelBt.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				dispose();
			}
		});

		showPasswordCheck = new JCheckBox("Show password");
		showPasswordCheck.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e)
			{
				if (e.getStateChange() == ItemEvent.SELECTED)
					passwordPF.setEchoChar((char) 0);
				else
					passwordPF.setEchoChar('*');
			}
		});

		JPanel southPanel = new JPanel(new FlowLayout());
		southPanel.setBorder(new EmptyBorder(0, 0, 5, 0));
		southPanel.add(loginBt);
		southPanel.add(cancelBt);
		southPanel.add(showPasswordCheck);

		getRootPane().setDefaultButton(loginBt);
		this.add(panel, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);
		this.pack();
		this.setVisible(true);
	}

	public String getUsername()
	{
		return usernameTF.getText().trim();
	}

	public String getPassword()
	{
		return new String(passwordPF.getPassword());
	}
}