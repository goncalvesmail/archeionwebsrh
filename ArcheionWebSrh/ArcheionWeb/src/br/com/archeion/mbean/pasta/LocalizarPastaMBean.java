package br.com.archeion.mbean.pasta;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.fill.JRFileVirtualizer;

import org.acegisecurity.AccessDeniedException;

import util.ChavePesquisaPasta;
import util.ChavesPasta;
import util.Operadores;
import util.OperadoresBoleanos;
import util.Relatorio;
import br.com.archeion.jsf.Constants;
import br.com.archeion.jsf.Util;
import br.com.archeion.mbean.ArcheionBean;
import br.com.archeion.mbean.AuthenticationController;
import br.com.archeion.modelo.SituacaoExpurgo;
import br.com.archeion.modelo.pasta.Pasta;
import br.com.archeion.negocio.pasta.PastaBO;
import br.com.archeion.negocio.relatoriotxt.RelatorioTxtBO;

/**
 * ManagedBean para tratar eventos de Localiza��o de Pasta
 * 
 * @author SInforme
 * 
 */
public class LocalizarPastaMBean extends ArcheionBean {

	/**
	 * Listagem para sele�ao de Pasta
	 */
	private List<Pasta> listaPastaTarget;

	/**
	 * Lista com as primeiras chaves de pesquisa
	 */
	private List<SelectItem> listaChaves1;
	/**
	 * Lista com os primeiros operadores
	 */
	private List<SelectItem> listaOperadores1;

	/**
	 * Lista com as segundas chaves de pesquisa
	 */
	private List<SelectItem> listaChaves2;
	/**
	 * Lista com os segundos operadores
	 */
	private List<SelectItem> listaOperadores2;

	/**
	 * Lista com as terceira chaves de pesquisa
	 */
	private List<SelectItem> listaChaves3;
	/**
	 * Lista com os terceiros operadores
	 */
	private List<SelectItem> listaOperadores3;

	/**
	 * Lista de operadores booleanos
	 */
	private List<SelectItem> listaOperadoresBoleanos;
	/**
	 * Lista de situa��es
	 */
	private List<SelectItem> listaSituacao;

	/**
	 * Index da primeira chave selecionada
	 */
	private int chave1;
	/**
	 * Index da segunda chave selecionada
	 */
	private int chave2;
	/**
	 * Index da terceira chave selecionada
	 */
	private int chave3;
	/**
	 * Index do primeiro operador selecionado
	 */
	private int operador1;
	/**
	 * Index do segundo operador selecionado
	 */
	private int operador2;
	/**
	 * Index do terceiro operador selecionado
	 */
	private int operador3;
	/**
	 * Primeiro valor de pesquisa
	 */
	private String valor1;
	/**
	 * Segundo valor de pesquisa
	 */
	private String valor2;
	/**
	 * Terceiro valor de pesquisa
	 */
	private String valor3;

	/**
	 * Index do primeiro operador booleano
	 */
	private int operadorBoleano1;
	/**
	 * Index do segundo operador booleano
	 */
	private int operadorBoleano2;

	/**
	 * Chaves de pesquisa para Pasta
	 */
	private ChavePesquisaPasta chavesPesquisa = new ChavePesquisaPasta();

	/**
	 * Pasta selecionada
	 */
	private Pasta pasta;
	/**
	 * Lista de Pasta pesquisada
	 */
	private List<Pasta> listaPasta;

	/**
	 * BO de Pasta
	 */
	private PastaBO pastaBO = (PastaBO) Util.getSpringBean("pastaBO");
	/**
	 * BO de relatorio
	 */
	private RelatorioTxtBO relatorioTxtBO = (RelatorioTxtBO) Util
			.getSpringBean("relatorioTxtBO");
	/**
	 * BO de Controle de acesso
	 */
	private AuthenticationController authenticationController = (AuthenticationController) Util
			.getManagedBean("authenticationController");

	/**
	 * Redireciona para pagina de localiza��o
	 * 
	 * @return Redireciona para pagina de localiza��o
	 */
	public String goToLocalizarPasta() {
		preparaTelaConsulta();
		return "formularioLocalizarPasta";
	}

