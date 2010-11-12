package br.com.archeion.persistencia.log;

import java.util.Date;
import java.util.List;

import br.com.archeion.modelo.log.Log;
import br.com.archeion.persistencia.GenericDAO;

/**
 * Classe repons�vel pelo pela manuten��o de Log do sistema.
 * 
 * @author SInforme
 */
public interface LogDAO extends GenericDAO<Log, Long> {
	
	/**
	 * Busca lista de Logs a partir de um Usu�rio e um per�odo 
	 * @param nome do Usu�rio
	 * @param iniDate Data inicial
	 * @param fimDate Data final
	 * @return Lista de Logs que atendem os parametros informados
	 */
	public List<Log> findAll(String user, Date iniDate, Date fimDate);
}
