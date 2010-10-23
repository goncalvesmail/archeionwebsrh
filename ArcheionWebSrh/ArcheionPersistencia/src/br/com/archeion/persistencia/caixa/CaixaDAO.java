package br.com.archeion.persistencia.caixa;

import java.util.Date;
import java.util.List;

import br.com.archeion.modelo.SituacaoExpurgo;
import br.com.archeion.modelo.TipoArquivo;
import br.com.archeion.modelo.caixa.Caixa;
import br.com.archeion.persistencia.GenericDAO;

/**
 * Interface com os m�todos de acesso � base relativo � Caixa
 * 
 * @author SInforme
 *
 */
public interface CaixaDAO extends GenericDAO<Caixa, Long> {
	
	/**
	 * Buscas as Caixas que ocupam um determinado V�o
	 * @param nomeVao Nome do V�o
	 * @return Lista de Caixas
	 */
	List<Caixa> findVaoOcupados(String nomeVao);
	
	/**
	 * Busca as Caixas que pertencem a uma Empresa ou Local, ou ambos.
	 * @param emp ID da Empresa
	 * @param local ID do Local
	 * @return Lista de Caixas
	 */
	List<Caixa> findByEmpresaLocal(int emp, int local);
	
	/**
	 * Buscas as Caixas de acordo com o ID de um V�o
	 * @param id ID do V�o
	 * @return Lista de Caixas
	 */
	List<Caixa> findByEndereco(Long id);
	
	/**
	 * Buscas as Caixas, N�O Expurgadas, a partir de um ID de Caixa e/ou uma Data de Cria��o e/ou Tipo de Arquivo
	 * @param caixaId ID de Caixa
	 * @param date Data de Cria��o
	 * @param tipo Tipo de Arquivo
	 * @return Lista de Caixas
	 */
	List<Caixa> consultaEtiquetaCaixa(int caixaId, Date date, TipoArquivo tipo);
	
	/**
	 * Busca uma Caixa a partir do seu V�o e a posi��o ocupada no V�o
	 * @param vao Nome do V�o
	 * @param numero Posi��o ocupada no V�o
	 * @return Caixa que ocupada o local
	 */
	Caixa findByVaoNumero(String vao, int numero);
	
	/**
	 * Busca uma Caixa a partir do seu V�o e a posi��o ocupada no V�o, e que N�O esteja Exourgada.
	 * @param vao Nome do V�o
	 * @param numero Posi��o ocupada no V�o
	 * @return Caixa que ocupada o local
	 */
	Caixa findByVaoNumeroAtiva(String vao, int numero);
	
	/**
	 * Buscas as Caixas ATIVAS e prontas para expurgo
	 * @return Lista de Caixas
	 */
	List<Caixa> findCaixaAtivasExpurgo();
	
	/**
	 * Busca as Caixas ATIVAS e dipon�veis para empr�stimo
	 * @return Lista de Caixa
	 */
	List<Caixa> findCaixaAtivasEmprestimo();
	
	/**
	 * Busca as Caixas que pertencem a uma Empresa ou Local, ou ambos, e que estejam em um determinada Situa��o
	 * @param emp ID da Empresa
	 * @param local ID do Local
	 * @param situacao Situa��o desejada
	 * @return Lista de Caixas
	 */
	List<Caixa> findByEmpresaLocalSituacao(int emp, int local, SituacaoExpurgo situacao);
	
}
