package br.com.archeion.negocio.pasta;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.acegisecurity.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

import util.Relatorio;

import br.com.archeion.exception.BusinessException;
import br.com.archeion.exception.CadastroDuplicadoException;
import br.com.archeion.modelo.SituacaoExpurgo;
import br.com.archeion.modelo.empresa.Empresa;
import br.com.archeion.modelo.local.Local;
import br.com.archeion.modelo.pasta.Pasta;
import br.com.archeion.util.Log;
import br.com.archeion.util.PaginationSupport;

/**
 * Classe reponsável pelo métodos de negócio relacionados à manutenção de Empréstimo de Pasta.
 * 
 * @author SInforme
 */
public interface PastaBO extends PaginationSupport<Pasta, Pasta> {
	
	/**
	 * Busca Pasta disponíveis para recolhimento, para arquivo PERMANETE, de acordo com Local e/ou Empresa dentro de um intervalo
	 * @param empresa Empresa com ID
	 * @param local Local com ID
	 * @param inicio Data de início do período
	 * @param fim Data final do período
	 * @return Lista de Pastas
	 */
	@Secured({ "ROLE_BUSCAR_PASTA" })
	List<Pasta> consultaPermanenteRecolhimentoIntervalo(Empresa empresa, Local local, Date inicio, Date fim);
	
	/**
	 * Busca Pasta disponíveis para recolhimento, para arquivo TEMPORÁRIO, de acordo com Local e/ou Empresa dentro de um intervalo
	 * @param empresa Empresa com ID
	 * @param local Local com ID
	 * @param inicio Data de início do período
	 * @param fim Data final do período
	 * @return Lista de Pastas
	 */
	@Secured({ "ROLE_BUSCAR_PASTA" })
	List<Pasta> consultaTemporarioRecolhimentoIntervalo(Empresa empresa, Local local, Date inicio, Date fim);

	/**
	 * Busca Pastas DISPONÍVEIS (Ativas) para expurgo a partir de uma Empresa e/ou Local
	 * @param emp ID da Empresa
	 * @param local ID do Local
	 * @return Lista de pastas, ATIVAS, 
	 */
	@Secured({ "ROLE_BUSCAR_PASTA" })
	List<Pasta> findByEmpresaLocalExpurgo(int emp, int local);
	
	/**
	 * Busca Pasta disponíveis para recolhimento, para arquivo TEMPORÁRIO, de acordo com Local
	 * @param local Local com as informações a serem filtradas
	 * @return Lista de Pastas
	 */
	@Secured({ "ROLE_BUSCAR_PASTA" })
	List<Pasta> consultaTemporarioRecolhimento(Local local);
	
	/**
	 * Busca Pasta disponíveis para recolhimento, para arquivo PERMANENTE, de acordo com Local
	 * @param local Local com as informações a serem filtradas
	 * @return Lista de Pastas
	 */
	@Secured({ "ROLE_BUSCAR_PASTA" })
	List<Pasta> consultaPermanenteRecolhimento(Local local);
	
	/**
	 * Busca lista de Pasta
	 * @return Lista de Pasta
	 */
	@Secured({ "ROLE_BUSCAR_PASTA" })
	List<Pasta> findAll();
	
	/**
	 * Busca uma Pasta a partir do seu ID
	 * @param id ID da Pasta a ser encontrada
	 * @return Pasta com o referido ID
	 */
	@Secured({ "ROLE_BUSCAR_PASTA" })
	Pasta findById(Long id);
	
	/**
	 * Busca uma Pasta a partir do seu Titulo
	 * @param titulo Titulo da Pasta a ser encontrada
	 * @return Pasta com o referido Título
	 */
	@Secured({ "ROLE_BUSCAR_PASTA" })
	Pasta findByTitulo(String titulo);
	
	/**
	 * Busca Pastas pelo Titulo e/ou Local e/ou Empresa
	 * @param titulo Titulo da Pasta
	 * @param nomeLocal Nome do Local
	 * @param nomeEmpresa Nome da Empresa
	 * @return Lista de Pastas que atendam os parametros
	 */
	@Secured({ "ROLE_BUSCAR_PASTA" })
	Pasta findByTituloLocalEmpresa(String titulo,String nomeLocal, String nomeEmpresa);
	
