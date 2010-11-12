package br.com.archeion.mbean.empresa;

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
import org.richfaces.model.TreeNode;
import org.richfaces.model.TreeNodeImpl;

import util.Relatorio;
import br.com.archeion.exception.BusinessException;
import br.com.archeion.exception.CadastroDuplicadoException;
import br.com.archeion.jsf.Constants;
import br.com.archeion.jsf.Util;
import br.com.archeion.mbean.ArcheionBean;
import br.com.archeion.mbean.ExceptionManagedBean;
import br.com.archeion.modelo.empresa.Empresa;
import br.com.archeion.negocio.empresa.EmpresaBO;
import br.com.archeion.negocio.relatoriotxt.RelatorioTxtBO;

public class EmpresaMBean extends ArcheionBean {

	/**
	 * Arvore de empresas
	 */
	private TreeNode rootNode = null;
	/**
	 * Representa a empresa
	 */
	private Empresa empresa;
	/**
	 * Lista de empresas
	 */
	private List<SelectItem> listaEmpresaPai;

	/**
	 * BO de empresa
	 */
	private EmpresaBO empresaBO = (EmpresaBO) Util.getSpringBean("empresaBO");
	/**
	 * Relatorios txt
	 */
	private RelatorioTxtBO relatorioTxtBO = (RelatorioTxtBO) Util.getSpringBean("relatorioTxtBO");

	public EmpresaMBean() {
		empresa = new Empresa();
		listaEmpresaPai = new ArrayList<SelectItem>();
	}

	/**
	 * Incluir empresa
	 * @return
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
			ExceptionManagedBean excBean = (ExceptionManagedBean) Util
			.getManagedBean("exceptionManagedBean");
			excBean.setExc(e);
			return Constants.ERROR_HANDLER;
		}
		return findAll();
	}

	/**
	 * Incluir mais de uma empresa
	 * @return
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
			ExceptionManagedBean excBean = (ExceptionManagedBean) Util
			.getManagedBean("exceptionManagedBean");
			excBean.setExc(e);
			return Constants.ERROR_HANDLER;
		}
		return this.goToForm();
	}

	/**
	 * Incluir empresa
	 * @throws AccessDeniedException
	 * @throws CadastroDuplicadoException
	 */
	public void incluirMBean() throws AccessDeniedException, CadastroDuplicadoException {

		empresa.setId(null);
		if ( empresa.getIdPai()!=null && empresa.getIdPai()==0 ) empresa.setIdPai(null);
		empresaBO.persist(empresa);

		addMessage(FacesMessage.SEVERITY_INFO, "geral.inclusao.sucesso",ArcheionBean.PERSIST_SUCESS);

	}

	/**
	 * Inicializa o alterar
	 * @return
	 */
	public String goToAlterar() {
		try {
			long id = Long.valueOf(Util.getParameter("_id"));			
			empresa = empresaBO.findById(id);
			
			List<Empresa> listaEmpresas = empresaBO.findAllInvisivel();		
			listaEmpresaPai = new ArrayList<SelectItem>();
			for(Empresa e:listaEmpresas) {
				SelectItem select = new SelectItem(e.getId(),e.getNome());
				listaEmpresaPai.add(select);
			}
			
		} catch (AccessDeniedException aex) {
			return Constants.ACCESS_DENIED;
		} catch (Exception e) {
			ExceptionManagedBean excBean = (ExceptionManagedBean) Util
			.getManagedBean("exceptionManagedBean");
			excBean.setExc(e);
			return Constants.ERROR_HANDLER;
		}
		return "formularioAlterarEmpresa";
	}

	/**
	 * Alterar empresa
	 * @return
	 */
	public String alterar() {
		try {
			
			if ( empresa.getIdPai()==0 ) empresa.setIdPai(null);
			empresa = empresaBO.merge(empresa);

			addMessage(FacesMessage.SEVERITY_INFO, "geral.alteracao.sucesso",
					ArcheionBean.PERSIST_SUCESS);
		} catch (AccessDeniedException aex) {
			return Constants.ACCESS_DENIED;
		} catch (CadastroDuplicadoException e) {
			addMessage(FacesMessage.SEVERITY_INFO, "error.business.cadastro.duplicado",ArcheionBean.PERSIST_FAILURE);
			return "formularioAlterarEmpresa";
		} catch (Exception e) {
			ExceptionManagedBean excBean = (ExceptionManagedBean) Util
			.getManagedBean("exceptionManagedBean");
			excBean.setExc(e);
			return Constants.ERROR_HANDLER;
		}
		return findAll();
	}

