package br.com.archeion.modelo.pasta;

import br.com.archeion.modelo.SituacaoExpurgo;

public class ParametroConsulta {

	private String parametrosConsulta;
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
