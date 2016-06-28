package caveOfProgrammingCourseContents.gui;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.JTextComponent;
import javax.swing.text.TextAction;

public class MyPopupMenuText extends JPopupMenu
{
	private static final long serialVersionUID = 1L;

	public MyPopupMenuText(MainWindow mw)
	{
		Action cut = new DefaultEditorKit.CutAction();
		cut.putValue(Action.NAME, "Cut");
		cut.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		add(cut);

		Action copy = new DefaultEditorKit.CopyAction();
		copy.putValue(Action.NAME, "Copy");
		copy.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		add(copy);

		Action paste = new DefaultEditorKit.PasteAction();
		paste.putValue(Action.NAME, "Paste");
		paste.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		add(paste);

		Action selectAll = new SelectAll();
		add(selectAll);
	}

	static class SelectAll extends TextAction
	{
		private static final long serialVersionUID = 1L;

		public SelectAll()
		{
			super("Select All");
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
