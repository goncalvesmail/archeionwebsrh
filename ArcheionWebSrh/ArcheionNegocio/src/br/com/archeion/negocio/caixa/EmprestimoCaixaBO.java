package br.com.archeion.negocio.caixa;

import java.util.Date;
import java.util.List;

import org.acegisecurity.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

import br.com.archeion.exception.BusinessException;
import br.com.archeion.exception.CadastroDuplicadoException;
import br.com.archeion.modelo.caixa.Caixa;
import br.com.archeion.modelo.caixa.EmprestimoCaixa;
import br.com.archeion.modelo.usuario.Usuario;
import br.com.archeion.util.Log;

/**
 * Classe responsável por manter os Empréstimos de Caixa
 * 
 * @author SInforme
 */
public interface EmprestimoCaixaBO {	

	/**
	 * Buscas as Caixas EMPRESTADAS a partir de uma Caixa, e/ou um Usuário, e/ou uma Data de Empréstimo 
	 * @param caixa Caixa com o ID 
	 * @param usuario Usuário com o ID
	 * @param data Data de empréstimo
	 * @return Lista com os Empréstimos de Caixa
	 */
	@Secured({ "ROLE_BUSCAR_EMPRESTIMO_CAIXA" })
	List<EmprestimoCaixa> findEmprestadas(Caixa caixa, Usuario usuario, Date data);
	
	/**
	 * Buscas as Caixas DEVOLVIDAS a partir de uma Caixa, e/ou um Usuário, e/ou uma Data de Empréstimo 
	 * @param caixa Caixa com o ID 
	 * @param usuario Usuário com o ID
	 * @param data Data de empréstimo
	 * @return Lista com os Empréstimos de Caixa
	 */
	@Secured({ "ROLE_BUSCAR_EMPRESTIMO_CAIXA" })
	List<EmprestimoCaixa> findDevolvidas(Caixa caixa, Usuario usuario, Date data);
	
	/**
	 * Busca todos os Empréstimos de Caixa
	 * @return Lista de Empréstimos
	 */
	@Secured({ "ROLE_BUSCAR_EMPRESTIMO_CAIXA" })
	List<EmprestimoCaixa> findAll();
	
	/**
	 * Busca um Empréstimo a partir de seu ID
	 * @param id ID do Empréstimo
	 * @return Empréstimo de Caixa relacionado ao ID
	 */
	@Secured({ "ROLE_BUSCAR_EMPRESTIMO_CAIXA" })
	EmprestimoCaixa findById(Long id);
	
	/**
	 * Atualiza um Empréstimo de Caixa
	 * @param emprestimoCaixa Empréstimo a ser atualizado
	 * @return Empréstimo sincronizado com o banco
	 * @throws BusinessException Caso ocorra algum erro de negócio
	 * @throws CadastroDuplicadoException Caso haja um cadastro duplicado
	 */
	@Secured({ "ROLE_ATUALIZAR_EMPRESTIMO_CAIXA" })
	@Transactional
	@Log(descricao="Alteração")
	EmprestimoCaixa merge(EmprestimoCaixa emprestimoCaixa) throws BusinessException, CadastroDuplicadoException;
	
	/**
	 * Remove um determinado Empréstimo
	 * @param emprestimoCaixa Empréstimo a ser removido
	 * @throws BusinessException Caso ocorra algum erro de negócio
	 */
	@Secured({ "ROLE_REMOVER_EMPRESTIMO_CAIXA" })
	@Transactional
	@Log(descricao="Exclusão")
	void remove(EmprestimoCaixa emprestimoCaixa) throws BusinessException;
	
	/**
	 * Inclui um novo Empréstimo de Caixa
	 * @param emprestimoCaixa Empréstimo a ser incluido
	 * @return Empréstimo sincronizado com o banco
	 * @throws CadastroDuplicadoException Caso haja um cadastro duplicado
	 * @throws BusinessException Caso ocorra algum erro de negócio
	 */
	@Secured({ "ROLE_INCLUIR_EMPRESTIMO_CAIXA" })
	@Transactional
	@Log(descricao="Inclusão")
	EmprestimoCaixa persist(EmprestimoCaixa emprestimoCaixa) throws CadastroDuplicadoException, BusinessException;
	
}
