package br.com.archeion.modelo;

/**
 * Indica a situa��o de uma pasta ou caixa
 * @author SInforme
 *
 */
public enum SituacaoExpurgo {
	
	TODOS(1, "Todas"),
	ATIVA(2, "Ativa"),
	EXPURGADA(3, "Expurgada");	

	/**
	 * Identifica��o �nica
	 */
	private Integer id;
	
	/**
	 * Descri��o
	 */
	private String descricao;
	
	private SituacaoExpurgo(final Integer id, final String descricao) {
		this.id = id;
		this.descricao = descricao;
	}
	public String getDescricao() {
		return descricao;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
}
