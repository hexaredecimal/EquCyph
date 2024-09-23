package equcyph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class EquCyph extends JFrame implements ActionListener {

	private Plane plane;
	private JTree functions_tree;
	private LinkedList<String> plotted = new LinkedList<>();
	private LinkedList<Color> plottedColors = new LinkedList<>();
	private JPopupMenu popupMenu;

	private void addMenus() {
		JMenuBar menubar = new JMenuBar();
		setJMenuBar(menubar);

		JMenu file = new JMenu("File");
		menubar.add(file);

		JMenuItem newSession = new JMenuItem("New Session");
		newSession.addActionListener(this);

		JMenuItem openSession = new JMenuItem("Open Session");
		openSession.addActionListener(this);

		JMenuItem saveSession = new JMenuItem("Save Session");
		saveSession.addActionListener(this);

		JMenuItem saveSessionAs = new JMenuItem("Save Session As");
		saveSessionAs.addActionListener(this);

		JMenu recent = new JMenu("Recent");

		JMenuItem clearGraph = new JMenuItem("Clear Graph");
		clearGraph.addActionListener(this);

		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(this);

		file.add(newSession);
		file.add(openSession);
		file.addSeparator();
		file.add(saveSession);
		file.add(saveSessionAs);
		file.addSeparator();
		file.add(recent);
		file.addSeparator();
		file.add(clearGraph);
		file.add(exit);

		JMenu tools = new JMenu("Tools");
		menubar.add(tools);

		String[] graphtypes = {"Advanced", "Cubic", "Hyperbolic", "Line", "Parabolic"};
		for (String type : graphtypes) {
			JMenuItem graphtype = new JMenuItem(type);
			graphtype.addActionListener(this);
			tools.add(graphtype);
		}

		JMenu help = new JMenu("Help");
		menubar.add(help);

		String[] helpitems = {"Contents", "Website", "_", "License", "Contributors", "_", "About"};
		for (String item : helpitems) {
			if (item.equals("_")) {
				help.addSeparator();
				continue;
			}

			JMenuItem help_item = new JMenuItem(item);
			help_item.addActionListener(this);
			help.add(help_item);
		}
	}

	private void addPopUpMenus() {
		popupMenu = new JPopupMenu("Options");
		
		JMenuItem remove = new JMenuItem("Info");
		JMenuItem edit = new JMenuItem("Delete");
		JMenuItem props = new JMenuItem("Properties");

		JMenu new_fx = new JMenu("new");

		String[] graphtypes = {"Advanced", "Cubic", "Hyperbolic", "Line", "Parabolic"};
		for (String type : graphtypes) {
			JMenuItem graphtype = new JMenuItem(type);
			graphtype.addActionListener(this);
			new_fx.add(graphtype);
		}
		
		popupMenu.add(new_fx);
		popupMenu.add(new JSeparator());
		popupMenu.add(remove);
		popupMenu.add(new JSeparator());
		popupMenu.add(edit);
		popupMenu.add(new JSeparator());
		popupMenu.add(props);
	}
	
	public void plotFunction(String name, String equation, Color style) {
		Function f = new Function(equation, name);
		f.setColor(style);
		plane.plot(f);
		populateTree();
	}

	public EquCyph() {
		super("EquCyph");
		setLookAndFeel();
		addMenus();
		addPopUpMenus();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900, 600);

		plane = new Plane();

		functions_tree = new JTree();
		functions_tree.addMouseListener(
			new MouseEvents(functions_tree, popupMenu)
		);
		
		

		
		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		split.add(functions_tree);
		split.add(plane);
		add(split);

		plane.setScaleInX(1);
		plane.setScaleInY(1);

		DefaultTreeModel model = (DefaultTreeModel) functions_tree.getModel();
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("functions");
		top.removeAllChildren();
		model.reload();
		model.setRoot(top);

		// Enable grid in plane
		plane.setShowGrid(true);

		setLocationRelativeTo(null);
		setVisible(true);
	}

	public final void populateTree() {
		//tree.removeAll();
		DefaultTreeModel model = (DefaultTreeModel) functions_tree.getModel();
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("functions");
		top.removeAllChildren();
		model.reload();
		model.setRoot(top);

		var functions = plane.getFunctionList();
		
		
		for (var fx: functions) {
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(fx.getName() + "=" + fx.getDefinition());
			top.add(node);
		}
	}

  private void setLookAndFeel() {
    try {
      for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          javax.swing.UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
      java.util.logging.Logger.getLogger(EquCyph.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
  }


	public static void main(String[] args) {
		new EquCyph();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String invoker = e.getActionCommand();

		switch (invoker) {
			case "Advanced":
				Advanced adv = new Advanced(this);
				adv.setVisible(true);
				break;

			case "Contributors": {

				String message = 
"""
<html>
	<h1 align="center">Equcyph</h1>
	<h3>Lead developer</h3>
	<p><b>Hexaredecimal</b></p>
	<br>
	<h3>Contributors</h3>
	<p><b>Hugs_Coding</b></p>
</html>
""";
				JOptionPane.showMessageDialog(this, message, invoker, JOptionPane.INFORMATION_MESSAGE);
			}
			break;
			case "About": {
				String message = "<html>EquCyph - The Cross-platform Mathemetics equation parser and plotter<br>";
				JOptionPane.showMessageDialog(this, message, invoker, JOptionPane.INFORMATION_MESSAGE);
			}
			break;
			case "License":
				JOptionPane.showMessageDialog(this, License.contents, invoker, JOptionPane.INFORMATION_MESSAGE);
				break;
			case "Line":
				LineGraph lng = new LineGraph(this);
				lng.setVisible(true);
				break;
			case "Hyperbolic":
				Hyperbolic hp = new Hyperbolic(this);
				hp.setVisible(true);
			case "Parabolic":
				Parabolic prb = new Parabolic(this);
				prb.setVisible(true);
				break;
			default:
				break;
		}
	}
}
