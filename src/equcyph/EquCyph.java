package equcyph;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class EquCyph extends JFrame implements ActionListener {

	private HashMap<Integer, Plane> tabPlanes;
	private JTree functions_tree;
	private LinkedList<String> plotted = new LinkedList<>();
	private LinkedList<Color> plottedColors = new LinkedList<>();
	private JPopupMenu functionNodePopUp = new JPopupMenu("Options");
	private JPopupMenu functionParent = new JPopupMenu("All functions");
	private JTabbedPane tabs;
	private static DefaultMutableTreeNode selection = null;

	public static void setTreeSelection(DefaultMutableTreeNode node) {
		selection = node;
	}

	public HashMap<Integer, Plane> getTabPlanes() {
		return tabPlanes;
	}

	private void addMenus() {
		try {
			var save_icon = new ImageIcon(this.getClass().getResource("/icons/save-16.png"));
			var save_as_icon = new ImageIcon(this.getClass().getResource("/icons/save-as-16.png"));
			var exit_icon = new ImageIcon(this.getClass().getResource("/icons/exit-16.png"));
			var open_icon = new ImageIcon(this.getClass().getResource("/icons/open-file-16.png"));
			var project_icon = new ImageIcon(this.getClass().getResource("/icons/project-16.png"));
			var clean_icon = new ImageIcon(this.getClass().getResource("/icons/clean-16.png"));
			var formula_icon = new ImageIcon(this.getClass().getResource("/icons/formula-fx-16.png"));
			var lambda_icon = new ImageIcon(this.getClass().getResource("/icons/lambda-16.png"));
			var add_tab_icon = new ImageIcon(this.getClass().getResource("/icons/add-tab-16.png"));
			var close_tab_icon = new ImageIcon(this.getClass().getResource("/icons/close-tab-16.png"));

			var about_icon = new ImageIcon(this.getClass().getResource("/icons/about-16.png"));
			var website_icon = new ImageIcon(this.getClass().getResource("/icons/website-16.png"));
			var book_icon = new ImageIcon(this.getClass().getResource("/icons/book-16.png"));
			var more_info_icon = new ImageIcon(this.getClass().getResource("/icons/more-info-16.png"));
			var software_license_icon = new ImageIcon(this.getClass().getResource("/icons/software-license-16.png"));

			JMenuBar menubar = new JMenuBar();
			setJMenuBar(menubar);

			JMenu file = new JMenu("File");
			menubar.add(file);

			JMenuItem newSession = new JMenuItem("New Session", project_icon);
			newSession.addActionListener(action -> {
				var root = functions_tree.getModel().getRoot(); 
				int child_count = functions_tree.getModel().getChildCount(root);
				
				if (child_count > 0) {
					String msg = String.format(
						"Are you sure you want to clear %d function%s and start a new project?",
						child_count, 
						child_count == 1 ? "" : "s");

					int option = JOptionPane.showConfirmDialog(this, msg, "Clear All", JOptionPane.YES_NO_OPTION);
					if (option == JOptionPane.NO_OPTION) {
						return; 
					}
				}
				
				var keys = tabPlanes.keySet(); 
				for (var key: keys) {
					var plane = tabPlanes.get(key); 
					plane.getFunctionList().clear();
				}
				tabs.removeAll();
				addTab(tabs);
				populateTree();
			});

			JMenuItem openSession = new JMenuItem("Open Session", open_icon);
			openSession.addActionListener(this);

			JMenuItem saveSession = new JMenuItem("Save Session", save_icon);
			saveSession.addActionListener(this);

			JMenuItem saveSessionAs = new JMenuItem("Save Session As", save_as_icon);
			saveSessionAs.addActionListener(this);

			JMenu recent = new JMenu("Recent");

			JMenuItem clearGraph = new JMenuItem("Clear Graph", clean_icon);
			clearGraph.addActionListener(action -> {
				int selectedIndex = tabs.getSelectedIndex();
				Plane plane = tabPlanes.get(selectedIndex);
				plane.getFunctionList().clear();
				plane.repaint();
				populateTree();
			});

			JMenuItem exit = new JMenuItem("Exit", exit_icon);
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

			JMenu graphs = new JMenu("Graphs");
			menubar.add(graphs);

			String[] graphtypes = {"Advanced", "Cubic", "Hyperbolic", "Line", "Parabolic"};
			for (String type : graphtypes) {
				JMenuItem graphtype = new JMenuItem(type, type.equals("Advanced") ? lambda_icon : formula_icon);
				graphtype.addActionListener(this);
				graphs.add(graphtype);
			}

			JMenu layout = new JMenu("Layout");
			menubar.add(layout);

			JMenuItem addtab = new JMenuItem("New Tab", add_tab_icon);
			addtab.addActionListener(action -> {
				addTab(tabs);
			});
			layout.add(addtab);

			JMenuItem removeTab = new JMenuItem("Remove Tab", close_tab_icon);
			removeTab.addActionListener(action -> {
				int i = tabs.getSelectedIndex();
				if (i != -1) {
					tabPlanes.remove(i);
					tabs.remove(i);
				}
			});
			layout.add(removeTab);

			JMenu themes = new JMenu("Themes");
			JMenuItem light = new JMenuItem("Light mode");
			light.addActionListener(action -> {
				FlatLightLaf.setup();
				FlatLaf.updateUI();
			});

			JMenuItem dark = new JMenuItem("Dark mode");
			dark.addActionListener(action -> {
				FlatDarkLaf.setup();
				FlatLaf.updateUI();
			});

			themes.add(dark);
			themes.add(light);
			layout.add(themes);

			JMenu help = new JMenu("Help");
			menubar.add(help);

			String[] helpitems = {"Contents", "Website", "_", "License", "Contributors", "_", "About"};
			for (String item : helpitems) {
				if (item.equals("_")) {
					help.addSeparator();
					continue;
				}

				JMenuItem help_item = new JMenuItem(item);
				if (item.equals("Contents")) {
					help_item.setIcon(book_icon);
				} else if (item.equals("Website")) {
					help_item.setIcon(website_icon);
				} else if (item.equals("License")) {
					help_item.setIcon(software_license_icon);
				} else if (item.equals("Contributors")) {
					help_item.setIcon(more_info_icon);
				} else if (item.equals("About")) {
					help_item.setIcon(about_icon);
				}
				help_item.addActionListener(this);
				help.add(help_item);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void addPopUpMenus() {
		var formula_icon = new ImageIcon(this.getClass().getResource("/icons/formula-fx-16.png"));
		var lambda_icon = new ImageIcon(this.getClass().getResource("/icons/lambda-16.png"));
		JMenuItem info = new JMenuItem("Info");
		JMenuItem delete = new JMenuItem("Delete");
		delete.addActionListener(action -> {
			if (selection == null) {
				return;
			}

			String fx_def = selection.toString();
			out:
			for (var key : tabPlanes.keySet()) {
				Plane pl = tabPlanes.get(key);
				var functions = pl.getFunctionList();
				for (int i = 0; i < functions.size(); i++) {
					var func = functions.get(i);
					if (!fx_def.equals(func.toString())) {
						continue;
					}
					System.out.println("YYYY");
					functions.remove(func);
					DefaultTreeModel model = (DefaultTreeModel) functions_tree.getModel();
					DefaultMutableTreeNode top = (DefaultMutableTreeNode) model.getRoot();
					top.remove(selection);
					pl.repaint();
					populateTree();
					break out;
				}
			}

		});

		JMenuItem deleteAll = new JMenuItem("Delete All");
		deleteAll.addActionListener(action -> {
			for (var key : tabPlanes.keySet()) {
				Plane pl = tabPlanes.get(key);
				pl.getFunctionList().clear();
				pl.repaint();
			}
			populateTree();
		});

		JMenuItem props = new JMenuItem("Properties");
		props.addActionListener(action -> {
			if (selection == null) {
				return;
			}

			String fx_def = selection.toString();
			out:
			for (var key : tabPlanes.keySet()) {
				Plane pl = tabPlanes.get(key);
				var fx_list = pl.getFunctionList();
				for (int index = 0; index < fx_list.size(); index++) {
					var func = fx_list.get(index);
					if (!fx_def.equals(func.toString())) {
						continue;
					}

					new Properties(this, pl, index, func)
						.setVisible(true);
					break out;
				}
			}
		});

		JMenu new_fx = new JMenu("new");

		String[] graphtypes = {"Cubic", "Hyperbolic", "Line", "Parabolic", "_", "Advanced"};
		for (String type : graphtypes) {
			if (type.equals("_")) {
				new_fx.add(new JSeparator());
				continue;
			}
			JMenuItem graphtype = new JMenuItem(type, type.equals("Advanced") ? lambda_icon : formula_icon);
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
		int selectedIndex = tabs.getSelectedIndex();

		if (selectedIndex == -1) {
			addTab(tabs);
		}
		selectedIndex = tabs.getSelectedIndex();

		Plane plane = tabPlanes.get(selectedIndex);
		Function f = new Function(equation, name);
		f.setColor(style);
		plane.plot(f);
		populateTree();
	}

	public EquCyph() {
		super("EquCyph");
		setLookAndFeel();
		Image icon = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/icons/EqucyphIcon.png"));
		this.setIconImage(icon);
		tabPlanes = new HashMap<>();  // Initialize the HashMap

		functions_tree = new JTree();
		functions_tree.addMouseListener(new MouseEvents(functions_tree, functionNodePopUp, functionParent));

		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		split.add(functions_tree);

		tabs = new JTabbedPane();
		tabs.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		// Add multiple tabs, each with its own Plane
		addTab(tabs);

		tabs.addChangeListener(e -> {
			int selectedIndex = tabs.getSelectedIndex();
			System.out.println("Tab: " + selectedIndex);
			System.out.println("" + tabPlanes);
			//Plane selectedPlane = tabPlanes.get(selectedIndex);
			//split.setRightComponent(selectedPlane);  // Show the correct Plane in the split pane
		});

		split.add(tabs);
		add(split);

		DefaultTreeModel model = (DefaultTreeModel) functions_tree.getModel();
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("functions");
		top.removeAllChildren();
		model.reload();
		model.setRoot(top);

		addMenus();
		addPopUpMenus();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900, 600);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void addTab(JTabbedPane tabs) {
		Plane plane = new Plane();
		plane.setScaleInX(1);
		plane.setScaleInY(1);
		plane.setShowGrid(true);

		int tabIndex = tabs.getTabCount();
		tabPlanes.put(tabIndex, plane);  // Associate the Plane with the tab index

		tabs.add(String.format("Graph %d", tabIndex + 1), plane);
		tabs.setTabComponentAt(tabIndex, new ButtonTabComponent(this, tabs));
	}

	public final void populateTree() {
		DefaultTreeModel model = (DefaultTreeModel) functions_tree.getModel();
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("functions");
		top.removeAllChildren();
		model.reload();
		model.setRoot(top);
		Vector<Function> all_functions = new Vector<>();

		for (var key : tabPlanes.keySet()) {
			Plane pl = tabPlanes.get(key);
			all_functions.addAll(pl.getFunctionList());
		}

		for (var fx : all_functions) {
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(fx.getName() + " = " + fx.getDefinition());
			top.add(node);
		}
	}

	private void setLookAndFeel() {
		FlatDarkLaf.setup();
	}

	public static void start(String[] args) {
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
					= """
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
				String art
					= """
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
