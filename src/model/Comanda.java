package model;

public class Comanda {
	private int id;
	private int idclient;
	private int idprodus;
	private int cantitate;
	
	public Comanda() {
		super();
	}
	public Comanda(int id,int idclient, int idprodus,int cantitate) {
		this.id = id;
		this.idclient = idclient;
		this.idprodus = idprodus;
		this.cantitate = cantitate;
	}
	
	public int getId() {
		return id;
	}
	public int getIdclient() {
		return idclient;
	}
	public int getIdprodus() {
		return idprodus;
	}
	public int getCantitate() {
		return cantitate;
	}
	@Override
	public String toString() {
		return "Comanda [id=" + id + ", idclient=" + idclient + ", idprodus=" + idprodus + ", cantitate=" + cantitate
				+ "]";
	}
	
}
