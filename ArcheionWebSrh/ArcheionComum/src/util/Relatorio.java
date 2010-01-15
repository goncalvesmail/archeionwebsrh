package util;

import java.sql.*;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import net.sf.jasperreports.engine.*;

/**
 * Relatorio.java<br>
 * 
 * <p>
 * A classe Relatorio deve ser utilizada para emiss�o de relat�rios. Ap�s a
 * cria��o de algum relat�rio � poss�vel exib�-lo na tela, export�-lo para um
 * arquivo pdf/html ou envi�-lo direto para impress�o.
 * </p>
 * 
 * <p>
 * A classe manipula relat�rios desenvolvidos utilizando-se a ferramenta iReport
 * e utiliza a ferramenta JasperReports para emiss�o dos relat�rios
 * </p>
 * 
 */
public class Relatorio {

	/** Representa o relat�rio gerado. */
	private JasperPrint jasperPrint_;

	/**
	 * Cria um novo Relatorio.
	 * 
	 * @param conn
	 *            Conex�o com o banco de dados.
	 * @param parameters
	 *            Par�metros a serem exibidos no relat�rio.
	 * @param localRelatorio
	 *            Localiza��o do relat�rio.
	 * @throws JRException
	 *             Caso o relat�rio n�o seja encontrado ou haja algum problema
	 *             com ele, uma exce��o � gerada.
	 */
	public Relatorio(Connection conn, HashMap<String, Object> parameters,
			URL localRelatorio) throws JRException {

		try {

			// O objeto JasperReport representa o objeto JasperDesign (arquivo
			// .jrxml) compilado.
			// Ou seja, o arquivo .jasper
			JasperReport jr = (JasperReport) JRLoader
					.loadObject(localRelatorio);

			// JasperPrint representa o relat�rio gerado.
			// � criado um JasperPrint a partir de um JasperReport, contendo o
			// relat�rio preenchido.
			this.jasperPrint_ = JasperFillManager.fillReport(jr, parameters,
					conn);
		} catch (JRException e) {
			throw e;
		}
	}

	/**
	 * Cria um novo Relatorio.
	 * 
	 * @param conn
	 *            Conex�o com o banco de dados.
	 * @param parameters
	 *            Par�metros a serem exibidos no relat�rio.
	 * @param localRelatorio
	 *            Localiza��o do relat�rio.
	 * @throws JRException
	 *             Caso o relat�rio n�o seja encontrado ou haja algum problema
	 *             com ele, uma exce��o � gerada.
	 */
	public Relatorio(Connection conn, HashMap<String, Object> parameters, String localRelatorio)
			throws JRException {

		try {

			// O objeto JasperReport representa o objeto JasperDesign (arquivo
			// .jrxml) compilado.
			// Ou seja, o arquivo .jasper
			JasperReport jr = (JasperReport) JRLoader
					.loadObject(localRelatorio);

			// JasperPrint representa o relat�rio gerado.
			// � criado um JasperPrint a partir de um JasperReport, contendo o
			// relat�rio preenchido.
			this.jasperPrint_ = JasperFillManager.fillReport(jr, parameters,
					conn);

		} catch (JRException e) {
			throw e;
		}
	}

