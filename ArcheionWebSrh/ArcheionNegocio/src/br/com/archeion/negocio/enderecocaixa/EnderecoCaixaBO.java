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
 * Classe reponsável pelo métodos de negócio relacionados à manutenção de Endereço de Caixa
 * 
 * @author SInforme
 */
public interface EnderecoCaixaBO {	
	
	/**
	 * Busca todos os Endereços de Caixa
	 * @return Lista de Endereços de Caixa
	 */
	@Secured({ "ROLE_BUSCAR_ENDERECO_CAIXA" })
	List<EnderecoCaixa> findAll();
	
	/**
	 * Busca um Endereço de Caixa a partir de um ID
	 * @param id ID do Endereço a ser buscado
	 * @return Endereço de Caixa com o respectivo ID
	 */
	@Secured({ "ROLE_BUSCAR_ENDERECO_CAIXA" })
	EnderecoCaixa findById(Long id);
	
	/**
	 * Altera um Endereço de Caixa
	 * @param enderecoCaixa Endereço de Caixa a ser alterado
	 * @return Endereço de Caixa sincronizado com o banco
	 * @throws BusinessException Caso ocorra algum erro de negócio
	 * @throws CadastroDuplicadoException Caso haja um cadastro duplicado
	 */
	@Secured({ "ROLE_ATUALIZAR_ENDERECO_CAIXA" })
	@Transactional
	@Log(descricao="Alteração")
	EnderecoCaixa merge(EnderecoCaixa enderecoCaixa) throws BusinessException, CadastroDuplicadoException;
	
	/**
	 * Remove um Endereço de Caixa
	 * @param enderecoCaixa Endereço de Caixa a ser removido
	 * @throws BusinessException Caso ocorra algum erro de negócio
	 */
	@Secured({ "ROLE_REMOVER_ENDERECO_CAIXA" })
	@Transactional
	@Log(descricao="Exclusão")
	void remove(EnderecoCaixa enderecoCaixa) throws BusinessException;
	
	/**
	 * Insere um novo Endereço de Caixa
	 * @param enderecoCaixa Endereço de Caixa a ser inserido
	 * @return Endereço de Caixa sincronizado com o banco
	 * @throws CadastroDuplicadoException Caso haja cadastro duplicado
	 * @throws BusinessException Caso ocorra algum erro de negócio
	 */
	@Secured({ "ROLE_INCLUIR_ENDERECO_CAIXA" })
	@Transactional
	@Log(descricao="Inclusão")
	EnderecoCaixa persist(EnderecoCaixa enderecoCaixa) throws CadastroDuplicadoException, BusinessException ;
	
	/**
	 * Gera o relatório de Endereço de Caixa
	 * @param parameters Parametros para geração do relatório
	 * @param localRelatorio Local onde será gerado o relatório
	 * @return Relatório de Endereço de Caixa
	 */
	@Secured({ "ROLE_IMPRIMIR_ENDERECO_CAIXA" })
	@Transactional
	Relatorio getRelatorio(HashMap<String, Object> parameters, String localRelatorio);
}
