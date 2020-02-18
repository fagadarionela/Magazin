package presentation;

import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class View extends JFrame {
	private JButton comanda = new JButton("Comanda");
	private JButton client = new JButton("client");
	private JButton produs = new JButton("produs");
	private JPanel panel;
	
	public View(){
		panel = new JPanel();
		panel.add(client);
		panel.add(produs);
		panel.add(comanda);
		panel.setLayout(new BoxLayout(panel,BoxLayout.X_AXIS));	
		panel.setVisible(true);
        this.setContentPane(panel);
        
        this.pack();
        this.setTitle("Magazin");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void addClientListener(ActionListener addCListener) {
        client.addActionListener(addCListener);
    }
	public void addProdusListener(ActionListener editCListener) {
        produs.addActionListener(editCListener);
    }
	public void addComandaListener(ActionListener deleteCListener) {
        comanda.addActionListener(deleteCListener);
    }
}