	/**
	 * Busca a lista de Pasta para impressão de etiquetas a partir de uma consulta pré-formatada
	 * @param where Critério para consulta
	 * @return Lista de Pastas que atendam o referido critério
	 */
	@Secured({ "ROLE_BUSCAR_PASTA" })
	List<Pasta> consultaEtiquetaPasta(String where);
	
	@Secured({ "ROLE_BUSCAR_PASTA" })
	List<Pasta> localizarPasta(String from, String where);
	
	/**
	 * Busca a lista de Pastas a partir de uma Empresa e/ou Local e/ou Situação
	 * @param emp Empresa com ID
	 * @param local Local com ID 
	 * @return Lista de Pastas que atendam o referido filtro
	 */
	@Secured({ "ROLE_BUSCAR_PASTA" })
	List<Pasta> findByEmpresaLocal(int emp, int local);

	/**
	 * Busca a lista de Pastas a partir de uma Empresa e/ou Local e/ou Situação
	 * @param emp Empresa com ID
	 * @param local Local com ID
	 * @param situacao Situação com ID, caso venha Null será buscado por todos
	 * @return Lista de Pastas que atendam o referido filtro
	 */
	@Secured({ "ROLE_BUSCAR_PASTA" })
	List<Pasta> findByEmpresaLocalSituacao(int emp, int local, SituacaoExpurgo situacao);
	
	/**
	 * Busca Pastas a partir de uma Caixeta
	 * @param caixeta Caixeta a ser buscada
	 * @return Lista de Pastas da referida Caixeta
	 */
	@Secured({ "ROLE_BUSCAR_PASTA" })
	List<Pasta> findByCaixeta(String caixeta);
	
	/**
	 * Atualiza um Pasta
	 * @param pasta Pasta a ser atualizada
	 * @return Pasta sincronizada com o banco
	 * @throws BusinessException Caso ocorra algum erro de negócio
	 * @throws CadastroDuplicadoException Caso haja algum cadastro duplicado
	 */
	@Secured({ "ROLE_ATUALIZAR_PASTA" })
	@Transactional
	@Log(descricao="Alteração")
	Pasta merge(Pasta pasta) throws BusinessException, CadastroDuplicadoException;
	
	/**
	 * Remove um Pasta
	 * @param pasta Pasta a ser removida
	 * @throws BusinessException Caso ocorra algum erro de negócio
	 */
	@Secured({ "ROLE_REMOVER_PASTA" })
	@Transactional
	@Log(descricao="Exclusão")
	void remove(Pasta pasta) throws BusinessException;
	
	/**
	 * Insere um nova Pasta
	 * @param pasta Pasta a ser incluida
	 * @return Pasta sincronizada com o banco
	 * @throws CadastroDuplicadoException Caso haja algum cadastro duplicado
	 * @throws BusinessException Caso ocorra algum erro de negócio
	 */
	@Secured({ "ROLE_INCLUIR_PASTA" })
	@Transactional
	@Log(descricao="Inclusão")
	Pasta persist(Pasta pasta) throws CadastroDuplicadoException, BusinessException;
	
	/**
	 * Gera o relatório de Pastas
	 * @param parameters Parametros para geração do relatório de Pastas
	 * @param localRelatorio Local para geração do relatório
	 * @return Relatório de Pastas
	 */
	@Secured({ "ROLE_IMPRIMIR_PASTA" })
	@Transactional
	@Log(descricao="Impressão")
	Relatorio getRelatorio(HashMap<String, Object> parameters, String localRelatorio);
	
	/**
	 * Busca as Pastas com empréstimos FINALIZADOS
	 * @return Lista de Pastas com empréstimos FINALIZADOS
	 */
	List<Pasta> findPastaAtivasEmprestimo();
	
	/**
	 * Busca a lista de Pasta a partir de uma Empresa e/ou Local e com empréstimo em ABERTO
	 * @param emp Empresa com ID
	 * @param local Local com ID
	 * @return Lista de Pastas com empréstimos em ABERTO
	 */
	List<Pasta> findByEmpresaLocalEmprestimo(Long emp, Long local);
}
