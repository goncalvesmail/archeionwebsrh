package br.com.archeion.mbean.pasta;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.acegisecurity.AccessDeniedException;

import br.com.archeion.jsf.Constants;
import br.com.archeion.jsf.Util;
import br.com.archeion.mbean.ArcheionBean;
import br.com.archeion.mbean.ExceptionManagedBean;
import br.com.archeion.modelo.SituacaoExpurgo;
import br.com.archeion.modelo.empresa.Empresa;
import br.com.archeion.modelo.local.Local;
import br.com.archeion.modelo.pasta.Pasta;
import br.com.archeion.negocio.empresa.EmpresaBO;
import br.com.archeion.negocio.local.LocalBO;
import br.com.archeion.negocio.pasta.PastaBO;

/**
 * ManagedBean para tratar eventos de Expurgo de Pasta
 *  
 * @author SInforme
 *
 */
public class ExpurgoPastaMBean extends ArcheionBean {

	/**
	 * Data de Expurgo
	 */
	private Date dataExpurgo = new Date();
	/**
	 * Listagem fonte para seleção de pasta
	 */
	private List<Pasta> funcSource;
	/**
	 * Listagem destino para seleção de pasta
	 */
	private List<Pasta> funcTarget;
	/**
	 * Pasta a ser expurgada
	 */
	private Pasta pasta;
	/**
	 * Lista de Empresas
	 */
	private List<SelectItem> listaEmpresa;
	/**
	 * Lista de Local
	 */
	private List<SelectItem> listaLocal;
	/**
	 * BO de Pasta
	 */
	private PastaBO pastaBO = (PastaBO) Util.getSpringBean("pastaBO");
	/**
	 * BO de Empresa
	 */
	private EmpresaBO empresaBO = (EmpresaBO) Util.getSpringBean("empresaBO");
	/**
	 * BO de Local
	 */
	private LocalBO localBO = (LocalBO) Util.getSpringBean("localBO");
	
	/**
	 * Construtor
	 */
	public ExpurgoPastaMBean() {
		funcSource = new ArrayList<Pasta>();
		funcTarget = new ArrayList<Pasta>();
	}
	
	/**
	 * Método chamado para atualizar os combos quando a Empresa é alterada
	 * @param event
	 */
	public void valueChangedEmpresa(ValueChangeEvent event) {
		Long empId = (Long)event.getNewValue();
		Empresa empresa = new Empresa();
		empresa.setId(empId);
		
		pasta.getLocal().setEmpresa(empresa);
		
		preencherCombos();
	}
	
	/**
	 * Método chamado para atualizar os combos quando o Local é alterado
	 * @param event
	 */
	public void valueChangedLocal(ValueChangeEvent event) {
		Long localId = (Long)event.getNewValue();
		Local local = localBO.findById(localId);
		pasta.setLocal(local);
		
		preencherCombos();
	}
	
	/**
	 * Preenche os Combos 
	 */
	private void preencherCombos() {
		listaEmpresa = new ArrayList<SelectItem>();
		listaLocal = new ArrayList<SelectItem>();
		
		List<Empresa> empresas = empresaBO.findAll();
		for(Empresa emp: empresas) {
			listaEmpresa.add(new SelectItem(emp.getId(),emp.getNome()));
		}
		
		if ( (pasta.getLocal().getEmpresa() == null || 
				pasta.getLocal().getEmpresa().getId() == null || 
				pasta.getLocal().getEmpresa().getId() == 0) && empresas.size()>0 ) {
			pasta.getLocal().setEmpresa(empresas.get(0));
		}
		
		List<Local> locais = localBO.findByEmpresa(pasta.getLocal().getEmpresa());
		for(Local loc: locais){
			listaLocal.add(new SelectItem(loc.getId(),loc.getNome())); 
		}
		
		if ( (locais!=null && locais.size()>0 && 
				(pasta.getLocal()==null || pasta.getLocal().getId()==null 
						|| pasta.getLocal().getId()==0)) && locais.size()>0 ) {
			pasta.setLocal(locais.get(0));
		}
		
		preencherPasta();
	}
	
	/**
	 * Preenche listagem de Pasta
	 */
	private void preencherPasta() {
		if ( pasta.getLocal().getEmpresa().getId()!=null && pasta.getLocal().getId()!=null ) {
			funcSource = pastaBO.findByEmpresaLocalExpurgo(pasta.getLocal().getEmpresa().getId().intValue(), 
					pasta.getLocal().getId().intValue());
		}
	}

	/**
	 * Vai para página de inclusão
	 * @return Redireciona para pagina de inclusão
	 */
	public String goToForm() {
		try {
			
			pasta = new Pasta();	
			dataExpurgo = new Date();
			
			preencherCombos();
			funcTarget = new ArrayList<Pasta>();
		}
		catch (AccessDeniedException aex) {
			return Constants.ACCESS_DENIED;
		} 
		return "formularioExpurgoPasta";
	}
	
	/**
	 * Expurga a Pasta selecionada
	 * @return Redireciona para página de inclusão
	 */
	public String expurgar() {
		try {
			
			for ( Pasta pasta:funcTarget ) {
				pasta.setDataExpurgo(dataExpurgo);
				pasta.setSituacao(SituacaoExpurgo.EXPURGADA);
				pastaBO.merge(pasta);	
			}
				
			addMessage(FacesMessage.SEVERITY_INFO,"pasta.expurgo.sucesso",ArcheionBean.PERSIST_SUCESS);
			
		} catch (AccessDeniedException aex) {
			return Constants.ACCESS_DENIED;
		} catch (Exception e) {
			ExceptionManagedBean excBean = (ExceptionManagedBean) Util.getManagedBean("exceptionManagedBean");
			excBean.setExc(e);
			return Constants.ERROR_HANDLER;
		}			
		return goToForm();
	}
	
	//-- Gets e Sets

	public List<Pasta> getFuncSource() {
		return funcSource;
	}

	public void setFuncSource(List<Pasta> funcSource) {
		this.funcSource = funcSource;
	}

	public List<Pasta> getFuncTarget() {
		return funcTarget;
	}

	public void setFuncTarget(List<Pasta> funcTarget) {
		this.funcTarget = funcTarget;
	}

	public Date getDataExpurgo() {
		return dataExpurgo;
	}

	public void setDataExpurgo(Date dataExpurgo) {
		this.dataExpurgo = dataExpurgo;
	}

	public PastaBO getPastaBO() {
		return pastaBO;
	}

	public void setPastaBO(PastaBO pastaBO) {
		this.pastaBO = pastaBO;
	}

	public Pasta getPasta() {
		return pasta;
	}

	public void setPasta(Pasta pasta) {
		this.pasta = pasta;
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
	
}
