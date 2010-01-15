/**
 * 
 */
package br.com.archeion.exception;


/**
 * Exce��o que ser� lan�ada quando for feita compara��o de datas.
 *
 */
public class CompareDateException extends BusinessException {

	/**
	 * serialVersionUID do objeto CompareDateException.java
	 */
	private static final long serialVersionUID = 2390474261011377204L;
	/**
	 * Mensagem exibida para o usu�rio caso nenhuma outra seja passada pelo construtor.
	 */
	private static final String ERROR_BUSINESS_COMPARE_DATE = "error.business.compareDate";

	/**
	 * 
	 */
	public CompareDateException() {
		this(ERROR_BUSINESS_COMPARE_DATE);
	}

	/**
	 * @param message Mensagem exibida para o usu�rio 
	 */
	public CompareDateException(final String message) {
		super(message);
	}

}
