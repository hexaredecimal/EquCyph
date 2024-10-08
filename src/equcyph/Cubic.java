package equcyph;

import java.awt.Color;
import java.util.HashMap;
import javax.swing.JFrame;

/**
 *
 * @author Hexaredecimal
 */
public class Cubic extends javax.swing.JFrame {

	/**
	 * Creates new form Advanced, Hello java docs
	 *
	 * @param parent
	 */
	public Cubic(EquCyph parent) {
		initComponents();
		this.parent = parent;
		String[] awt_colors = {"BLACK", "BLUE", "CYAN", "DARK_GRAY", "GRAY", "GREEN", "LIGHT_GRAY", "MAGENTA", "ORANGE", "PINK", "RED", "WHITE", "YELLOW"};
		for (String color : awt_colors) {
			colorSelection.addItem(color);
			Color value = this.getColorFromString(color);
			colortable.put(color, value);
		}

		this.setVisible(true);
	}

	private Color getColorFromString(String col) {
		switch (col.toLowerCase()) {
			case "black":
				return Color.BLACK;
			case "blue":
				return Color.BLUE;
			case "cyan":
				return Color.CYAN;
			case "darkgray":
				return Color.DARK_GRAY;
			case "gray":
				return Color.GRAY;
			case "green":
				return Color.GREEN;
			case "yellow":
				return Color.YELLOW;
			case "lightgray":
				return Color.LIGHT_GRAY;
			case "magneta":
				return Color.MAGENTA;
			case "orange":
				return Color.ORANGE;
			case "pink":
				return Color.PINK;
			case "red":
				return Color.RED;
			case "white":
				return Color.WHITE;
		}
		return Color.BLACK;
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is alw
				Cubic cbc = new Cubic(this);ays
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jPanel1 = new javax.swing.JPanel();
    jLabel1 = new javax.swing.JLabel();
    jSeparator1 = new javax.swing.JSeparator();
    jLabel3 = new javax.swing.JLabel();
    jPanel2 = new javax.swing.JPanel();
    btnCancel = new javax.swing.JButton();
    btnPlot = new javax.swing.JButton();
    jLabel2 = new javax.swing.JLabel();
    colorSelection = new javax.swing.JComboBox<>();
    jLabel6 = new javax.swing.JLabel();
    tf_a = new javax.swing.JTextField();
    jLabel8 = new javax.swing.JLabel();
    tf_b = new javax.swing.JTextField();
    jLabel9 = new javax.swing.JLabel();
    tf_c = new javax.swing.JTextField();
    cb_a = new javax.swing.JComboBox<>();
    cb_b = new javax.swing.JComboBox<>();
    cb_c = new javax.swing.JComboBox<>();
    jLabel10 = new javax.swing.JLabel();
    tf_d = new javax.swing.JTextField();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setAlwaysOnTop(true);
    setLocation(new java.awt.Point(300, 400));
    setResizable(false);
    setType(java.awt.Window.Type.UTILITY);

    jPanel1.setBackground(new java.awt.Color(255, 255, 255));

    jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
    jLabel1.setText("Cubic Graphs");

    jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

    jLabel3.setText("<html>Create and plot advanced <br><b><u>Algebraic Equations</u> <b></html>");

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel1Layout.createSequentialGroup()
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jSeparator1)
          .addGroup(jPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
              .addComponent(jLabel3))))
        .addContainerGap())
    );
    jPanel1Layout.setVerticalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel1Layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jLabel1)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    btnCancel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
    btnCancel.setText("Cancel");
    btnCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnCancelActionPerformed(evt);
      }
    });

    btnPlot.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
    btnPlot.setText("Plot");
    btnPlot.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnPlotActionPerformed(evt);
      }
    });

    jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
    jLabel2.setText("Graph color");
    jLabel2.setToolTipText("");

    colorSelection.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

    jLabel6.setText("A:");

    jLabel8.setText("B:");

    jLabel9.setText("C:");

    tf_c.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        tf_cActionPerformed(evt);
      }
    });

    cb_a.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "+", "-", "*", "/" }));

    cb_b.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "+", "-", "*", "/" }));

    cb_c.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "+", "-", "*", "/" }));

    jLabel10.setText("D:");

    tf_d.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        tf_dActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
    jPanel2.setLayout(jPanel2Layout);
    jPanel2Layout.setHorizontalGroup(
      jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel2Layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
          .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
            .addComponent(colorSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(btnCancel)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(btnPlot))
          .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
            .addComponent(jLabel6)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(tf_a, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(cb_a, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel8)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(tf_b, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(cb_b, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel9)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(tf_c, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(cb_c, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel10)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(tf_d, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    jPanel2Layout.setVerticalGroup(
      jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel2Layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
            .addComponent(jLabel10)
            .addComponent(tf_d, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(cb_c, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
          .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
            .addComponent(jLabel6)
            .addComponent(tf_a, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jLabel9)
            .addComponent(tf_c, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(cb_a, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jLabel8)
            .addComponent(tf_b, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(cb_b, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(jLabel2)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(colorSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(btnCancel)
          .addComponent(btnPlot))
        .addContainerGap())
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

    private void btnPlotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlotActionPerformed
			String func = "p(x)";
			String a = tf_a.getText();
			String b = tf_b.getText();
			String c = tf_c.getText();
			String d = tf_d.getText();

			String left_op = (String) cb_a.getSelectedItem();
			String right_op = (String) cb_b.getSelectedItem();
			String last_op = (String) cb_c.getSelectedItem();

			// String head = a + " * (x ^ 3) " + left_op + " " + b + " * (x ^ 2) " + right_op + " " + c + " * x " + last_op + " " + d;
			String head = String.format(
				"%s * (x ^ 3) %s %s * (x ^ 2) %s %s * x %s %s", 
				a, 
				left_op, 
				b, 
				right_op, 
				c,
				last_op, 
				d
			);
			System.out.println("Cubic: " + head);
			String colorString = String.valueOf(colorSelection.getSelectedItem());
			Color color = colortable.get(colorString);
			this.parent.plotFunction(func, head, color);
			this.dispose();
    }//GEN-LAST:event_btnPlotActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
			this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void tf_cActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_cActionPerformed
			// TODO add your handling code here:
    }//GEN-LAST:event_tf_cActionPerformed

  private void tf_dActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_dActionPerformed
		// TODO add your handling code here:
  }//GEN-LAST:event_tf_dActionPerformed

	private HashMap<String, Color> colortable = new HashMap<>();
	private EquCyph parent;
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton btnCancel;
  private javax.swing.JButton btnPlot;
  private javax.swing.JComboBox<String> cb_a;
  private javax.swing.JComboBox<String> cb_b;
  private javax.swing.JComboBox<String> cb_c;
  private javax.swing.JComboBox<String> colorSelection;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel10;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel6;
  private javax.swing.JLabel jLabel8;
  private javax.swing.JLabel jLabel9;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JPanel jPanel2;
  private javax.swing.JSeparator jSeparator1;
  private javax.swing.JTextField tf_a;
  private javax.swing.JTextField tf_b;
  private javax.swing.JTextField tf_c;
  private javax.swing.JTextField tf_d;
  // End of variables declaration//GEN-END:variables
}
