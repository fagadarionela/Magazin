package model;


public class Client {
	private int id;
	private String nume;
	private int varsta;
	private String adresa;
	private String email;
	
	public Client() {
		super();
	}
	public Client(int idclient, String nume, int varsta, String adresa, String email) {
		super();
		this.id = idclient;
		this.nume = nume;
		this.varsta = varsta;
		this.adresa = adresa;
		this.email = email;
	}
	public Client(String nume, int varsta, String adresa, String email) {
		super();
		this.nume = nume;
		this.varsta = varsta;
		this.adresa = adresa;
		this.email = email;
	}
	public int getId() {
		return id;
	}
	public String getNume() {
		return nume;
	}
	public int getVarsta() {
		return varsta;
	}
	public String getAdresa() {
		return adresa;
	}
	public String getEmail() {
		return email;
	}
	@Override
	public String toString() {
		return "Client " + id+ ", " + nume ;
	}
}