package br.com.archeion.persistencia.ttd;

import java.util.List;

import br.com.archeion.modelo.ttd.TTD;
import br.com.archeion.persistencia.GenericDAO;

/**
 * Classe reponsável pelo pela manutenção de TTD (Tabela de Temporaridade de Documentos).
 * 
 * @author SInforme
 */
public interface TTDDAO extends GenericDAO<TTD, Long> {
	
	/**
	 * Busca uma TTD a partir do Local, Item Documental e Evento de Contagem
	 * @param ttd TTD com Local, Item Documental e Evento de Contagem
	 * @return TTD que contempla os parametros recebidos
	 */
	TTD findByTTD(TTD ttd);
	
	/**
	 * Busca as TTD de acordo com a Empresa e/ou Local e/ou Item Documental
	 * @param emp Empresa com ID
	 * @param local Local com ID
	 * @param item Item Documental com ID
	 * @return Lista de TTD que contemple os parametros passados
	 */
	List<TTD> findByEmpresaLocalItemDocumental(int emp, int local, int item);
	
	/**
	 * Busca uma TTD a partir de um Evento de Contagem
	 * @param idEvento ID do Evento de Contagem
	 * @return Lista de TTD do referido Evento de Contagem
	 */
	List<TTD> findByEvento(Long idEvento);
	
	/**
	 * Atualiza as pastas ligadas a uma determinada TTD quando esta for alterada. Setando a previsão de expurgo, caso temporária,
	 * ou colocando para null caso permanente. Além de retirar as Pastas das Caixas caso o tipo da TTD tenha sido alterado.
	 * 
	 * @param ttd A TTD da qual seram atualizadas as Pastas.
	 */
	void atualizarPastasTTD(TTD ttd);
}
