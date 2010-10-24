package br.com.archeion.negocio.itemdocumental;

import java.util.HashMap;
import java.util.List;

import org.acegisecurity.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

import util.Relatorio;
import br.com.archeion.exception.BusinessException;
import br.com.archeion.exception.CadastroDuplicadoException;
import br.com.archeion.modelo.itemdocumental.ItemDocumental;
import br.com.archeion.util.Log;


/**
 * Classe repons�vel pelo m�todos de neg�cio relacionados � manuten��o de Item Documental.
 * 
 * @author SInforme
 */
public interface ItemDocumentalBO {
	
	/**
	 * Busca todos os Itens Documentais
	 * @return Lista de Itens Documentais
	 */
	@Secured({ "ROLE_BUSCAR_ITEM_DOCUMENTAL" })
	List<ItemDocumental> findAll();
	
	/**
	 * Busca um Item Documental a partir do seu ID
	 * @param id ID do Item Documental
	 * @return Item Documental com o referido ID
	 */
	@Secured({ "ROLE_BUSCAR_ITEM_DOCUMENTAL" })
	ItemDocumental findById(Long id);
	
	/**
	 * Atualiza um Item Documental
	 * @param item Item Documental a ser atualizado
	 * @return Item Documental sincronizado com o banco
	 * @throws BusinessException Caso ocorra algum erro de neg�cio
	 * @throws CadastroDuplicadoException Caso haja um cadastro duplicado
	 */
	@Secured({ "ROLE_ATUALIZAR_ITEM_DOCUMENTAL" })
	@Transactional
	@Log(descricao="Altera��o")
	ItemDocumental merge(ItemDocumental item) throws BusinessException, CadastroDuplicadoException;
	
	/**
	 * Remove um Item Documental
	 * @param item Item Documental a ser removido
	 * @throws BusinessException Caso haja um erro de neg�cio
	 */
	@Secured({ "ROLE_REMOVER_ITEM_DOCUMENTAL" })
	@Transactional
	@Log(descricao="Exclus�o")
	void remove(ItemDocumental item) throws BusinessException;
	
	/**
	 * Insere um Item Documental
	 * @param item Item Documental a ser inserido
	 * @return Item Documental sincronizado com o banco
	 * @throws CadastroDuplicadoException Caso haja algum item duplicado
	 */
	@Secured({ "ROLE_INCLUIR_ITEM_DOCUMENTAL" })
	@Transactional
	@Log(descricao="Inclus�o")
	ItemDocumental persist(ItemDocumental item) throws CadastroDuplicadoException;
	
	/**
	 * Gera o relat�rio de Itens Documentais
	 * @param parameters Parametros para gera��o do relat�rio
	 * @param localRelatorio Local a ser gerado o relat�rio
	 * @return Relat�rio de Item Documental
	 */
	@Secured({ "ROLE_IMPRIMIR_ITEM_DOCUMENTAL" })
	Relatorio getRelatorio(HashMap<String, Object> parameters, String localRelatorio);
}
