package bll;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.swing.JTable;

import bll.validators.EmailValidator;
import bll.validators.IdValidator;
import bll.validators.VarstaValidator;
import bll.validators.Validator;
import dao.ClientDAO;
import model.Client;

/**
 * 
 * @author fagai
 * Clasa de logica a aplicatiei aplicata clientilor
 */
public class ClientBLL {
	private List<Validator<Client>> validators;
	private ClientDAO clientDAO;

	/**
	 * Contructor logica client
	 */
	public ClientBLL() {
		validators = new ArrayList<Validator<Client>>();
		validators.add(new IdValidator<Client>());
		validators.add(new EmailValidator());
		validators.add(new VarstaValidator());

		clientDAO = new ClientDAO();
	}
	/**
	 * In aceasta metoda se apeleaza interogarea din DAO ce gaseste clientul dupa id, si este returnat.
	 * @param id parametrul dupa care se cauta
	 * @return clientul gasit
	 */
	public Client findClientById(int id) {
		Client client = (Client) clientDAO.findById(id);
		if (client == null) {
			throw new NoSuchElementException("The client with id =" + id + " was not found!");
		}
		return client;
	}
	/**
	 * In aceasta metoda se apeleaza interogarea din DAO ce gaseste clientii dupa nume, si sunt returnati
	 * @param nume parametrul dupa care se cauta
	 * @return clientii gasiti
	 */
	public List<Client> findClientByNume(String nume) {
		List<Client> clienti =  clientDAO.findByNume(nume);
		if (clienti == null) {
			throw new NoSuchElementException("The client with nume =" + nume + " was not found!");
		}
		return clienti;
	}
	/**
	 * In aceasta metoda se apeleaza interogarea din DAO ce gaseste clientii dupa adresa, si sunt returnati
	 * @param adresa parametrul dupa care se cauta
	 * @return clientii gasiti
	 */
	public List<Client> findClientByAdresa(String adresa) {
		List<Client> clienti =  clientDAO.findByAdresa(adresa);
		if (clienti == null) {
			throw new NoSuchElementException("The client was not found!");
		}
		return clienti;
	}
	/**
	 * In aceasta metoda se apeleaza interogarea din DAO ce gaseste clientii dupa email, si sunt returnati
	 * @param email parametrul dupa care se cauta
	 * @return clientii gasiti
	 */
	public List<Client> findClientByEmail(String email) {
		List<Client> clienti =  clientDAO.findByEmail(email);
		if (clienti == null) {
			throw new NoSuchElementException("The client was not found!");
		}
		return clienti;
	}
	/**
	 * In aceasta metoda se apeleaza interogarea din DAO ce insereaza un client, si acest client este returnat
	 * @param id id-ul clientului introdus
	 * @param nume numele clientului introdus
	 * @param varsta varsta clientului introdus
	 * @param adresa adresa clientului introdus
	 * @param email email-ul clientului introdus
	 * @return clientul introdus
	 */
	public Client insert(int id,String nume,int varsta, String adresa, String email) {
		Client client = new Client(id,nume,varsta,adresa,email);
		validators.get(0).validate(client);
		validators.get(1).validate(client);
		validators.get(2).validate(client);
		Client client1 = (Client) clientDAO.insert(client);
		if (client1 == null) {
			throw new NullPointerException("Nu se poate introduce");
		}
		return client1;
	}
	/**
	 * In aceasta metoda se apeleaza interogarea din DAO ce actualizeaza un client, si acest client este returnat
	 * @param id id-ul clientului actualizat
	 * @param nume numele clientului actualizat
	 * @param varsta varsta clientului actualizat
	 * @param adresa adresa clientului actualizat
	 * @param email email-ul clientului actualizat
	 * @return clientul actualizat
	 */
	public Client update(int id,String nume,int varsta, String adresa, String email) {
		Client client = new Client(id,nume,varsta,adresa,email);
		validators.get(1).validate(client);
		validators.get(2).validate(client);
		Client client1 = (Client) clientDAO.update(client);
		if (client1 == null) {
			throw new NullPointerException("Nu se poate face update");
		}
		return client1;
	}
	/**
	 * metoda ce returneaza JTabel-ul corespunzator tuturor clientilor din baza de date
	 * @return tabelul creat
	 */
	public JTable findAllClient() {
		List<Client> clienti = clientDAO.findAll();
		if (clienti == null) {
			throw new NullPointerException("Nu se poate afisa");
		}
			JTable clientJTable = clientDAO.createTable(clienti);
		return clientJTable;
	}
	/**
	 * metoda ce returneaza lista corespunzatoare tuturor clientilor din baza de date
	 * @return lista creata
	 */
	public List<Client> findAllClientList() {
		List<Client> clienti = clientDAO.findAll();
		if (clienti == null) {
			throw new NullPointerException("Nu se poate afisa");
		}
		return clienti;
	}
	/**
	 * Metoda ce sterge un client dupa id-ul sau
	 * @param id id-ul dupa care se sterge
	 */
	public void delete(int id) {
		clientDAO.delete(id);
		return;
	}
}