	/**
	 * Redireciona para pagina de impressao de etiqueta
	 * 
	 * @return Redireciona para pagina de Etiqueta
	 */
	public String goToEtiquetaPasta() {
		preparaTelaConsulta();
		return "formularioPastaEtiqueta";
	}

	/**
	 * Inicializa os valores para tela de consulta
	 */
	private void preparaTelaConsulta() {
		pasta = new Pasta();
		pasta.setSituacao(SituacaoExpurgo.TODOS);
		listaPasta = new ArrayList<Pasta>();
		listaPastaTarget = new ArrayList<Pasta>();
		listaSituacao = new ArrayList<SelectItem>();

		this.chave1 = -1;
		this.chave2 = -1;
		this.chave3 = -1;

		this.operador1 = 1;
		this.operador2 = 1;
		this.operador3 = 1;

		this.valor1 = "";
		this.valor2 = "";
		this.valor3 = "";

		listaOperadoresBoleanos = new ArrayList<SelectItem>();
		for (OperadoresBoleanos opb : OperadoresBoleanos.values()) {
			listaOperadoresBoleanos.add(new SelectItem(opb.getId(), opb
					.getLabel()));
		}

		// 1
		listaChaves1 = new ArrayList<SelectItem>();
		listaOperadores1 = new ArrayList<SelectItem>();

		for (ChavesPasta c : chavesPesquisa.getChavesPesquisa()) {
			listaChaves1.add(new SelectItem(c.getId(), c.getLabel()));
		}
		for (Operadores op : chavesPesquisa.getTodosOperadores()) {
			listaOperadores1.add(new SelectItem(op.getId(), op.getLabel()));
		}

		// 2
		listaChaves2 = new ArrayList<SelectItem>();
		listaOperadores2 = new ArrayList<SelectItem>();

		for (ChavesPasta c : chavesPesquisa.getChavesPesquisa()) {
			listaChaves2.add(new SelectItem(c.getId(), c.getLabel()));
		}
		for (Operadores op : chavesPesquisa.getTodosOperadores()) {
			listaOperadores2.add(new SelectItem(op.getId(), op.getLabel()));
		}

		// 3
		listaChaves3 = new ArrayList<SelectItem>();
		listaOperadores3 = new ArrayList<SelectItem>();

		for (ChavesPasta c : chavesPesquisa.getChavesPesquisa()) {
			listaChaves3.add(new SelectItem(c.getId(), c.getLabel()));
		}
		for (Operadores op : chavesPesquisa.getTodosOperadores()) {
			listaOperadores3.add(new SelectItem(op.getId(), op.getLabel()));
		}

		for (SituacaoExpurgo situacao : SituacaoExpurgo.values()) {
			listaSituacao.add(new SelectItem(situacao.toString(), situacao
					.getDescricao()));
		}
	}

	/**
	 * Localiza etiquetas de Pasta
	 * 
	 * @return Redireciona para p�gina de Etiquetas
	 */
	public String localizarEtiquetas() {
		this.consultarPasta();

		if (pasta.getSituacao().getId() != SituacaoExpurgo.TODOS.getId()) {
			for (int i = 0; i < listaPasta.size(); i++) {
				Pasta p = listaPasta.get(i);
				if (p.getSituacao().getId() != pasta.getSituacao().getId()) {
					listaPasta.remove(p);
					i--;
				}
			}
		}

		return "formularioPastaEtiqueta";
	}

	/**
	 * Executa a consulta de Pasta
	 * 
	 * @return Redireciona para p�gina de localiza��o de pasta
	 */
	public String localizarPasta() {
		this.consultarPasta();

		if (pasta.getSituacao().getId() != SituacaoExpurgo.TODOS.getId()) {
			for (int i = 0; i < listaPasta.size(); i++) {
				Pasta p = listaPasta.get(i);
				if (p.getSituacao().getId() != pasta.getSituacao().getId()) {
					listaPasta.remove(p);
					i--;
				}
			}
		}

		return "formularioLocalizarPasta";
	}

	/**
	 * Busca todas as Pasta
	 * 
	 * @return Redireciona para p�gina de localiza��o
	 */
	public String localizarTodasPasta() {
		listaPasta = pastaBO.findAll();
		return "formularioLocalizarPasta";
	}

