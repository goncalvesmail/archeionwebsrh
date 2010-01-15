package br.com.archeion.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import br.com.archeion.negocio.context.DBContext;

/**
 * Classe respons�vel por inicializar e destruir o banco.
 
 */
public class DBListener implements ServletContextListener {

	/**
	 * Inicializa e d� o shutdown do banco.
	 */
	private DBContext context;

	/**
	 * 
	 */
	public DBListener() {
		context = new DBContext();
	}

	/**
	 * D� o shutdown no banco de dados.
	 * 
	 * @param arg0
	 *            context
	 */
	public void contextDestroyed(final ServletContextEvent arg0) {
		//context.shutdown();
	}

	/**
	 * Inicializa o banco de dados.
	 * 
	 * @param evt
	 *            context
	 */
	public void contextInitialized(final ServletContextEvent evt) {
		//context.init();
	}

}
