package br.com.archeion.negocio.funcionalidade;

import java.util.List;
import br.com.archeion.modelo.funcionalidade.Funcionalidade;

/**
 * Classe reponsável pelo métodos de negócio relacionados à manutenção de Funcionalidade.
 * 
 * @author SInforme
 */
public interface FuncionalidadeBO {

	/**
	 * Retorna a lista com todas as Funcionalidades.
	 * @return List<Funcionalidade>
	 */
	List<Funcionalidade> findAll();
	
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
