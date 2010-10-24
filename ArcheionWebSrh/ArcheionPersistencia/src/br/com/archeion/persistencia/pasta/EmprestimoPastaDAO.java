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
 * Classe repons�vel pelo pela manuten��o de Empr�stimo de Pasta.
 * 
 * @author SInforme
 */
public interface EmprestimoPastaDAO extends GenericDAO<EmprestimoPasta, Long> {
	
	/**
	 * Busca os Empr�stimo de Pastas em ABERTO a partir de uma Empresa, e/ou Local, e/ou Usua�rio e/ou Data de Empr�stimo
	 * @param empresa Empresa com ID
	 * @param local Local com ID
	 * @param usuario Usu�rio com ID
	 * @param data Data de empr�stimo
	 * @return Lista de Empr�stimo de Pasta em ABERTO
	 */
	List<EmprestimoPasta> findEmprestadas(Empresa empresa, Local local, Usuario usuario, Date data);
	
	/**
	 * Busca os Empr�stimo de Pastas FINALIZADOS a partir de uma Empresa, e/ou Local, e/ou Usua�rio e/ou Data de Empr�stimo
	 * @param empresa Empresa com ID
	 * @param local Local com ID
	 * @param usuario Usu�rio com ID
	 * @param data Data de empr�stimo
	 * @return Lista de Empr�stimo de Pasta FINALIZADOS
	 */
	List<EmprestimoPasta> findDevolvidas(Empresa empresa, Local local, Usuario usuario, Date data);
	
	/**
	 * Busca os Empr�stimos relacionados a uma Pasta
	 * @param pasta Pasta com ID
	 * @return Lista de Empr�stimos da referida Pasta
	 */
	List<EmprestimoPasta> findByPasta(Pasta pasta);
	
}
