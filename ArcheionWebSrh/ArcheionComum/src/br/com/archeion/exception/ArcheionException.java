/**
 * 
 */
package br.com.archeion.exception;

import org.apache.log4j.Logger;

import br.com.archeion.resources.MessagesResources;

/**
 * Exce��o base para toda a aplica��o.
  
 */
public class ArcheionException extends Exception {


	private static final long serialVersionUID = 2509265031138983635L;
	/**
	 * Log da exce��o.
	 */
	private static final Logger LOG = Logger.getLogger(ArcheionException.class);

	/**
	 * 
	 */
	public ArcheionException() {
		LOG.info("Instanciando exce��o");
	}

	/**
	 * @param message
	 *            Mensagem que ser� exibida
	 */
	public ArcheionException(final String message) {
		super(message);
		LOG.info("Instanciando exce��o");
	}

	/**
	 * @param message
	 *            Mensagem que ser� exibida
	 * @param cause
	 *            Causa da exce��o.
	 */
	public ArcheionException(final String message, final Throwable cause) {
		super(message, cause);
		LOG.info("Instanciando exce��o");
	}

	/**
	 * @param cause
	 *            Causa da exce��o.
	 */
	public ArcheionException(final Throwable cause) {
		super(cause);
		LOG.info("Instanciando exce��o");
	}

	/**
	 * @return Retorna a mensagem de erro internacionaliz�vel.
	 */
	public String getErrorI18n() {
		String message = super.getMessage();
		String i18n = null;
		if (message != null) {
			LOG.info("Recuperando mensagem de erro internacionalizada: " + message);
			i18n = MessagesResources.getBundleMessage(message);
		}
		return i18n;
	}

	/**
	 * @param params
	 *            Array com todos os par�metros.
	 * @return Retorna a mensagem de erro internacionaliz�vel.
	 */
	public String getErrorI18n(final String[] params) {
		String message = super.getMessage();
		LOG.info("Recuperando mensagem de erro internacionalizada: " + message);
		return MessagesResources.getBundleMessage(message, params);
	}

	/**
	 * 
	 * @see java.lang.Throwable#getMessage()
	 * 
	 * @return A mensagem internacionalizada.
	 */
	@Override
	public String getMessage() {
		return getErrorI18n();
	}
	
	public String getMessageCode() {
		return super.getMessage();
	}
}
