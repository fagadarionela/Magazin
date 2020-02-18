package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.ConnectionFactory;
import model.Comanda;

public class ComandaDAO extends AbstractDAO<Comanda> {
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
	public List<Comanda> findByCantitate(int cantitateMin,int cantitateMax) {
		Comanda toReturn = null;
		List<Comanda> produse = new ArrayList<Comanda>();
		
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
					int idclient = rs.getInt("idclient");
					int idprodus = rs.getInt("idprodus");
					int cantitate = rs.getInt("cantitate");
					toReturn= new Comanda(id,idclient,idprodus,cantitate);
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
	public List<Comanda> findByPret(int pretMin,int pretMax) {
		Comanda toReturn = null;
		List<Comanda> produse = new ArrayList<Comanda>();
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
					int idclient = rs.getInt("idclient");
					int idprodus = rs.getInt("idprodus");
					int cantitate = rs.getInt("cantitate");
					toReturn= new Comanda(id,idclient,idprodus,cantitate);
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
	public Comanda update(Comanda c) {
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
			try {
				for(int i=0;i<3;i++) {
					if (i==0) {
						String query = createUpdateQuery("idclient");
						findStatement =  dbConnection.prepareStatement(query);
						findStatement.setInt(1, c.getIdclient());
					}
					else if (i==1) {
						String query = createUpdateQuery("idprodus");
						findStatement =  dbConnection.prepareStatement(query);
						findStatement.setInt(1, c.getIdprodus());
					}
					else if (i==2){
						String query = createUpdateQuery("cantitate");
						findStatement =  dbConnection.prepareStatement(query);
						findStatement.setInt(1, c.getCantitate());
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