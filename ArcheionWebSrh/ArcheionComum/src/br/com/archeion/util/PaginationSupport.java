package br.com.archeion.util;

import java.util.List;

/**
 * <p>
 * Interface utilizada pelo componente de paginação sob demanda para o acesso a
 * base de dados.
 * </p>
 * 
 * <p>
 * Define tipos diferentes para os parâmetros de busca e de retorno do método
 * <code>search</code>. Se os tipos forem iguais, a classe que implementar esta
 * interface pode definir o mesmo tipo para os dois parâmetros, exemplo.:
 * <code>MyObjImpl implements PaginationSupport&lt;Obj, Obj&gt;</code>.
 * </p>
 * 
 * @author Tarso Bessa
 * @author Daniel Sousa
 * 
 * @param <E>
 *            Tipo do parâmetro de retorno
 * @param <S>
 *            Tipo do parâmetro de busca
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
	 * Retorna uma lista de objetos que satisfazem aos parâmetros da consulta
	 * <code>searchParameters</code>, limitado aos índices startIndex e
	 * pageSize.
	 * 
	 * @param searchParameters
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	List<E> search(S searchParameters, int startIndex, int pageSize);

}

