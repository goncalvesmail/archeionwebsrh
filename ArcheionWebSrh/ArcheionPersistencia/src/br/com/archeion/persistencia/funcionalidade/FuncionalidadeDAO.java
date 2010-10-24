package br.com.archeion.persistencia.funcionalidade;

import br.com.archeion.modelo.funcionalidade.Funcionalidade;
import br.com.archeion.persistencia.GenericDAO;

/**
 * Classe reponsável pelo pela manutenção de Funcionalidades.
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
	 * Busca uma Funcionalidade a partir de sua descrição
	 * @param descricao Descrição da Funcionalidade
	 * @return Funcionalidade com a respectiva descrição
	 */
	Funcionalidade findByDescricao(String descricao);
	
}
