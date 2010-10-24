package br.com.archeion.persistencia.enderecocaixa;

import br.com.archeion.modelo.enderecocaixa.EnderecoCaixa;
import br.com.archeion.persistencia.GenericDAO;

/**
 * Classe reponsável pelo pela manutenção de Endereços de Caixa.
 * 
 * @author SInforme
 */
public interface EnderecoCaixaDAO extends GenericDAO<EnderecoCaixa, Long> {

	/**
	 * Busca um Endereço de Caixa a partir do nome de um Vão
	 * @param name Nome do Vão
	 * @return Endereço de Caixa com o respectivo nome de Vão
	 */
	public EnderecoCaixa findByName(String name);
	
	/**
	 * Busca um Endereço de Caixa a partir do nome do Vão, e intervalo inicial e final
	 * @param endereco Endereço de Caixa com o intervalo a ser buscado
	 * @return Endereço de Caixa com os valores desejados
	 */
	public EnderecoCaixa findIntervalo(EnderecoCaixa endereco);

}
