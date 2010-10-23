package br.com.archeion.persistencia.documento;

import java.util.List;

import br.com.archeion.modelo.documento.Documento;
import br.com.archeion.persistencia.GenericDAO;

/**
 * Classe reponsável pelo pela manutenção de Documentos.
 * 
 * @author SInforme
 */
public interface DocumentoDAO extends GenericDAO<Documento, Long> {
	
	/**
	 * Busca um Documento a partir de seu ID
	 * @param d Documento com ID preenchido
	 * @return Documento
	 */
	Documento findByDocumento(Documento d);

	/**
	 * Busca os Documentos a partir do ID de um Local
	 * @param id ID do Local
	 * @return Lista de Documentos
	 */
	List<Documento> findByLocal(Long id);
	
	/**
	 * Busca os Documentos a partir do seu Tipo
	 * @param id ID do Tipo desejado
	 * @return Lista de Documentos
	 */
	List<Documento> findByTipo(Long id);
	
	/**
	 * Buscas os documentos a partir do ID de uma Pasta
	 * @param id ID da Pasta
	 * @return Lista de Documentos
	 */
	List<Documento> findByPasta(Long id);
	
	/**
	 * Consulta Documentos a patir de uma consulta pré-definida
	 * @param where Consulta que deseja realizar
	 * @return Lista de Documentos
	 */
	List<Documento> consultarDocumento(String where);
}
