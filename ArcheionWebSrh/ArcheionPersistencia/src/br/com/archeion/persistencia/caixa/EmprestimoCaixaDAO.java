package br.com.archeion.persistencia.caixa;

import java.util.Date;
import java.util.List;

import br.com.archeion.modelo.caixa.Caixa;
import br.com.archeion.modelo.caixa.EmprestimoCaixa;
import br.com.archeion.modelo.usuario.Usuario;
import br.com.archeion.persistencia.GenericDAO;

public interface EmprestimoCaixaDAO extends GenericDAO<EmprestimoCaixa, Long> {
	
	/**
	 * Buscas as Caixas emprestadas a partir de uma Caixa, e/ou um Usuário, e/ou uma Data de Empréstimo 
	 * @param caixa Caixa com o ID 
	 * @param usuario Usuário com o ID
	 * @param data Data de empréstimo
	 * @return Lista com os Empréstimos de Caixa
	 */
	List<EmprestimoCaixa> findEmprestadas(Caixa caixa, Usuario usuario, Date data);
	
	/**
	 * Buscas as Caixas DEVOLVIDAS a partir de uma Caixa, e/ou um Usuário, e/ou uma Data de Empréstimo 
	 * @param caixa Caixa com o ID 
	 * @param usuario Usuário com o ID
	 * @param data Data de empréstimo
	 * @return Lista com os Empréstimos de Caixa
	 */
	List<EmprestimoCaixa> findDevolvidas(Caixa caixa, Usuario usuario, Date data);
	
	/**
	 * Buscas os Emprestismos a partir de uma Caixa
	 * @param caixa Caixa com ID
	 * @return Lista de Empréstimos para a Caixa
	 */
	List<EmprestimoCaixa> findByCaixa(Caixa caixa);
	
}
