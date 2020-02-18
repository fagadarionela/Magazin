package bll.validators;


import dao.ClientDAO;
import dao.ComandaDAO;
import dao.ProdusDAO;
import model.Client;
import model.Comanda;
import model.Produs;

public class IdValidator<T> implements Validator<T> {
	@Override
	public void validate(T t) {
		if (t instanceof Client) {
			try {
				if (((Client) t).getId()<0) throw new IllegalArgumentException("ID-ul trebuie sa fie positiv");
				for(Client c: ClientDAO.class.newInstance().findAll())
				{
					if (((Client) t).getId() == c.getId())
						throw new IllegalArgumentException("Id Client trebuie sa fie unic!");
					
				}
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		else if(t instanceof Produs) {
			try {
				if (((Produs) t).getId()<0) throw new IllegalArgumentException("ID-ul trebuie sa fie positiv");
				for(Produs p: ProdusDAO.class.newInstance().findAll())
				{
					if (((Produs) t).getId() == p.getId())
						throw new IllegalArgumentException("Id Produs trebuie sa fie unic!");
					
				}
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		else if(t instanceof Comanda) {
			try {
				if (((Comanda) t).getId()<0 || ((Comanda)t).getIdclient()<0 || ((Comanda)t).getIdprodus()<0) throw new IllegalArgumentException("ID-ul trebuie sa fie positiv");
				for(Comanda c: ComandaDAO.class.newInstance().findAll())
				{
					if (((Comanda) t).getId() == c.getId())
						throw new IllegalArgumentException("Id Comanda trebuie sa fie unic!");
					
				}
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
}
