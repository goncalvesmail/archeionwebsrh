package br.com.archeion.negocio.relatoriotxt;

import java.io.OutputStream;

/**
 * Classe gen�rica para gera��o de Relat�rios no formato TXT
 * 
 * @author SInforme
 */
public interface RelatorioTxtBO {
	
	/**
	 * M�todo respons�vel pela gera��o de Relat�rios no formato TXT
	 * @param sql Consulta para confec��o do relat�rio
	 * @param stream Stream de saida do Relat�rio
	 */
	public void geraRelatorioTxt(String sql, OutputStream stream);

}
