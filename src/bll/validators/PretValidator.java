package bll.validators;

import model.Produs;

public class PretValidator<T> implements Validator<T> {
	@Override
	public void validate(T t) {
		if (t instanceof Produs)
			if (((Produs) t).getPret() < 0) {
			throw new IllegalArgumentException("Condifita de pret nu e respectata");
		}
	}
}
