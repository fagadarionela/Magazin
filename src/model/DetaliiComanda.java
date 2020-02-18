package model;

public class DetaliiComanda {
	int id;
	int idprodus;
	int pret;
	public DetaliiComanda() {
		super();
	}
	public DetaliiComanda(int id, int idprodus, int pret) {
		super();
		this.id = id;
		this.idprodus = idprodus;
		this.pret = pret;
	}
	public int getId() {
		return id;
	}
	public int getIdprodus() {
		return idprodus;
	}
	public int getPret() {
		return pret;
	}
	@Override
	public String toString() {
		return "idprodus=" + idprodus + ", pret=" + pret;
	}
	
}
