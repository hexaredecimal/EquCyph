package equcyph;

//import com.formdev.flatlaf.FlatIntelliJLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class EquCyph extends JFrame implements ActionListener {

    private Plane plane;
    private LinkedList<String> plotted = new LinkedList<>();
    private LinkedList<Color> plottedColors = new LinkedList<>();

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

    public void plotFunction(String name, String equation, Color style) {
        Function f = new Function(equation, name);
        f.setColor(style);
        plane.plot(f);
    }

    public EquCyph() {
        super("EquCyph");
//        FlatIntelliJLaf.setup();
        addMenus();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);

        plane = new Plane();
        add(plane, BorderLayout.CENTER);

        // Create a function with expression and name
//        Function f = new Function("(2 * x) + 4", "f(x)");
//        f.setColor(Color.RED); // Set color for the graph
//        plane.plot(f);
        // Set the desired scale for the plane
        plane.setScaleInX(1);
        plane.setScaleInY(1);

        // Enable grid in plane
        plane.setShowGrid(true);

        // Plot function, the plane store a list of functions so that you can
        // graph many functions at the same time
        //plane.removeFunction("g(x)");
        setLocationRelativeTo(null);
        setVisible(true);
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

                String message = "<html><h3 align=\"center\">Lead developers</h3><p align=\"center\">HexAreDecimal</b></p>";
                message += "<p align=\"center\">Hugs_Coding</b></p></html>";
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
