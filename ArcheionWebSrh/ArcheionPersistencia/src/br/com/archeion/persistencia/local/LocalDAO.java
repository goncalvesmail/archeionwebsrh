package br.com.archeion.persistencia.local;

import java.util.List;

import br.com.archeion.modelo.empresa.Empresa;
import br.com.archeion.modelo.local.Local;
import br.com.archeion.persistencia.GenericDAO;

/**
 * Classe reponsável pelo pela manutenção de Locais.
 * 
 * @author SInforme
 */
public interface LocalDAO extends GenericDAO<Local, Long> {
	
	/**
	 * Busca um local a partir de um nome e uma Empresa
	 * @param l Local com um nome e com um ID de Empresa
	 * @return Local com o referido nome e da referida Empresa
	 */
	Local findByLocal(Local l);
	
	/**
	 * Busca os Locais de um determinada Empresa
	 * @param empresa Empresa com ID
	 * @return Lista de Locais da respectiva Empresa
	 */
	List<Local> findByEmpresa(Empresa empresa);
}
