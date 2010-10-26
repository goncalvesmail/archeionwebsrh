package br.com.archeion.mbean.pasta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;

import org.acegisecurity.AccessDeniedException;

import util.Relatorio;
import br.com.archeion.exception.BusinessException;
import br.com.archeion.exception.CadastroDuplicadoException;
import br.com.archeion.jsf.Constants;
import br.com.archeion.jsf.Util;
import br.com.archeion.mbean.ArcheionBean;
import br.com.archeion.mbean.AuthenticationController;
import br.com.archeion.mbean.ExceptionManagedBean;
import br.com.archeion.modelo.empresa.Empresa;
import br.com.archeion.modelo.local.Local;
import br.com.archeion.modelo.pasta.EmprestimoPasta;
import br.com.archeion.modelo.pasta.Pasta;
import br.com.archeion.modelo.usuario.Usuario;
import br.com.archeion.negocio.empresa.EmpresaBO;
import br.com.archeion.negocio.local.LocalBO;
import br.com.archeion.negocio.pasta.EmprestimoPastaBO;
import br.com.archeion.negocio.pasta.PastaBO;
import br.com.archeion.negocio.relatoriotxt.RelatorioTxtBO;
import br.com.archeion.negocio.usuario.UsuarioBO;

/**
 * ManagedBean para tratar eventos de Emprestimo de Pasta
 *  
 * @author SInforme
 *
 */
public class EmprestimoPastaMBean extends ArcheionBean {

	/**
	 * Objeto de Emprestimo de Pasta para inclusão/alteração/exclusão
	 */
	private EmprestimoPasta emprestimo;
	/**
	 * Lista de pasta
	 */
	private List<SelectItem> listaPasta;
	/**
	 * Lista de Reponsavel
	 */
	private List<SelectItem> listaResponsavel;
	/**
	 * Lista de Solicitantes
	 */
	private List<SelectItem> listaSolicitante;
	/**
	 * Lista de Emrpesa
	 */
	private List<SelectItem> listaEmpresa;
	/**
	 * Lista de Local
	 */
	private List<SelectItem> listaLocal;
	/**
	 * Lista de Emprestimo de Pasta
	 */
	private List<EmprestimoPasta> listaEmprestimoPasta;
	/**
	 * Lista de Emprestimo de Pasta para emprestimo por Caixeta
	 */
	private List<Pasta> listaEmprestarPastaPorCaixeta;
	/**
	 * Map de Pastas para emprestimo por Caixeta
	 */
	private Map mapPastas;
	/**
	 * Pasta selecionada no Emprestimo por Caixeta
	 */
	private Pasta pastaEmprestar;
	/**
	 * Data para filtro
	 */
	private Date dataFiltro;
	/**
	 * Usuário para filtro
	 */
	private Usuario usuarioFiltro;
	/**
	 * Situação para filtro
	 */
	private String situacaoFiltro;
	/**
	 * Pasta para Filtro
	 */
	private Pasta pastaFiltro;
	/**
	 * Indicador de emprestimo
	 */
	private boolean emprestada = true;
	/**
	 * Caixeta utilizada para filtragem do Emprestimo
	 */
	private String caixetaPraEmprestar;
	/**
	 * Empresa selecionada
	 */
	private Empresa empresaSelcionada;

	/**
	 * BO de Pasta
	 */
	private PastaBO pastaBO = (PastaBO) Util.getSpringBean("pastaBO");
	/**
	 * BO de Usuário
	 */
	private UsuarioBO usuarioBO = (UsuarioBO) Util.getSpringBean("usuarioBO");
	/**
	 * BO de Empresa
	 */
	private EmpresaBO empresaBO = (EmpresaBO) Util.getSpringBean("empresaBO");
	/**
	 * BO de Local
	 */
	private LocalBO localBO = (LocalBO) Util.getSpringBean("localBO");
	/**
	 * BO de Emprestimo de Pasta
	 */
	private EmprestimoPastaBO emprestimoPastaBO = (EmprestimoPastaBO) Util
			.getSpringBean("emprestimoPastaBO");
	/**
	 * BO de Relatório
	 */
	private RelatorioTxtBO relatorioTxtBO = (RelatorioTxtBO) Util
			.getSpringBean("relatorioTxtBO");
	/**
	 * BO de Controle de Autenticação
	 */
	private AuthenticationController authenticationController = (AuthenticationController) Util
			.getManagedBean("authenticationController");

