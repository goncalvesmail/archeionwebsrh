package br.com.archeion.negocio.tipodocumento;

import java.util.HashMap;
import java.util.List;

import org.acegisecurity.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

import util.Relatorio;
import br.com.archeion.exception.BusinessException;
import br.com.archeion.exception.CadastroDuplicadoException;
import br.com.archeion.modelo.tipodocumento.TipoDocumento;
import br.com.archeion.util.Log;

/**
 * Classe repons�vel pelo m�todos de neg�cio relacionados � manuten��o de Tipos de Documentos.
 * 
 * @author SInforme
 */
public interface TipoDocumentoBO {
	
	/**
	 * Busca todos os Tipos de Documentos
	 * @return Lista de Tipos de Documentos
	 */
	@Secured({ "ROLE_BUSCAR_TIPO_DOCUMENTO" })
	List<TipoDocumento> findAll();
	
	/**
	 * Busca um Tipo de Documento a partir de um ID
	 * @param id ID do Tipo de Documento
	 * @return Tipo de Documento com o referido ID
	 */
	@Secured({ "ROLE_BUSCAR_TIPO_DOCUMENTO" })
	TipoDocumento findById(Long id);
	
	/**
	 * Atualiza um Tipo de Documento
	 * @param item Tipo de Documento a ser atualizado
	 * @return Tipo de Documento sincronizado com o banco
	 * @throws BusinessException Caso ocorra algum erro de neg�cio
	 * @throws CadastroDuplicadoException Caso haja algum cadastro duplicado
	 */
	@Secured({ "ROLE_ATUALIZAR_TIPO_DOCUMENTO" })
	@Transactional
	@Log(descricao="Altera��o")
	TipoDocumento merge(TipoDocumento item) throws BusinessException, CadastroDuplicadoException;
	
	/**
	 * Exclui um Tipo de Documento
	 * @param item Tipo de Documento a ser excluido
	 * @throws BusinessException Caso haja algum erro de neg�cio
	 */
	@Secured({ "ROLE_REMOVER_TIPO_DOCUMENTO" })
	@Transactional
	@Log(descricao="Exclus�o")
	void remove(TipoDocumento item) throws BusinessException;
	
	/**
	 * Inclui um novo Tipo de Documento
	 * @param item Tipo de Documento a ser criado
	 * @return Tipo de Documento sincronizado com o banco
	 * @throws CadastroDuplicadoException Caso haja algum cadastro duplicado
	 */
	@Secured({ "ROLE_INCLUIR_TIPO_DOCUMENTO" })
	@Transactional
	@Log(descricao="Inclus�o")
	TipoDocumento persist(TipoDocumento item) throws CadastroDuplicadoException;
	
	/**
	 * Gera o relat�rio de Tipo de Documento de acordo com os parametros
	 * @param parameters Parametros para gera��o do relat�rio
	 * @param localRelatorio Local de gera��o do relat�rio
	 * @return Relat�rio de Tipo de Documento
	 */
	@Secured({ "ROLE_IMPRIMIR_TIPO_DOCUMENTO" })
	Relatorio getRelatorio(HashMap<String, Object> parameters, String localRelatorio);
}
