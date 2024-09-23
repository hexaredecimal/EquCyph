package equcyph;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.SwingUtilities;

/**
 *
 * @author hexaredecimal
 */
public class MouseEvents implements MouseListener{
	private JTree tree; 
	private JPopupMenu popupMenu;
	public MouseEvents(JTree tree, JPopupMenu popupMenu) {
		this.tree = tree;
		this.popupMenu = popupMenu;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			System.out.println("Here");
			int row = tree.getClosestRowForLocation(e.getX(), e.getY());
			tree.setSelectionRow(row);
			popupMenu.show(e.getComponent(), e.getX(), e.getY());
		}
	}

	@Override
	public void mousePressed(MouseEvent me) {
	}

	@Override
	public void mouseReleased(MouseEvent me) {
	}

	@Override
	public void mouseEntered(MouseEvent me) {
	}

	@Override
	public void mouseExited(MouseEvent me) {
	}
	
}
