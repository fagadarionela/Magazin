package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.ConnectionFactory;
import model.Produs;

public class ProdusDAO extends AbstractDAO<Produs> {
	private final static String findCantitateStatementString = "SELECT * FROM produs WHERE cantitate BETWEEN ? AND ?";
	private final static String findPretStatementString = "SELECT * FROM produs WHERE pret BETWEEN ? AND ?";	
	
	private String createUpdateQuery(String field) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ");
		sb.append(" produs");
		sb.append(" SET " + field + " =?");
		sb.append(" WHERE id"+ " =?");
		return sb.toString();
	}
	public List<Produs> findByCantitate(int cantitateMin,int cantitateMax) {
		Produs toReturn = null;
		List<Produs> produse = new ArrayList<Produs>();
		
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
			try {
				findStatement =  dbConnection.prepareStatement(findCantitateStatementString);
				findStatement.setInt(1, cantitateMin);
				findStatement.setInt(2, cantitateMax);
				rs = findStatement.executeQuery();
				while(rs.next()) {
					int id = rs.getInt("id");
					String nume = rs.getString("nume");
					int cantitate = rs.getInt("cantitate");
					int pret = rs.getInt("pret");
					toReturn= new Produs(id,nume,cantitate,pret);
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
	public List<Produs> findByPret(int pretMin,int pretMax) {
		Produs toReturn = null;
		List<Produs> produse = new ArrayList<Produs>();
		
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
					String nume = rs.getString("nume");
					int cantitate = rs.getInt("cantitate");
					int pret = rs.getInt("pret");
					toReturn= new Produs(id,nume,cantitate,pret);
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

	public Produs update(Produs c) {
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
			try {
				for(int i=0;i<3;i++) {
					if (i==0) {
						String query = createUpdateQuery("nume");
						findStatement =  dbConnection.prepareStatement(query);
						findStatement.setString(1, c.getNume());
					}
					else if (i==1) {
						String query = createUpdateQuery("cantitate");
						findStatement =  dbConnection.prepareStatement(query);
						findStatement.setInt(1, c.getCantitate());
					}
					else if (i==2) {
						String query = createUpdateQuery("pret");
						findStatement =  dbConnection.prepareStatement(query);
						findStatement.setInt(1, c.getPret());
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
	public Produs updateStoc(int id,int cantitate) throws Exception {
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		Produs produsGasit = new Produs();
			try {
				String query = createUpdateQuery("cantitate");
				findStatement =  dbConnection.prepareStatement(query);
				try {
					for(Produs p: ProdusDAO.class.newInstance().findAll()) {
						if (p.getId() == id) produsGasit = p;
					}
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
				if (produsGasit.getCantitate()-cantitate<0) {
					throw new Exception("Stoc epuizat! Sunt "+produsGasit.getCantitate()+" produse");
				}
				else System.out.println("Se actualizeaza stocul...");
				findStatement.setInt(1, produsGasit.getCantitate()-cantitate );
				findStatement.setInt(2, id);
				findStatement.executeUpdate();
				ConnectionFactory.close(findStatement);
				ConnectionFactory.close(dbConnection);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return produsGasit;
	}
}