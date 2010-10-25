package br.com.archeion.mbean.local;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
import br.com.archeion.mbean.ExceptionManagedBean;
import br.com.archeion.modelo.empresa.Empresa;
import br.com.archeion.modelo.local.Local;
import br.com.archeion.negocio.empresa.EmpresaBO;
import br.com.archeion.negocio.local.LocalBO;
import br.com.archeion.negocio.relatoriotxt.RelatorioTxtBO;

public class LocalMBean extends ArcheionBean {

	/**
	 * representa o local
	 */
	private Local local;
	/**
	 * Lista de empresas
	 */
	private List<SelectItem> listaEmpresa;
	/**
	 * Lista de locais
	 */
	private List<Local> listaLocal;
	/**
	 * BO de locais
	 */
	private LocalBO localBO = (LocalBO) Util.getSpringBean("localBO");
	/**
	 * BO de empresa
	 */
	private EmpresaBO empresaBO = (EmpresaBO) Util.getSpringBean("empresaBO");
	/**
	 * BO de relatorio txt
	 */
	private RelatorioTxtBO relatorioTxtBO = (RelatorioTxtBO) Util.getSpringBean("relatorioTxtBO");
	/**
	 * construtor
	 */
	public LocalMBean() {
		local = new Local();
		listaEmpresa = new ArrayList<SelectItem>();
	}
	/**
	 * Incluir um local
	 * @return pagina com a lista de locais
	 */
	public String incluir() {
		try {
			incluirMBean();
		} catch (AccessDeniedException aex) {
			return Constants.ACCESS_DENIED;
		} catch (CadastroDuplicadoException e) {
			addMessage(FacesMessage.SEVERITY_INFO, "error.business.cadastro.duplicado",ArcheionBean.PERSIST_FAILURE);
			return goToForm();
		} catch (Exception e) {
			ExceptionManagedBean excBean = (ExceptionManagedBean) Util.getManagedBean("exceptionManagedBean");
			excBean.setExc(e);
			return Constants.ERROR_HANDLER;
		}			
		return findAll();
	}

	/**
	 * Incluir varios locais
	 * @return continua na pagina de inseção
	 */
	public String incluirMais() {
		try {
			incluirMBean();
		} catch (AccessDeniedException aex) {
			return Constants.ACCESS_DENIED;
		} catch (CadastroDuplicadoException e) {
			addMessage(FacesMessage.SEVERITY_INFO, "error.business.cadastro.duplicado",ArcheionBean.PERSIST_FAILURE);
			return goToForm();
		} catch (Exception e) {
			ExceptionManagedBean excBean = (ExceptionManagedBean) Util.getManagedBean("exceptionManagedBean");
			excBean.setExc(e);
			return Constants.ERROR_HANDLER;
		}			
		return goToForm();
	}

	/**
	 * Incluir o local
	 * @throws AccessDeniedException
	 * @throws CadastroDuplicadoException
	 */
	public void incluirMBean() throws AccessDeniedException, CadastroDuplicadoException {
		local.setId(null);
		Empresa empresa = empresaBO.findById(local.getEmpresa().getId());
		local.setEmpresa(empresa);
		localBO.persist(local);

		addMessage(FacesMessage.SEVERITY_INFO,"geral.inclusao.sucesso",ArcheionBean.PERSIST_SUCESS);
	}

	/**
	 * Inicializa e vai pra pagina de alterar
	 * @return
	 */
	public String goToAlterar() {
		try {
			Long id = Long.valueOf(Util.getParameter("_id"));			
			local = localBO.findById(id);

			listaEmpresa = new ArrayList<SelectItem>();
			List<Empresa> listaEmpresas = empresaBO.findAll();
			for(Empresa e:listaEmpresas) {
				SelectItem select = new SelectItem(e.getId(),e.getNome());
				listaEmpresa.add(select);
			}
		} catch (AccessDeniedException aex) {
			return Constants.ACCESS_DENIED;
		} catch (Exception e) {
			ExceptionManagedBean excBean = (ExceptionManagedBean) Util.getManagedBean("exceptionManagedBean");
			excBean.setExc(e);
			return Constants.ERROR_HANDLER;
		}
		return "formularioAlterarLocal";
	}	