	/**
	 * Metodo acionado quando a primeira chave � alterada
	 * 
	 * @param event
	 */
	public void changedChave1(ValueChangeEvent event) {
		Integer id = (Integer) event.getNewValue();
		ChavesPasta chave = (ChavesPasta) chavesPesquisa.getChaves(id);

		listaOperadores1 = new ArrayList<SelectItem>();
		Operadores[] operadores = chavesPesquisa.getTodosOperadores();
		if (chave != null) {
			operadores = chavesPesquisa.getOperadores(chave.getId());
		}

		for (Operadores op : operadores) {
			listaOperadores1.add(new SelectItem(op.getId(), op.getLabel()));
		}
	}

	/**
	 * Metodo acionado quando a segunda chave � alterada
	 * 
	 * @param event
	 */
	public void changedChave2(ValueChangeEvent event) {
		Integer id = (Integer) event.getNewValue();
		ChavesPasta chave = (ChavesPasta) chavesPesquisa.getChaves(id);

		listaOperadores2 = new ArrayList<SelectItem>();
		Operadores[] operadores = chavesPesquisa.getTodosOperadores();
		if (chave != null) {
			operadores = chavesPesquisa.getOperadores(chave.getId());
		}

		for (Operadores op : operadores) {
			listaOperadores2.add(new SelectItem(op.getId(), op.getLabel()));
		}
	}

	/**
	 * Metodo acionado quando a terceira chave � alterada
	 * 
	 * @param event
	 */
	public void changedChave3(ValueChangeEvent event) {
		Integer id = (Integer) event.getNewValue();
		ChavesPasta chave = (ChavesPasta) chavesPesquisa.getChaves(id);

		listaOperadores3 = new ArrayList<SelectItem>();
		Operadores[] operadores = chavesPesquisa.getTodosOperadores();
		if (chave != null) {
			operadores = chavesPesquisa.getOperadores(chave.getId());
		}

		for (Operadores op : operadores) {
			listaOperadores3.add(new SelectItem(op.getId(), op.getLabel()));
		}
	}

