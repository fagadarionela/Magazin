package bll.validators;

import model.Client;

public class VarstaValidator implements Validator<Client> {
		private static final int MIN_AGE = 18;
		private static final int MAX_AGE = 100;

		public void validate(Client t) {

			if (t.getVarsta() < MIN_AGE || t.getVarsta() > MAX_AGE) {
				throw new IllegalArgumentException("Limita de varsta nu e acceptata!");
			}

		}

}
