package br.com.archeion.negocio.caixa;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.acegisecurity.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

import util.Relatorio;
import br.com.archeion.exception.BusinessException;
import br.com.archeion.exception.CadastroDuplicadoException;
import br.com.archeion.modelo.SituacaoExpurgo;
import br.com.archeion.modelo.TipoArquivo;
import br.com.archeion.modelo.caixa.Caixa;
import br.com.archeion.util.Log;

/**
 * Classe reponsável pelo métodos de negócio relacionados à manutenção de Caixas.
 * 
 * @author SInforme
 */
public interface CaixaBO {	

	/**
	 * Busca as Caixas ATIVAS e diponíveis para empréstimo
	 * @return Lista de Caixa
	 */
	@Secured({ "ROLE_BUSCAR_CAIXA" })
	List<Caixa> findCaixaAtivasEmprestimo();
	
	/**
	 * Buscas as Caixas ATIVAS e prontas para expurgo
	 * @return Lista de caixas
	 */
	@Secured({ "ROLE_BUSCAR_CAIXA" })
	List<Caixa> findCaixaAtivasExpurgo();
	
	/**
	 * Buscas as Caixas que ocupam um determinado Vão
	 * @param nomeVao Nome do Vão
	 * @return Lista de Caixas
	 */
	@Secured({ "ROLE_BUSCAR_CAIXA" })
	List<Caixa> findVaoOcupados(String nomeVao);
	
	/**
	 * Buscas as Caixas de acordo com o ID de um Vão
	 * @param id ID do Vão
	 * @return Lista de Caixas
	 */
	@Secured({ "ROLE_BUSCAR_CAIXA" })
	List<Caixa> findByEndereco(Long id);
	
	/**
	 * Busca TODAS as Caixas
	 * @return Lista de Caixas
	 */
	@Secured({ "ROLE_BUSCAR_CAIXA" })
	List<Caixa> findAll();
	
	/**
	 * Busca as Caixas que pertencem a uma Empresa ou Local, ou ambos.
	 * @param emp ID da Empresa
	 * @param local ID do Local
	 * @return Lista de Caixas
	 */
	@Secured({ "ROLE_BUSCAR_CAIXA" })
	List<Caixa> findByEmpresaLocal(int emp, int local);

	/**
	 * Busca as Caixas que pertencem a uma Empresa ou Local, ou ambos, e que estejam em um determinada Situação
	 * @param emp ID da Empresa
	 * @param local ID do Local
	 * @param situacao Situação desejada
	 * @return Lista de Caixas
	 */
	@Secured({ "ROLE_BUSCAR_CAIXA" })
	List<Caixa> findByEmpresaLocalSituacao(int emp, int local, SituacaoExpurgo situacao); 
	
	/**
	 * Buscas as Caixas, NÃO Expurgadas, a partir de um ID de Caixa e/ou uma Data de Criação e/ou Tipo de Arquivo
	 * @param caixaId ID de Caixa
	 * @param date Data de Criação
	 * @param tipo Tipo de Arquivo
	 * @return Lista de Caixas
	 */
	@Secured({ "ROLE_BUSCAR_CAIXA" })
	List<Caixa> consultaEtiquetaCaixa(int caixaId, Date date, TipoArquivo tipo);
	
	/**
	 * Buscas uma Caixa a partir de seu ID
	 * @param id ID da Caixa
	 * @return Caixa com o ID, ou Null caso não exista
	 */
	@Secured({ "ROLE_BUSCAR_CAIXA" })
	Caixa findById(Long id);

	/**
	 * Busca uma Caixa a partir do seu Vão e a posição ocupada no Vão
	 * @param vao Nome do Vão
	 * @param numero Posição ocupada no Vão
	 * @return Caixa que ocupada o local
	 */
	@Secured({ "ROLE_BUSCAR_CAIXA" })
	Caixa findByVaoNumero(String vao, int numero);

	/**
	 * Busca uma Caixa a partir do seu Vão e a posição ocupada no Vão, e que NÃO esteja Exourgada.
	 * @param vao Nome do Vão
	 * @param numero Posição ocupada no Vão
	 * @return Caixa que ocupada o local
	 */
	@Secured({ "ROLE_BUSCAR_CAIXA" })
	Caixa findByVaoNumeroAtiva(String vao, int numero);
	
	/**
	 * Atualiza uma Caixa
	 * @param caixa Caixa a ser atualizada
	 * @return Caixa com os valores sincronizados com o banco
	 * @throws BusinessException Caso ocorra algum problema na validação
	 * @throws CadastroDuplicadoException Caso haja um cadastro duplicado da Caixa
	 */
	@Secured({ "ROLE_ATUALIZAR_CAIXA" })
	@Transactional
	@Log(descricao="Alteração")
	Caixa merge(Caixa caixa) throws BusinessException, CadastroDuplicadoException;
	
	/**
	 * Exclui um determinada Caixa
	 * @param caixa Caixa a ser excluida
	 * @throws BusinessException Caso ocorra algum problema na exclusão
	 */
	@Secured({ "ROLE_REMOVER_CAIXA" })
	@Transactional
	@Log(descricao="Exclusão")
	void remove(Caixa caixa) throws BusinessException;
	
	/**
	 * Inclui uma nova Caixa
	 * @param caixa Caixa a ser incluida
	 * @return Caixa sincronizada com o Banco
	 * @throws CadastroDuplicadoException Caso haja um cadastro duplicado da Caixa
	 * @throws BusinessException Caso ocorra algum problema na validação
	 */
	@Secured({ "ROLE_INCLUIR_CAIXA" })
	@Transactional
	@Log(descricao="Inclusão")
	Caixa persist(Caixa caixa) throws CadastroDuplicadoException, BusinessException;
	
	/**
	 * Monta o relatório de Caixas
	 */
	@Secured({ "ROLE_IMPRIMIR_CAIXA" })
	@Transactional
	Relatorio getRelatorio(HashMap<String, Object> parameters, String localRelatorio);
}
