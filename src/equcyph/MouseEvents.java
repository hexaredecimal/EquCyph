package equcyph;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author hexaredecimal
 */
public class MouseEvents implements MouseListener {

	private JTree tree;
	private JPopupMenu functionNode, functionsParent;

	public MouseEvents(JTree tree, JPopupMenu popupMenu, JPopupMenu parent) {
		this.tree = tree;
		this.functionNode = popupMenu;
		this.functionsParent = parent;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {

			// Get the tree path for the clicked location
			TreePath path = tree.getPathForLocation(e.getX(), e.getY());

			if (path != null) {
				// Get the clicked node
				DefaultMutableTreeNode clickedNode = (DefaultMutableTreeNode) path.getLastPathComponent();

				// Check if it's a child node and its parent's text is "functions"
				DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) clickedNode.getParent();
				if (parentNode != null && "functions".equals(parentNode.toString())) {
					// Set the selected row and show the popup menu
					int row = tree.getClosestRowForLocation(e.getX(), e.getY());
					EquCyph.setTreeSelection(clickedNode);
					tree.setSelectionRow(row);
					functionNode.show(e.getComponent(), e.getX(), e.getY());
				} else {
					int row = tree.getClosestRowForLocation(e.getX(), e.getY());
					tree.setSelectionRow(row);
					functionsParent.show(e.getComponent(), e.getX(), e.getY());
				}
			}
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
