package presentation;

import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import bll.*;

@SuppressWarnings("serial")
public class ViewProdus extends JFrame {

	ProdusBLL produsBll;
	private JPanel produsPanel;

	private JButton addP = new JButton("Adauga Produs");
	private JButton editP = new JButton("Editeaza Produs");
	private JButton deleteP = new JButton("Sterge Produs");
	private JButton afiseazaP = new JButton("Afiseaza Produsele");
	private JLabel lIdP= new JLabel("Id:");
	private JLabel lNumeP= new JLabel("Nume:");
	private JLabel lCantitateP = new JLabel("Cantitate:");
	private JLabel lPretP= new JLabel("Pret:");
	private JTextField tIdP = new JTextField(11);
	private JTextField tNumeP = new JTextField(45);
	private JTextField tCantitateP = new JTextField(11);
	private JTextField tPretP = new JTextField(11);
	
	JTable table = new JTable();
	
	public ViewProdus() {
		tIdP.setText("0");
        produsBll = new ProdusBLL();
        produsPanel = new JPanel();
        produsPanel.setLayout(new BoxLayout(produsPanel,BoxLayout.Y_AXIS));		
        produsPanel.add(lIdP);
        produsPanel.add(tIdP);
        produsPanel.add(lNumeP);
        produsPanel.add(tNumeP);
        produsPanel.add(lCantitateP);
        produsPanel.add(tCantitateP);
        produsPanel.add(lPretP);
        produsPanel.add(tPretP);
        produsPanel.add(addP);
        produsPanel.add(editP);
        produsPanel.add(deleteP);
        produsPanel.add(afiseazaP);
        produsPanel.setVisible(true);
        this.setContentPane(produsPanel);
        this.pack();
        this.setTitle("Magazin");
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        
    }
	public void showError(String errMessage) {
        JOptionPane.showMessageDialog(this, errMessage);
    }
	public ProdusBLL getProdusBll() {
		return produsBll;
	}
	public int gettIdP() {
		return Integer.parseInt(tIdP.getText());
	}
	public String gettNumeP() {
		return tNumeP.getText();
	}
	public int gettCantitateP() {
		return Integer.parseInt(tCantitateP.getText());
	}
	public int gettPretP() {
		return Integer.parseInt(tPretP.getText());
	}
	public void addAddPListener(ActionListener addPListener) {
        addP.addActionListener(addPListener);
    }
	public void addEditPListener(ActionListener editPListener) {
        editP.addActionListener(editPListener);
    }
	public void addDeletePListener(ActionListener deletePListener) {
        deleteP.addActionListener(deletePListener);
    }
	public void addAfiseazaPListener(ActionListener afisPListener) {
        afiseazaP.addActionListener(afisPListener);
	}
}