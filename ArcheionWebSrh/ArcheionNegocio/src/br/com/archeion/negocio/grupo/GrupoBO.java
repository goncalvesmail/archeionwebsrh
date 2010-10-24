package br.com.archeion.negocio.grupo;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import br.com.archeion.exception.BusinessException;
import br.com.archeion.exception.CadastroDuplicadoException;
import br.com.archeion.modelo.grupo.Grupo;
import br.com.archeion.util.Log;


/**
 * Classe reponsável pelo métodos de negócio relacionados à manutenção de Grupos.
 * 
 * @author SInforme
 */
public interface GrupoBO {

	/**
	 * Retorna a lista com todos os grupos.
	 * @return List<Grupo> Lista de Grupo
	 */
	List<Grupo> findAll();

	/**
	 * Retorna o Grupo a partir do Id informado.
	 * @param id Id do grupo
	 * @return Grupo com o referido ID
	 */
	Grupo findById(Long id);
	
	/**
	 * Retorna o Grupo a partir do nome informado.
	 * @param name Nome do grupo
	 * @return Grupo com o referido nome
	 */
	Grupo findByName(String name);

	/**
	 * Retorna os Grupos a partir de uma lista de grupos com ID's preenchidos.
	 * @param Lista de Grupos com ID's preenchidos
	 * @return Lista de Grupos com os referidos ID´s
	 */
	List<Grupo> findById(List<Grupo> listaId);
	
	/**
	 * Insere um novo Grupo 
	 * @param grupo Grupo a ser inserido
	 * @return Grupo sincronizado com o banco
	 * @throws CadastroDuplicadoException Caso haja cadastro duplicado
	 */
	@Transactional
	@Log(descricao="Inclusão")
	Grupo persist(Grupo grupo) throws CadastroDuplicadoException;
	
	/**
	 * Atualiza um Grupo 
	 * @param grupo Grupo a ser atualizado
	 * @return Grupo sincronizado com o banco
	 * @throws BusinessException Caso ocorra algum erro de negócio
	 * @throws CadastroDuplicadoException Caso haja algum cadastro duplicado
	 */
	@Transactional
	@Log(descricao="Alteração")
	Grupo merge(Grupo grupo) throws BusinessException, CadastroDuplicadoException;
	
	/**
	 * Remove um referido Grupo
	 * @param grupo Grupo a ser removido
	 * @throws BusinessException Caso ocorra algum erro de negócio
	 */
	@Transactional
	@Log(descricao="Exclusão")
	void remove(Grupo grupo) throws BusinessException;

}
