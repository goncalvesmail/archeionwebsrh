package br.com.archeion.modelo.documento;

public enum Origem {
	/**
	 * Origem do Sistema Emitido
	 */
	EMITIDO(1, "Emitido"),

	/**
	 * Origem do Sistema Recebido
	 */
	RECEBIDO(2, "Recebido");

	/**
	 * Valor (Ordinal) da enumera��o
	 */
	private Integer id;

	/**
	 * Descri��o (String) da enumera��o
	 */
	private String descricao;

	/**
	 * Construtor da enumera��o
	 * 
	 * @param id Identificador
	 * @param descricao Descri��o
	 */
	private Origem(final Integer id, final String descricao) {
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
