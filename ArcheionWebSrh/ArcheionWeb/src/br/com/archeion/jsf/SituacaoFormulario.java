/**
 * 
 */
package br.com.archeion.jsf;

/**
 * Define se o Formul�rio � para edi��o, inser��o, detalhamento, ...
 */
public enum SituacaoFormulario {
	/**
	 * N�o est� sendo realizada NENHUMA a��o pelo formul�rio.
	 * Isto �, ele n�o est� sendo utilizado.
	 */
	NENHUM,
	
	/**
	 * O formul�rio est� sendo utilizado para inser��o de dados.
	 */
	INCLUINDO,
	
	/**
	 * O formul�rio est� sendo utilizado para altera��o dos dados.
	 */
	ALTERANDO,
	
	/**
	 * O formul�rio est� sendo utilizado para detalhamento dos dados.
	 */
	DETALHANDO;
}