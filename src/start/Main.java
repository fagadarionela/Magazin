package start;

import java.sql.SQLException;

import java.util.logging.Logger;

import presentation.Controller;
import presentation.View;
import presentation.ViewClient;
import presentation.ViewComanda;
import presentation.ViewProdus;


public class Main {
	protected static final Logger LOGGER = Logger.getLogger(Main.class.getName());

	public static void main(String[] args) throws SQLException {
		View view = new View();
		ViewClient viewClient = new ViewClient();
		ViewProdus viewProdus = new ViewProdus();
		ViewComanda viewComanda = new ViewComanda(viewClient,viewProdus);
		new Controller(view,viewClient,viewProdus,viewComanda);
		view.setVisible(true);
	}
}