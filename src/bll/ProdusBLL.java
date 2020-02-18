package bll;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.swing.JTable;

import bll.validators.CantitateValidator;
import bll.validators.IdValidator;
import bll.validators.PretValidator;
import bll.validators.Validator;
import dao.ProdusDAO;
import model.Produs;

/**
 * 
 * @author fagai
 * Clasa de logica a aplicatiei aplicata produselor
 */
public class ProdusBLL {
	private List<Validator<Produs>> validators;
	private ProdusDAO produsDAO;
	/**
	 * Contructor logica produs
	 */
	public ProdusBLL() {
		validators = new ArrayList<Validator<Produs>>();
		validators.add(new IdValidator<Produs>());
		validators.add(new PretValidator<Produs>());
		validators.add(new CantitateValidator<Produs>());
		produsDAO = new ProdusDAO();
	}
	/**
	 * In aceasta metoda se apeleaza interogarea din DAO ce gaseste produsul dupa id, si este returnat.
	 * @param id parametrul dupa care se cauta
	 * @return produsul gasit
	 */
	public Produs findProdusById(int id) {
		Produs produs = (Produs) produsDAO.findById(id);
		if (produs == null) {
			throw new NoSuchElementException("The product with id =" + id + " was not found!");
		}
		return produs;
	}
	/**
	 * In aceasta metoda se apeleaza interogarea din DAO ce gaseste produsele dupa nume, si sunt returnate
	 * @param nume parametrul dupa care se cauta
	 * @return produsele gasite
	 */
	public List<Produs> findProdusByNume(String nume) {
		List<Produs> produse =  produsDAO.findByNume(nume);
		if (produse == null) {
			throw new NoSuchElementException("The product with nume =" + nume + " was not found!");
		}
		return produse;
	}
	/**
	 * Metoda ce apeleaza interogarea din DAO ce gaseste produsele dupa cantitate, si returneaza o lista de produse
	 * @param cantitateMin cantitatea minima
	 * @param cantitateMax cantitatea maxima
	 * @return lista de produse
	 */
	public List<Produs> findProdusByCantitate(int cantitateMin,int cantitateMax) {
		List<Produs> produse = produsDAO.findByCantitate(cantitateMin,cantitateMax);
		if (produse == null) {
			throw new NoSuchElementException("The product was not found!");
		}
		return produse;
	}
	/**
	 * Metoda ce apeleaza interogarea din DAO ce gaseste produsele dupa pret, si returneaza o lista de produse
	 * @param pretMin pretul minim
	 * @param pretMax pretul maxim
	 * @return lista de produse
	 */
	public List<Produs> findProdusByPret(int pretMin,int pretMax) {
		List<Produs> produse =  produsDAO.findByPret(pretMin,pretMax);
		if (produse == null) {
			throw new NoSuchElementException("The product was not found!");
		}
		return produse;
	}
	/**
	 * Metoda ce apeleaza interogarea din DAO ce  insereaza un produs si il returneaza
	 * @param id id-ul produsului de inserat
	 * @param nume numele produsului de inserat
	 * @param cantitate cantitatea produsului de inserat
	 * @param pret pretul produsului de inserat
	 * @return produsul inserat
	 */
	public Produs insert(int id,String nume,int cantitate, int pret) {
		Produs produs = new Produs(id,nume,cantitate,pret);
		validators.get(0).validate(produs);
		validators.get(1).validate(produs);
		validators.get(2).validate(produs);
		Produs produs1 = (Produs) produsDAO.insert(produs);
		if (produs1 == null) {
			throw new NullPointerException("Nu se poate introduce");
		}
		return produs1;
	}
	/**
	 * Metoda ce apeleaza interogarea din DAO ce actualizeaza un produs si il returneaza
	 * @param id id-ul produsului de actualizat
	 * @param nume numele produsului de actualizat
	 * @param cantitate cantitatea produsului de actualizat
	 * @param pret pretul produsului de actualizat
	 * @return produsul actualizat
	 */
	public Produs update(int id,String nume,int cantitate, int pret) {
		Produs produs = new Produs(id,nume,cantitate,pret);
		validators.get(1).validate(produs);
		validators.get(2).validate(produs);
		Produs produs1 = (Produs) produsDAO.update(produs);
		if (produs1 == null) {
			throw new NullPointerException("Nu se poate face update");
		}
		return produs1;
	}
	/**
	 * Metoda ce apeleaza  metoda din DAO ce gaseste toate produsele, si genereaza un tabel de tip JTable
	 * @return tabelul generat
	 */
	public JTable findAllProdus() {
		List<Produs> produse = produsDAO.findAll();
		if (produse == null) {
			throw new NullPointerException("Nu se poate afisa");
		}
		JTable produsJTable = produsDAO.createTable(produse);
		return produsJTable;
	}
	/**
	 * Metoda ce apeleaza  metoda din DAO ce gaseste toate produsele, si returneaza o lista de produse
	 * @return lista generata
	 */
	public List<Produs> findAllProdusList() {
		List<Produs> produse = produsDAO.findAll();
		if (produse == null) {
			throw new NullPointerException("Nu se poate afisa");
		}
		return produse;
	}
	/**
	 * Metoda ce apeleaza metoda din DAO ce sterge un produs din baza de date
	 * @param id id-ul produsului ce se sterge
	 */
	public void delete(int id) {
		produsDAO.delete(id);
		return;
	}
}
