package br.com.archeion.modelo;

/**
 * Indica a situação de uma pasta ou caixa
 * @author SInforme
 *
 */
public enum SituacaoExpurgo {
	
	TODOS(1, "Todas"),
	ATIVA(2, "Ativa"),
	EXPURGADA(3, "Expurgada");	

	/**
	 * Identificação única
	 */
	private Integer id;
	
	/**
	 * Descrição
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
