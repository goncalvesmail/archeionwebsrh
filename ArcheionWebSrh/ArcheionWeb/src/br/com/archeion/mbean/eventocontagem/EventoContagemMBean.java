package br.com.archeion.mbean.eventocontagem;

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
import br.com.archeion.modelo.eventocontagem.EventoContagem;
import br.com.archeion.negocio.eventocontagem.EventoContagemBO;
import br.com.archeion.negocio.relatoriotxt.RelatorioTxtBO;

public class EventoContagemMBean extends ArcheionBean {
	/**
	 * Evento de contagem
	 */
	private EventoContagem eventoContagem;
	/**
	 * Lista de eventos de contagem
	 */
	private List<EventoContagem> listEventoContagem;
	/**
	 * BO de evento de contagem
	 */
	private EventoContagemBO eventoContagemBO = (EventoContagemBO) Util.getSpringBean("eventoContagemBO");
	/**
	 * BO de relatorio txt
	 */
	private RelatorioTxtBO relatorioTxtBO = (RelatorioTxtBO) Util.getSpringBean("relatorioTxtBO");
	
	/**
	 * construtor
	 */
	public EventoContagemMBean() {
		this.eventoContagem = new EventoContagem();
	}
	/**
	 * Incluir evento de contagem
	 * @return para a tela principal
	 */
	public String incluir() {
		try {
			this.incluirMBean();
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
	 * Incluir varios eventos de contagem
	 * @return continua na pagina de incluir
	 */
	public String incluirMais() {
		try {
			this.incluirMBean();
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
	 * Incluir evento de contagem
	 * @throws AccessDeniedException
	 * @throws CadastroDuplicadoException
	 */
	public void incluirMBean() throws AccessDeniedException, CadastroDuplicadoException {
		eventoContagem.setId(null);
		eventoContagemBO.persist(eventoContagem);
		
		addMessage(FacesMessage.SEVERITY_INFO,"geral.inclusao.sucesso",ArcheionBean.PERSIST_SUCESS);
	}

	/**
	 * Inicializa e vai para pagina de altera
	 * @return pagina de alterar
	 */
	public String goToAlterar() {
		try {
			Long id = Long.valueOf(Util.getParameter("_id"));			
			eventoContagem = eventoContagemBO.findById(id);
		} catch (AccessDeniedException aex) {
			return Constants.ACCESS_DENIED;
		} catch (Exception e) {
			ExceptionManagedBean excBean = (ExceptionManagedBean) Util.getManagedBean("exceptionManagedBean");
			excBean.setExc(e);
			return Constants.ERROR_HANDLER;
		}
		return "formularioAlterarEventoContagem";
	}	
	/**
	 * Alterar evento de contagem
	 * @return
	 */
	public String alterar() {
		try {			
			eventoContagem = eventoContagemBO.merge(eventoContagem);
			
			addMessage(FacesMessage.SEVERITY_INFO,"geral.alteracao.sucesso",ArcheionBean.PERSIST_SUCESS);
		} catch (AccessDeniedException aex) {
			return Constants.ACCESS_DENIED;
		} catch (CadastroDuplicadoException e) {
			addMessage(FacesMessage.SEVERITY_INFO, "error.business.cadastro.duplicado",ArcheionBean.PERSIST_FAILURE);
			return "formularioAlterarEventoContagem";
		} catch (Exception e) {
			ExceptionManagedBean excBean = (ExceptionManagedBean) Util.getManagedBean("exceptionManagedBean");
			excBean.setExc(e);
			return Constants.ERROR_HANDLER;
		}
		return findAll();
	}	
	/**
	 * Remover evento de contagem
	 * @return pagina principal
	 */
	public String remover() {
		try {
			Long id = Long.valueOf(Util.getParameter("_id"));
			eventoContagem.setId( id );
			eventoContagem = eventoContagemBO.findById(id);
			eventoContagemBO.remove(eventoContagem);

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
	 * Imprimir relatorio de evento de contagem
	 * @return
	 */
	public String imprimir() {
		FacesContext context = getContext();
		try {
			HttpServletResponse response = (HttpServletResponse) context
					.getExternalContext().getResponse();
			
			ServletOutputStream responseStream;
			responseStream = response.getOutputStream();
			String pathJasper = ((ServletContext)context.getExternalContext().getContext()).getRealPath("/WEB-INF/relatorios/")+ "/ArcheionEventoContagem.jasper";
			Relatorio relatorio = eventoContagemBO.getRelatorio(new HashMap<String, Object>(), pathJasper);
			relatorio.exportarParaPdfStream(responseStream);
			
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition",
					"filename=\"RelacaoEventoContagem.pdf\"");
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
	 * Imprimir txt de envento de contagem
	 * @return
	 */
	public String imprimirTxt() {
		FacesContext context = getContext();
		try {
			HttpServletResponse response = (HttpServletResponse) context
			.getExternalContext().getResponse();
			
			ServletOutputStream responseStream;
			responseStream = response.getOutputStream();
			StringBuilder sb = new StringBuilder("select a.nm_evento_contagem as evento_contagem ");
			sb.append("from tb_evento_contagem a ");
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
	 * retornar todos
	 * @return
	 */
	public String findAll() {
		try {
			listEventoContagem = eventoContagemBO.findAll();
		}
		catch (AccessDeniedException aex) {
			return Constants.ACCESS_DENIED;
		} 
		return "listaEventoContagem";
	}
	/**
	 * Inicializa e vai pra pagina principal de evento de contagem
	 * @return
	 */
	public String goToForm() {
		try {
			eventoContagem = new EventoContagem();
		}
		catch (AccessDeniedException aex) {
			return Constants.ACCESS_DENIED;
		} 
		return "formularioEventoContagem";
	}
	
	public EventoContagem getEventoContagem() {
		return eventoContagem;
	}
	public void setEventoContagem(EventoContagem eventoContagem) {
		this.eventoContagem = eventoContagem;
	}
	public List<EventoContagem> getListEventoContagem() {
		return listEventoContagem;
	}
	public void setListEventoContagem(List<EventoContagem> listEventoContagem) {
		this.listEventoContagem = listEventoContagem;
	}

	public EventoContagemBO getEventoContagemBO() {
		return eventoContagemBO;
	}

	public void setEventoContagemBO(EventoContagemBO eventoContagemBO) {
		this.eventoContagemBO = eventoContagemBO;
	}

}
