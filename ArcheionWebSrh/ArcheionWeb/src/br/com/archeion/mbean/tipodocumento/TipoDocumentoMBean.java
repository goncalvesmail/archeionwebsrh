package br.com.archeion.mbean.tipodocumento;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
import br.com.archeion.modelo.tipodocumento.TipoDocumento;
import br.com.archeion.negocio.tipodocumento.TipoDocumentoBO;

/**
 * ManagedBean para tratar eventos de Tipo de Documento
 *  
 * @author SInforme
 *
 */
public class TipoDocumentoMBean extends ArcheionBean {

	/**
	 * Tipo de documento
	 */
	private TipoDocumento tipoDocumento;
	/**
	 * Lista de Tipos de Documentos
	 */
	private List<TipoDocumento> listaTipoDocumento;
	/**
	 * BO de Tipo de Documento
	 */
	private TipoDocumentoBO tipoDocumentoBO = (TipoDocumentoBO) Util.getSpringBean("tipoDocumentoBO");
	
	/**
	 * Construtor
	 */
	public TipoDocumentoMBean() {
		tipoDocumento = new TipoDocumento();
	}
	
	/**
	 * Incluir um novo Tipo de Documento
	 * @return Retorna para listagem geral
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
	 * Inclui um novo Tipo de Documento e se mantém na página
	 * @return Redireciona para página de inclusão
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
	 * Efetua o cadastro do novo Tipo de Documento
	 * @throws AccessDeniedException
	 * @throws CadastroDuplicadoException
	 */
	public void incluirMBean() throws AccessDeniedException, CadastroDuplicadoException {
		tipoDocumento.setId(null);
		tipoDocumentoBO.persist(tipoDocumento);
		
		addMessage(FacesMessage.SEVERITY_INFO,"geral.inclusao.sucesso",ArcheionBean.PERSIST_SUCESS);
	}

	/**
	 * Vai para página de alteração de Tipo de Documento
	 * @return Redireciona para página de alteração de Tipo de Documento
	 */
	public String goToAlterar() {
		try {
			Long id = Long.valueOf(Util.getParameter("_id"));			
			tipoDocumento = tipoDocumentoBO.findById(id);
		} catch (AccessDeniedException aex) {
			return Constants.ACCESS_DENIED;
		} catch (Exception e) {
			ExceptionManagedBean excBean = (ExceptionManagedBean) Util.getManagedBean("exceptionManagedBean");
			excBean.setExc(e);
			return Constants.ERROR_HANDLER;
		}
		return "formularioAlterarTipoDocumento";
	}	
	
	/**
	 * Altera um Tipo de Documento
	 * @return Redireciona para página de listagem geral
	 */
	public String alterar() {
		try {			
			tipoDocumento = tipoDocumentoBO.merge(tipoDocumento);
			
			addMessage(FacesMessage.SEVERITY_INFO,"geral.alteracao.sucesso",ArcheionBean.PERSIST_SUCESS);
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
	 * Exclui um Tipo de Documento
	 * @return Redireciona para página de listagem geral
	 */
	public String remover() {
		try {
			Long id = Long.valueOf(Util.getParameter("_id"));
			tipoDocumento.setId( id );
			tipoDocumento = tipoDocumentoBO.findById(id);
			tipoDocumentoBO.remove(tipoDocumento);
			
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
	 * Imprime a listagem de Tipo de Documento
	 * @return Redireciona para impressão de Tipo de Documento
	 */
	public String imprimir() {
		FacesContext context = getContext();
		try {
			HttpServletResponse response = (HttpServletResponse) context
					.getExternalContext().getResponse();
			
			ServletOutputStream responseStream;
			responseStream = response.getOutputStream();
			String pathJasper = ((ServletContext)context.getExternalContext().getContext()).getRealPath("/WEB-INF/relatorios/")+ "/ArcheionTipoDocumento.jasper";
			Relatorio relatorio = tipoDocumentoBO.getRelatorio(new HashMap<String, Object>(), pathJasper);
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
	 * Busca todos os Tipos de Documento
	 * @return Redireciona para página de listagem geral
	 */
	public String findAll() {
		try {
			listaTipoDocumento = tipoDocumentoBO.findAll();
		}
		catch (AccessDeniedException aex) {
			return Constants.ACCESS_DENIED;
		}
		return "listaTipoDocumento";
	}
	
	/**
	 * Redireciona para página de inclusão/alteração
	 * @return Redireciona para página de inclusão/alteração
	 */
	public String goToForm() {
		tipoDocumento = new TipoDocumento();
		return "formularioTipoDocumento";
	}

	//-- Gets e Sets
	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public List<TipoDocumento> getListaTipoDocumento() {
		return listaTipoDocumento;
	}

	public void setListaTipoDocumento(List<TipoDocumento> listaTipoDocumento) {
		this.listaTipoDocumento = listaTipoDocumento;
	}

	public void setTipoDocumentoBO(TipoDocumentoBO tipoDocumentoBO) {
		this.tipoDocumentoBO = tipoDocumentoBO;
	}
	
}