	/**
	 * Construtor
	 */
	public EmprestimoPastaMBean() {
		emprestimo = new EmprestimoPasta();
		pastaFiltro = new Pasta();
		
	}
	
	/**
	 * Gera relatório no formato TXT
	 * @return Redireciona para página de relatório
	 */
	public String imprimirTxt() {
		FacesContext context = getContext();
		try {
			HttpServletResponse response = (HttpServletResponse) context
					.getExternalContext().getResponse();

			ServletOutputStream responseStream;
			responseStream = response.getOutputStream();
			StringBuilder sb = new StringBuilder(
					"select b.nm_titulo as pasta, a.dt_emprestimo as data_emprestimo, a.dt_devolucao as data_devolucao, ");
			sb.append("a.tx_solicitante_externo as solicitante_externo, ");
			sb
					.append("c.nm_usuario as solicitante_interno, d.nm_usuario as responsavel_emprestimo ");
			sb
					.append("from tb_emprestimo_pasta a join tb_pasta b on (a.id_pasta = b.id_pasta) ");
			sb
					.append("join tb_usuarios d on (a.id_usuario_responsavel  = d.id_usuario) ");
			sb
					.append("left join tb_usuarios c on (a.id_usuario_solicitante = c.id_usuario) ");
			sb.append("order by 2,1");

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
		return findAll();
	}

	/**
	 * Método disparado quando o combo de Empresa é altera
	 * @param event
	 */
	public void valueChangedLista(ValueChangeEvent event) {

		Long empId = (Long) event.getNewValue();
		empresaSelcionada = new Empresa();
		empresaSelcionada.setId(empId);

		pastaFiltro.getLocal().setId(null);
		pastaFiltro.getLocal().setEmpresa(empresaSelcionada);

		preencherCombos();
		//preencherComboLocal();
	}

	/**
	 * Método disparado quando o combo de Local é altero
	 * @param event
	 */
	public void valueChangedLocal(ValueChangeEvent event) {
		Long localId = (Long) event.getNewValue();
		Local local = new Local();
		local.setId(localId);
		local.setEmpresa(empresaSelcionada);

		pastaFiltro.setLocal(local);		

		preencherCombos();
		//preencherComboPasta();
	}
	
	/**
	 * Preenche os combos de filtragem
	 */
	private void preencherCombos() {
		listaPasta = new ArrayList<SelectItem>();
		listaResponsavel = new ArrayList<SelectItem>();
		listaSolicitante = new ArrayList<SelectItem>();
		listaEmpresa = new ArrayList<SelectItem>();
		listaLocal = new ArrayList<SelectItem>();
		
		List<Empresa> empresas = empresaBO.findAll();
		for(Empresa emp: empresas) {
			listaEmpresa.add(new SelectItem(emp.getId(),emp.getNome()));
		}
		
		if ( (pastaFiltro.getLocal().getEmpresa() == null || 
				pastaFiltro.getLocal().getEmpresa().getId() == null || 
				pastaFiltro.getLocal().getEmpresa().getId() == 0) && empresas.size()>0 ) {
			empresaSelcionada = empresas.get(0);
			pastaFiltro.getLocal().setEmpresa(empresaSelcionada);
		}
		
		if ( pastaFiltro.getLocal().getEmpresa() != null && 
				pastaFiltro.getLocal().getEmpresa().getId() != null && 
				pastaFiltro.getLocal().getEmpresa().getId() != 0 ) {
			List<Local> locais = localBO.findByEmpresa(pastaFiltro.getLocal().getEmpresa());
			for(Local loc: locais){
				listaLocal.add(new SelectItem(loc.getId(),loc.getNome())); 
			}
			
			if ( locais!=null && locais.size()>0 && 
					(pastaFiltro.getLocal()==null || pastaFiltro.getLocal().getId()==null 
							|| pastaFiltro.getLocal().getId()==0) ) {
				pastaFiltro.setLocal(locais.get(0));
			}
		}		
		
		List<Usuario> responsaveis = usuarioBO.findAllAluga();
		for( Usuario u:responsaveis ) {
			listaResponsavel.add(new SelectItem(u.getId(),u.getNome()));
		}

		List<Usuario> solicitantes  = usuarioBO.findAll();
		for( Usuario u:solicitantes ) {
			listaSolicitante.add(new SelectItem(u.getId(),u.getNome()));
		}		

		List<Pasta> pastas = null;		
		if ( pastaFiltro.getLocal().getId()!=null && pastaFiltro.getLocal().getId().longValue()!=0 ) {
			pastas = pastaBO.findByEmpresaLocalEmprestimo(pastaFiltro.getLocal().getEmpresa().getId(),
					pastaFiltro.getLocal().getId());
		}
		else if ( pastaFiltro.getLocal().getEmpresa().getId()!=null 
					&& pastaFiltro.getLocal().getEmpresa().getId().longValue()!=0 ) {
			pastas = pastaBO.findByEmpresaLocalEmprestimo(pastaFiltro.getLocal().getEmpresa().getId(),
					-1l);
		}
		else {
			pastas = new ArrayList<Pasta>();
		}
		String auxPasta = "";
		for( Pasta p:pastas ) {
			auxPasta = p.getTitulo();
			if(p.getDescricao() != null){
				auxPasta += " - " + p.getDescricao(); 
			}
			
			if(p.getNumeroProtocolo() != null){
				auxPasta += " - " + p.getNumeroProtocolo();
			}
			listaPasta.add(new SelectItem(p.getId(),auxPasta));
		}
	}
	
	/**
	 * Método chamado pela página para inclusão de Empréstimo
	 * @return Pagina de listagem
	 */
	public String incluir() {
		try {
			incluirMBean();
		} catch (AccessDeniedException aex) {
			return Constants.ACCESS_DENIED;
		} catch (CadastroDuplicadoException e) {
			addMessage(FacesMessage.SEVERITY_INFO,
					"error.business.cadastro.duplicado",
					ArcheionBean.PERSIST_FAILURE);
			return "formularioEmprestimoPasta";
		} catch (BusinessException e) {
			addMessage(FacesMessage.SEVERITY_INFO, e.getMessageCode(),
					ArcheionBean.PERSIST_FAILURE);
			return "formularioEmprestimoPasta";
		} catch (Exception e) {
			ExceptionManagedBean excBean = (ExceptionManagedBean) Util
					.getManagedBean("exceptionManagedBean");
			excBean.setExc(e);
			return Constants.ERROR_HANDLER;
		}
		return findAll();
	}
	
	/**
	 * Inclui um Empréstimo a partir da Caixeta
	 * @return Listagem geral
	 */
	public String incluirPorCaixeta() {
	
		try {
			
			
			if(pastaEmprestar==null || pastaEmprestar.getId()==null || pastaEmprestar.getId()==0){
				addMessage(FacesMessage.SEVERITY_INFO,
						"error.business.nenhum.radio.selecionado",
						ArcheionBean.PERSIST_FAILURE);
				return "formularioEmprestimoPastaPorCaixeta";
			}
			
			emprestimo.setPasta(pastaEmprestar);
			
			incluirMBean();
		} catch (AccessDeniedException aex) {
			return Constants.ACCESS_DENIED;
		} catch (CadastroDuplicadoException e) {
			addMessage(FacesMessage.SEVERITY_INFO,
					"error.business.cadastro.duplicado",
					ArcheionBean.PERSIST_FAILURE);
			return "formularioEmprestimoPasta";
		} catch (BusinessException e) {
			addMessage(FacesMessage.SEVERITY_INFO, e.getMessageCode(),
					ArcheionBean.PERSIST_FAILURE);
			return "formularioEmprestimoPasta";
		} catch (Exception e) {
			ExceptionManagedBean excBean = (ExceptionManagedBean) Util
			.getManagedBean("exceptionManagedBean");
			excBean.setExc(e);
			return Constants.ERROR_HANDLER;
		}
		return findAll();
	}

	/**
	 * Inclusão de Empréstimo e permanecer na página
	 * @return Retorna para o formulario de inclusão
	 */
	public String incluirMais() {
		try {
			incluirMBean();
		} catch (AccessDeniedException aex) {
			return Constants.ACCESS_DENIED;
		} catch (CadastroDuplicadoException e) {
			addMessage(FacesMessage.SEVERITY_INFO,
					"error.business.cadastro.duplicado",
					ArcheionBean.PERSIST_FAILURE);
			return "formularioEmprestimoPasta";
		} catch (BusinessException e) {
			addMessage(FacesMessage.SEVERITY_INFO, e.getMessageCode(),
					ArcheionBean.PERSIST_FAILURE);
			return "formularioEmprestimoPasta";
		} catch (Exception e) {
			ExceptionManagedBean excBean = (ExceptionManagedBean) Util
					.getManagedBean("exceptionManagedBean");
			excBean.setExc(e);
			return Constants.ERROR_HANDLER;
		}
		return goToForm();
	}

	/**
	 * Inclusão do Emprestimo de Pasta
	 * @throws AccessDeniedException
	 * @throws CadastroDuplicadoException
	 * @throws BusinessException
	 */
	public void incluirMBean() throws AccessDeniedException,
			CadastroDuplicadoException, BusinessException {
		emprestimo.setId(null);

		Pasta pasta = pastaBO.findById(emprestimo.getPasta().getId());
		if (pasta == null || pasta.getId() == null || pasta.getId() <= 0) {
			throw new BusinessException("emprestimo.pasta.erro.pasta");
		}
		emprestimo.setPasta(pasta);

		if (emprestimo.getSolicitante().getId() == -1) {
			emprestimo.setSolicitante(null);
		} else {
			emprestimo.setSolicitanteExterno(null);
			Usuario solicitante = usuarioBO.findById(emprestimo
					.getSolicitante().getId());
			emprestimo.setSolicitante(solicitante);
		}

		emprestimoPastaBO.persist(emprestimo);
		addMessage(FacesMessage.SEVERITY_INFO, "geral.inclusao.sucesso",
				ArcheionBean.PERSIST_SUCESS);
	}
	
	/**
	 * Redireciona para página de Empréstimo por Caixeta
	 * @return Redireciona para página de Empréstimo por Caixeta
	 */
	public String goToEmprestarCaixeta() {
		empresaSelcionada = new Empresa();
		pastaEmprestar = new Pasta();
		emprestimo = new EmprestimoPasta();
		preencherCombos();
		
		//iniciarPreenchimentoCombos();
		
		if (caixetaPraEmprestar != null && !caixetaPraEmprestar.equals("")) {
			listaEmprestarPastaPorCaixeta = pastaBO.findByCaixeta(caixetaPraEmprestar);
						
			if (listaEmprestarPastaPorCaixeta != null && listaEmprestarPastaPorCaixeta.size() > 0) {
				
				mapPastas = new Hashtable<Long, String>();
				for(Pasta p:listaEmprestarPastaPorCaixeta) {
					Long id = p.getId();
					
					StringBuffer text = new StringBuffer();
					text.append("Título: ");
					text.append(p.getTitulo());
					text.append(" Protocolo: ");
					text.append(p.getNumeroProtocolo());
					text.append(" Local: "); 
					text.append(p.getLocal().getNome());
					
					mapPastas.put(text,id);
				}
				
				return "formularioEmprestimoPastaPorCaixeta";
			} else {
				addMessage(FacesMessage.SEVERITY_INFO,
						"geral.emprestimo.por.caixeta.inixistente",
						ArcheionBean.PERSIST_FAILURE);
				return findAll();
			}
		} else {
			addMessage(FacesMessage.SEVERITY_INFO,
					"geral.emprestimo.por.caixeta.vazia",
					ArcheionBean.PERSIST_FAILURE);
			return findAll();
		}
	}

	/**
	 * Redireciona para página de alteração de Empréstimo
	 * @return Redireciona para página de alteração de Empréstimo
	 */
	public String goToAlterar() {
		try {
			Long id = Long.valueOf(Util.getParameter("_id"));
			emprestimo = emprestimoPastaBO.findById(id);

			Local local = new Local();
			local.setId(emprestimo.getPasta().getLocal().getId());			
			empresaSelcionada = emprestimo.getPasta().getLocal().getEmpresa();
			local.setEmpresa(empresaSelcionada);
			pastaFiltro.setLocal(local);
			
			
			preencherCombos();
			//iniciarPreenchimentoCombos();

			if (emprestimo.getSolicitante() == null
					|| emprestimo.getSolicitante().getId() == null) {
				Usuario solicitante = new Usuario();
				solicitante.setId(-1l);
				emprestimo.setSolicitante(solicitante);
			}

			Pasta c = emprestimo.getPasta();
			String auxPasta = c.getTitulo();
			if (c.getDescricao() != null) {
				auxPasta += " - " + c.getDescricao();
			}

			if (c.getNumeroProtocolo() != null) {
				auxPasta += " - " + c.getNumeroProtocolo();
			}
			listaPasta.add(new SelectItem(c.getId(), auxPasta));

		} catch (AccessDeniedException aex) {
			return Constants.ACCESS_DENIED;
		} catch (Exception e) {
			ExceptionManagedBean excBean = (ExceptionManagedBean) Util
					.getManagedBean("exceptionManagedBean");
			excBean.setExc(e);
			return Constants.ERROR_HANDLER;
		}
		return "formularioAlterarEmprestimoPasta";
	}

	/**
	 * Altera o Emprestimo de Pasta
	 * @return Redireciona para página de listagem
	 */
	public String alterar() {
		try {

			Pasta pasta = pastaBO.findById(emprestimo.getPasta().getId());
			if (pasta == null || pasta.getId() == null || pasta.getId() <= 0) {
				throw new BusinessException("emprestimo.pasta.erro.pasta");
			}
			emprestimo.setPasta(pasta);

			if (emprestimo.getSolicitante().getId() == -1) {
				emprestimo.setSolicitante(null);
			} else {
				emprestimo.setSolicitanteExterno(null);
				Usuario solicitante = usuarioBO.findById(emprestimo
						.getSolicitante().getId());
				emprestimo.setSolicitante(solicitante);
			}

			emprestimo = emprestimoPastaBO.merge(emprestimo);

			addMessage(FacesMessage.SEVERITY_INFO, "geral.alteracao.sucesso",
					ArcheionBean.PERSIST_SUCESS);
		} catch (AccessDeniedException aex) {
			return Constants.ACCESS_DENIED;
		} catch (CadastroDuplicadoException e) {
			addMessage(FacesMessage.SEVERITY_INFO,
					"error.business.cadastro.duplicado",
					ArcheionBean.PERSIST_FAILURE);
			return "formularioAlterarEmprestimoPasta";
		} catch (BusinessException e) {
			addMessage(FacesMessage.SEVERITY_INFO, e.getMessageCode(),
					ArcheionBean.PERSIST_FAILURE);
			return "formularioAlterarEmprestimoPasta";
		} catch (Exception e) {
			ExceptionManagedBean excBean = (ExceptionManagedBean) Util
					.getManagedBean("exceptionManagedBean");
			excBean.setExc(e);
			return Constants.ERROR_HANDLER;
		}
		return findAll();
	}

	/**
	 * Vai para página de devolução de Empréstimo
	 * @return Redirecionamento para página de devolução
	 */
	public String goToDevolver() {
		Long id = Long.valueOf(Util.getParameter("_id"));
		emprestimo.setId(id);
		emprestimo = emprestimoPastaBO.findById(id);

		emprestimo.setDataDevolucao(emprestimo.getPrevisaoDevolucao());

		return "formularioDevolverPasta";
	}

	/**
	 * Efetua a devolução de pasta
	 * @return Redireciona para pagina de listagem
	 */
	public String devolver() {
		try {
			emprestimoPastaBO.merge(emprestimo);

			addMessage(FacesMessage.SEVERITY_INFO,
					"emprestimo.caixa.msg.devolucao",
					ArcheionBean.PERSIST_SUCESS);

		} catch (CadastroDuplicadoException e) {
			addMessage(FacesMessage.SEVERITY_INFO,
					"error.business.cadastro.duplicado",
					ArcheionBean.PERSIST_FAILURE);
		} catch (BusinessException e) {
			addMessage(FacesMessage.SEVERITY_INFO, e.getMessageCode(),
					ArcheionBean.PERSIST_FAILURE);
		}

		return findAll();
	}

	/**
	 * Exlcui um Emprestimo de Pasta
	 * @return Retorna para listagem geral
	 */
	public String remover() {
		try {
			Long id = Long.valueOf(Util.getParameter("_id"));
			emprestimo.setId(id);
			emprestimo = emprestimoPastaBO.findById(id);
			emprestimoPastaBO.remove(emprestimo);

			addMessage(FacesMessage.SEVERITY_INFO, "geral.remocao.sucesso",
					ArcheionBean.PERSIST_SUCESS);
		} catch (AccessDeniedException aex) {
			return Constants.ACCESS_DENIED;
		} catch (BusinessException e) {
			addMessage(FacesMessage.SEVERITY_INFO, e.getMessageCode(),
					ArcheionBean.PERSIST_FAILURE);
		} catch (Exception e) {
			ExceptionManagedBean excBean = (ExceptionManagedBean) Util
					.getManagedBean("exceptionManagedBean");
			excBean.setExc(e);
			return Constants.ERROR_HANDLER;
		}
		return findAll();
	}

	/**
	 * Busca todos os Empréstimo de pasta
	 * @return Redireciona para pagina de listagem 
	 */
	public String findAll() {
		try {

			emprestimo = new EmprestimoPasta();
			listaEmpresa = new ArrayList<SelectItem>();
			listaLocal = new ArrayList<SelectItem>();

			List<Empresa> empresas = empresaBO.findAll();
			for (Empresa emp : empresas) {
				listaEmpresa.add(new SelectItem(emp.getId(), emp.getNome()));
			}

			if ((pastaFiltro.getLocal().getEmpresa() == null
					|| pastaFiltro.getLocal().getEmpresa().getId() == null || pastaFiltro
					.getLocal().getEmpresa().getId() == 0)
					&& empresas.size() > 0) {
				pastaFiltro.getLocal().setEmpresa(empresas.get(0));
			}

			if (pastaFiltro.getLocal().getEmpresa() != null
					&& pastaFiltro.getLocal().getEmpresa().getId() != null
					&& pastaFiltro.getLocal().getEmpresa().getId() != 0) {
				List<Local> locais = localBO.findByEmpresa(pastaFiltro
						.getLocal().getEmpresa());
				for (Local loc : locais) {
					listaLocal.add(new SelectItem(loc.getId(), loc.getNome()));
				}

				if (locais != null
						&& locais.size() > 0
						&& (pastaFiltro.getLocal() == null
								|| pastaFiltro.getLocal().getId() == null || pastaFiltro
								.getLocal().getId() == 0)) {
					pastaFiltro.setLocal(locais.get(0));
				}
			}

			List<Usuario> solicitantes = usuarioBO.findAll();
			listaSolicitante = new ArrayList<SelectItem>();
			for (Usuario u : solicitantes) {
				listaSolicitante.add(new SelectItem(u.getId(), u.getNome()));
			}

			usuarioFiltro = new Usuario();
			dataFiltro = null;
			emprestada = true;
			situacaoFiltro = "1";
			pastaFiltro = new Pasta();

			listaEmprestimoPasta = emprestimoPastaBO.findEmprestadas(
					pastaFiltro.getLocal().getEmpresa(),
					pastaFiltro.getLocal(), null, null);
		} catch (AccessDeniedException aex) {
			return Constants.ACCESS_DENIED;
		}
		return "listaEmprestimoPasta";
	}

	/**
	 * Efetua a pesquisa de emprestimo de acordo com o filtros selecionados
	 * @return Redireciona para pagina de listagem
	 */
	public String pesquisar() {
		try {

			List<Pasta> pastas = pastaBO.findAll();
			listaPasta = new ArrayList<SelectItem>();
			for (Pasta c : pastas) {
				listaPasta.add(new SelectItem(c.getId(), c.getTitulo()));
			}

			List<Usuario> solicitantes = usuarioBO.findAll();
			listaSolicitante = new ArrayList<SelectItem>();
			for (Usuario u : solicitantes) {
				listaSolicitante.add(new SelectItem(u.getId(), u.getNome()));
			}

			if (situacaoFiltro.equals("1")) {
				listaEmprestimoPasta = emprestimoPastaBO.findEmprestadas(
						pastaFiltro.getLocal().getEmpresa(), pastaFiltro
								.getLocal(), usuarioFiltro, dataFiltro);
				emprestada = true;
			} else {
				listaEmprestimoPasta = emprestimoPastaBO.findDevolvidas(
						pastaFiltro.getLocal().getEmpresa(), pastaFiltro
								.getLocal(), usuarioFiltro, dataFiltro);
				emprestada = false;
			}
		} catch (AccessDeniedException aex) {
			return Constants.ACCESS_DENIED;
		}
		return "listaEmprestimoPasta";
	}

	/**
	 * Redireciona para formulário de inclusão
	 * @return Redireciona para formulário de inclusão
	 */
	public String goToForm() {

		emprestimo = new EmprestimoPasta();
		preencherCombos();
		//iniciarPreenchimentoCombos();

		return "formularioEmprestimoPasta";
	}

	/**
	 * Imprime protocolo de emprestimo
	 * @return Redireciona para protocolo de emprestimo
	 */
	public String imprimirProtocoloEmprestimo() {
		FacesContext context = getContext();
		try {

			Long id = Long.valueOf(Util.getParameter("_id"));
			emprestimo = emprestimoPastaBO.findById(id);

			HttpServletResponse response = (HttpServletResponse) context
					.getExternalContext().getResponse();

			ServletOutputStream responseStream;
			responseStream = response.getOutputStream();
			String pathJasper = ((ServletContext) context.getExternalContext()
					.getContext()).getRealPath("/WEB-INF/relatorios/")
					+ "/ProtocoloEmprestimoPasta.jasper";
			HashMap<String, Object> param = new HashMap<String, Object>();

			param.put("idEmprestimoPasta", emprestimo.getId());
			param.put("user", authenticationController.getUsuario().getNome());
			Relatorio relatorio = pastaBO.getRelatorio(param, pathJasper);
			relatorio.exportarParaPdfStream(responseStream);

			response.setContentType("application/pdf");
			response.setHeader("Content-disposition",
					"filename=\"ProtocoloEmprestimoPasta.pdf\"");
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
		return findAll();
	}

	/**
	 * Imprime protocolo de devolução
	 * @return Redireciona para protocolo de devolução
	 */
	public String imprimirProtocoloDevolucao() {
		FacesContext context = getContext();
		try {

			Long id = Long.valueOf(Util.getParameter("_id"));
			emprestimo = emprestimoPastaBO.findById(id);

			HttpServletResponse response = (HttpServletResponse) context
					.getExternalContext().getResponse();

			ServletOutputStream responseStream;
			responseStream = response.getOutputStream();
			String pathJasper = ((ServletContext) context.getExternalContext()
					.getContext()).getRealPath("/WEB-INF/relatorios/")
					+ "/ProtocoloDevolucaoPasta.jasper";
			HashMap<String, Object> param = new HashMap<String, Object>();

			param.put("idEmprestimoPasta", emprestimo.getId());
			param.put("user", authenticationController.getUsuario().getNome());
			Relatorio relatorio = pastaBO.getRelatorio(param, pathJasper);
			relatorio.exportarParaPdfStream(responseStream);

			response.setContentType("application/pdf");
			response.setHeader("Content-disposition",
					"filename=\"ProtocoloEmprestimoPasta.pdf\"");
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
		return findAll();
	}

	
	//-- Gets e Sets
	public EmprestimoPasta getEmprestimo() {
		return emprestimo;
	}

	public void setEmprestimo(EmprestimoPasta emprestimo) {
		this.emprestimo = emprestimo;
	}

	public List<SelectItem> getListaCaixa() {
		return listaPasta;
	}

	public void setListaCaixa(List<SelectItem> listaCaixa) {
		this.listaPasta = listaCaixa;
	}

	public EmprestimoPastaBO getEmprestimoPastaBO() {
		return emprestimoPastaBO;
	}

	public void setEmprestimoPastaBO(EmprestimoPastaBO emprestimoCaixaBO) {
		this.emprestimoPastaBO = emprestimoCaixaBO;
	}

	public List<SelectItem> getListaResponsavel() {
		return listaResponsavel;
	}

	public void setListaResponsavel(List<SelectItem> listaResponsavel) {
		this.listaResponsavel = listaResponsavel;
	}

	public List<SelectItem> getListaSolicitante() {
		return listaSolicitante;
	}

	public void setListaSolicitante(List<SelectItem> listaSolicitante) {
		this.listaSolicitante = listaSolicitante;
	}

	public List<EmprestimoPasta> getListaEmprestimoPasta() {
		return listaEmprestimoPasta;
	}

	public void setListaEmprestimoPasta(
			List<EmprestimoPasta> listaEmprestimoPasta) {
		this.listaEmprestimoPasta = listaEmprestimoPasta;
	}

	public Date getDataFiltro() {
		return dataFiltro;
	}

	public void setDataFiltro(Date dataFiltro) {
		this.dataFiltro = dataFiltro;
	}

	public Usuario getUsuarioFiltro() {
		return usuarioFiltro;
	}

	public void setUsuarioFiltro(Usuario usuarioFiltro) {
		this.usuarioFiltro = usuarioFiltro;
	}

	public String getSituacaoFiltro() {
		return situacaoFiltro;
	}

	public void setSituacaoFiltro(String situacaoFiltro) {
		this.situacaoFiltro = situacaoFiltro;
	}

	public boolean isEmprestada() {
		return emprestada;
	}

	public void setEmprestada(boolean emprestada) {
		this.emprestada = emprestada;
	}

	public List<SelectItem> getListaPasta() {
		return listaPasta;
	}

	public void setListaPasta(List<SelectItem> listaPasta) {
		this.listaPasta = listaPasta;
	}

	public void setPastaBO(PastaBO pastaBO) {
		this.pastaBO = pastaBO;
	}

	public void setUsuarioBO(UsuarioBO usuarioBO) {
		this.usuarioBO = usuarioBO;
	}

	public void setEmprestimoCaixaBO(EmprestimoPastaBO emprestimoCaixaBO) {
		this.emprestimoPastaBO = emprestimoCaixaBO;
	}

	public Pasta getPastaFiltro() {
		return pastaFiltro;
	}

	public void setPastaFiltro(Pasta pastaFiltro) {
		this.pastaFiltro = pastaFiltro;
	}

	public List<SelectItem> getListaEmpresa() {
		return listaEmpresa;
	}

	public void setListaEmpresa(List<SelectItem> listaEmpresa) {
		this.listaEmpresa = listaEmpresa;
	}

	public List<SelectItem> getListaLocal() {
		return listaLocal;
	}

	public void setListaLocal(List<SelectItem> listaLocal) {
		this.listaLocal = listaLocal;
	}

	public void setEmpresaBO(EmpresaBO empresaBO) {
		this.empresaBO = empresaBO;
	}

	public void setLocalBO(LocalBO localBO) {
		this.localBO = localBO;
	}

	public String getCaixetaPraEmprestar() {
		return caixetaPraEmprestar;
	}

	public void setCaixetaPraEmprestar(String caixetaPraEmprestar) {
		this.caixetaPraEmprestar = caixetaPraEmprestar;
	}

	public List<Pasta> getListaEmprestarPastaPorCaixeta() {
		return listaEmprestarPastaPorCaixeta;
	}

	public void setListaEmprestarPastaPorCaixeta(
			List<Pasta> listaEmprestarPastaPorCaixeta) {
		this.listaEmprestarPastaPorCaixeta = listaEmprestarPastaPorCaixeta;
	}

	public Map getMapPastas() {
		return mapPastas;
	}

	public void setMapPastas(Map mapPastas) {
		this.mapPastas = mapPastas;
	}

	public Pasta getPastaEmprestar() {
		return pastaEmprestar;
	}

	public void setPastaEmprestar(Pasta pastaEmprestar) {
		this.pastaEmprestar = pastaEmprestar;
	}

}
