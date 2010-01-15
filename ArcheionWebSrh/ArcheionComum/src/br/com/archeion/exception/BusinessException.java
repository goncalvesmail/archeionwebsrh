package br.com.archeion.exception;

import java.util.List;

/**
 * Exce��o base para todas as exce��es de neg�cio.
 */
public class BusinessException extends ArcheionException {

	/**
	 * serialVersionUID do objeto BusinessException.java
	 */
	private static final long serialVersionUID = -2293411545446615058L;

	/**
	 * Lista de exce��es.
	 */
	private final List<BusinessException> exceptions;

	/**
	 * @param message
	 *            Mensagem que ser� exibida
	 */
	public BusinessException(final String message) {
		this(message, null);
	}

	/**
	 * @param exceptions
	 *            Lista de exce��es.
	 */
	public BusinessException(final List<BusinessException> exceptions) {
		this(null, exceptions);
	}

	/**
	 * @param message
	 *            Mensagem.
	 * @param exceptions
	 *            Lista de exce��es.
	 */
	public BusinessException(final String message,
			final List<BusinessException> exceptions) {
		super(message);
		this.exceptions = exceptions;
	}

	/**
	 * @return O campo exceptions
	 */
	public List<BusinessException> getExceptions() {
		return exceptions;
	}
}
