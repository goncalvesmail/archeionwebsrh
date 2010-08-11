package br.com.archeion.util;

import java.util.List;

/**
 * <p>
 * Interface utilizada pelo componente de pagina��o sob demanda para o acesso a
 * base de dados.
 * </p>
 * 
 * <p>
 * Define tipos diferentes para os par�metros de busca e de retorno do m�todo
 * <code>search</code>. Se os tipos forem iguais, a classe que implementar esta
 * interface pode definir o mesmo tipo para os dois par�metros, exemplo.:
 * <code>MyObjImpl implements PaginationSupport&lt;Obj, Obj&gt;</code>.
 * </p>
 * 
 * @author Tarso Bessa
 * @author Daniel Sousa
 * 
 * @param <E>
 *            Tipo do par�metro de retorno
 * @param <S>
 *            Tipo do par�metro de busca
 */
public interface PaginationSupport<E, S> {
	/**
	 * Retorna a quantidade de itens da consulta.
	 * 
	 * @param searchParameters
	 * @return
	 */
	int count(S searchParameters);

	/**
	 * Retorna uma lista de objetos que satisfazem aos par�metros da consulta
	 * <code>searchParameters</code>, limitado aos �ndices startIndex e
	 * pageSize.
	 * 
	 * @param searchParameters
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	List<E> search(S searchParameters, int startIndex, int pageSize);

}