	/**
	 * Executa a consulta de Pasta
	 */
	private void consultarPasta() {
		listaPasta = new ArrayList<Pasta>();
		listaPastaTarget = new ArrayList<Pasta>();
		String from = null;

		StringBuffer sb = new StringBuffer();

		if (this.chave1 != -1 && !this.valor1.equals("")) {
			String conv = ChavesPasta.getConversorValue(this.chave1);
			if (ChavesPasta.getFromValue(this.chave1) != null) {
				from = ChavesPasta.getFromValue(this.chave1);
			}

			if (conv == null) {
				sb.append("UPPER(" + ChavesPasta.getDataBaseValue(this.chave1)
						+ ")");
			} else {
				if (conv.equals("date")) {
					sb.append(ChavesPasta.getDataBaseValue(this.chave1));
				} else {
					sb.append(ChavesPasta.getDataBaseValue(this.chave1));
				}
			}
			sb.append(Operadores.getDataBaseValue(this.operador1));
			if (conv != null && !conv.equals("date")) {
				sb.append("'");
			}
			if (this.operador1 == Operadores.CONTEM.getId())
				sb.append("%");

			if (conv != null && conv.equals("date")) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				try {
					sb.append("to_date(' ");
					sb.append(sdf.format(sdf.parse(this.valor1)));
					sb.append(" ','DD/MM/YYYY') ");
				} catch (ParseException e) {
					addMessage(FacesMessage.SEVERITY_INFO,
							"error.business.dataInvalida",
							ArcheionBean.PERSIST_FAILURE);
					return;
				}
			} else {
				sb.append(this.valor1.toUpperCase());
			}
			if (this.operador1 == Operadores.CONTEM.getId())
				sb.append("%");
			if (conv != null && !conv.equals("date")) {
				sb.append("'");
			}

			if (this.chave2 != -1 && !this.valor2.equals("")) {
				String conv2 = ChavesPasta.getConversorValue(this.chave2);
				if ((from != null)
						&& (ChavesPasta.getFromValue(this.chave2) != null)) {
					from += "," + ChavesPasta.getFromValue(this.chave2);
				} else {
					if (from == null) {
						from = ChavesPasta.getFromValue(this.chave2);
					}
				}

				sb.append(OperadoresBoleanos
						.getDataBaseValue(this.operadorBoleano1));
				if (conv2 == null) {
					sb.append("UPPER("
							+ ChavesPasta.getDataBaseValue(this.chave2) + ")");
				} else {
					sb.append(ChavesPasta.getDataBaseValue(this.chave2));
				}
				sb.append(Operadores.getDataBaseValue(this.operador2));
				if (conv2 != null && !conv2.equals("date")) {
					sb.append("'");
				}
				if (this.operador2 == Operadores.CONTEM.getId())
					sb.append("%");

				if (conv2 != null && conv2.equals("date")) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					try {
						sb.append("to_date(' ");
						sb.append(sdf.format(sdf.parse(this.valor2)));
						sb.append(" ','DD/MM/YYYY') ");
					} catch (ParseException e) {
						addMessage(FacesMessage.SEVERITY_INFO,
								"error.business.dataInvalida",
								ArcheionBean.PERSIST_FAILURE);
						return;
					}
				} else {
					sb.append(this.valor2.toUpperCase());
				}
				if (this.operador2 == Operadores.CONTEM.getId())
					sb.append("%");
				if (conv2 != null && !conv2.equals("date")) {
					sb.append("'");
				}

				if (this.chave3 != -1 && !this.valor3.equals("")) {
					String conv3 = ChavesPasta.getConversorValue(this.chave3);
					if ((from != null)
							&& (ChavesPasta.getFromValue(this.chave3) != null)) {
						from += "," + ChavesPasta.getFromValue(this.chave3);
					} else {
						if (from == null) {
							from = ChavesPasta.getFromValue(this.chave3);
						}
					}

					sb.append(OperadoresBoleanos
							.getDataBaseValue(this.operadorBoleano2));
					if (conv3 == null) {
						sb.append("UPPER("
								+ ChavesPasta.getDataBaseValue(this.chave3)
								+ ")");
					} else {
						sb.append(ChavesPasta.getDataBaseValue(this.chave3));
					}
					sb.append(Operadores.getDataBaseValue(this.operador3));
					if (conv3 != null && !conv3.equals("date")) {
						sb.append("'");
					}
					if (this.operador3 == Operadores.CONTEM.getId())
						sb.append("%");

					if (conv3 != null && conv3.equals("date")) {
						SimpleDateFormat sdf = new SimpleDateFormat(
								"dd/MM/yyyy");
						try {
							sb.append("to_date(' ");
							sb.append(sdf.format(sdf.parse(this.valor3)));
							sb.append(" ','DD/MM/YYYY') ");
						} catch (ParseException e) {
							addMessage(FacesMessage.SEVERITY_INFO,
									"error.business.dataInvalida",
									ArcheionBean.PERSIST_FAILURE);
							return;
						}
					} else {
						sb.append(this.valor3.toUpperCase());
					}
					if (this.operador3 == Operadores.CONTEM.getId())
						sb.append("%");
					if (conv3 != null && !conv3.equals("date")) {
						sb.append("'");
					}
				}
			}
		}

		if (!sb.toString().equals("")) {
			String rf = "";
			if (from != null) {
				String[] f = from.split(",");
				HashSet<String> fs = new HashSet<String>();

				for (int i = 0; i < f.length; i++) {
					if (!fs.contains(f[i])) {
						fs.add(f[i]);
					}
				}

				Iterator<String> iterator = fs.iterator();

				while (iterator.hasNext()) {
					rf += iterator.next() + ",";
				}
				rf = rf.substring(0, rf.lastIndexOf(","));
			}

			listaPasta = pastaBO.localizarPasta(rf, sb.toString());
			// listaPasta = pastaBO.consultaEtiquetaPasta(sb.toString());
		} else {
			listaPasta = pastaBO.findAll();
		}
	}

	/**
	 * Impress�o do resultado da busca
	 * 
	 * @return Redireciona para p�gina de impress�o
	 */
	public String imprimirLocalizar() {
		FacesContext context = getContext();
		try {
			if (listaPasta.size() <= 0) {
				addMessage(FacesMessage.SEVERITY_INFO,
						"pasta.error.selecione.pasta",
						ArcheionBean.PERSIST_FAILURE);
				return goToLocalizarPasta();
			}
			HttpServletResponse response = (HttpServletResponse) context
					.getExternalContext().getResponse();

			ServletOutputStream responseStream;
			responseStream = response.getOutputStream();
			String pathJasper = ((ServletContext) context.getExternalContext()
					.getContext()).getRealPath("/WEB-INF/relatorios/")
					+ "/ArcheionLocalizarPasta.jasper";
			HashMap<String, Object> param = new HashMap<String, Object>();
			ParametrosReport ids = new ParametrosReport();
			for (Pasta p : listaPasta) {
				ids.add(p.getId());
			}
			param.put("idsPasta", ids.toString());
			param.put("user", authenticationController.getUsuario().getNome());
			Relatorio relatorio = pastaBO.getRelatorio(param, pathJasper);
			relatorio.exportarParaPdfStream(responseStream);

			response.setContentType("application/pdf");
			response.setHeader("Content-disposition",
					"filename=\"RelacaoPasta.pdf\"");
			responseStream.flush();
			responseStream.close();
			context.renderResponse();
			context.responseComplete();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		} catch (AccessDeniedException aex) {
			return Constants.ACCESS_DENIED;
		}
		return goToLocalizarPasta();
	}

	/**
	 * Impress�o do resultado em TXT
	 * 
	 * @return Redireciona para p�gina de impress�o
	 */
	public String imprimirTxt() {
		FacesContext context = getContext();
		try {
			HttpServletResponse response = (HttpServletResponse) context
					.getExternalContext().getResponse();

			ServletOutputStream responseStream;
			responseStream = response.getOutputStream();

			ParametrosReport ids = new ParametrosReport();
			for (Pasta p : listaPasta) {
				ids.add(p.getId());
			}

			StringBuilder sb = new StringBuilder(
					"select d.nm_empresa as empresa, b.nm_local as local, c.nm_item_documental as item_documental, ");
			sb
					.append("a.nm_titulo as titulo, a.nm_caixeta as caixeta, a.dt_expurgo as data_expurgo, (f.vao_endereco_caixa || e.nu_vao_endereco_caixa) as caixa, ");
			sb
					.append("(case when a.cs_situacao_pasta = 1 then 'Ativa' when a.cs_situacao_pasta = 2 then 'Expurgada' end ) as situacao ");
			sb
					.append("from tb_pasta a join tb_local b on (a.id_local = b.id_local) ");
			sb
					.append("join tb_item_documental c on (a.id_item_documental = c.id_item_documental) ");
			sb.append("join tb_empresa d on (b.id_empresa = d.id_empresa) ");
			sb.append("left join tb_caixa e on (a.id_caixa = e.id_caixa) ");
			sb
					.append("left join tb_endereco_caixa f on (e.id_endereco_caixa = f.id_endereco_caixa) ");
			sb.append("where a.id_pasta in (");
			sb.append(ids.toString());
			sb.append(") order by 1,2,3,4");

			relatorioTxtBO.geraRelatorioTxt(sb.toString(), responseStream);

			response.setContentType("application/txt");
			response.setHeader("Content-disposition",
					"filename=\"relatorio.txt\"");
			responseStream.flush();
			responseStream.close();
			context.renderResponse();
			context.responseComplete();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (AccessDeniedException aex) {
			return Constants.ACCESS_DENIED;
		}
		return goToLocalizarPasta();
	}

	/**
	 * Impress�o Etiquetas a partir das Caixas
	 * 
	 * @return Redirecina para p�gina de Etiquetas
	 */
	public String imprimirEtiquetaPorCaixa() {
		FacesContext context = getContext();
		try {
			if (listaPastaTarget.size() <= 0) {
				addMessage(FacesMessage.SEVERITY_INFO,
						"pasta.error.selecione.pasta",
						ArcheionBean.PERSIST_FAILURE);
				return goToEtiquetaPasta();
			}
			HttpServletResponse response = (HttpServletResponse) context
					.getExternalContext().getResponse();
			// Cria um cache na pasta web-inf
			JRFileVirtualizer fileVirtualizer = new JRFileVirtualizer(
					100,
					((ServletContext) context.getExternalContext().getContext())
							.getRealPath("/WEB-INF/"));

			ServletOutputStream responseStream;
			responseStream = response.getOutputStream();
			String pathJasper = ((ServletContext) context.getExternalContext()
					.getContext()).getRealPath("/WEB-INF/relatorios/")
					+ "/ArcheionEtiquetaPastaPorCaixa.jasper";
			HashMap<String, Object> param = new HashMap<String, Object>();
			ParametrosReport ids = new ParametrosReport();
			for (Pasta p : listaPastaTarget) {
				ids.add(p.getId());
			}
			// Seta o parametro REPORT_VIRTUALIZER com o diret�rio onde ser�
			// armazenado o cache
			param.put(JRParameter.REPORT_VIRTUALIZER, fileVirtualizer);
			param.put("idsPasta", ids.toString());
			Relatorio relatorio = pastaBO.getRelatorio(param, pathJasper);
			relatorio.exportarParaPdfStream(responseStream);

			response.setContentType("application/pdf");
			response.setHeader("Content-disposition",
					"filename=\"EtiquetaPasta.pdf\"");
			responseStream.flush();
			responseStream.close();
			context.renderResponse();
			context.responseComplete();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		} catch (AccessDeniedException aex) {
			return Constants.ACCESS_DENIED;
		}
		return goToEtiquetaPasta();
	}

	/**
	 * Impress�o Etiquetas a partir das Caixetas
	 * 
	 * @return Redirecina para p�gina de Etiquetas
	 */
	public String imprimirEtiquetaPorCaixeta() {
		FacesContext context = getContext();

		try {
			if (listaPastaTarget.size() <= 0) {
				addMessage(FacesMessage.SEVERITY_INFO,
						"pasta.error.selecione.pasta",
						ArcheionBean.PERSIST_FAILURE);
				return goToEtiquetaPasta();
			}
			HttpServletResponse response = (HttpServletResponse) context
					.getExternalContext().getResponse();
			// Cria um cache na pasta web-inf
			JRFileVirtualizer fileVirtualizer = new JRFileVirtualizer(
					100,
					((ServletContext) context.getExternalContext().getContext())
							.getRealPath("/WEB-INF/"));

			ServletOutputStream responseStream;
			responseStream = response.getOutputStream();
			String pathJasper = ((ServletContext) context.getExternalContext()
					.getContext()).getRealPath("/WEB-INF/relatorios/")
					+ "/ArcheionEtiquetaPastaPorCaixeta.jasper";
			HashMap<String, Object> param = new HashMap<String, Object>();
			ParametrosReport ids = new ParametrosReport();
			for (Pasta p : listaPastaTarget) {
				ids.add(p.getId());
			}
			// Seta o parametro REPORT_VIRTUALIZER com o diret�rio onde ser�
			// armazenado o cache
			param.put(JRParameter.REPORT_VIRTUALIZER, fileVirtualizer);
			param.put("idsPasta", ids.toString());
			Relatorio relatorio = pastaBO.getRelatorio(param, pathJasper);
			relatorio.exportarParaPdfStream(responseStream);

			response.setContentType("application/pdf");
			response.setHeader("Content-disposition",
					"filename=\"EtiquetaPasta.pdf\"");
			responseStream.flush();
			responseStream.close();
			context.renderResponse();
			context.responseComplete();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		} catch (AccessDeniedException aex) {
			return Constants.ACCESS_DENIED;
		}
		return goToEtiquetaPasta();
	}

	// GETs & SETs

	public List<SelectItem> getListaChaves1() {
		return listaChaves1;
	}

	public void setListaChaves1(List<SelectItem> listaChaves1) {
		this.listaChaves1 = listaChaves1;
	}

	public List<SelectItem> getListaOperadores1() {
		return listaOperadores1;
	}

	public void setListaOperadores1(List<SelectItem> listaOperadores1) {
		this.listaOperadores1 = listaOperadores1;
	}

	public List<SelectItem> getListaChaves2() {
		return listaChaves2;
	}

	public void setListaChaves2(List<SelectItem> listaChaves2) {
		this.listaChaves2 = listaChaves2;
	}

	public List<SelectItem> getListaOperadores2() {
		return listaOperadores2;
	}

	public void setListaOperadores2(List<SelectItem> listaOperadores2) {
		this.listaOperadores2 = listaOperadores2;
	}

	public List<SelectItem> getListaChaves3() {
		return listaChaves3;
	}

	public void setListaChaves3(List<SelectItem> listaChaves3) {
		this.listaChaves3 = listaChaves3;
	}

	public List<SelectItem> getListaOperadores3() {
		return listaOperadores3;
	}

	public void setListaOperadores3(List<SelectItem> listaOperadores3) {
		this.listaOperadores3 = listaOperadores3;
	}

	public int getChave1() {
		return chave1;
	}

	public void setChave1(int chave1) {
		this.chave1 = chave1;
	}

	public int getChave2() {
		return chave2;
	}

	public void setChave2(int chave2) {
		this.chave2 = chave2;
	}

	public int getChave3() {
		return chave3;
	}

	public void setChave3(int chave3) {
		this.chave3 = chave3;
	}

	public int getOperador1() {
		return operador1;
	}

	public void setOperador1(int operador1) {
		this.operador1 = operador1;
	}

	public int getOperador2() {
		return operador2;
	}

	public void setOperador2(int operador2) {
		this.operador2 = operador2;
	}

	public int getOperador3() {
		return operador3;
	}

	public void setOperador3(int operador3) {
		this.operador3 = operador3;
	}

	public String getValor1() {
		return valor1;
	}

	public void setValor1(String valor1) {
		this.valor1 = valor1;
	}

	public String getValor2() {
		return valor2;
	}

	public void setValor2(String valor2) {
		this.valor2 = valor2;
	}

	public String getValor3() {
		return valor3;
	}

	public void setValor3(String valor3) {
		this.valor3 = valor3;
	}

	public int getOperadorBoleano1() {
		return operadorBoleano1;
	}

	public void setOperadorBoleano1(int operadorBoleano1) {
		this.operadorBoleano1 = operadorBoleano1;
	}

	public int getOperadorBoleano2() {
		return operadorBoleano2;
	}

	public void setOperadorBoleano2(int operadorBoleano2) {
		this.operadorBoleano2 = operadorBoleano2;
	}

	public ChavePesquisaPasta getChavesPesquisa() {
		return chavesPesquisa;
	}

	public void setChavesPesquisa(ChavePesquisaPasta chavesPesquisa) {
		this.chavesPesquisa = chavesPesquisa;
	}

	public Pasta getPasta() {
		return pasta;
	}

	public void setPasta(Pasta pasta) {
		this.pasta = pasta;
	}

	public List<Pasta> getListaPasta() {
		return listaPasta;
	}

	public void setListaPasta(List<Pasta> listaPasta) {
		this.listaPasta = listaPasta;
	}

	public PastaBO getPastaBO() {
		return pastaBO;
	}

	public void setPastaBO(PastaBO pastaBO) {
		this.pastaBO = pastaBO;
	}

	public List<SelectItem> getListaOperadoresBoleanos() {
		return listaOperadoresBoleanos;
	}

	public void setListaOperadoresBoleanos(
			List<SelectItem> listaOperadoresBoleanos) {
		this.listaOperadoresBoleanos = listaOperadoresBoleanos;
	}

	public List<Pasta> getListaPastaTarget() {
		return listaPastaTarget;
	}

	public void setListaPastaTarget(List<Pasta> listaPastaTarget) {
		this.listaPastaTarget = listaPastaTarget;
	}

	public List<SelectItem> getListaSituacao() {
		return listaSituacao;
	}

	public void setListaSituacao(List<SelectItem> listaSituacao) {
		this.listaSituacao = listaSituacao;
	}

	public AuthenticationController getAuthenticationController() {
		return authenticationController;
	}

	public void setAuthenticationController(
			AuthenticationController authenticationController) {
		this.authenticationController = authenticationController;
	}

}
