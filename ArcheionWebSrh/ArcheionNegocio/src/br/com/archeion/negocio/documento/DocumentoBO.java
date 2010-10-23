package br.com.archeion.negocio.documento;

import java.util.HashMap;
import java.util.List;

import org.acegisecurity.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

import util.Relatorio;

import br.com.archeion.exception.BusinessException;
import br.com.archeion.exception.CadastroDuplicadoException;
import br.com.archeion.exception.CompareDateException;
import br.com.archeion.modelo.documento.Documento;
import br.com.archeion.util.Log;

/**
 * Classe reponsável pelo métodos de negócio relacionados à manutenção de Documentos.
 * 
 * @author SInforme
 */
public interface DocumentoBO {	

	/**
	 * Buscas os documentos a partir do ID de uma Pasta
	 * @param id ID da Pasta
	 * @return Lista de Documentos
	 */
	@Secured({ "ROLE_BUSCAR_DOCUMENTO" })
	List<Documento> findByPasta(Long id);
	
	/**
	 * Busca os Documentos a partir do seu Tipo
	 * @param id ID do Tipo desejado
	 * @return Lista de Documentos
	 */
	@Secured({ "ROLE_BUSCAR_DOCUMENTO" })
	List<Documento> findByTipo(Long id);
	
	/**
	 * Busca os Documentos a partir do ID de um Local
	 * @param id ID do Local
	 * @return Lista de Documentos
	 */
	@Secured({ "ROLE_BUSCAR_DOCUMENTO" })
	List<Documento> findByLocal(Long id);
	
	/**
	 * Consulta Documentos a patir de uma consulta pré-definida
	 * @param where Consulta que deseja realizar
	 * @return Lista de Documentos
	 */
	@Secured({ "ROLE_BUSCAR_DOCUMENTO" })
	List<Documento> consultarDocumento(String where);
	
	/**
	 * Busca todos os Documento
	 * @return Lista com todos os Documentos
	 */
	@Secured({ "ROLE_BUSCAR_DOCUMENTO" })
	List<Documento> findAll();
	
	/**
	 * Busca um Documento a partir de um ID
	 * @param id ID de um Documento
	 * @return Documento com o respectivo ID, ou null caso não exista
	 */
	@Secured({ "ROLE_BUSCAR_DOCUMENTO" })
	Documento findById(Long id);
	
	/**
	 * Atualiza um Documento
	 * @param documento Documento que se deseja atualizar
	 * @return Documento sincronizado com o banco
	 * @throws BusinessException Caso ocorra algum erro de negócio
	 * @throws CompareDateException Caso a Data do Documento seja maior que a atual
	 * @throws CadastroDuplicadoException Caso haja um cadastro duplicado
	 */
	@Secured({ "ROLE_ATUALIZAR_DOCUMENTO" })
	@Transactional
	@Log(descricao="Alteração")
	Documento merge(Documento documento) throws BusinessException, CompareDateException, CadastroDuplicadoException;
	
	/**
	 * Exclui um Documento
	 * @param documento Documento que se deseja excluir
	 * @throws BusinessException Caso ocorra algum erro de negócio
	 */
	@Secured({ "ROLE_REMOVER_DOCUMENTO" })
	@Transactional
	@Log(descricao="Exclusão")
	void remove(Documento documento) throws BusinessException;
	
	/**
	 * Inclui um Documento
	 * @param documento Documento que se deseja inserir
	 * @return Documento sincronizado com o banco
	 * @throws BusinessException Caso ocorra algum erro de negócio
	 * @throws CadastroDuplicadoException Caso haja um cadastro duplicado
	 */
	@Secured({ "ROLE_INCLUIR_DOCUMENTO" })
	@Transactional
	@Log(descricao="Inclusão")
	Documento persist(Documento documento)throws BusinessException, CadastroDuplicadoException;
	
	/**
	 * Monta o relatório de Documentos
	 * @param parameters Parametros para formar o Relatório
	 * @param localRelatorio Local para geração do Relatório
	 * @return Relatório de Documentos
	 */
	@Secured({ "ROLE_IMPRIMIR_DOCUMENTO" })
	@Transactional
	Relatorio getRelatorio(HashMap<String, Object> parameters, String localRelatorio);
}
