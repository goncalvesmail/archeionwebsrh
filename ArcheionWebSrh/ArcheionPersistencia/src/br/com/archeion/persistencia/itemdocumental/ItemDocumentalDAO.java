package br.com.archeion.persistencia.itemdocumental;

import br.com.archeion.modelo.itemdocumental.ItemDocumental;
import br.com.archeion.persistencia.GenericDAO;

/**
 * Classe reponsável pelo pela manutenção de Item Documental.
 * 
 * @author SInforme
 */
public interface ItemDocumentalDAO extends GenericDAO<ItemDocumental, Long> {
	
	/**
	 * Busca um Item Documental a partir do seu Nome
	 * @param nome Nome do Item Documental
	 * @return Item Documental com o referido nome
	 */
	public ItemDocumental findByName(String nome);
}
