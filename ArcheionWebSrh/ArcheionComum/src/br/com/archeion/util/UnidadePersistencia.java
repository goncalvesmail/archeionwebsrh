package br.com.archeion.util;

/**
 * 
 * Informa qual a Unidade de Persist�ncia que est� sendo utilizada.
 * 
 */
public class UnidadePersistencia {
	
	/**
	 * Unidade de Persist�ncia utilizada.
	 */
	public static ListaUnidadesPersistencia unidadePersistencia;

	/**
	 * @see java.lang.Object#toString()
	 * @return String
	 */
	@Override
	public String toString() {
		return unidadePersistencia.getDescricao();
	}
}
