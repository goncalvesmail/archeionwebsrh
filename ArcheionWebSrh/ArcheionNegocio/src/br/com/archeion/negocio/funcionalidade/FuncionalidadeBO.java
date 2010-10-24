package br.com.archeion.negocio.funcionalidade;

import java.util.List;
import br.com.archeion.modelo.funcionalidade.Funcionalidade;

/**
 * Classe repons�vel pelo m�todos de neg�cio relacionados � manuten��o de Funcionalidade.
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
	 * Busca uma Funcionalidade a partir de sua descri��o
	 * @param descricao Descri��o da Funcionalidade
	 * @return Funcionalidade com a respectiva descri��o
	 */
	Funcionalidade findByDescricao(String descricao);

}
