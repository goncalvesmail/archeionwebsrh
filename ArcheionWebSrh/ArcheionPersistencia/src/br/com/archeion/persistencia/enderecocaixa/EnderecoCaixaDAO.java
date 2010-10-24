package br.com.archeion.persistencia.enderecocaixa;

import br.com.archeion.modelo.enderecocaixa.EnderecoCaixa;
import br.com.archeion.persistencia.GenericDAO;

/**
 * Classe repons�vel pelo pela manuten��o de Endere�os de Caixa.
 * 
 * @author SInforme
 */
public interface EnderecoCaixaDAO extends GenericDAO<EnderecoCaixa, Long> {

	/**
	 * Busca um Endere�o de Caixa a partir do nome de um V�o
	 * @param name Nome do V�o
	 * @return Endere�o de Caixa com o respectivo nome de V�o
	 */
	public EnderecoCaixa findByName(String name);
	
	/**
	 * Busca um Endere�o de Caixa a partir do nome do V�o, e intervalo inicial e final
	 * @param endereco Endere�o de Caixa com o intervalo a ser buscado
	 * @return Endere�o de Caixa com os valores desejados
	 */
	public EnderecoCaixa findIntervalo(EnderecoCaixa endereco);

}
