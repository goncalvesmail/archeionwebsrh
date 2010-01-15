package br.com.archeion.bd;

/**
 * 
 * Lista de Unidades de Persist�ncia poss�veis.
 * 
 */
public enum ListaUnidadesPersistencia {

	/**
	 * Unidade utilizada para persistir os dados no BD.
	 */
	ARCHEION(1, "ARCHEION"),

	
	/**
	 * Unidade utilizada nos Testes Unit�rios.
	 */
	ARCHEION_POSTGRE(2, "ARCHEION_POSTGRE");

	/**
	 * Valor (Ordinal) da enumera��o.
	 */
	private Integer id;

	/**
	 * Descri��o (String) da enumera��o.
	 */
	private String descricao;

	/**
	 * Construtor da enumera��o.
	 * 
	 * @param id
	 *            the id to set
	 */
	private ListaUnidadesPersistencia(final Integer id) {
		this.id = id;
	}

	/**
	 * Construtor da enumera��o.
	 * 
	 * @param id
	 *            the id to set
	 * @param descricao
	 *            the descricao to set
	 */
	private ListaUnidadesPersistencia(final Integer id, final String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	/**
	 * @return Recuperando o campo descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @return Recuperando o campo id
	 */
	public Integer getId() {
		return id;
	}

}
