package br.com.archeion.persistencia;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;

/**
 * Interface genérica para comunicação com o banco de dados
 * 
 * @author SInforme
 *
 * @param <T> Entidade a ser utilizada
 * @param <ID> Tipo do identificador da Entidade
 */
public interface GenericDAO<T, ID extends Serializable> {

	/**
	 * Busca a lista de todos os objetos da Entidade
	 * @return Lista com todos os objetos da Entidade
	 */
	List<T> findAll();

	/**
	 * Busca uma Entidade a partir do seu ID
	 * @param id ID da Entidade desejada
	 * @return Entidade sincronizada com o banco
	 */
	T findById(ID id);

	/**
	 * Atualiza uma Entidade no banco
	 * @param entity Entidade a ser atualizada
	 * @return Entidade sincronizada com o banco
	 */
	T merge(T entity);

	/**
	 * Insere uma nova Entidade no banco
	 * @param entity Entidade a ser inserida
	 * @return Entidade sincronizada com o banco
	 */
	T persist(T entity);

	/**
	 * Sincroniza a Entidade com o banco
	 * @param entity Entidade sincronizada com o banco
	 */
	void refresh(T entity);

	/**
	 * Remove uma Entidade do banco
	 * @param entity Entidade a ser removida
	 */
	void remove(T entity);

	/** 
	 * Remove uma Entidade a partir do seu ID
	 * @param id ID da Entidade a ser removida
	 */
	void removeById(ID id);
	
	/**
	 * Busca uma instância da Conexão utilizada pela JPA para utilizar 
	 * na geração dos relatórios 
	 * @return
	 */
	Connection getConnection();
}