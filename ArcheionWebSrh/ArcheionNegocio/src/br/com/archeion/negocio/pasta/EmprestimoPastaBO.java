package br.com.archeion.negocio.pasta;

import java.util.Date;
import java.util.List;

import org.acegisecurity.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

import br.com.archeion.exception.BusinessException;
import br.com.archeion.exception.CadastroDuplicadoException;
import br.com.archeion.modelo.empresa.Empresa;
import br.com.archeion.modelo.local.Local;
import br.com.archeion.modelo.pasta.EmprestimoPasta;
import br.com.archeion.modelo.usuario.Usuario;
import br.com.archeion.util.Log;


/**
 * Classe repons�vel pelo m�todos de neg�cio relacionados � manuten��o de Empr�stimo de Pasta.
 * 
 * @author SInforme
 */
public interface EmprestimoPastaBO {	

	/**
	 * Busca os Empr�stimo de Pastas em ABERTO a partir de uma Empresa, e/ou Local, e/ou Usua�rio e/ou Data de Empr�stimo
	 * @param empresa Empresa com ID
	 * @param local Local com ID
	 * @param usuario Usu�rio com ID
	 * @param data Data de empr�stimo
	 * @return Lista de Empr�stimo de Pasta em ABERTO
	 */
	@Secured({ "ROLE_BUSCAR_EMPRESTIMO_PASTA" })
	List<EmprestimoPasta> findEmprestadas(Empresa empresa, Local local, Usuario usuario, Date data);
	
	/**
	 * Busca os Empr�stimo de Pastas FINALIZADOS a partir de uma Empresa, e/ou Local, e/ou Usua�rio e/ou Data de Empr�stimo
	 * @param empresa Empresa com ID
	 * @param local Local com ID
	 * @param usuario Usu�rio com ID
	 * @param data Data de empr�stimo
	 * @return Lista de Empr�stimo de Pasta FINALIZADOS
	 */
	@Secured({ "ROLE_BUSCAR_EMPRESTIMO_PASTA" })
	List<EmprestimoPasta> findDevolvidas(Empresa empresa, Local local, Usuario usuario, Date data);
	
	/**
	 * Busca todos os Empr�stimos de Pasta
	 * @return Lista de Empr�stimos de Pasta
	 */
	@Secured({ "ROLE_BUSCAR_EMPRESTIMO_PASTA" })
	List<EmprestimoPasta> findAll();
	
	/**
	 * Busca um Empr�stimo de Pasta a partir de um ID
	 * @param id ID de um Empr�stimo de Pasta
	 * @return Lista de Empr�stimo de Pasta
	 */
	@Secured({ "ROLE_BUSCAR_EMPRESTIMO_PASTA" })
	EmprestimoPasta findById(Long id);
	
	/**
	 * Atualiza um Empr�stimo de Pasta
	 * @param emprestimoPasta Empr�stimo de Pasta a ser atualizado
	 * @return Empr�stimo de Pasta sincronizado com o banco
	 * @throws BusinessException Caso ocorra algum erro de neg�cio
	 * @throws CadastroDuplicadoException Caso haja algum cadastro duplicado
	 */
	@Secured({ "ROLE_ATUALIZAR_EMPRESTIMO_PASTA" })
	@Transactional
	@Log(descricao="Altera��o")
	EmprestimoPasta merge(EmprestimoPasta emprestimoPasta) throws BusinessException, CadastroDuplicadoException;
	
	/**
	 * Remove um Empr�stimo de Pasta
	 * @param emprestimoPasta Empr�stimo de Pasta a ser removido
	 * @throws BusinessException Caso ocorra algum erro de neg�cio
	 */
	@Secured({ "ROLE_REMOVER_EMPRESTIMO_PASTA" })
	@Transactional
	@Log(descricao="Exclus�o")
	void remove(EmprestimoPasta emprestimoPasta) throws BusinessException;
	
	/**
	 * Insere um novo Empr�stimo de Pasta
	 * @param emprestimoPasta Emmpr�stimo de Pasta a ser inserido
	 * @return Empr�stimo de Pasta sincronizado com o banco
	 * @throws CadastroDuplicadoException Caso haja algum cadastro duplicado
	 * @throws BusinessException Caso ocorra algum erro de neg�cio
	 */
	@Secured({ "ROLE_INCLUIR_EMPRESTIMO_PASTA" })
	@Transactional
	@Log(descricao="Inclus�o")
	EmprestimoPasta persist(EmprestimoPasta emprestimoPasta) throws CadastroDuplicadoException, BusinessException;
	
}
