package br.com.archeion.negocio.ttd;

import java.util.HashMap;
import java.util.List;

import org.acegisecurity.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

import util.Relatorio;
import br.com.archeion.exception.BusinessException;
import br.com.archeion.exception.CadastroDuplicadoException;
import br.com.archeion.modelo.ttd.TTD;
import br.com.archeion.util.Log;

/**
 * Classe reponsável pelo métodos de negócio relacionados à manutenção de TTD (Tabela de Temporaridade de Documentos).
 * 
 * @author SInforme
 */
public interface TTDBO {
	
	/**
	 * Busca todas as TTD
	 * @return Lista de TTD
	 */
	@Secured({ "ROLE_BUSCAR_TTD" })
	List<TTD> findAll();
	
	/**
	 * Busca uma TTD a partir de um ID
	 * @param id ID desejado
	 * @return TTD com o referido ID
	 */
	@Secured({ "ROLE_BUSCAR_TTD" })
	TTD findById(Long id);
	
	/**
	 * Busca as TTD de acordo com a Empresa e/ou Local e/ou Item Documental
	 * @param emp Empresa com ID
	 * @param local Local com ID
	 * @param item Item Documental com ID
	 * @return Lista de TTD que contemple os parametros passados
	 */
	@Secured({ "ROLE_BUSCAR_TTD" })
	List<TTD> findByEmpresaLocalItemDocumental(int emp, int local, int item);
	
	/**
	 * Busca uma TTD a partir de um Evento de Contagem
	 * @param idEvento ID do Evento de Contagem
	 * @return Lista de TTD do referido Evento de Contagem
	 */
	@Secured({ "ROLE_BUSCAR_TTD" })
	List<TTD> findByEvento(Long idEvento);
	
	/**
	 * Atualiza uma TTD
	 * @param ttd TTD a ser atualizada
	 * @return TTD sincronizada com o banco
	 * @throws BusinessException Caso ocorra algum erro de negócio
	 * @throws CadastroDuplicadoException Caso haja algum cadastro duplicado
	 */
	@Secured({ "ROLE_ATUALIZAR_TTD" })
	@Transactional
	@Log(descricao="Alteração")
	TTD merge(TTD ttd) throws BusinessException, CadastroDuplicadoException;
	
	/**
	 * Remove uma TTD
	 * @param ttd TTD a ser removida
	 * @throws BusinessException Caso ocorra algum erro de negócio
	 */
	@Secured({ "ROLE_REMOVER_TTD" })
	@Transactional
	@Log(descricao="Exclusão")
	void remove(TTD ttd) throws BusinessException;
	
	/**
	 * Insere uma nova TTD 
	 * @param ttd TTD a ser incluida
	 * @return TTD sincronizada com o banco
	 * @throws CadastroDuplicadoException Caso haja algum cadastro duplicado
	 * @throws BusinessException Caso ocorra algum erro de negócio
	 */
	@Secured({ "ROLE_INCLUIR_TTD" })
	@Transactional
	@Log(descricao="Inclusão")
	TTD persist(TTD ttd) throws CadastroDuplicadoException, BusinessException;
	
	/**
	 * Gera o realtório de TTD
	 * @param parameters Parametros para geração do relatório
	 * @param localRelatorio Local de geração do relatório
	 * @return Relatório de TTD
	 */
	@Secured({ "ROLE_IMPRIMIR_TTD" })
	@Transactional
	Relatorio getRelatorio(HashMap<String, Object> parameters, String localRelatorio);

}
