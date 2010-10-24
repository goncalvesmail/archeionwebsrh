package br.com.archeion.negocio.local;

import java.util.List;

import org.acegisecurity.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

import br.com.archeion.exception.BusinessException;
import br.com.archeion.exception.CadastroDuplicadoException;
import br.com.archeion.modelo.empresa.Empresa;
import br.com.archeion.modelo.local.Local;
import br.com.archeion.util.Log;

/**
 * Classe repons�vel pelo m�todos de neg�cio relacionados � manuten��o de Local.
 * 
 * @author SInforme
 */
public interface LocalBO {	
	
	/**
	 * Busca todos os Locais
	 * @return Lista de Locais
	 */
	@Secured({ "ROLE_BUSCAR_LOCAL" })
	List<Local> findAll();
	
	/**
	 * Busca Locais a partir de uma Empresa
	 * @param empresa Empresa, com ID, da qual se deseja os Locais
	 * @return Lista de Locais da referida Empresa
	 */
	@Secured({ "ROLE_BUSCAR_LOCAL" })
	List<Local> findByEmpresa(Empresa empresa);
	
	/**
	 * Busca um Local a partir de um ID
	 * @param id ID do Local
	 * @return Local com o referido ID ou Null caso n�o exista
	 */
	@Secured({ "ROLE_BUSCAR_LOCAL" })
	Local findById(Long id);
	
	/**
	 * Atualiza um Local
	 * @param local Local a ser atualizado
	 * @return Local sincronizado com o banco
	 * @throws BusinessException Caso ocorra algum erro de neg�cio
	 * @throws CadastroDuplicadoException Caso haja um cadastro duplicado
	 */
	@Secured({ "ROLE_ATUALIZAR_LOCAL" })
	@Transactional
	@Log(descricao="Altera��o")
	Local merge(Local local) throws BusinessException, CadastroDuplicadoException;
	
	/**
	 * Remove um Local
	 * @param local Local a ser removido
	 * @throws BusinessException Caso haja algum erro de neg�cio
	 */
	@Secured({ "ROLE_REMOVER_LOCAL" })
	@Transactional
	@Log(descricao="Exclus�o")
	void remove(Local local) throws BusinessException;
	
	/**
	 * Insere um novo Local
	 * @param local Local a ser inserido
	 * @return Local sincronizado com o banco
	 * @throws CadastroDuplicadoException Caso haja um cadastro duplicado
	 */
	@Secured({ "ROLE_INCLUIR_LOCAL" })
	@Transactional
	@Log(descricao="Inclus�o")
	Local persist(Local local) throws CadastroDuplicadoException;
}
