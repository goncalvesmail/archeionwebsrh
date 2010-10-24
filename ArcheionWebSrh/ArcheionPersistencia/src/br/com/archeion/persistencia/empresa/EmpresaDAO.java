package br.com.archeion.persistencia.empresa;

import java.util.List;

import br.com.archeion.modelo.empresa.Empresa;
import br.com.archeion.persistencia.GenericDAO;

/**
 * Classe repons�vel pela manuten��o de Empresa.
 * 
 * @author SInforme
 */
public interface EmpresaDAO extends GenericDAO<Empresa, Long> {
	
	/**
	 * Busca as Empresas que n�o possuem pai
	 * @return Lista de Empresas
	 */
	public List<Empresa> findRoots() ;
	
	/**
	 * Busca uma Empresa a partir do seu Nome
	 * @param nome Nome da Empresa
	 * @return Empresa com o respectivo Nome
	 */
	public Empresa findByName(String name);
	
	/**
	 * Busca todas as Empresas, inclusive INVIS�VEIS
	 * @return Lista de Empresas
	 */
	public List<Empresa> findAllInvisivel();

}