	/**
	 * Cria um novo Relatorio.
	 * 
	 * @param conn
	 *            Conex�o com o banco de dados.
	 * @param sql
	 *            Express�o SQL (SELECT...) a ser utilizada para preenchimento
	 *            do relat�rio
	 * @param parameters
	 *            Par�metros a serem exibidos no relat�rio.
	 * @param localRelatorio
	 *            Localiza��o do relat�rio.
	 * @throws JRException
	 *             Caso o relat�rio n�o seja encontrado ou haja algum problema
	 *             com ele, uma exce��o � gerada.
	 * @throws SQLException
	 *             Caso exista alguma diverg�ncia ou problema com a Express�o
	 *             SQL passada como par�metro, uma exce��o � gerada.
	 */
	public Relatorio(Connection conn, String sql, HashMap<String, String> parameters,
			URL localRelatorio) throws SQLException, JRException {
		Statement st = null;
		ResultSet rs = null;
		try {

			// O objeto JasperReport representa o objeto JasperDesign (arquivo
			// .jrxml) compilado.
			// Ou seja, o arquivo .jasper
			JasperReport jr = (JasperReport) JRLoader
					.loadObject(localRelatorio);

			// Resultado da consulta
			st = conn.createStatement();
			rs = st.executeQuery(sql);

			// JRResultSetDataSource � uma implementa�ao de JRDataSource, o qual
			// � requerido
			// como parametro para preencher o relat�rio criado.
			// Ele armazena o dados do ResultSet
			JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);

			// JasperPrint representa o relat�rio gerado.
			// � criado um JasperPrint a partir de um JasperReport, contendo o
			// relat�rio preenchido.
			this.jasperPrint_ = JasperFillManager.fillReport(jr, parameters,
					jrRS);

			rs.close();

		} catch (SQLException e) {
			throw e;
		} catch (JRException e) {
			throw e;
		} finally {
			if(st != null) {
				st.close();
			}
			if(rs != null){
				rs.close();
			}
		}
	}

	/**
	 * Cria um novo Relatorio.
	 * 
	 * @param conn
	 *            Conex�o com o banco de dados.
	 * @param sql
	 *            Express�o SQL (SELECT...) a ser utilizada para preenchimento
	 *            do relat�rio
	 * @param parameters
	 *            Par�metros a serem exibidos no relat�rio.
	 * @param localRelatorio
	 *            Localiza��o do relat�rio.
	 * @throws JRException
	 *             Caso o relat�rio n�o seja encontrado ou haja algum problema
	 *             com ele, uma exce��o � gerada.
	 * @throws SQLException
	 *             Caso exista alguma diverg�ncia ou problema com a Express�o
	 *             SQL passada como par�metro, uma exce��o � gerada.
	 */
	public Relatorio(Connection conn, String sql, HashMap<String, String> parameters,
			String localRelatorio) throws SQLException, JRException {
		Statement st = null;
		ResultSet rs = null;
		try {

			// O objeto JasperReport representa o objeto JasperDesign (arquivo
			// .jrxml) compilado.
			// Ou seja, o arquivo .jasper
			JasperReport jr = (JasperReport) JRLoader
					.loadObject(localRelatorio);

			// Resultado da consulta
			st = conn.createStatement();
			rs = st.executeQuery(sql);

			// JRResultSetDataSource � uma implementa�ao de JRDataSource, o qual
			// � requerido
			// como parametro para preencher o relat�rio criado.
			// Ele armazena o dados do ResultSet
			JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);

			// JasperPrint representa o relat�rio gerado.
			// � criado um JasperPrint a partir de um JasperReport, contendo o
			// relat�rio preenchido.
			this.jasperPrint_ = JasperFillManager.fillReport(jr, parameters,
					jrRS);

			rs.close();

		} catch (SQLException e) {
			throw e;
		} catch (JRException e) {
			throw e;
		}finally {
			if(st != null) {
				st.close();
			}
			if(rs != null){
				rs.close();
			}
		}
	}

	/**
	 * Cria um novo Relatorio.
	 * 
	 * @param parameters
	 *            Par�metros a serem exibidos no relat�rio.
	 * @param localRelatorio
	 *            Localiza��o do relat�rio.
	 * @throws JRException
	 *             Caso o relat�rio n�o seja encontrado ou haja algum problema
	 *             com ele, uma exce��o � gerada.
	 */
	public Relatorio(HashMap<String, String> parameters, URL localRelatorio) throws JRException {

		try {

			// O objeto JasperReport representa o objeto JasperDesign (arquivo
			// .jrxml) compilado.
			// Ou seja, o arquivo .jasper
			JasperReport jr = (JasperReport) JRLoader
					.loadObject(localRelatorio);

			// JREmptyDataSource � uma implementa�ao de JRDataSource, o qual �
			// requerido
			// como parametro para preencher o relat�rio criado.
			// Ele armazena o dados do ResultSet, que, neste caso, � vazio
			JREmptyDataSource jrEDS = new JREmptyDataSource();

			// Jasper Print representa o relat�rio gerado.
			// � criado um JasperPrint a partir de um JasperReport, contendo o
			// relat�rio preenchido.
			this.jasperPrint_ = JasperFillManager.fillReport(jr, parameters,
					jrEDS);

		} catch (JRException e) {
			throw e;
		}
	}

	/**
	 * Cria um novo Relatorio
	 * 
	 * @param parameters
	 *            Par�metros a serem exibidos no relat�rio.
	 * @param localRelatorio
	 *            Localiza��o do relat�rio.
	 * @throws JRException
	 *             Caso o relat�rio n�o seja encontrado ou haja algum problema
	 *             com ele, uma exce��o � gerada.
	 */
	public Relatorio(HashMap<String, String> parameters, String localRelatorio)
			throws JRException {

		try {

			// O objeto JasperReport representa o objeto JasperDesign (arquivo
			// .jrxml) compilado.
			// Ou seja, o arquivo .jasper
			JasperReport jr = (JasperReport) JRLoader
					.loadObject(localRelatorio);

			// JREmptyDataSource � uma implementa�ao de JRDataSource, o qual �
			// requerido
			// como parametro para preencher o relat�rio criado.
			// Ele armazena o dados do ResultSet, que, neste caso, � vazio
			JREmptyDataSource jrEDS = new JREmptyDataSource();

			// Jasper Print representa o relat�rio gerado.
			// � criado um JasperPrint a partir de um JasperReport, contendo o
			// relat�rio preenchido.
			this.jasperPrint_ = JasperFillManager.fillReport(jr, parameters,
					jrEDS);

		} catch (JRException e) {
			throw e;
		}
	}

	/**
	 * Exibe o relat�rio na tela.
	 */
	public void exibirRelatorio() {
		// emite o relat�rio na tela
		// false indica que a aplica��o n�o ser� finalizada caso o relat�rio
		// seja fechado
		JasperViewer.viewReport(this.jasperPrint_, false);
	}

	/**
	 * Grava o relat�rio em um arquivo de formato pdf.
	 * 
	 * @param caminhoDestino
	 *            Caminho onde o arquivo ser� gravado.
	 */
	public void exportaParaPdf(String caminhoDestino) throws JRException {

		try {
			// Gera o arquivo PDF
			JasperExportManager.exportReportToPdfFile(this.jasperPrint_,
					caminhoDestino);
		} catch (JRException e) {
			throw e;
		}

	}

	/**
	 * Grava o relat�rio em um arquivo de formato html.
	 * 
	 * @param caminhoDestino
	 *            Caminho onde o arquivo ser� gravado.
	 */
	public void exportaParaHtml(String caminhoDestino) throws JRException {

		try {
			// Gera o arquivo PDF
			JasperExportManager.exportReportToHtmlFile(this.jasperPrint_,
					caminhoDestino);
		} catch (JRException e) {
			throw e;
		}

	}
	
	public void exportarParaPdfStream(OutputStream outputStream) throws JRException {
		try {
			JasperExportManager.exportReportToPdfStream(this.jasperPrint_, outputStream);
		} catch (JRException e) {
			throw e;
		}
	}

	/**
	 * Envia o relat�rio para impress�o, exibindo uma caixa de dialogo de
	 * impress�o ou n�o.
	 * 
	 * @param exibeCaixaDialogo
	 *            Boolean indicando se ser� exibida uma caixa de di�logo ou n�o.
	 */
	public void imprimir(boolean exibeCaixaDialogo) throws JRException {

		try {
			// Imprime o relat�rio
			// o segundo par�metro indica se existir� uma caixa de dialogo antes
			// ou nao
			JasperPrintManager
					.printReport(this.jasperPrint_, exibeCaixaDialogo);
		} catch (JRException e) {
			throw e;
		}

	}

}
