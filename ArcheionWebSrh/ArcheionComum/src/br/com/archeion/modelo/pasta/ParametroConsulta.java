package br.com.archeion.modelo.pasta;

import br.com.archeion.modelo.SituacaoExpurgo;

/**
 * Classe para os parametros das consultas
 * @author SInforme
 */
public class ParametroConsulta {

	/**
	 * Nome do parametro de consulta
	 */
	private String parametrosConsulta;
	
	/**
	 * Situação
	 */
	private SituacaoExpurgo situacao;
	
	public String getParametrosConsulta() {
		return parametrosConsulta;
	}
	public void setParametrosConsulta(String parametrosConsulta) {
		this.parametrosConsulta = parametrosConsulta;
	}
	public SituacaoExpurgo getSituacao() {
		return situacao;
	}
	public void setSituacao(SituacaoExpurgo situacao) {
		this.situacao = situacao;
	}	
	
}
