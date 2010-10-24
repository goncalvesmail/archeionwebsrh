package br.com.archeion.negocio.empresa;

import java.util.HashMap;
import java.util.List;

import org.acegisecurity.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

import util.Relatorio;
import br.com.archeion.exception.BusinessException;
import br.com.archeion.exception.CadastroDuplicadoException;
import br.com.archeion.modelo.empresa.Empresa;
import br.com.archeion.util.Log;

/**
 * Classe reponsável pelo métodos de negócio relacionados à manutenção de Empresa.
 * 
 * @author SInforme
 */
public interface EmpresaBO {	
	
	/**
	 * Busca todas as Empresas VISÍVEIS
	 * @return Lista de Empresas Visíveis
	 */
	@Secured({ "ROLE_BUSCAR_EMPRESA" })
	List<Empresa> findAll();
	
	/**
	 * Busca todas as Empresas, inclusive INVISÍVEIS
	 * @return Lista de Empresas
	 */
	@Secured({ "ROLE_BUSCAR_EMPRESA" })
	public List<Empresa> findAllInvisivel();
	
	/**
	 * Busca as Empresas que não possuem pai
	 * @return Lista de Empresas
	 */
	@Secured({ "ROLE_BUSCAR_EMPRESA" })
	List<Empresa> findRoots();
	
	/**
	 * Busca uma Empresa a partir do seu ID
	 * @param id ID da Empresa
	 * @return Empresa com o respectivo ID
	 */
	@Secured({ "ROLE_BUSCAR_EMPRESA" })
	Empresa findById(Long id);
	
	/**
	 * Busca uma Empresa a partir do seu Nome
	 * @param nome Nome da Empresa
	 * @return Empresa com o respectivo Nome
	 */
	@Secured({ "ROLE_BUSCAR_EMPRESA" })
	Empresa findByName(String nome);
	
	/**
	 * Atualiza uma Empresa
	 * @param empresa Empresa a ser atualizada
	 * @return Empresa sincronizada com o banco
	 * @throws BusinessException Caso ocorra algum erro de negócio
	 * @throws CadastroDuplicadoException Caso haja um cadastro duplicado
	 */
	@Secured({ "ROLE_ATUALIZAR_EMPRESA" })
	@Transactional
	@Log(descricao="Alteração")
	Empresa merge(Empresa empresa) throws BusinessException, CadastroDuplicadoException;
	
	/**
	 * Remove uma Empresa
	 * @param empresa Empresa a ser removida, deve possuir o ID
	 * @throws BusinessException Caso ocorra algum erro de negócio
	 */
	@Secured({ "ROLE_REMOVER_EMPRESA" })
	@Transactional
	@Log(descricao="Exclusão")
	void remove(Empresa empresa) throws BusinessException;
	
	/**
	 * Insere um nova Empresa
	 * @param empresa Empresa a ser inserida
	 * @return Empresa sincronizada com o banco
	 * @throws CadastroDuplicadoException Caso haja um cadastro duplicado
	 */
	@Secured({ "ROLE_INCLUIR_EMPRESA" })
	@Transactional
	@Log(descricao="Inclusão")
	Empresa persist(Empresa empresa) throws CadastroDuplicadoException;
	
	/**
	 * Gera o relatório de Empresas
	 * @param parameters Parametros para geração do relatório
	 * @param localRelatorio Local onde será gerado o relatório
	 * @return Relatório de Empresas
	 */
	@Secured({ "ROLE_IMPRIMIR_EMPRESA" })
	@Transactional
	Relatorio getRelatorio(HashMap<String, Object> parameters, String localRelatorio);
}
