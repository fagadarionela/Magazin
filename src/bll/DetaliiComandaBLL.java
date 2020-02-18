package bll;

import java.util.List;


import javax.swing.JTable;

import dao.DetaliiComandaDAO;
import dao.ProdusDAO;

import model.DetaliiComanda;

/**
 * 
 * @author fagai
 *Clasa de logica a aplicatiei aplicata detaliilor de comanda - cosul de cumparaturi propriu zis
 */
public class DetaliiComandaBLL {
	private DetaliiComandaDAO detaliiComandaDAO;
	/**
	 * Constructor logica detalii comanda
	 */
	public DetaliiComandaBLL() {

		detaliiComandaDAO = new DetaliiComandaDAO();
		new ProdusDAO();
	}
	/**
	 * Metoda ce apeleaza metoda din DAO de gasire a detaliilor comenzii dupa id, si genereaza un JTabel
	 * @param id parametrul dupa care se cauta
	 * @return JTabel generat
	 */
	public JTable findDetaliiComandaById(int id) {
		List<DetaliiComanda> comenzi = (List<DetaliiComanda>) detaliiComandaDAO.findByIdList(id);
		if (comenzi == null) {
			throw new NullPointerException("Nu se poate afisa");
		}
		JTable idJTable = detaliiComandaDAO.createTable(comenzi);
		return idJTable;
	}
	/**
	 * Metoda ce apeleaza metoda din DAO de gasire a detaliilor comenzii dupa id, si returneaza o lista de detalii comanda
	 * @param id parametrul dupa care se cauta
	 * @return lista de detalii comanda
	 */
	public List<DetaliiComanda> findDetaliiComandaByIdList(int id) {
		List<DetaliiComanda> comenzi = (List<DetaliiComanda>) detaliiComandaDAO.findByIdList(id);
		if (comenzi == null) {
			throw new NullPointerException("Nu se poate afisa");
		}
		return comenzi;
	}

	/**
	 * Metoda ce apeleaza metoda de inserare din DAO si returneaza obiectul inserat
	 * @param id id-ul de inserat
	 * @param idprodus id-ul produsului de inserat
	 * @param pret pretul produselor
	 * @return obiectul returnat de inserare
	 * @throws Exception exceptie in cazul in care nu se poate introduce
	 */
	public DetaliiComanda insert(int id,int idprodus, int pret) throws Exception {
		DetaliiComanda comanda = new DetaliiComanda(id,idprodus,pret);
		DetaliiComanda comanda1 =(DetaliiComanda) detaliiComandaDAO.insert(comanda);
		if (comanda1 == null) {
			throw new NullPointerException("Nu se poate introduce");
		}
		return comanda1;
	}
	/**
	 * Metoda ce apeleaza metoda de stergere din DAO
	 * @param id id-ul de sters
	 * @throws Exception exceptie in cazul in care nu se poate sterge
	 */
	public void delete(int id) throws Exception {
		detaliiComandaDAO.delete(id);
		return;
	}
	/**
	 * Metoda ce apeleaza metoda de afisare a tuturor detaliilor de comanda din DAO
	 * @return lista de detalii comanda
	 */
	public List<DetaliiComanda> findAllLista() {
		List<DetaliiComanda> comenzi = detaliiComandaDAO.findAll();
		if (comenzi == null) {
			throw new NullPointerException("Nu se poate afisa");
		}
		return comenzi;
	}
	/**
	 * Metoda ce apeleaza metoda de afisare a tuturor detaliilor de comanda din DAO si genereaza un JTabel
	 * @return JTabel generat
	 */
	public JTable findAllDetaliiComanda() {
		List<DetaliiComanda> comenzi = detaliiComandaDAO.findAll();
		if (comenzi == null) {
			throw new NullPointerException("Nu se poate afisa");
		}
		JTable produsJTable = detaliiComandaDAO.createTable(comenzi);
		return produsJTable;
	}
}