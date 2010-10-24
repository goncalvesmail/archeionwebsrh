package br.com.archeion.persistencia.eventocontagem;

import br.com.archeion.modelo.eventocontagem.EventoContagem;
import br.com.archeion.persistencia.GenericDAO;

/**
 * Classe reponsável pelo pela manutenção de Evento de Contagem.
 * 
 * @author SInforme
 */
public interface EventoContagemDAO extends GenericDAO<EventoContagem, Long>{
	
	/**
	 * Busca um Evendo de Contagem a partir do seu nome
	 * @param name Nome do Evendo de Contagem a ser localizado
	 * @return Evento de Contagem com o respectivo nome
	 */
	public EventoContagem findByName(String name);
}
