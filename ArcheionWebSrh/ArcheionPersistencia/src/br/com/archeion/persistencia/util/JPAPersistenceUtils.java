package br.com.archeion.persistencia.util;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import br.com.archeion.util.UnidadePersistencia;

/**
 * Classe com m�todos �teis para o JPA. Utilizadas nos Testes Unit�rios.
 * 
 */
public class JPAPersistenceUtils {

	/**
	 * Retorna uma inst�ncia do EntityManager a partir da Unidade de
	 * Persist�ncia informada.
	 * 
	 * @param unidadePersistencia
	 *            o nome da Unidade de Persist�ncia.
	 * @return Uma inst�ncia do Entity Manager.
	 */
	public static EntityManager getEntityManager(
			final String unidadePersistencia) {
		return Persistence.createEntityManagerFactory(unidadePersistencia)
				.createEntityManager();
	}

	/**
	 * Limpa todas os registros de uma entidade. Usado para testes.
	 * 
	 * @param nomeEntidade
	 *            O nome da entidade.
	 * @return O n�mero de registros apagados.
	 */
	public static int limpar(final String nomeEntidade) {
		int count = 0;
		EntityManager em = getEntityManager(getUnidadePersistencia());
		em.getTransaction().begin();
		try {
			count = (Integer) em.createQuery("DELETE FROM " + nomeEntidade)
					.executeUpdate();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		em.getTransaction().commit();
		return count;
	}

	/**
	 * Retorna a Unidade de Persist�ncia.
	 * 
	 * @return String
	 */
	public static String getUnidadePersistencia() {
		return UnidadePersistencia.unidadePersistencia.getDescricao();
	}
}
