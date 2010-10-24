package br.com.archeion.persistencia.grupo;

import java.util.List;

import org.acegisecurity.annotation.Secured;

import br.com.archeion.modelo.grupo.Grupo;
import br.com.archeion.persistencia.GenericDAO;

/**
 * Classe reponsável pelo pela manutenção de Grupo.
 * 
 * @author SInforme
 */
public interface GrupoDAO extends GenericDAO<Grupo, Long> {
	
	/**
	 * Busca uma lista de Grupos a partir de uma lista de ID´s
	 * @param listaId Lista dos ID´s dos quais se deseja o Grupo
	 * @return Lista de Grupo
	 */
	@Secured ({ "ROLE_LISTAR_GRUPO" })
	List<Grupo> findById(List<Grupo> listaId);
	
	/**
	 * Busca um Grupo a partir do seu nome
	 * @param name Nome do Grupo
	 * @return Grupo com o referido nome
	 */
	public Grupo findByName(String name);
	
}
