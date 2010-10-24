package br.com.archeion.persistencia.pasta;

import java.util.Date;
import java.util.List;

import br.com.archeion.modelo.SituacaoExpurgo;
import br.com.archeion.modelo.empresa.Empresa;
import br.com.archeion.modelo.local.Local;
import br.com.archeion.modelo.pasta.ParametroConsulta;
import br.com.archeion.modelo.pasta.Pasta;
import br.com.archeion.persistencia.GenericDAO;

/**
 * Classe repons�vel pelo pela manuten��o de Pastas.
 * 
 * @author SInforme
 */
public interface PastaDAO extends GenericDAO<Pasta, Long> {
	
	/**
	 * Busca a lista de Pastas a partir de uma Empresa e/ou Local e/ou Situa��o
	 * @param emp Empresa com ID
	 * @param local Local com ID 
	 * @return Lista de Pastas que atendam o referido filtro
	 */
	List<Pasta> findByEmpresaLocal(int emp, int local);
	
	/**
	 * Busca uma Pasta a partir do seu Titulo
	 * @param titulo Titulo da Pasta a ser encontrada
	 * @return Pasta com o referido T�tulo
	 */
	Pasta findByTitulo(String titulo);
	
	/**
	 * Busca Pastas pelo Titulo e/ou Local e/ou Empresa
	 * @param titulo Titulo da Pasta
	 * @param nomeLocal Nome do Local
	 * @param nomeEmpresa Nome da Empresa
	 * @return Lista de Pastas que atendam os parametros
	 */
	Pasta findByTituloLocalEmpresa(String titulo,String nomeLocal, String nomeEmpresa);
	
	/**
	 * Busca a lista de Pasta para impress�o de etiquetas a partir de uma consulta pr�-formatada
	 * @param where Crit�rio para consulta
	 * @return Lista de Pastas que atendam o referido crit�rio
	 */
	List<Pasta> consultaEtiquetaPasta(String where);
	
	/**
	 * Busca o tamanho da lista de Pasta para impress�o de etiquetas a partir de uma consulta pr�-formatada
	 * @param where Crit�rio para consulta
	 * @return Tamanho da lista de Pastas que atendam o referido crit�rio
	 */
	Long consultaEtiquetaPastaSize(ParametroConsulta where);
	
	/**
	 * Busca a lista de Pasta para impress�o de etiquetas a partir de uma consulta pr�-formatada
	 * @param where Crit�rio para consulta
	 * @param start Pasta inicial
	 * @param quantity Quantidade de Pastas
	 * @return Lista de Pastas que atendam o referido crit�rio
	 */
	List<Pasta> consultaEtiquetaPasta(final ParametroConsulta where, final int start, final int quantity);

	/**
	 * Busca Pasta dispon�veis para recolhimento, para arquivo PERMANENTE, de acordo com Local
	 * @param local Local com as informa��es a serem filtradas
	 * @return Lista de Pastas
	 */
	List<Pasta> consultaPermanenteRecolhimento(Local local);

	/**
	 * Busca Pasta dispon�veis para recolhimento, para arquivo TEMPOR�RIO, de acordo com Local
	 * @param local Local com as informa��es a serem filtradas
	 * @return Lista de Pastas
	 */
	List<Pasta> consultaTemporarioRecolhimento(Local local);
	
	/**
	 * Busca Pastas DISPON�VEIS (Ativas) para expurgo a partir de uma Empresa e/ou Local
	 * @param emp ID da Empresa
	 * @param local ID do Local
	 * @return Lista de pastas, ATIVAS, 
	 */
	List<Pasta> findByEmpresaLocalExpurgo(int emp, int local);
	
	/**
	 * Busca as Pastas com empr�stimos FINALIZADOS
	 * @return Lista de Pastas com empr�stimos FINALIZADOS
	 */
	List<Pasta> findPastaAtivasEmprestimo();
	
