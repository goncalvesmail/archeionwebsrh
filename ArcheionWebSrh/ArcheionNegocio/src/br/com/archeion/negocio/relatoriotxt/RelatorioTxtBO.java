package br.com.archeion.negocio.relatoriotxt;

import java.io.OutputStream;

/**
 * Classe genérica para geração de Relatórios no formato TXT
 * 
 * @author SInforme
 */
public interface RelatorioTxtBO {
	
	/**
	 * Método responsável pela geração de Relatórios no formato TXT
	 * @param sql Consulta para confecção do relatório
	 * @param stream Stream de saida do Relatório
	 */
	public void geraRelatorioTxt(String sql, OutputStream stream);

}