	/**
	 * Remover empresa
	 * @return
	 */
	public String remover() {
		try {
			Long id = empresa.getId();
			empresa = empresaBO.findById(id);
			empresaBO.remove(empresa);

			addMessage(FacesMessage.SEVERITY_INFO, "geral.remocao.sucesso",
					ArcheionBean.PERSIST_SUCESS);
		} catch (AccessDeniedException aex) {
			return Constants.ACCESS_DENIED;
		} catch (BusinessException e) {
			addMessage(FacesMessage.SEVERITY_INFO, 
					e.getMessageCode(),ArcheionBean.PERSIST_FAILURE);
		} catch (Exception e) {
			ExceptionManagedBean excBean = (ExceptionManagedBean) Util
			.getManagedBean("exceptionManagedBean");
			excBean.setExc(e);
			return Constants.ERROR_HANDLER;
		}
		return findAll();
	}

	/**
	 * Buscar todas as empresas
	 * @return
	 */
	public String findAll() {
		try {
			loadTree(empresaBO.findRoots());
		}
		catch (AccessDeniedException aex) {
			return Constants.ACCESS_DENIED;
		} 
		return "listaEmpresa";
	}

	/**
	 * Inicializa e mostra formulário de empresas
	 * @return
	 */
	public String goToForm() {
		try {
			empresa = new Empresa();
			empresa.setIdPai(null);

			List<Empresa> listaEmpresas = empresaBO.findAllInvisivel();		
			listaEmpresaPai = new ArrayList<SelectItem>();
			for(Empresa e:listaEmpresas) {
				SelectItem select = new SelectItem(e.getId(),e.getNome());
				listaEmpresaPai.add(select);
			}
		}
		catch (AccessDeniedException aex) {
			return Constants.ACCESS_DENIED;
		} 
		return "formularioEmpresa";
	}
	
	/**
	 * Imprimir relação de empresas
	 * @return
	 */
	public String imprimir() {
		FacesContext context = getContext();
		try {
			HttpServletResponse response = (HttpServletResponse) context
			.getExternalContext().getResponse();

			ServletOutputStream responseStream;
			responseStream = response.getOutputStream();
			String pathJasper = ((ServletContext) context.getExternalContext()
					.getContext()).getRealPath("/WEB-INF/relatorios/")
					+ "/ArcheionEmpresas.jasper";
			Relatorio relatorio = empresaBO.getRelatorio(
					new HashMap<String, Object>(), pathJasper);
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
	 * Imprimir txt de empresas
	 * @return
	 */
	public String imprimirTxt() {
		FacesContext context = getContext();
		try {
			HttpServletResponse response = (HttpServletResponse) context
			.getExternalContext().getResponse();
			
			ServletOutputStream responseStream;
			responseStream = response.getOutputStream();
			StringBuilder sb = new StringBuilder("select a.nm_empresa as empresa ");
			sb.append("from tb_empresa a ");
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

	/**
	 * Carrega arvore de empresa
	 * @param lista
	 */
	private void loadTree(List<Empresa> lista) {
		if ( lista!=null && lista.size()>0 ) {
			rootNode = new TreeNodeImpl();
			rootNode.setData("Empresas");
			
			addNodes(rootNode,lista);
		}
		else {
			rootNode = null;
		}
	}

	/**
	 * Cria nós da arvore de empresas
	 * @param node
	 * @param lista
	 */
	private void addNodes(TreeNode node, List<Empresa> lista) {
		int counter = 0;

		for(Empresa e:lista ) {

			String value = e.getNome();
			if (value != null) {
				TreeNodeImpl nodeImpl = new TreeNodeImpl();
				nodeImpl.setData(e);
				
				counter++;
				node.addChild(new Integer(counter), nodeImpl);

				if ( e.getFilhos()!=null && e.getFilhos().size()>0 ) {
					addNodes(nodeImpl, e.getFilhos());
				}

			}
		}
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public void setEmpresaBO(EmpresaBO empresaBO) {
		this.empresaBO = empresaBO;
	}

	public TreeNode getRootNode() {
		return rootNode;
	}

	public void setRootNode(TreeNode rootNode) {
		this.rootNode = rootNode;
	}

	public List<SelectItem> getListaEmpresaPai() {
		return listaEmpresaPai;
	}

	public void setListaEmpresaPai(List<SelectItem> listaEmpresaPai) {
		this.listaEmpresaPai = listaEmpresaPai;
	}


}
