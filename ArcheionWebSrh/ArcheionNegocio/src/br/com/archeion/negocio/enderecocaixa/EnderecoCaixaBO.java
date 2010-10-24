package br.com.archeion.negocio.enderecocaixa;

import java.util.HashMap;
import java.util.List;

import org.acegisecurity.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

import util.Relatorio;
import br.com.archeion.exception.BusinessException;
import br.com.archeion.exception.CadastroDuplicadoException;
import br.com.archeion.modelo.enderecocaixa.EnderecoCaixa;
import br.com.archeion.util.Log;

/**
 * Classe repons�vel pelo m�todos de neg�cio relacionados � manuten��o de Endere�o de Caixa
 * 
 * @author SInforme
 */
public interface EnderecoCaixaBO {	
	
	/**
	 * Busca todos os Endere�os de Caixa
	 * @return Lista de Endere�os de Caixa
	 */
	@Secured({ "ROLE_BUSCAR_ENDERECO_CAIXA" })
	List<EnderecoCaixa> findAll();
	
	/**
	 * Busca um Endere�o de Caixa a partir de um ID
	 * @param id ID do Endere�o a ser buscado
	 * @return Endere�o de Caixa com o respectivo ID
	 */
	@Secured({ "ROLE_BUSCAR_ENDERECO_CAIXA" })
	EnderecoCaixa findById(Long id);
	
	/**
	 * Altera um Endere�o de Caixa
	 * @param enderecoCaixa Endere�o de Caixa a ser alterado
	 * @return Endere�o de Caixa sincronizado com o banco
	 * @throws BusinessException Caso ocorra algum erro de neg�cio
	 * @throws CadastroDuplicadoException Caso haja um cadastro duplicado
	 */
	@Secured({ "ROLE_ATUALIZAR_ENDERECO_CAIXA" })
	@Transactional
	@Log(descricao="Altera��o")
	EnderecoCaixa merge(EnderecoCaixa enderecoCaixa) throws BusinessException, CadastroDuplicadoException;
	
	/**
	 * Remove um Endere�o de Caixa
	 * @param enderecoCaixa Endere�o de Caixa a ser removido
	 * @throws BusinessException Caso ocorra algum erro de neg�cio
	 */
	@Secured({ "ROLE_REMOVER_ENDERECO_CAIXA" })
	@Transactional
	@Log(descricao="Exclus�o")
	void remove(EnderecoCaixa enderecoCaixa) throws BusinessException;
	
	/**
	 * Insere um novo Endere�o de Caixa
	 * @param enderecoCaixa Endere�o de Caixa a ser inserido
	 * @return Endere�o de Caixa sincronizado com o banco
	 * @throws CadastroDuplicadoException Caso haja cadastro duplicado
	 * @throws BusinessException Caso ocorra algum erro de neg�cio
	 */
	@Secured({ "ROLE_INCLUIR_ENDERECO_CAIXA" })
	@Transactional
	@Log(descricao="Inclus�o")
	EnderecoCaixa persist(EnderecoCaixa enderecoCaixa) throws CadastroDuplicadoException, BusinessException ;
	
	/**
	 * Gera o relat�rio de Endere�o de Caixa
	 * @param parameters Parametros para gera��o do relat�rio
	 * @param localRelatorio Local onde ser� gerado o relat�rio
	 * @return Relat�rio de Endere�o de Caixa
	 */
	@Secured({ "ROLE_IMPRIMIR_ENDERECO_CAIXA" })
	@Transactional
	Relatorio getRelatorio(HashMap<String, Object> parameters, String localRelatorio);
}
