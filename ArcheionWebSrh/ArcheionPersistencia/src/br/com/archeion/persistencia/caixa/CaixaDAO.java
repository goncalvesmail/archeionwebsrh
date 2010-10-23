package br.com.archeion.persistencia.caixa;

import java.util.Date;
import java.util.List;

import br.com.archeion.modelo.SituacaoExpurgo;
import br.com.archeion.modelo.TipoArquivo;
import br.com.archeion.modelo.caixa.Caixa;
import br.com.archeion.persistencia.GenericDAO;

/**
 * Interface com os métodos de acesso à base relativo à Caixa
 * 
 * @author SInforme
 *
 */
public interface CaixaDAO extends GenericDAO<Caixa, Long> {
	
	/**
	 * Buscas as Caixas que ocupam um determinado Vão
	 * @param nomeVao Nome do Vão
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
	 * Buscas as Caixas de acordo com o ID de um Vão
	 * @param id ID do Vão
	 * @return Lista de Caixas
	 */
	List<Caixa> findByEndereco(Long id);
	
	/**
	 * Buscas as Caixas, NÃO Expurgadas, a partir de um ID de Caixa e/ou uma Data de Criação e/ou Tipo de Arquivo
	 * @param caixaId ID de Caixa
	 * @param date Data de Criação
	 * @param tipo Tipo de Arquivo
	 * @return Lista de Caixas
	 */
	List<Caixa> consultaEtiquetaCaixa(int caixaId, Date date, TipoArquivo tipo);
	
	/**
	 * Busca uma Caixa a partir do seu Vão e a posição ocupada no Vão
	 * @param vao Nome do Vão
	 * @param numero Posição ocupada no Vão
	 * @return Caixa que ocupada o local
	 */
	Caixa findByVaoNumero(String vao, int numero);
	
	/**
	 * Busca uma Caixa a partir do seu Vão e a posição ocupada no Vão, e que NÃO esteja Exourgada.
	 * @param vao Nome do Vão
	 * @param numero Posição ocupada no Vão
	 * @return Caixa que ocupada o local
	 */
	Caixa findByVaoNumeroAtiva(String vao, int numero);
	
	/**
	 * Buscas as Caixas ATIVAS e prontas para expurgo
	 * @return Lista de Caixas
	 */
	List<Caixa> findCaixaAtivasExpurgo();
	
	/**
	 * Busca as Caixas ATIVAS e diponíveis para empréstimo
	 * @return Lista de Caixa
	 */
	List<Caixa> findCaixaAtivasEmprestimo();
	
	/**
	 * Busca as Caixas que pertencem a uma Empresa ou Local, ou ambos, e que estejam em um determinada Situação
	 * @param emp ID da Empresa
	 * @param local ID do Local
	 * @param situacao Situação desejada
	 * @return Lista de Caixas
	 */
	List<Caixa> findByEmpresaLocalSituacao(int emp, int local, SituacaoExpurgo situacao);
	
}
