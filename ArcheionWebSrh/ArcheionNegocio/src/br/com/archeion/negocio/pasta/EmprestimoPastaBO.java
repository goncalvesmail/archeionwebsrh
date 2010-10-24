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
 * Classe reponsável pelo métodos de negócio relacionados à manutenção de Empréstimo de Pasta.
 * 
 * @author SInforme
 */
public interface EmprestimoPastaBO {	

	/**
	 * Busca os Empréstimo de Pastas em ABERTO a partir de uma Empresa, e/ou Local, e/ou Usuaário e/ou Data de Empréstimo
	 * @param empresa Empresa com ID
	 * @param local Local com ID
	 * @param usuario Usuário com ID
	 * @param data Data de empréstimo
	 * @return Lista de Empréstimo de Pasta em ABERTO
	 */
	@Secured({ "ROLE_BUSCAR_EMPRESTIMO_PASTA" })
	List<EmprestimoPasta> findEmprestadas(Empresa empresa, Local local, Usuario usuario, Date data);
	
	/**
	 * Busca os Empréstimo de Pastas FINALIZADOS a partir de uma Empresa, e/ou Local, e/ou Usuaário e/ou Data de Empréstimo
	 * @param empresa Empresa com ID
	 * @param local Local com ID
	 * @param usuario Usuário com ID
	 * @param data Data de empréstimo
	 * @return Lista de Empréstimo de Pasta FINALIZADOS
	 */
	@Secured({ "ROLE_BUSCAR_EMPRESTIMO_PASTA" })
	List<EmprestimoPasta> findDevolvidas(Empresa empresa, Local local, Usuario usuario, Date data);
	
	/**
	 * Busca todos os Empréstimos de Pasta
	 * @return Lista de Empréstimos de Pasta
	 */
	@Secured({ "ROLE_BUSCAR_EMPRESTIMO_PASTA" })
	List<EmprestimoPasta> findAll();
	
	/**
	 * Busca um Empréstimo de Pasta a partir de um ID
	 * @param id ID de um Empréstimo de Pasta
	 * @return Lista de Empréstimo de Pasta
	 */
	@Secured({ "ROLE_BUSCAR_EMPRESTIMO_PASTA" })
	EmprestimoPasta findById(Long id);
	
	/**
	 * Atualiza um Empréstimo de Pasta
	 * @param emprestimoPasta Empréstimo de Pasta a ser atualizado
	 * @return Empréstimo de Pasta sincronizado com o banco
	 * @throws BusinessException Caso ocorra algum erro de negócio
	 * @throws CadastroDuplicadoException Caso haja algum cadastro duplicado
	 */
	@Secured({ "ROLE_ATUALIZAR_EMPRESTIMO_PASTA" })
	@Transactional
	@Log(descricao="Alteração")
	EmprestimoPasta merge(EmprestimoPasta emprestimoPasta) throws BusinessException, CadastroDuplicadoException;
	
	/**
	 * Remove um Empréstimo de Pasta
	 * @param emprestimoPasta Empréstimo de Pasta a ser removido
	 * @throws BusinessException Caso ocorra algum erro de negócio
	 */
	@Secured({ "ROLE_REMOVER_EMPRESTIMO_PASTA" })
	@Transactional
	@Log(descricao="Exclusão")
	void remove(EmprestimoPasta emprestimoPasta) throws BusinessException;
	
	/**
	 * Insere um novo Empréstimo de Pasta
	 * @param emprestimoPasta Emmpréstimo de Pasta a ser inserido
	 * @return Empréstimo de Pasta sincronizado com o banco
	 * @throws CadastroDuplicadoException Caso haja algum cadastro duplicado
	 * @throws BusinessException Caso ocorra algum erro de negócio
	 */
	@Secured({ "ROLE_INCLUIR_EMPRESTIMO_PASTA" })
	@Transactional
	@Log(descricao="Inclusão")
	EmprestimoPasta persist(EmprestimoPasta emprestimoPasta) throws CadastroDuplicadoException, BusinessException;
	
}
