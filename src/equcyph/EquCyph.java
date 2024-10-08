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
	private JPopupMenu functionNodePopUp;
	private JPopupMenu functionParent;
	private static DefaultMutableTreeNode selection = null;

	public static void setTreeSelection(DefaultMutableTreeNode node) {
		selection = node;
	}
	
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
		clearGraph.addActionListener(action -> {
			plane.getFunctionList().clear();
			plane.repaint();
			populateTree();
		});

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
		functionNodePopUp = new JPopupMenu("Options");
		functionParent = new JPopupMenu("All functions");

		JMenuItem info = new JMenuItem("Info");
		JMenuItem delete = new JMenuItem("Delete");
		delete.addActionListener(action -> {
			if (selection == null)
				return; 

			String fx_def = selection.toString(); 
			var fx_list = plane.getFunctionList();
			for (var func: fx_list) {
				if (!fx_def.equals(func.toString())) {
					continue;
				}
				
				fx_list.remove(func);
				DefaultTreeModel model = (DefaultTreeModel) functions_tree.getModel();
				DefaultMutableTreeNode top = (DefaultMutableTreeNode) model.getRoot();
				top.remove(selection);
				plane.repaint();
				populateTree();
			}
			
		});
		
		
		JMenuItem deleteAll = new JMenuItem("Delete All");
		deleteAll.addActionListener(action -> {
			plane.getFunctionList().clear();
			plane.repaint();
			populateTree();
		});

		JMenuItem props = new JMenuItem("Properties");
		props.addActionListener(action -> {
			if (selection == null)
				return; 

			String fx_def = selection.toString(); 
			var fx_list = plane.getFunctionList();
			for (int index = 0; index < fx_list.size(); index++) {
				var func = fx_list.get(index);
				if (!fx_def.equals(func.toString())) {
					continue;
				}
				
				new Properties(this, plane, index, func)
					.setVisible(true);
			}
		});


		JMenu new_fx = new JMenu("new");

		String[] graphtypes = {"Cubic", "Hyperbolic", "Line", "Parabolic", "_", "Advanced"};
		for (String type : graphtypes) {
			if (type.equals("_")) {
				new_fx.add(new JSeparator());
				continue;
			}
			JMenuItem graphtype = new JMenuItem(type);
			graphtype.addActionListener(this);
			new_fx.add(graphtype);
		}

		functionParent.add(new_fx);
		functionParent.add(deleteAll);

		functionNodePopUp.add(info);
		functionNodePopUp.add(new JSeparator());
		functionNodePopUp.add(delete);
		functionNodePopUp.add(new JSeparator());
		functionNodePopUp.add(props);
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
		functions_tree.addMouseListener(new MouseEvents(functions_tree, functionNodePopUp, functionParent));

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
		DefaultTreeModel model = (DefaultTreeModel) functions_tree.getModel();
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("functions");
		top.removeAllChildren();
		model.reload();
		model.setRoot(top);

		var functions = plane.getFunctionList();

		for (var fx : functions) {
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(fx.getName() + " = " + fx.getDefinition());
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

				String message
					= 
"""
<html>
	<h1 align="center">Equcyph</h1>
	<h3>Lead developer</h3>
	<p><b>Hexaredecimal</b></p>
	<br>
	<h3>Contributors</h3>
	<p><b>Hugs_Coding</b></p>
</htaml>
""";
				JOptionPane.showMessageDialog(this, message, invoker, JOptionPane.INFORMATION_MESSAGE);
			}
			break;
			case "About": {
				String art = 
"""
<br>
<p>   Y<p>
<p>   ^</p>
<p>   |                ^</p>
<p>   |               /</p>
<p>2  |     +     /</p>
<p>   |	     / \\   / </p>
<p>   |	    /   \\ /  </p>
<p>1  |   /     +    </p>
<p>   |  /					</p>
<p>   | *</p>
<p>   +-----------------------------------> X</p> 
<p>   0          1        2         3 <p><br>
<h4> Version <b>2.0 - 2024 Edition</b></h4>
""".replaceAll(" ", "&nbsp;");
				String message = String.format("<html>EquCyph - The Cross-platform Mathemetics <br>equation parser and plotter<br>%s</html>", art);
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
			case "Cubic":
				Cubic cbc = new Cubic(this);
				cbc.setVisible(true);
				break;
			case "Exit":
				System.exit(0);
				break;
			default:
				break;
		}
	}
}
