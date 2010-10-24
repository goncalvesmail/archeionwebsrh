package br.com.archeion.persistencia.pasta;

import java.util.Date;
import java.util.List;

import br.com.archeion.modelo.empresa.Empresa;
import br.com.archeion.modelo.local.Local;
import br.com.archeion.modelo.pasta.EmprestimoPasta;
import br.com.archeion.modelo.pasta.Pasta;
import br.com.archeion.modelo.usuario.Usuario;
import br.com.archeion.persistencia.GenericDAO;

/**
 * Classe reponsável pelo pela manutenção de Empréstimo de Pasta.
 * 
 * @author SInforme
 */
public interface EmprestimoPastaDAO extends GenericDAO<EmprestimoPasta, Long> {
	
	/**
	 * Busca os Empréstimo de Pastas em ABERTO a partir de uma Empresa, e/ou Local, e/ou Usuaário e/ou Data de Empréstimo
	 * @param empresa Empresa com ID
	 * @param local Local com ID
	 * @param usuario Usuário com ID
	 * @param data Data de empréstimo
	 * @return Lista de Empréstimo de Pasta em ABERTO
	 */
	List<EmprestimoPasta> findEmprestadas(Empresa empresa, Local local, Usuario usuario, Date data);
	
	/**
	 * Busca os Empréstimo de Pastas FINALIZADOS a partir de uma Empresa, e/ou Local, e/ou Usuaário e/ou Data de Empréstimo
	 * @param empresa Empresa com ID
	 * @param local Local com ID
	 * @param usuario Usuário com ID
	 * @param data Data de empréstimo
	 * @return Lista de Empréstimo de Pasta FINALIZADOS
	 */
	List<EmprestimoPasta> findDevolvidas(Empresa empresa, Local local, Usuario usuario, Date data);
	
	/**
	 * Busca os Empréstimos relacionados a uma Pasta
	 * @param pasta Pasta com ID
	 * @return Lista de Empréstimos da referida Pasta
	 */
	List<EmprestimoPasta> findByPasta(Pasta pasta);
	
}
