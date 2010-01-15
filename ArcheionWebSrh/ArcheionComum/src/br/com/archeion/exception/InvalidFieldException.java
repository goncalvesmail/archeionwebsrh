/**
 * 
 */
package br.com.archeion.exception;

import br.com.archeion.resources.MessagesResources;

/**
 * Exce��o lan�ada quando ocorreu uma exce��o de valida��o.
 */
public class InvalidFieldException extends BusinessException {

	/**
	 * serialVersionUID do objeto InvalidField.java
	 */
	private static final long serialVersionUID = -4221293304846677342L;

	/**
	 * Tipo de valida��o que ocorreu sem sucesso.
	 */
	private InvalidFieldType type;

	/**
	 * Par�metros
	 */
	private String[] params;

	/**
	 * @param type
	 *            Tipo de exce��o que ocorreu.
	 */
	public InvalidFieldException(final InvalidFieldType type) {
		this(type, type.getMensagem());
	}

	/**
	 * @param type
	 *            Tipo de exce��o que ocorreu.
	 * @param params
	 *            parametros
	 */
	public InvalidFieldException(final InvalidFieldType type, final String[] params) {
		this(type, type.getMensagemWithParameter());
		this.params = params;
	}

	/**
	 * @param type
	 *            Tipo de exce��o que ocorreu.
	 * @param mensagem
	 *            Mensagem que ser� exibida quando ocorrer um erro.
	 */
	public InvalidFieldException(final InvalidFieldType type, final String mensagem) {
		super(mensagem);
		this.type = type;
	}

	/**
	 * @return O campo type
	 */
	public InvalidFieldType getType() {
		return type;
	}

	@Override
	public String getErrorI18n() {
		return super.getErrorI18n(params);
	}

	/**
	 * @see br.com.archeion.exception.ArcheionException#getMessage()
	 * 
	 * @return Mensagem
	 */
	@Override
	public String getMessage() {
		if (params != null) {
			String[] fields = new String[params.length];
			int i = 0;
			for (String param : params) {
				fields[i] = MessagesResources.getBundleMessage(param);
			}
			return super.getErrorI18n(fields);
		} else {
			return super.getErrorI18n();
		}
	}
}
