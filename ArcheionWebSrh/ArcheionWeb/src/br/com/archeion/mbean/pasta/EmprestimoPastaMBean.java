package br.com.archeion.mbean.pasta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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

public class EmprestimoPastaMBean extends ArcheionBean {

	private EmprestimoPasta emprestimo;
	private List<SelectItem> listaPasta;
	private List<SelectItem> listaResponsavel;
	private List<SelectItem> listaSolicitante;
	private List<SelectItem> listaEmpresa;
	private List<SelectItem> listaLocal;

	private List<EmprestimoPasta> listaEmprestimoPasta;

	private List<Pasta> listaEmprestarPastaPorCaixeta;

	private Date dataFiltro;
	private Usuario usuarioFiltro;
	private String situacaoFiltro;
	private Pasta pastaFiltro;	
	private boolean emprestada = true;
	private String caixetaPraEmprestar;
	private int index; //utilizado pelo radiobutton
	private Empresa empresaSelcionada;

	private PastaBO pastaBO = (PastaBO) Util.getSpringBean("pastaBO");
	private UsuarioBO usuarioBO = (UsuarioBO) Util.getSpringBean("usuarioBO");
	private EmpresaBO empresaBO = (EmpresaBO) Util.getSpringBean("empresaBO");
	private LocalBO localBO = (LocalBO) Util.getSpringBean("localBO");
	private EmprestimoPastaBO emprestimoPastaBO = (EmprestimoPastaBO) Util
			.getSpringBean("emprestimoPastaBO");
	private RelatorioTxtBO relatorioTxtBO = (RelatorioTxtBO) Util
			.getSpringBean("relatorioTxtBO");
	private AuthenticationController authenticationController = (AuthenticationController) Util
			.getManagedBean("authenticationController");

	public EmprestimoPastaMBean() {
		emprestimo = new EmprestimoPasta();
		pastaFiltro = new Pasta();
		
	}

	public void marcar() {
		// primeiro desmarca todos os radiobuttons
		for (Pasta temp : listaEmprestarPastaPorCaixeta)
			temp.setSelecionado(0);

		// marcar o radio certo
		listaEmprestarPastaPorCaixeta.get(index).setSelecionado(1);
	}

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

	public void valueChangedLista(ValueChangeEvent event) {

		Long empId = (Long) event.getNewValue();
		empresaSelcionada = new Empresa();
		empresaSelcionada.setId(empId);

		pastaFiltro.getLocal().setId(null);
		pastaFiltro.getLocal().setEmpresa(empresaSelcionada);

		preencherCombos();
		//preencherComboLocal();
	}

	public void valueChangedLocal(ValueChangeEvent event) {
		Long localId = (Long) event.getNewValue();
		Local local = new Local();
		local.setId(localId);
		local.setEmpresa(empresaSelcionada);

		pastaFiltro.setLocal(local);		

		preencherCombos();
		//preencherComboPasta();
	}
	
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

	/*private void iniciarPreenchimentoCombos() {
		listaPasta = new ArrayList<SelectItem>();
		listaResponsavel = new ArrayList<SelectItem>();
		listaSolicitante = new ArrayList<SelectItem>();
		listaEmpresa = new ArrayList<SelectItem>();
		listaLocal = new ArrayList<SelectItem>();

		List<Empresa> empresas = empresaBO.findAll();
		listaEmpresa.add(new SelectItem(0, "----------------"));
		for (Empresa emp : empresas) {
			listaEmpresa.add(new SelectItem(emp.getId(), emp.getNome()));
		}

		listaLocal.add(new SelectItem(0, "----------------"));
		listaPasta.add(new SelectItem(0, "----------------"));

		List<Usuario> responsaveis = usuarioBO.findAllAluga();
		for (Usuario u : responsaveis) {
			listaResponsavel.add(new SelectItem(u.getId(), u.getNome()));
		}

		List<Usuario> solicitantes = usuarioBO.findAll();
		for (Usuario u : solicitantes) {
			listaSolicitante.add(new SelectItem(u.getId(), u.getNome()));
		}
	}*/

	/*private void preencherComboLocal() {
		listaLocal.clear();
		listaLocal.add(new SelectItem(0, "----------------"));
		
		 * if (pastaFiltro.getLocal().getEmpresa() != null &&
		 * pastaFiltro.getLocal().getEmpresa().getId() != null &&
		 * pastaFiltro.getLocal().getEmpresa().getId() != 0) {
		 
		if (empresaSelecionada > 0) {
			List<Local> locais = localBO.findByEmpresa(pastaFiltro.getLocal()
					.getEmpresa());

			for (Local loc : locais) {
				listaLocal.add(new SelectItem(loc.getId(), loc.getNome()));
			}

		}
	}*/

	/*private void preencherComboPasta() {
		listaPasta.clear();
		List<Pasta> pastas = null;
		
		 * if (pastaFiltro.getLocal().getId() != null &&
		 * pastaFiltro.getLocal().getId().longValue() != 0) {
		 
		if (empresaSelecionada > 0 && localSelecionado > 0) {
			pastas = pastaBO.findByEmpresaLocalEmprestimo(empresaSelecionada,
					localSelecionado);
		} else {
			pastas = new ArrayList<Pasta>();
		}
		String auxPasta = "";
		listaPasta.add(new SelectItem(0, "----------------"));
		for (Pasta p : pastas) {
			auxPasta = p.getTitulo();
			if (p.getDescricao() != null) {
				auxPasta += " - " + p.getDescricao();
			}

			if (p.getNumeroProtocolo() != null) {
				auxPasta += " - " + p.getNumeroProtocolo();
			}
			listaPasta.add(new SelectItem(p.getId(), auxPasta));
		}
	}*/

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
	
	public String incluirPorCaixeta() {
		boolean achou = false;
		try {
			for(Pasta p: listaEmprestarPastaPorCaixeta){
				if(p.getSelecionado() == 1){
					achou = true;
					emprestimo.setPasta(p);
					break;
				}
			}
			if(!achou){
				addMessage(FacesMessage.SEVERITY_INFO,
						"error.business.nenhum.radio.selecionado",
						ArcheionBean.PERSIST_FAILURE);
				return "formularioEmprestimoPastaPorCaixeta";
			}
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

	public String goToEmprestarCaixeta() {
		empresaSelcionada = new Empresa();
		preencherCombos();
		//iniciarPreenchimentoCombos();
		if (caixetaPraEmprestar != null && !caixetaPraEmprestar.equals("")) {
			listaEmprestarPastaPorCaixeta = pastaBO
					.findByCaixeta(caixetaPraEmprestar);
			if (listaEmprestarPastaPorCaixeta != null
					&& listaEmprestarPastaPorCaixeta.size() > 0) {
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

	public String goToDevolver() {
		Long id = Long.valueOf(Util.getParameter("_id"));
		emprestimo.setId(id);
		emprestimo = emprestimoPastaBO.findById(id);

		emprestimo.setDataDevolucao(emprestimo.getPrevisaoDevolucao());

		return "formularioDevolverPasta";
	}

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

	public String findAll() {
		try {

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

	public String goToForm() {

		emprestimo = new EmprestimoPasta();
		preencherCombos();
		//iniciarPreenchimentoCombos();

		return "formularioEmprestimoPasta";
	}

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

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
