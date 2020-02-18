package presentation;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class ViewJTable extends JFrame{
	private JTable table;
	public ViewJTable(JTable table) {
		this.table = table;
		table.setEnabled(false);
		table.setPreferredSize(new Dimension(500,1000));
		table.setAutoscrolls(true);
		JPanel panel = new JPanel();
		panel.setAutoscrolls(true);
		panel.setPreferredSize(new Dimension(500,1000));
		panel.add(table);
		this.setContentPane(panel);
		this.pack();
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
	}
}
