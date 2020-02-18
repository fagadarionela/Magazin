package bll.validators;

import model.Comanda;
import model.Produs;

public class CantitateValidator<T> implements Validator<T> {
	@Override
	public void validate(T t) {
		if (t instanceof Comanda)
			if (((Comanda) t).getCantitate() <= 0) {
			throw new IllegalArgumentException("Conditia de cantitate nu e respectata!");
		}
		if (t instanceof Produs)
			if (((Produs) t).getCantitate()  <= 0) {
			throw new IllegalArgumentException("Conditia de cantitate nu e respectata!");
		}
	}
}
