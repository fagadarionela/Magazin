package dao;

//java doc pentru abstract doa(metode) si pt business logic

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTable;

import connection.ConnectionFactory;
import model.*;
/**
 * AbstractDAO este o clasa generica, cu metode in care se utilizeaza reflexia, ce este utilizata la definirea operatiilor cu baza de date.
 * @author fagai
 *
 * @param <T> parametrul generic pentru ca, metodele clasei sa poata fi accesate din alte clase, corespunzator.
 */
public class AbstractDAO<T> {
	protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
	protected final Class<T> type;
	@SuppressWarnings("unchecked")
	public AbstractDAO() {
		this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	protected String createSelectQuery(String field) {
		StringBuilder sb = new StringBuilder();  //actualizeaza String-ul, nu creeaza un obiect nou.Nu e threadSafe, StringBuffer - thread safe
		sb.append("SELECT ");
		sb.append(" * ");
		sb.append(" FROM ");
		sb.append(type.getSimpleName());
		sb.append(" WHERE " + field + " =?");
		return sb.toString();
	}
	
	private String createSelectQueryWithoutWhere() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" * ");
		sb.append(" FROM ");
		sb.append(type.getSimpleName());
		return sb.toString();
	}
	private String createInsertQueryClient() {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ");
		sb.append(type.getSimpleName());
		sb.append(" VALUES(?,?,?,?,?)");
		return sb.toString();
	}
	private String createInsertQueryProdus() {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ");
		sb.append(type.getSimpleName());
		sb.append(" VALUES(?,?,?,?)");
		return sb.toString();
	}
	private String createInsertQueryComanda() {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ");
		sb.append(type.getSimpleName());
		sb.append(" VALUES(?,?,?,?)");
		return sb.toString();
	}
	private String createInsertQueryDetaliiComanda() {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ");
		sb.append(type.getSimpleName());
		sb.append(" VALUES(?,?,?)");
		return sb.toString();
	}
	private String createDeleteQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE");
		sb.append(" FROM ");
		sb.append(type.getSimpleName());
		sb.append(" WHERE id" + " =?");
		return sb.toString();
	}
	/**
	 * Metoda pentru a afisa toate datele dintr-un tabel al bazei de date.
	 * Se creeaza instructiunea SELECT* from x, unde x e un tabel al bazei de date.Se face conexiunea cu baza de date, se executa instructiunea.
	 * Rezultatele se returneaza, iar la sfarsit se face deconectarea de la baza de date. 
	 * @return obiectele rezultate din interogare sau null, in cazul in care nu se poate executa
	 */
	public List<T> findAll() {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createSelectQueryWithoutWhere();
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			return createObjects(resultSet);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return null;
	}
	/**
	 * Metoda pentru a afisa datele dintr-un tabel al bazei de date, filtrate dupa id.
	 * Se creeaza instructiunea SELECT* from x where id=y, unde x e un tabel al bazei de date,iar y e o valoare.
	 * Se face conexiunea cu baza de date,se inlocuieste y cu valoarea dorita se executa instructiunea.
	 * Rezultatele se returneaza, iar la sfarsit se face deconectarea de la baza de date. 
	 * @param id id-ul dupa care se cauta
	 * @return  obiectul rezultat din interogare sau null, in cazul in care nu se poate executa
	 */
	public T findById(int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createSelectQuery("id");
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			return createObjects(resultSet).get(0);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return null;
	}

	/**
	 * Metoda pentru a afisa datele dintr-un tabel al bazei de date, filtrate dupa nume.
	 * Se creeaza instructiunea SELECT* from x where nume=y, unde x e un tabel al bazei de date,iar y e o valoare.
	 * Se face conexiunea cu baza de date,se inlocuieste y cu valoarea dorita se executa instructiunea.
	 * Rezultatele se returneaza, iar la sfarsit se face deconectarea de la baza de date.
	 * @param nume numele dupa care se cauta
	 * @return obiectele rezultate din interogare sau null, in cazul in care nu se poate executa
	 */
	public List<T> findByNume(String nume) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createSelectQuery("nume");
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			statement.setString(1, nume);
			resultSet = statement.executeQuery();
			return createObjects(resultSet);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findByNume " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return null;
	}
	protected List<T> createObjects(ResultSet resultSet) {
		List<T> list = new ArrayList<T>();
		try {
			while (resultSet.next()) {
				T instance = type.newInstance();
				for (Field field : type.getDeclaredFields()) {
					Object value = resultSet.getObject(field.getName());
					PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
					Method method = propertyDescriptor.getWriteMethod();
					method.invoke(instance, value);
				}
				list.add(instance);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return list;
	}	
	/**
	 * Metode in care se inlocuiesc ? din instructiuni, cu valorile dorite
	 * @param connection conexiunea la baza de date
	 * @param statement interogarea
	 * @param t obiectul corespunzator actiunii interogarii
	 * @return interogare
	 * @throws SQLException exceptie sql
	 */
	public PreparedStatement statementClient(Connection connection,PreparedStatement statement,T t) throws SQLException {
		String query = createInsertQueryClient();
		statement = connection.prepareStatement(query);
		statement.setInt(1,((Client) t).getId());
		statement.setString(2, ((Client) t).getNume());
		statement.setInt(3,((Client) t).getVarsta());
		statement.setString(4, ((Client) t).getAdresa());
		statement.setString(5, ((Client) t).getEmail());
		return statement;
	}
	public PreparedStatement statementProdus(Connection connection,PreparedStatement statement,T t) throws SQLException {
		String query = createInsertQueryProdus();
		statement = connection.prepareStatement(query);
		statement.setInt(1,((Produs) t).getId());
		statement.setString(2, ((Produs) t).getNume());
		statement.setInt(3,((Produs) t).getCantitate());
		statement.setInt(4, ((Produs) t).getPret());
		return statement;
	}
	public PreparedStatement statementComanda(Connection connection,PreparedStatement statement,T t) throws SQLException {
		String query = createInsertQueryComanda();
		statement = connection.prepareStatement(query);
		statement.setInt(1, ((Comanda) t).getId());
		statement.setInt(2,((Comanda) t).getIdclient());
		statement.setInt(3, ((Comanda) t).getIdprodus());
		statement.setInt(4, ((Comanda) t).getCantitate());
		return statement;
	}
	public PreparedStatement statementDetaliiComanda(Connection connection,PreparedStatement statement,T t) throws SQLException {
		String query = createInsertQueryDetaliiComanda();
		statement = connection.prepareStatement(query);
		statement.setInt(1, ((DetaliiComanda) t).getId());
		statement.setInt(2, ((DetaliiComanda) t).getIdprodus());
		statement.setInt(3, ((DetaliiComanda) t).getPret());
		return statement;
	}
	/**
	 * Metoda folosita pentru a insera date in tabele.
	 * Se face conexiunea cu baza de date, se executa update-ul fata de baza de date, depinzand de clasa din care facem insert.
	 * Se returneaza valoarea de inserat
	 * La sfarsit, se inchide conexiunea cu baza de date.
	 * @param t obiectul ce trebuie introdus
	 * @return  obiectul rezultat din interogare sau null, in cazul in care nu se poate executa
	 */
	public T insert(T t) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
			try {
				connection = ConnectionFactory.getConnection();
				if(t instanceof Client) 
					statement = statementClient(connection,statement,t);
				else if (t instanceof Produs)
					statement = statementProdus(connection,statement,t);
				else if (t instanceof Comanda)
					statement = statementComanda(connection,statement,t);
				else if (t instanceof DetaliiComanda)
					statement = statementDetaliiComanda(connection,statement,t);
				statement.executeUpdate();
				return t;
			} catch (SQLException e) {
				LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
			} finally {
				ConnectionFactory.close(resultSet);
				ConnectionFactory.close(statement);
				ConnectionFactory.close(connection);
			}
		return null;
	}
	/**
	 * Metoda folosita pentru a sterge date din tabele.
	 * Se face conexiunea cu baza de date,se inlocuieste ? cu id-ul obiectului pe care vrem sa il stergem.
	 * Se executa update-ul fata de baza de date, depinzand de clasa din care facem delete.
	 * La sfarsit, se inchide conexiunea cu baza de date.
	 * @param id pentru a selecta ce data sa se stearga
	 */
	public void delete(int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		String query = createDeleteQuery();
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			statement.setLong(1, id);
			statement.executeUpdate();
			return;
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findByNume " + e.getMessage());
		} finally {
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return ;
	}
	/**
	 * Se face legatura cu baza de date,apoi se executa un select* from tabel.
	 * Rezultatele se salveaza intr-o variabila de tip metaData
	 * Se numara cate coloane sunt, apoi se creeaza un string de dimensiune nrCol
	 * Se adauga o la string-ul de coloane, numele coloanelor.
	 * Conexiunile se inchid, apoi se returneaza numele coloanelor. 
	 * @return coloanele din tabelele bazei de date
	 */
	public String[] afiseazaColoane() {
		String[] coloane = null;
		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		String query = createSelectQueryWithoutWhere();
		ResultSetMetaData rsMeta = null;
		int nrCol = -1;
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			rsMeta =  resultSet.getMetaData();
			nrCol = rsMeta.getColumnCount();
			coloane = new String[nrCol];
			for(int i =0 ;i<nrCol;i++) {
				coloane[i] = rsMeta.getColumnName(i+1);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:afiseazaColoane " + e.getMessage());
		} finally {
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return coloane;
	}
	/**
	 * Metoda ce creeaza un JTable pentru a afisa datele din tabelele bazelor de date.
	 * String[] coloane care primeste ceea ce returneaza functia de mai sus
	 * String[][] date pentru a afisa datele in tabel
	 * Se initializeaza datele din prima linie a matricii cu coloanele
	 * Apoi,pentru fiecare obiect din tabel, se adauga in fiecare celula a datei, valoarea corespunzatoare.
	 * Se creeaza un JTable cu datele calculate
	 * @param lista - lista ce contine ce trebuie afisat in tabel
	 * @return tabelul creat
	 */
	public JTable createTable(List<T> lista) {
		JTable table;
		String[] coloane = afiseazaColoane();
		String[][] date = new String[100][coloane.length];
		for(int i=0;i<coloane.length;i++) {
			date[0][i] = coloane[i];
		}
		int indexColoana = -1;
		for(int i=1;i<lista.size()+1;i++) {
			indexColoana = -1;
			for(Field f:lista.get(i-1).getClass().getDeclaredFields()) {
				indexColoana ++;
				f.setAccessible(true);
					Object valoare;
					try {
						valoare = f.get(lista.get(i-1));
						date[i][indexColoana] = valoare.toString();
					} catch (IllegalArgumentException | IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
		table = new JTable(date,coloane);
		return table;
	}
}