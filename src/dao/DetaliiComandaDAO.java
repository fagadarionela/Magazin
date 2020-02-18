package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import connection.ConnectionFactory;
import model.DetaliiComanda;

public class DetaliiComandaDAO extends AbstractDAO<DetaliiComanda> {
	private final static String findPretStatementString = "SELECT * FROM produs WHERE pret BETWEEN ? AND ?";	
	public List<DetaliiComanda> findByPret(int pretMin,int pretMax) {
		DetaliiComanda toReturn = null;
		List<DetaliiComanda> produse = new ArrayList<DetaliiComanda>();
		
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
			try {
				findStatement =  dbConnection.prepareStatement(findPretStatementString);
				findStatement.setInt(1, pretMin);
				findStatement.setInt(2, pretMax);
				rs = findStatement.executeQuery();
				while(rs.next()) {
					int id = rs.getInt("id");
					int idprodus = rs.getInt("idprodus");
					int pret = rs.getInt("pret");
					toReturn= new DetaliiComanda(id,idprodus,pret);
					produse.add(toReturn);
				}
				ConnectionFactory.close(rs);
				ConnectionFactory.close(findStatement);
				ConnectionFactory.close(dbConnection);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return produse;
	}
	/**
	 * Aceeasi metoda cu cea de sus, doar ca se returneaza o lista.
	 * @param id id-ul dupa care se cauta
	 * @return  obiectele rezultate din interogare sau null, in cazul in care nu se poate executa
	 */
	public List<DetaliiComanda> findByIdList(int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createSelectQuery("id");
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
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
}