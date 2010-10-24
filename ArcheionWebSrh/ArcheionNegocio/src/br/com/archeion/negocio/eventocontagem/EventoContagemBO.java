package br.com.archeion.negocio.eventocontagem;

import java.util.HashMap;
import java.util.List;

import org.acegisecurity.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

import util.Relatorio;
import br.com.archeion.exception.BusinessException;
import br.com.archeion.exception.CadastroDuplicadoException;
import br.com.archeion.modelo.eventocontagem.EventoContagem;
import br.com.archeion.util.Log;

/**
 * Classe reponsável pelo métodos de negócio relacionados à manutenção de Evento de Contagem.
 * 
 * @author SInforme
 */
public interface EventoContagemBO {
	
	/**
	 * Busca todos os Eventos de Contagem
	 * @return Lista de Eventos de Contagem
	 */
	@Secured({ "ROLE_BUSCAR_EVENTO_CONTAGEM" })
	List<EventoContagem> findAll();
	
	/**
	 * Busca um Evento de Contagem a partir de um ID
	 * @param id ID do Evento de Contagem
	 * @return Evento de Contagem com o respectivo ID
	 */
	@Secured({ "ROLE_BUSCAR_EVENTO_CONTAGEM" })
	EventoContagem findById(Long id);
	
	/**
	 * Atualiza um Evento de Contagem
	 * @param item Evento de Contagem a ser atualizado
	 * @return Evento de Contagem sincronizado com o banco
	 * @throws BusinessException Caso ocorra algum erro de negócio
	 * @throws CadastroDuplicadoException Caso haja um cadastro duplicado
	 */
	@Transactional
	@Secured({ "ROLE_ATUALIZAR_EVENTO_CONTAGEM" })
	@Log(descricao="Alteração")
	EventoContagem merge(EventoContagem item) throws BusinessException, CadastroDuplicadoException;
	
	/**
	 * Remove um Evento de Contagem
	 * @param item Evento de Contagem a ser removido
	 * @throws BusinessException Caso ocorra algum erro de negócio
	 */
	@Transactional
	@Secured({ "ROLE_REMOVER_EVENTO_CONTAGEM" })
	@Log(descricao="Exclusão")
	void remove(EventoContagem item) throws BusinessException;
	
	/**
	 * Insere um novo Evento de Contagem
	 * @param item Evento de Contagem a ser inserido
	 * @return Evento de Contagem sincronizado com o banco
	 * @throws CadastroDuplicadoException Caso haja um cadastro duplicado
	 */
	@Transactional
	@Secured({ "ROLE_INCLUIR_EVENTO_CONTAGEM" })
	@Log(descricao="Inclusão")
	EventoContagem persist(EventoContagem item) throws CadastroDuplicadoException;
	
	/**
	 * Gera o relatório de Evento de Contagem
	 * @param parameters Parametros para geração do Evendo de Contagem
	 * @param localRelatorio Local para geração do relatório e Evendo de Contagem
	 * @return Relatório de Evento de Contagem
	 */
	@Transactional
	@Secured({ "ROLE_IMPRIMIR_EVENTO_CONTAGEM" })
	Relatorio getRelatorio(HashMap<String, Object> parameters, String localRelatorio);
}
