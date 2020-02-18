package presentation;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.JTable;
import javax.swing.JTextField;


import bll.*;
import model.Client;
import model.Produs;

@SuppressWarnings("serial")
public class ViewComanda extends JFrame {
	ComandaBLL comandaBll;

	private JPanel comandaPanel;
	
	ProdusBLL produsBll;
	ClientBLL clientBll;
	DetaliiComandaBLL detaliiComandaBll;
	private JLabel lIdCmd= new JLabel("Id:");
	private JLabel lClientCmd= new JLabel("Client:");
	private JLabel lProdusCmd = new JLabel("Produs:");
	private JLabel lCantitateCmd= new JLabel("Cantitate:");
	private JTextField tIdCmd = new JTextField(11);
	private JComboBox<Object> clienti;
	private JComboBox<Object> produse;
	private JTextField tCantitate = new JTextField(11);
	
	private JButton addCmd = new JButton("Adauga in cos");
	private JButton afisCmd = new JButton("Afisare cos cumparaturi");
	private JButton afiseazaCmd = new JButton("Afiseaza comenzi");
	private JButton gataCmd = new JButton("Plasare comanda");

	JTable table = new JTable();
	
	public ViewComanda(ViewClient viewClient,ViewProdus viewProdus) {
		tIdCmd.setText("0");
		clientBll = viewClient.getClientBll();
		produsBll = viewProdus.getProdusBll();
        comandaBll = new ComandaBLL();
        detaliiComandaBll = new DetaliiComandaBLL();
        comandaPanel = new JPanel();
        comandaPanel.setLayout(new BoxLayout(comandaPanel,BoxLayout.Y_AXIS));			 
        List<Client> listaC = new ArrayList<Client>();
        listaC = clientBll.findAllClientList();
        clienti = new JComboBox<Object>(listaC.toArray());
        List<Produs> listaP = new ArrayList<Produs>();
        listaP = produsBll.findAllProdusList();
       	produse = new JComboBox<Object>(listaP.toArray());
        comandaPanel.add(lIdCmd);
        comandaPanel.add(tIdCmd);
        comandaPanel.add(lClientCmd);
        comandaPanel.add(clienti);
        comandaPanel.add(lProdusCmd);
        comandaPanel.add(produse);
        comandaPanel.add(lCantitateCmd);
        comandaPanel.add(tCantitate);
        comandaPanel.add(addCmd);
        comandaPanel.add(afisCmd);
        comandaPanel.add(gataCmd);
        comandaPanel.add(afiseazaCmd);
        comandaPanel.setVisible(true);
        this.setContentPane(comandaPanel);
        this.pack();
        this.setTitle("Magazin");
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        
    }
	public void showError(String errMessage) {
        JOptionPane.showMessageDialog(this, errMessage);
    }
	public ComandaBLL getComandaBll() {
		return comandaBll;
	}
	public DetaliiComandaBLL getDetaliiComandaBll() {
		return detaliiComandaBll;
	}
	public int gettIdCmd() {
		return Integer.parseInt(tIdCmd.getText());
	}
	public int gettCantitate() {
		return Integer.parseInt(tCantitate.getText());
	}
	public Client getClienti() {
		System.out.println(clienti.getSelectedItem());
		return  (Client) clienti.getSelectedItem();
	}
	public Produs getProduse() {
		return (Produs) produse.getSelectedItem();
	}
	public void addAddCmdListener(ActionListener addCmdListener) {
        addCmd.addActionListener(addCmdListener);
    }
	public void addGataCmdListener(ActionListener gataCmdListener) {
        gataCmd.addActionListener(gataCmdListener);
    }
	public void addAfisCmdListener(ActionListener afisCmdListener) {
        afisCmd.addActionListener(afisCmdListener);
    }
	public void addAfiseazaCmdListener(ActionListener afisCmdListener) {
        afiseazaCmd.addActionListener(afisCmdListener);
	}
}