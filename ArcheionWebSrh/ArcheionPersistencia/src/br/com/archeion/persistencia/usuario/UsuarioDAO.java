package br.com.archeion.persistencia.usuario;

import java.util.List;

import br.com.archeion.modelo.usuario.Usuario;
import br.com.archeion.persistencia.GenericDAO;

/**
 * Classe reponsável pelo pela manutenção de Usuários.
 * 
 * @author SInforme
 */
public interface UsuarioDAO extends GenericDAO<Usuario, Long> {

	/**
	 * Retorna o Usuário com o login informado.
	 * 
	 * @param login Login do usuário
	 * @return Usuario com o referido login
	 */
	Usuario findByLogin(String login);
	
	/**
	 * Retorna o Usuário com o login informado.
	 * 
	 * @param usuario Usuário com login
	 * @return Usuario com o referido login
	 */
	List<Usuario> findByLogin(Usuario usuario);

	/**
	 * Retorna uma lista de usuários passando como parametro uma lista de id's
	 * @param listaId
	 * @return Lista de usuários
	 */
	List<Usuario> find(List<Usuario> listaId);

	/**
	 * Retorna a lista de Usuários.
	 * 
	 * @return List<Usuario>
	 */
	List<Usuario> findAll();
	
	/**
	 * Busca a lista dos Usuários que possuem permissão de alugar
	 * @return Lista dos Usuários com permissão de alugar
	 */
	List<Usuario> findAllAluga();

}
