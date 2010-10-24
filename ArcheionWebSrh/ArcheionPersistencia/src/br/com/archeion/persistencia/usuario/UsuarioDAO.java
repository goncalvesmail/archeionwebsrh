package br.com.archeion.persistencia.usuario;

import java.util.List;

import br.com.archeion.modelo.usuario.Usuario;
import br.com.archeion.persistencia.GenericDAO;

/**
 * Classe repons�vel pelo pela manuten��o de Usu�rios.
 * 
 * @author SInforme
 */
public interface UsuarioDAO extends GenericDAO<Usuario, Long> {

	/**
	 * Retorna o Usu�rio com o login informado.
	 * 
	 * @param login Login do usu�rio
	 * @return Usuario com o referido login
	 */
	Usuario findByLogin(String login);
	
	/**
	 * Retorna o Usu�rio com o login informado.
	 * 
	 * @param usuario Usu�rio com login
	 * @return Usuario com o referido login
	 */
	List<Usuario> findByLogin(Usuario usuario);

	/**
	 * Retorna uma lista de usu�rios passando como parametro uma lista de id's
	 * @param listaId
	 * @return Lista de usu�rios
	 */
	List<Usuario> find(List<Usuario> listaId);

	/**
	 * Retorna a lista de Usu�rios.
	 * 
	 * @return List<Usuario>
	 */
	List<Usuario> findAll();
	
	/**
	 * Busca a lista dos Usu�rios que possuem permiss�o de alugar
	 * @return Lista dos Usu�rios com permiss�o de alugar
	 */
	List<Usuario> findAllAluga();

}
