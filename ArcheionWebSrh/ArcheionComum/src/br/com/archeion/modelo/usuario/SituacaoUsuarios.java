package br.com.archeion.modelo.usuario;

/**
 * Enumera��o que relaciona as Situa��es de um Usu�rio.
 * 
 */
public enum SituacaoUsuarios {

	/**
	 * Usu�rio do Sistema ativo
	 */
	ATIVO(1, "Ativo"),

	/**
	 * Usu�rio do Sistema Inativo
	 */
	INATIVO(2, "Inativo");

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
	private SituacaoUsuarios(final Integer id, final String descricao) {
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



