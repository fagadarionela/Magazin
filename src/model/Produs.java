package model;

public class Produs {
	private int id;
	private String nume;
	private int cantitate;
	private int pret;
	
	public Produs() {
		super();
	}
	public Produs(int id,String nume,int cantitate,int pret){
		super();
		this.id = id;
		this.nume = nume;
		this.cantitate = cantitate;
		this.pret = pret;
	}
	public int getId() {
		return id;
	}
	public String getNume() {
		return nume;
	}
	public int getCantitate() {
		return cantitate;
	}
	public int getPret() {
		return pret;
	}
	@Override
	public String toString() {
		return "Produs: " + nume + " pret=" + pret + "]";
	}
}
