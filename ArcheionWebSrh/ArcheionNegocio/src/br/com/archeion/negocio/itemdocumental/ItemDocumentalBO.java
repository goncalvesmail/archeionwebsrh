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
 * Classe reponsável pelo métodos de negócio relacionados à manutenção de Item Documental.
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
	 * @throws BusinessException Caso ocorra algum erro de negócio
	 * @throws CadastroDuplicadoException Caso haja um cadastro duplicado
	 */
	@Secured({ "ROLE_ATUALIZAR_ITEM_DOCUMENTAL" })
	@Transactional
	@Log(descricao="Alteração")
	ItemDocumental merge(ItemDocumental item) throws BusinessException, CadastroDuplicadoException;
	
	/**
	 * Remove um Item Documental
	 * @param item Item Documental a ser removido
	 * @throws BusinessException Caso haja um erro de negócio
	 */
	@Secured({ "ROLE_REMOVER_ITEM_DOCUMENTAL" })
	@Transactional
	@Log(descricao="Exclusão")
	void remove(ItemDocumental item) throws BusinessException;
	
	/**
	 * Insere um Item Documental
	 * @param item Item Documental a ser inserido
	 * @return Item Documental sincronizado com o banco
	 * @throws CadastroDuplicadoException Caso haja algum item duplicado
	 */
	@Secured({ "ROLE_INCLUIR_ITEM_DOCUMENTAL" })
	@Transactional
	@Log(descricao="Inclusão")
	ItemDocumental persist(ItemDocumental item) throws CadastroDuplicadoException;
	
	/**
	 * Gera o relatório de Itens Documentais
	 * @param parameters Parametros para geração do relatório
	 * @param localRelatorio Local a ser gerado o relatório
	 * @return Relatório de Item Documental
	 */
	@Secured({ "ROLE_IMPRIMIR_ITEM_DOCUMENTAL" })
	Relatorio getRelatorio(HashMap<String, Object> parameters, String localRelatorio);
}
