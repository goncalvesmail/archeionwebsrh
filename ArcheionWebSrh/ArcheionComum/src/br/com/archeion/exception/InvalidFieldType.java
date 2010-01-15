/**
 * 
 */
package br.com.archeion.exception;

/**
 * Tipos de valida��es que ser�o feitas com as suas respectivas mensagens
 * internacionalizaveis.
  */
public enum InvalidFieldType {

	/**
	 * Quando houver uma exce��o com o CNPJ.
	 */
	CNPJ("MSG-23", ""),

	/**
	 * Quando houver uma exce��o com o CPF.
	 */
	CPF("MSG-11", ""),

	/**
	 * Quando houver uma exce��o com o RG.
	 */
	RG("MSG-12", ""),

	/**
	 * Quando houver uma exce��o com o NIT.
	 */
	NIT("MSG-10", ""),

	/**
	 * Quandou houver uma exce��o com o Email.
	 */
	EMAIL("MSG-21", ""),

	/**
	 * Quandou houver uma exce��o com o CEP.
	 */
	CEP("MSG-18", ""),
	
	/**
	 * Quandou houver uma exce��o com o ddd do Telefone.
	 */
	TELEFONE_DDD("MSG-221", ""),

	/**
	 * Quandou houver uma exce��o com o n�mero do Telefone.
	 */
	TELEFONE_NUMERO("MSG-19", ""),
	
	/**
	 * Quandou houver uma exce��o com o ddd do Celular.
	 */
	CELULAR_DDD("MSG-222", ""),

	/**
	 * Quandou houver uma exce��o com o n�mero do Celular.
	 */
	CELULAR_NUMERO("MSG-20", ""),

	/**
	 * Quandou houver uma exce��o com o n�mero da CTPS.
	 */
	CTPS_NUMERO("MSG-14", ""),

	/**
	 * Quandou houver uma exce��o com o n�mero do T�tulo Eleitoral.
	 */
	TITULO_ELEITORAL_NUMERO("MSG-16", ""),

	/**
	 * Quando houver uma exce��o com um n�mero.
	 */
	NUMERO("error.business.invalidNumero", ""),
	
	/**
	 * Quando houver uma exce��o com uma senha.
	 */
	SENHA("MSG-206", "");
	

	/**
	 * Mensagem que ser� exibida para o usu�rio.
	 */
	private final String mensagem;

	/**
	 * Mensagem que ser� exibida para o usu�rio.
	 * 
	 * Essa mensagem possui par�metros.
	 */
	private final String mensagemWithParameter;

	/**
	 * @param msg
	 *            Mensagem que ser� exibida para o usu�rio.
	 * @param msgWithParameter
	 *            Mensagem com par�metro.
	 */
	private InvalidFieldType(final String msg, final String msgWithParameter) {
		this.mensagem = msg;
		this.mensagemWithParameter = msgWithParameter;
	}

	/**
	 * @return O campo msg
	 */
	public String getMensagem() {
		return mensagem;
	}

	/**
	 * @return O campo mensagemWithParameter
	 */
	public String getMensagemWithParameter() {
		return mensagemWithParameter;
	}
}
