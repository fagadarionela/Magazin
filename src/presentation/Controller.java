package presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;

import javax.swing.JTable;

import bll.*;
import model.DetaliiComanda;


public class Controller {
	ViewClient viewClient;
	ViewProdus viewProdus;
	ViewComanda viewComanda;
	ClientBLL clientBll;
	ProdusBLL produsBll;
	ComandaBLL comandaBll;
	DetaliiComandaBLL detaliiComandaBll;
	
	public Controller(View view,ViewClient viewClient,ViewProdus viewProdus,ViewComanda viewComanda) {
    	this.viewClient  = viewClient;
    	this.viewProdus = viewProdus;
    	this.clientBll = viewClient.getClientBll();
    	this.produsBll = viewProdus.getProdusBll();
    	this.comandaBll = viewComanda.getComandaBll();
    	this.detaliiComandaBll = viewComanda.getDetaliiComandaBll();
    	viewClient.addAddCListener(new AddCListener());
    	viewClient.addEditCListener(new EditCListener());
    	viewClient.addDeleteCListener(new DeleteCListener());
    	viewClient.addAfiseazaCListener(new AfiseazaCListener());
    	viewProdus.addAddPListener(new AddPListener());
    	viewProdus.addEditPListener(new EditPListener());
    	viewProdus.addDeletePListener(new DeletePListener());
    	viewProdus.addAfiseazaPListener(new AfiseazaPListener());
    	view.addClientListener(new ViewClientListener());
    	view.addProdusListener(new ViewProdusListener());
    	view.addComandaListener(new ViewComandaListener());
    }
	class ViewClientListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			viewClient.setVisible(true);
		}
	}
	class ViewProdusListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			viewProdus.setVisible(true);
		}
	}
	class ViewComandaListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			viewComanda = new ViewComanda(viewClient,viewProdus);
			viewComanda.addAddCmdListener(new AddCmdListener());
			viewComanda.addAfiseazaCmdListener(new AfiseazaCmdListener());
			viewComanda.addAfisCmdListener(new AfisCmdListener());
			viewComanda.addGataCmdListener(new GataCmdListener());
			viewComanda.setVisible(true);
		}
	}
	class AddCListener implements ActionListener{
    	public void actionPerformed(ActionEvent e) {
    		try {
	    		if (viewClient.gettNumeC() == null || viewClient.gettVarstaC() == 0 || viewClient.gettAdresaC() == null || viewClient.gettEmailC() == null) viewClient.showError("Introdu datele");
	    		else {
	    			clientBll.insert(viewClient.gettIdC(), viewClient.gettNumeC(), viewClient.gettVarstaC(), viewClient.gettAdresaC(), viewClient.gettEmailC());
	    			viewClient.showError("S-a introdus clientul");
	    		}
    		}
    		catch (NumberFormatException e1) {
    			viewClient.showError("introdu datele");
    		}
    		catch (IllegalArgumentException e1) {
    				viewClient.showError(e1.getMessage());
    		}
    	}
    }  
	class EditCListener implements ActionListener{
    	public void actionPerformed(ActionEvent e) {
	    	try {
	    		clientBll.update(viewClient.gettIdC(), viewClient.gettNumeC(), viewClient.gettVarstaC(), viewClient.gettAdresaC(), viewClient.gettEmailC());
	    		viewClient.showError("S-a actualizat clientul");
	    		}
			catch (NumberFormatException e1) {
				viewClient.showError("introdu datele");
			}
			catch (IllegalArgumentException e1) {
					viewClient.showError(e1.getMessage());
			}
    	}
    } 
	class DeleteCListener implements ActionListener{
    	public void actionPerformed(ActionEvent e) {
    		try {
    		clientBll.delete(viewClient.gettIdC());
    	}
		catch (NumberFormatException e1) {
			viewClient.showError("introdu datele");
		}
		catch (IllegalArgumentException e1) {
				viewClient.showError(e1.getMessage());
		}
    	}
    } 
	class AfiseazaCListener implements ActionListener{
    	public void actionPerformed(ActionEvent e) {
    		JTable t = clientBll.findAllClient();
    		ViewJTable viewJTable = new ViewJTable(t);
    		viewJTable.setVisible(true);
    	}
    } 
	class AddPListener implements ActionListener{
    	public void actionPerformed(ActionEvent e) {
    		
    		try {
        		if (viewProdus.gettNumeP() == null || viewProdus.gettCantitateP() == 0|| viewProdus.gettPretP()==0)
        			viewProdus.showError("introdu datele");
        		else {
        			produsBll.insert(viewProdus.gettIdP(), viewProdus.gettNumeP(), viewProdus.gettCantitateP() , viewProdus.gettPretP());
        			viewProdus.showError("S-a introdus produsul");
        		}
        	}
        	catch (NumberFormatException e1) {
        		viewProdus.showError("introdu datele");
        	}
        	catch (IllegalArgumentException e1) {
        		viewProdus.showError(e1.getMessage());
        	}
        }
    }  
	class EditPListener implements ActionListener{
    	public void actionPerformed(ActionEvent e) {
    		try {
    			produsBll.update(viewProdus.gettIdP(), viewProdus.gettNumeP(), viewProdus.gettCantitateP() , viewProdus.gettPretP());
    			viewProdus.showError("S-a actualizat produsul");
	    		}
			catch (NumberFormatException e1) {
				viewProdus.showError("introdu datele");
			}
			catch (IllegalArgumentException e1) {
				viewProdus.showError(e1.getMessage());
			}
       	}
    } 
	class DeletePListener implements ActionListener{
    	public void actionPerformed(ActionEvent e) {
    		try {
        		produsBll.delete(viewProdus.gettIdP());
        	}
    		catch (NumberFormatException e1) {
    			viewProdus.showError("introdu datele");
    		}
    		catch (IllegalArgumentException e1) {
    			viewProdus.showError(e1.getMessage());
    		}
    	}
    } 
	class AfiseazaPListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
    		JTable t = produsBll.findAllProdus();
    		ViewJTable viewJTable = new ViewJTable(t);
    		viewJTable.setVisible(true);
    	}
    } 
	class AddCmdListener implements ActionListener{
    	public void actionPerformed(ActionEvent e) {
    		try {
        		if (viewComanda.gettCantitate() ==0) viewComanda.showError("Introdu datele");
        		else {
        			comandaBll.insert(viewComanda.gettIdCmd(), viewComanda.getClienti().getId(), viewComanda.getProduse().getId(), viewComanda.gettCantitate());
        			detaliiComandaBll.insert(viewComanda.getClienti().getId(), viewComanda.getProduse().getId(), viewComanda.gettCantitate()* produsBll.findProdusById(viewComanda.getProduse().getId()).getPret());
        		}
        		viewComanda.showError("S-a introdus comanda");
        		//viewComanda.setVisible(false);
        		}
        		catch (NumberFormatException e1) {
        			viewComanda.showError("introdu datele");
        		}
        		catch (IllegalArgumentException e1) {
        				viewComanda.showError(e1.getMessage());
        		} catch (InstantiationException e1) {
        			viewComanda.showError(e1.getMessage());
				} catch (IllegalAccessException e1) {
					viewComanda.showError(e1.getMessage());
				} catch (Exception e1) {
					viewComanda.showError(e1.getMessage());
				}
    	}
    }
	class GataCmdListener implements ActionListener{
    	public void actionPerformed(ActionEvent e) {
    		try {
    			BufferedWriter out = new BufferedWriter(new FileWriter("file.txt"));
    			out.write("Nume: "+viewComanda.getClienti().getNume()+"\n");
    			out.write("Adresa: "+viewComanda.getClienti().getAdresa()+"\n");
    			out.write("COMANDA:\n");
                for (DetaliiComanda c: detaliiComandaBll.findDetaliiComandaByIdList(viewComanda.getClienti().getId())) {
                    out.write(produsBll.findProdusById(c.getIdprodus()).getNume()+" "+produsBll.findProdusById(c.getIdprodus()).getPret()+"lei/buc cantitate: "+c.getPret()/produsBll.findProdusById(c.getIdprodus()).getPret()+" "+c.toString()+"\n");
                }
                int pret = 0;
                for (DetaliiComanda c: detaliiComandaBll.findDetaliiComandaByIdList(viewComanda.getClienti().getId())) {
                   pret+=c.getPret();
                }
                out.write("Total:"+pret);
                out.close();
    			
				detaliiComandaBll.delete(viewComanda.getClienti().getId());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
    	}
    }
	class AfiseazaCmdListener implements ActionListener{
    	public void actionPerformed(ActionEvent e) {
    		try{
    			JTable t = comandaBll.findAllComanda();
        		if (t!=null) {
        			ViewJTable viewJTable = new ViewJTable(t);
        			viewJTable.setVisible(true);
        		}
    		}
    		catch(Exception e1) {
    			e1.printStackTrace();
    		}
    	}
    } 
	class AfisCmdListener implements ActionListener{
    	public void actionPerformed(ActionEvent e) {
    		try{
    			JTable t = detaliiComandaBll.findDetaliiComandaById(viewComanda.getClienti().getId());
        		if (t!=null) {
        			ViewJTable viewJTable = new ViewJTable(t);
        			viewJTable.setVisible(true);
        		}
    		}
    		catch(Exception e1) {
    			e1.printStackTrace();
    		}
    	}
    } 
}