	/**
	 * Busca a lista de Pasta a partir de uma Empresa e/ou Local e com empr�stimo em ABERTO
	 * @param emp Empresa com ID
	 * @param local Local com ID
	 * @return Lista de Pastas com empr�stimos em ABERTO
	 */
	List<Pasta> findByEmpresaLocalEmprestimo(Long emp, Long local);
	
	/**
	 * Busca o tamanho da lista de Pastas a partir de uma Empresa e/ou Local e/ou Situa��o
	 * @param emp Empresa com ID
	 * @param local Local com ID
	 * @param situacao Situa��o com ID, caso venha Null ser� buscado por todos
	 * @return Tamanho da lista de Pastas que atendam o referido filtro
	 */
	Long findByEmpresaLocalSituacaoSize(int emp, int local, SituacaoExpurgo situacao);
	
	/**
	 * Busca a lista de Pastas a partir de uma Empresa e/ou Local e/ou Situa��o
	 * @param emp Empresa com ID
	 * @param local Local com ID
	 * @param situacao Situa��o com ID, caso venha Null ser� buscado por todos
	 * @return Lista de Pastas que atendam o referido filtro
	 */
	List<Pasta> findByEmpresaLocalSituacao(int emp, int local, SituacaoExpurgo situacao);
	
	/**
	 * Busca a lista de Pastas a partir de uma Empresa e/ou Local e/ou Situa��o
	 * @param emp Empresa com ID
	 * @param local Local com ID
	 * @param situacao Situa��o com ID, caso venha Null ser� buscado por todos
	 * @param start Pasta inicial
	 * @param Amount Quantidade de Pastas
	 * @return Lista de Pastas que atendam o referido filtro
	 */
	List<Pasta> findByEmpresaLocalSituacao(int emp, int local, SituacaoExpurgo situacao, int start, int amount);
	
	/**
	 * Busca Pasta dispon�veis para recolhimento, para arquivo TEMPOR�RIO, de acordo com Local e/ou Empresa dentro de um intervalo
	 * @param empresa Empresa com ID
	 * @param local Local com ID
	 * @param inicio Data de in�cio do per�odo
	 * @param fim Data final do per�odo
	 * @return Lista de Pastas
	 */
	List<Pasta> consultaTemporarioRecolhimentoIntervalo(Empresa empresa, Local local, Date inicio, Date fim);
	
	/**
	 * Busca Pasta dispon�veis para recolhimento, para arquivo PERMANETE, de acordo com Local e/ou Empresa dentro de um intervalo
	 * @param empresa Empresa com ID
	 * @param local Local com ID
	 * @param inicio Data de in�cio do per�odo
	 * @param fim Data final do per�odo
	 * @return Lista de Pastas
	 */
	List<Pasta> consultaPermanenteRecolhimentoIntervalo(Empresa empresa, Local local, Date inicio, Date fim);
	
	/**
	 * Busca as Pastas a partir de um Item Documental e um Local
	 * @param item Item Documental com ID
	 * @param local Local com ID
	 * @return Lista de Pastas
	 */
	List<Pasta> findByLocalItemDocumental(long item, long local);
	
	/**
	 * Busca Pastas a partir de uma Caixeta
	 * @param caixeta Caixeta a ser buscada
	 * @return Lista de Pastas da referida Caixeta
	 */
	List<Pasta> findByCaixeta(String caixeta);
	
	/**
	 * Busca n�mero de Pastas a partir de uma Caixeta
	 * @param caixeta Caixeta a ser buscada
	 * @return N�mero de Pastas com a referida Caixeta
	 */
	Long findByCaixetaSize(String caixeta);
	
	/**
	 * Busca Pastas a partir de uma Caixeta
	 * @param caixeta Caixeta a ser buscada
	 * @param start Pasta inicial
	 * @param quatity Quantidade de Pastas
	 * @return Lista de Pastas da referida Caixeta
	 */
	List<Pasta> findByCaixeta(final String caixeta, final int start, final int quantity);
	
}
