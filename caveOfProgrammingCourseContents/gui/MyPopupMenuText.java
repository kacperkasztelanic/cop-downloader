package caveOfProgrammingCourseContents.gui;

import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

import javax.swing.Action;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.JTextComponent;
import javax.swing.text.TextAction;

public class MyPopupMenuText extends JPopupMenu
{
	private static final long serialVersionUID = 1L;
	private ResourceBundle rb;
	private Action cut = new DefaultEditorKit.CutAction();
	private Action copy = new DefaultEditorKit.CopyAction();
	private Action paste = new DefaultEditorKit.PasteAction();

	public MyPopupMenuText(MainWindow mw)
	{
		setDesc();

		cut.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		add(cut);

		copy.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		add(copy);

		paste.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		add(paste);

		add(new SelectAll());
	}

	protected void setDesc()
	{
		rb = RAO.getInstance();
		cut.putValue(Action.NAME, rb.getString("cut"));
		copy.putValue(Action.NAME, rb.getString("copy"));
		paste.putValue(Action.NAME, rb.getString("paste"));
		if (this.getComponentCount() > 3)
		{
			remove(3);
			add(new SelectAll());
		}
	}

	private class SelectAll extends TextAction
	{
		private static final long serialVersionUID = 1L;

		public SelectAll()
		{
			super(RAO.getInstance().getString("selectAll"));
			putValue(Action.NAME, rb.getString("selectAll"));
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control A"));
		}

		public void actionPerformed(ActionEvent e)
		{
			JTextComponent component = getFocusedComponent();
			component.selectAll();
			component.requestFocusInWindow();
		}
	}
}
