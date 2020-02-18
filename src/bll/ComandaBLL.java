package bll;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.swing.JTable;

import bll.validators.CantitateValidator;
import bll.validators.IdClientSiProdusValidator;
import bll.validators.IdValidator;
import bll.validators.Validator;
import dao.ComandaDAO;
import dao.ProdusDAO;
import model.Comanda;
import model.Produs;

/**
 * 
 * @author fagai
 * Clasa de logica a aplicatiei aplicata comenzilor
 */
public class ComandaBLL {
	private List<Validator<Comanda>> validators;
	private ComandaDAO comandaDAO;
	private ProdusDAO produsDAO;
	
	/**
	 * Contructor logica comanda
	 */
	public ComandaBLL() {
		validators = new ArrayList<Validator<Comanda>>();
		validators.add(new IdValidator<Comanda>());
		validators.add(new IdClientSiProdusValidator());
		validators.add(new CantitateValidator<Comanda>());
		comandaDAO = new ComandaDAO();
		this.produsDAO = new ProdusDAO();
	}
	/**
	 * In aceasta metoda se apeleaza interogarea din DAO ce gaseste comanda dupa id, si este returnata.
	 * @param id parametrul dupa care se cauta
	 * @return comanda gasita
	 */
	public Comanda findComandaById(int id) {
		Comanda comanda = (Comanda) comandaDAO.findById(id);
		if (comanda == null) {
			throw new NoSuchElementException("The order with id =" + id + " was not found!");
		}
		return comanda;
	}
	/**
	 * In aceasta metoda se apeleaza interogarea din DAO ce gaseste comenzile dupa cantitate, si sunt returnate
	 * @param cantitateMin cantitatea minima
	 * @param cantitateMax cantitatea maxima
	 * @return comenzile gasite
	 */
	public List<Comanda> findComandaByCantitate(int cantitateMin,int cantitateMax) {
		List<Comanda> comenzi = comandaDAO.findByCantitate(cantitateMin,cantitateMax);
		if (comenzi == null) {
			throw new NoSuchElementException("The order was not found!");
		}
		return comenzi;
	}
	/**
	 * In aceasta metoda se apeleaza interogarea din DAO ce gaseste clientii dupa pret, si sunt returnati
	 * @param pretMin cantitatea minima
	 * @param pretMax cantitatea maxima
	 * @return comenzile gasite
	 */
	public List<Comanda> findComandaByPret(int pretMin,int pretMax) {
		List<Comanda> comenzi =  comandaDAO.findByPret(pretMin,pretMax);
		if (comenzi == null) {
			throw new NoSuchElementException("The order was not found!");
		}
		return comenzi;
	}
	/**
	 * Metoda ce apeleaza interogarea din DAO ce insereaza o comanda si o returneaza
	 * @param id id-ul comenzii
	 * @param idclient	id-ul clientului ce cumpara
	 * @param idprodus id-ul produsului cumparat
	 * @param cantitate cantitatea pe care o cumpara
	 * @return comanda introdusa
	 * @throws Exception exceptie cand nu se poate introduce
	 */
	public Comanda insert(int id,int idclient,int idprodus,int cantitate) throws Exception {
		Comanda comanda = new Comanda(id,idclient,idprodus,cantitate);
		validators.get(0).validate(comanda);
		validators.get(1).validate(comanda);
		validators.get(2).validate(comanda);
		Produs p = produsDAO.updateStoc(idprodus,cantitate);
		Comanda comanda1 = null;
		if (p!=null) {
			comanda1 =(Comanda) comandaDAO.insert(comanda);
			if (comanda1 == null) {
				throw new NullPointerException("Nu se poate introduce");
			}
		}
		return comanda1;
	}
	/**
	 * Metoda ce apeleaza interogarea din DAO ce actualizeaza o comanda si o returneaza
	 * @param id id-ul comenzii de actualizat
	 * @param idclient	id-ul clientului actualizat
	 * @param idprodus id-ul produsului actualizat
	 * @param cantitate cantitatea ce se actualizeaza
	 * @return comanda actualizata
	 */
	public Comanda update(int id,int idclient,int idprodus,int cantitate) {
		Comanda comanda = new Comanda(id,idclient,idprodus,cantitate);
		validators.get(0).validate(comanda);
		validators.get(1).validate(comanda);
		validators.get(2).validate(comanda);
		Comanda comanda1 = (Comanda) comandaDAO.update(comanda);
		if (comanda1 == null) {
			throw new NullPointerException("Nu se poate face update");
		}
		return comanda1;
	}
	/**
	 * metoda ce returneaza lista corespunzatoare tuturor comenzilor din baza de date
	 * @return lista creata
	 */
	public List<Comanda> findAllComanda1() {
		List<Comanda> comenzi = comandaDAO.findAll();
		if (comenzi == null) {
			throw new NullPointerException("Nu se poate afisa");
		}
		return comenzi;
	}
	/**
	 * Metoda ce sterge o domanda dupa id-ul sau
	 * @param id id-ul dupa care se sterge
	 */
	public void delete(int id) {
		comandaDAO.delete(id);
		return;
	}
	/**
	 * metoda ce returneaza JTabel-ul corespunzator tuturor comenzilor din baza de date
	 * @return tabelul creat
	 */
	public JTable findAllComanda() {
		List<Comanda> comenzi = comandaDAO.findAll();
		if (comenzi == null) {
			throw new NullPointerException("Nu se poate afisa");
		}
		JTable comandaJTable = comandaDAO.createTable(comenzi);
		return comandaJTable;
	}
}