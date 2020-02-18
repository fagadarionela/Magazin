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
public class ViewClient extends JFrame {
	ClientBLL clientBll;

	private JPanel clientPanel;
	private JButton addC = new JButton("Adauga Client");
	private JButton editC = new JButton("Editeaza Client");
	private JButton deleteC = new JButton("Sterge Client");
	private JButton afiseazaC = new JButton("Afiseaza Clientii");
	private JLabel lIdC= new JLabel("Id:");
	private JLabel lNumeC= new JLabel("Nume:");
	private JLabel lVarstaC = new JLabel("Varsta:");
	private JLabel lAdresaC= new JLabel("Adresa:");
	private JLabel lEmailC= new JLabel("Email:");
	private JTextField tIdC = new JTextField(11);
	private JTextField tNumeC = new JTextField(45);
	private JTextField tVarstaC = new JTextField(3);
	private JTextField tAdresaC = new JTextField(45);
	private JTextField tEmailC = new JTextField(45);
	JTable table = new JTable();
	
	public ViewClient() {
		tIdC.setText("0");
        clientBll = new ClientBLL();
        clientPanel = new JPanel();	
        clientPanel.setLayout(new BoxLayout(clientPanel,BoxLayout.Y_AXIS));		
        clientPanel.add(lIdC);
        clientPanel.add(tIdC);
        clientPanel.add(lNumeC);
        clientPanel.add(tNumeC);
        clientPanel.add(lVarstaC);
        clientPanel.add(tVarstaC);
        clientPanel.add(lAdresaC);
        clientPanel.add(tAdresaC);
        clientPanel.add(lEmailC);
        clientPanel.add(tEmailC);
        clientPanel.add(addC);
        clientPanel.add(editC);
        clientPanel.add(deleteC);
        clientPanel.add(afiseazaC);
        clientPanel.setVisible(true);
        this.setContentPane(clientPanel);
        this.pack();
        this.setTitle("Magazin");
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }
	public void showError(String errMessage) {
        JOptionPane.showMessageDialog(this, errMessage);
    }
	public ClientBLL getClientBll() {
		return clientBll;
	}
	public int gettIdC() {
		return Integer.parseInt(tIdC.getText());
	}
	public String gettNumeC() {
		return tNumeC.getText();
	}
	public int gettVarstaC() {
		return Integer.parseInt(tVarstaC.getText());
	}
	public String gettAdresaC() {
		return tAdresaC.getText();
	}
	public String gettEmailC() {
		return tEmailC.getText();
	}
	public void addAddCListener(ActionListener addCListener) {
        addC.addActionListener(addCListener);
    }
	public void addEditCListener(ActionListener editCListener) {
        editC.addActionListener(editCListener);
    }
	public void addDeleteCListener(ActionListener deleteCListener) {
        deleteC.addActionListener(deleteCListener);
    }
	public void addAfiseazaCListener(ActionListener afisCListener) {
        afiseazaC.addActionListener(afisCListener);
	}
}
