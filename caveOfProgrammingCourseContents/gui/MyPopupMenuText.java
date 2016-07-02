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
	private ResourceBundle rb = RAO.getInstance();

	public MyPopupMenuText(MainWindow mw)
	{
		Action cut = new DefaultEditorKit.CutAction();
		cut.putValue(Action.NAME, rb.getString("cut"));
		cut.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		add(cut);

		Action copy = new DefaultEditorKit.CopyAction();
		copy.putValue(Action.NAME, rb.getString("copy"));
		copy.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		add(copy);

		Action paste = new DefaultEditorKit.PasteAction();
		paste.putValue(Action.NAME, rb.getString("paste"));
		paste.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		add(paste);

		Action selectAll = new SelectAll();
		add(selectAll);
	}

	static class SelectAll extends TextAction
	{
		private static final long serialVersionUID = 1L;
		private static ResourceBundle rb = ResourceBundle.getBundle("text");

		public SelectAll()
		{
			super(rb.getString("selectAll"));
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
