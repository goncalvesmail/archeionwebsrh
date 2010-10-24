package br.com.archeion.persistencia.funcionalidade;

import br.com.archeion.modelo.funcionalidade.Funcionalidade;
import br.com.archeion.persistencia.GenericDAO;

/**
 * Classe repons�vel pelo pela manuten��o de Funcionalidades.
 * 
 * @author SInforme
 */
public interface FuncionalidadeDAO extends GenericDAO<Funcionalidade, Long> {
	
	/**
	 * Busca um Funcionalidade a partir do seu nome
	 * @param nome Nome da Funcionalidade
	 * @return Funcionalidade com o repectivo nome
	 */
	Funcionalidade findByName(String nome);
	
	/**
	 * Busca uma Funcionalidade a partir de sua descri��o
	 * @param descricao Descri��o da Funcionalidade
	 * @return Funcionalidade com a respectiva descri��o
	 */
	Funcionalidade findByDescricao(String descricao);
	
}