	/**
	 * Altera um local
	 * @return
	 */
	public String alterar() {
		try {			
			local = localBO.merge(local);

			addMessage(FacesMessage.SEVERITY_INFO,"geral.alteracao.sucesso",ArcheionBean.PERSIST_SUCESS);
		} catch (AccessDeniedException aex) {
			return Constants.ACCESS_DENIED;
		} catch (CadastroDuplicadoException e) {
			addMessage(FacesMessage.SEVERITY_INFO, "error.business.cadastro.duplicado",ArcheionBean.PERSIST_FAILURE);
			return "formularioAlterarLocal";
		} catch (Exception e) {
			ExceptionManagedBean excBean = (ExceptionManagedBean) Util.getManagedBean("exceptionManagedBean");
			excBean.setExc(e);
			return Constants.ERROR_HANDLER;
		}
		return findAll();
	}	
	/**
	 * Remover um logal
	 * @return para a pagina com a lista de locais
	 */
	public String remover() {
		try {
			Long id = Long.valueOf(Util.getParameter("_id"));
			local.setId( id );
			local = localBO.findById(id);
			localBO.remove(local);

			addMessage(FacesMessage.SEVERITY_INFO,"geral.remocao.sucesso",ArcheionBean.PERSIST_SUCESS);
		} catch (AccessDeniedException aex) {
			return Constants.ACCESS_DENIED;
		} catch (BusinessException e) {
			addMessage(FacesMessage.SEVERITY_INFO, 
					e.getMessageCode(),ArcheionBean.PERSIST_FAILURE);
		} catch (Exception e) {
			ExceptionManagedBean excBean = (ExceptionManagedBean) Util.getManagedBean("exceptionManagedBean");
			excBean.setExc(e);
			return Constants.ERROR_HANDLER;
		}
		return findAll();
	}	

	/**
	 * Busca todos os locais
	 * @return
	 */
	public String findAll() {
		try {
			listaLocal = localBO.findAll();
		}
		catch (AccessDeniedException aex) {
			return Constants.ACCESS_DENIED;
		}
		return "listaLocal";
	}

	/**
	 * Inicializa e vai para a lista de locais
	 * @return
	 */
	public String goToForm() {
		try { 
			local = new Local();

			List<Empresa> listaEmpresas = empresaBO.findAll();		
			listaEmpresa = new ArrayList<SelectItem>();
			for(Empresa e:listaEmpresas) {
				SelectItem select = new SelectItem(e.getId(),e.getNome());
				listaEmpresa.add(select);
			}
		}
		catch (AccessDeniedException aex) {
			return Constants.ACCESS_DENIED;
		}
		return "formularioLocal";
	}

	/**
	 * Imprimir uma relação de locais
	 * @return
	 */
	public String imprimir() {
		FacesContext context = getContext();
		try {
			HttpServletResponse response = (HttpServletResponse) context
			.getExternalContext().getResponse();

			ServletOutputStream responseStream;
			responseStream = response.getOutputStream();
			String pathJasper = ((ServletContext)context.getExternalContext().getContext()).getRealPath("/WEB-INF/relatorios/")+ "/ArcheionLocais.jasper";
			Relatorio relatorio = empresaBO.getRelatorio(new HashMap<String, Object>(), pathJasper);
			relatorio.exportarParaPdfStream(responseStream);

			response.setContentType("application/pdf");
			response.setHeader("Content-disposition",
			"filename=\"RelacaoEmpresa.pdf\"");
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
	 * Imprimir txt de local
	 * @return
	 */
	public String imprimirTxt() {
		FacesContext context = getContext();
		try {
			HttpServletResponse response = (HttpServletResponse) context
			.getExternalContext().getResponse();
			
			ServletOutputStream responseStream;
			responseStream = response.getOutputStream();
			StringBuilder sb = new StringBuilder("select b.nm_empresa as empresa, a.nm_local as local, a.nu_ultimo_documento as numero_ultimo_documento ");
			sb.append("from tb_local a join tb_empresa b on (a.id_empresa = b.id_empresa) ");
			sb.append("order by 1 ");
						
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

	public Local getEmpresa() {
		return local;
	}

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

	public List<SelectItem> getListaEmpresa() {
		return listaEmpresa;
	}

	public void setListaEmpresa(List<SelectItem> listaEmpresa) {
		this.listaEmpresa = listaEmpresa;
	}

	public List<Local> getListaLocal() {
		return listaLocal;
	}

	public void setListaLocal(List<Local> listaLocal) {
		this.listaLocal = listaLocal;
	}

	public void setLocalBO(LocalBO localBO) {
		this.localBO = localBO;
	}

	public void setEmpresaBO(EmpresaBO empresaBO) {
		this.empresaBO = empresaBO;
	}

}
