package br.com.archeion.persistencia.tipodocumento;

import br.com.archeion.modelo.tipodocumento.TipoDocumento;
import br.com.archeion.persistencia.GenericDAO;

/**
 * Classe reponsável pelo pela manutenção de Tipos de Documentos.
 * 
 * @author SInforme
 */
public interface TipoDocumentoDAO extends GenericDAO<TipoDocumento, Long> {
	
	/**
	 * Busca um Tipo de Documento a partir do nome
	 * @param name Nome a ser buscado
	 * @return Tipo de Documento com o referido nome
	 */
	TipoDocumento findByName(String name);
}
