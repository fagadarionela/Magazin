package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.ConnectionFactory;
import model.Client;

public class ClientDAO extends AbstractDAO<Client> {
	private final static String findVarstaStatementString = "SELECT * FROM client WHERE varsta BETWEEN ? AND ?";
	private final static String findAdresaStatementString = "SELECT * FROM client WHERE adresa= ?";
	private final static String findEmailStatementString = "SELECT * FROM client WHERE email=?";

	private String createUpdateQuery(String field) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ");
		sb.append(" client");
		sb.append(" SET " + field + " =?");
		sb.append(" WHERE id"+ " =?");
		return sb.toString();
	}
	public List<Client> findByVarsta(int varstaMin,int varstaMax) {
		Client toReturn = null;
		List<Client> clienti = new ArrayList<Client>();
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
			try {
				findStatement =  dbConnection.prepareStatement(findVarstaStatementString);
				findStatement.setInt(1, varstaMin);
				findStatement.setInt(2, varstaMax);
				rs = findStatement.executeQuery();
				while(rs.next()) {
					int clientId = rs.getInt("id");
					String nume = rs.getString("nume");
					int varsta = rs.getInt("varsta");
					String adresa = rs.getString("adresa");
					String email =rs.getString("email");
					toReturn= new Client(clientId,nume,varsta,adresa,email);
					clienti.add(toReturn);
				}
				ConnectionFactory.close(rs);
				ConnectionFactory.close(findStatement);
				ConnectionFactory.close(dbConnection);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return clienti;
	}
	public List<Client>  findByAdresa(String adresa) {
		Client toReturn = null;
		List<Client> clienti = new ArrayList<Client>();
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
			try {
				findStatement =  dbConnection.prepareStatement(findAdresaStatementString);
				findStatement.setString(1,adresa);
				rs = findStatement.executeQuery();
				while(rs.next()) {	
					int clientId = rs.getInt("id");
					String nume = rs.getString("nume");
					int varsta = rs.getInt("varsta");
					String email =rs.getString("email");
					toReturn= new Client(clientId,nume,varsta,adresa,email);
					clienti.add(toReturn);
				}
				ConnectionFactory.close(rs);
				ConnectionFactory.close(findStatement);
				ConnectionFactory.close(dbConnection);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return clienti;
	}
	public List<Client> findByEmail(String email) {
		Client toReturn = null;
		List<Client> clienti = new ArrayList<Client>();
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
			try {
				findStatement =  dbConnection.prepareStatement(findEmailStatementString);
				findStatement.setString(1,email);
				rs = findStatement.executeQuery();
				while(rs.next()) {	
					int clientId = rs.getInt("id");
					String nume = rs.getString("nume");
					int varsta = rs.getInt("varsta");
					String adresa =rs.getString("adresa");
					toReturn= new Client(clientId,nume,varsta,adresa,email);
					clienti.add(toReturn);
				}
				ConnectionFactory.close(rs);
				ConnectionFactory.close(findStatement);
				ConnectionFactory.close(dbConnection);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return clienti;
	}
	public Client update(Client c) {
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
			try {
				for(int i=0;i<4;i++) {
					if (i==0) {
						String query = createUpdateQuery("nume");
						findStatement =  dbConnection.prepareStatement(query);
						findStatement.setString(1, c.getNume());
					}
					else if (i==1) {
						String query = createUpdateQuery("varsta");
						findStatement =  dbConnection.prepareStatement(query);
						findStatement.setInt(1, c.getVarsta());
					}
					else if (i==2) {
						String query = createUpdateQuery("adresa");
						findStatement =  dbConnection.prepareStatement(query);
						findStatement.setString(1, c.getAdresa());
					}
					else if(i==3) {
						String query = createUpdateQuery("email");
						findStatement =  dbConnection.prepareStatement(query);
						findStatement.setString(1, c.getEmail());
					}
					findStatement.setInt(2, c.getId());
					findStatement.executeUpdate();
				}
				ConnectionFactory.close(findStatement);
				ConnectionFactory.close(dbConnection);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return c;
	}
}