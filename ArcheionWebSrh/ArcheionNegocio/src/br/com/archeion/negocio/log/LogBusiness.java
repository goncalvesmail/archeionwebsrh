package br.com.archeion.negocio.log;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import util.Relatorio;
import br.com.archeion.exception.CadastroDuplicadoException;
import br.com.archeion.modelo.log.Log;

/**
 * Classe repons�vel pelo m�todos de neg�cio relacionados � manuten��o de Log do sistema.
 * 
 * @author SInforme
 */
public interface LogBusiness {	
	
	/**
	 * Busca todos os Logs do sistema
	 * @return Lista de Logs do sistema
	 */
	List<Log> findAll();
	
	/**
	 * Busca lista de Logs a partir de um Usu�rio e um per�odo 
	 * @param nome do Usu�rio
	 * @param iniDate Data inicial
	 * @param fimDate Data final
	 * @return Lista de Logs que atendem os parametros informados
	 */
	List<Log> findAll(String user, Date iniDate, Date fimDate);
	
	/**
	 * Busca um Log a partir do seu ID
	 * @param id ID do Log desejado
	 * @return Log com o referido ID
	 */
	Log findById(Long id);
	
	/**
	 * Grava uma nova informa��o de Log
	 * @param log Log a ser persistido
	 * @return Log sincronizado com o banco
	 * @throws CadastroDuplicadoException Caso haja algum cadastro duplicado
	 */
	@Transactional
	Log persist(Log log) throws CadastroDuplicadoException;

	/**
	 * Gera relat�rio de Logs do sistema
	 * @param parameters Parametros para gera��o do Log
	 * @param localRelatorio Local para gera��o do Log
	 * @return Relat�rio de Log
	 */
	@Transactional
	Relatorio getRelatorio(HashMap<String, String> parameters, String localRelatorio);
}
