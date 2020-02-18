package bll.validators;


import dao.ClientDAO;
import dao.ProdusDAO;
import model.Client;
import model.Comanda;
import model.Produs;

public class IdClientSiProdusValidator implements Validator<Comanda> {
	boolean gasit = false;
	public void validate(Comanda t) {
		try {
			for(Client c: ClientDAO.class.newInstance().findAll())
			{
				if (t.getIdclient() == c.getId())
					gasit = true;				
			}
			if (gasit == false) throw new IllegalArgumentException("Client ID necunoscut!");
			gasit = false;
			for(Produs p: ProdusDAO.class.newInstance().findAll()) {
				if (t.getIdprodus() == p.getId())
					gasit = true;
			}
			if (gasit == false) throw new IllegalArgumentException("Produs ID necunoscut!");
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
