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
 * Classe repons�vel pelo m�todos de neg�cio relacionados � manuten��o de Empresa.
 * 
 * @author SInforme
 */
public interface EmpresaBO {	
	
	/**
	 * Busca todas as Empresas VIS�VEIS
	 * @return Lista de Empresas Vis�veis
	 */
	@Secured({ "ROLE_BUSCAR_EMPRESA" })
	List<Empresa> findAll();
	
	/**
	 * Busca todas as Empresas, inclusive INVIS�VEIS
	 * @return Lista de Empresas
	 */
	@Secured({ "ROLE_BUSCAR_EMPRESA" })
	public List<Empresa> findAllInvisivel();
	
	/**
	 * Busca as Empresas que n�o possuem pai
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
	 * @throws BusinessException Caso ocorra algum erro de neg�cio
	 * @throws CadastroDuplicadoException Caso haja um cadastro duplicado
	 */
	@Secured({ "ROLE_ATUALIZAR_EMPRESA" })
	@Transactional
	@Log(descricao="Altera��o")
	Empresa merge(Empresa empresa) throws BusinessException, CadastroDuplicadoException;
	
	/**
	 * Remove uma Empresa
	 * @param empresa Empresa a ser removida, deve possuir o ID
	 * @throws BusinessException Caso ocorra algum erro de neg�cio
	 */
	@Secured({ "ROLE_REMOVER_EMPRESA" })
	@Transactional
	@Log(descricao="Exclus�o")
	void remove(Empresa empresa) throws BusinessException;
	
	/**
	 * Insere um nova Empresa
	 * @param empresa Empresa a ser inserida
	 * @return Empresa sincronizada com o banco
	 * @throws CadastroDuplicadoException Caso haja um cadastro duplicado
	 */
	@Secured({ "ROLE_INCLUIR_EMPRESA" })
	@Transactional
	@Log(descricao="Inclus�o")
	Empresa persist(Empresa empresa) throws CadastroDuplicadoException;
	
	/**
	 * Gera o relat�rio de Empresas
	 * @param parameters Parametros para gera��o do relat�rio
	 * @param localRelatorio Local onde ser� gerado o relat�rio
	 * @return Relat�rio de Empresas
	 */
	@Secured({ "ROLE_IMPRIMIR_EMPRESA" })
	@Transactional
	Relatorio getRelatorio(HashMap<String, Object> parameters, String localRelatorio);
}
