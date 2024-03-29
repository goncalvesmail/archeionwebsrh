package br.com.archeion.negocio.usuario;

import java.util.HashMap;
import java.util.List;

import org.acegisecurity.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

import util.Relatorio;
import br.com.archeion.exception.BusinessException;
import br.com.archeion.exception.CadastroDuplicadoException;
import br.com.archeion.modelo.grupo.Grupo;
import br.com.archeion.modelo.usuario.Usuario;
import br.com.archeion.util.Log;

/**
 * Classe repons�vel pelo m�todos de neg�cio relacionados � manuten��o de Usu�rios.
 * 
 * @author SInforme
 */
public interface UsuarioBO {

	/**
	 * Inclui um novo Usu�rio
	 * @param usuario Usu�rio a ser incluido
	 * @return Usu�rio sincronizado com o banco
	 */
	@Transactional
	@Log(descricao="Inclus�o")
	public Usuario salvar(Usuario usuario);
	

	/**
	 * retorna uma lista de grupos usu�rios
	 * @return Lista de Grupos
	 */	
	public List<Grupo> findAllGrupo();
	
	/**
	 * retorna uma lista de grupos usu�rios passando como parametro uma lista de id's
	 * @return Lista de Grupos
	 */
	public 	List<Grupo> findGrupobyId( List<Grupo> listaId );
	
	/**
	 * Retorna o Usu�rio com o login informado.
	 * 
	 * @param usuario Usu�rio com login
	 * @return Usuario com o referido login
	 */
	@Secured({ "ROLE_BUSCAR_USUARIO" })
	List<Usuario> findByLogin(Usuario usuario);

	/**
	 * Retorna a lista de Usu�rios.
	 * 
	 * @return List<Usuario>
	 */
	@Secured({ "ROLE_BUSCAR_USUARIO" })
	List<Usuario> findAll();
	

	/**
	 * Retorna uma lista de usu�rios passando como parametro uma lista de id's
	 * @param listaId
	 * @return Lista de usu�rios
	 */
	@Secured({ "ROLE_BUSCAR_USUARIO" })
	List<Usuario> find(List<Usuario> listaId);

	
	/**
	 * Retorna um usu�rio passando como parametro um id
	 * @param id
	 * @return Usuario
	 */
	@Secured({ "ROLE_ATUALIZAR_USUARIO" })
	Usuario findById(Long id);
	
	
	/**
	 * Persiste a entidade Usuario passada como parametro
	 * @param usuario
	 * @return usuario persistido
	 * @throws BusinessException
	 * @throws CadastroDuplicadoException 
	 */
	@Secured( { "ROLE_INCLUIR_USUARIO" })
	@Transactional
	@Log(descricao="Inclus�o")
	Usuario persist(Usuario usuario) throws BusinessException, CadastroDuplicadoException;

	/**
	 * Altera a entidade Usuario passada como parametro
	 * @param usuario
	 * @return usuario alterado
	 * @throws BusinessException
	 * @throws CadastroDuplicadoException 
	 */
	@Secured( { "ROLE_ATUALIZAR_USUARIO" })
	@Transactional
	@Log(descricao="Altera��o")
	Usuario merge(Usuario usuario) throws BusinessException, CadastroDuplicadoException;

	@Log(descricao="Altera��o")
	Usuario mergeSenha(Usuario usuario) throws BusinessException, CadastroDuplicadoException;
	
	/**
	 * Exclui uma entidade Usuario passada como parametro
	 * @param usuario
	 * @return 
	 * @throws BusinessException
	 */
	@Secured( { "ROLE_REMOVER_USUARIO" })
	@Transactional
	@Log(descricao="Exclus�o")
	void remove(final Usuario usuario) throws BusinessException;
	
	/**
	 * Gera o relat�rio de Usu�rio
	 * @param parameters Parametros para gera��o do relat�rio
	 * @param localRelatorio Local para gera��o do relat�rio
	 * @return Relat�rio de Usu�rio
	 */
	@Secured( { "ROLE_IMPRIMIR_USUARIO" })
	Relatorio getRelatorio(HashMap<String, Object> parameters, String localRelatorio);

	/**
	 * Busca a lista dos Usu�rios que possuem permiss�o de alugar
	 * @return Lista dos Usu�rios com permiss�o de alugar
	 */
	List<Usuario> findAllAluga();
}


