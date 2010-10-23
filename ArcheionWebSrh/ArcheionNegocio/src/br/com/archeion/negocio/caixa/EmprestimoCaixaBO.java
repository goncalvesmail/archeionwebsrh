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
 * Classe respons�vel por manter os Empr�stimos de Caixa
 * 
 * @author SInforme
 */
public interface EmprestimoCaixaBO {	

	/**
	 * Buscas as Caixas EMPRESTADAS a partir de uma Caixa, e/ou um Usu�rio, e/ou uma Data de Empr�stimo 
	 * @param caixa Caixa com o ID 
	 * @param usuario Usu�rio com o ID
	 * @param data Data de empr�stimo
	 * @return Lista com os Empr�stimos de Caixa
	 */
	@Secured({ "ROLE_BUSCAR_EMPRESTIMO_CAIXA" })
	List<EmprestimoCaixa> findEmprestadas(Caixa caixa, Usuario usuario, Date data);
	
	/**
	 * Buscas as Caixas DEVOLVIDAS a partir de uma Caixa, e/ou um Usu�rio, e/ou uma Data de Empr�stimo 
	 * @param caixa Caixa com o ID 
	 * @param usuario Usu�rio com o ID
	 * @param data Data de empr�stimo
	 * @return Lista com os Empr�stimos de Caixa
	 */
	@Secured({ "ROLE_BUSCAR_EMPRESTIMO_CAIXA" })
	List<EmprestimoCaixa> findDevolvidas(Caixa caixa, Usuario usuario, Date data);
	
	/**
	 * Busca todos os Empr�stimos de Caixa
	 * @return Lista de Empr�stimos
	 */
	@Secured({ "ROLE_BUSCAR_EMPRESTIMO_CAIXA" })
	List<EmprestimoCaixa> findAll();
	
	/**
	 * Busca um Empr�stimo a partir de seu ID
	 * @param id ID do Empr�stimo
	 * @return Empr�stimo de Caixa relacionado ao ID
	 */
	@Secured({ "ROLE_BUSCAR_EMPRESTIMO_CAIXA" })
	EmprestimoCaixa findById(Long id);
	
	/**
	 * Atualiza um Empr�stimo de Caixa
	 * @param emprestimoCaixa Empr�stimo a ser atualizado
	 * @return Empr�stimo sincronizado com o banco
	 * @throws BusinessException Caso ocorra algum erro de neg�cio
	 * @throws CadastroDuplicadoException Caso haja um cadastro duplicado
	 */
	@Secured({ "ROLE_ATUALIZAR_EMPRESTIMO_CAIXA" })
	@Transactional
	@Log(descricao="Altera��o")
	EmprestimoCaixa merge(EmprestimoCaixa emprestimoCaixa) throws BusinessException, CadastroDuplicadoException;
	
	/**
	 * Remove um determinado Empr�stimo
	 * @param emprestimoCaixa Empr�stimo a ser removido
	 * @throws BusinessException Caso ocorra algum erro de neg�cio
	 */
	@Secured({ "ROLE_REMOVER_EMPRESTIMO_CAIXA" })
	@Transactional
	@Log(descricao="Exclus�o")
	void remove(EmprestimoCaixa emprestimoCaixa) throws BusinessException;
	
	/**
	 * Inclui um novo Empr�stimo de Caixa
	 * @param emprestimoCaixa Empr�stimo a ser incluido
	 * @return Empr�stimo sincronizado com o banco
	 * @throws CadastroDuplicadoException Caso haja um cadastro duplicado
	 * @throws BusinessException Caso ocorra algum erro de neg�cio
	 */
	@Secured({ "ROLE_INCLUIR_EMPRESTIMO_CAIXA" })
	@Transactional
	@Log(descricao="Inclus�o")
	EmprestimoCaixa persist(EmprestimoCaixa emprestimoCaixa) throws CadastroDuplicadoException, BusinessException;
	
}
