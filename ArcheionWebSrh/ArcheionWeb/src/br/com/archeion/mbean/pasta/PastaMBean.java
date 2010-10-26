package br.com.archeion.mbean.pasta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
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
import br.com.archeion.jsf.PagedCollectionModel;
import br.com.archeion.jsf.Util;
import br.com.archeion.mbean.ArcheionBean;
import br.com.archeion.mbean.AuthenticationController;
import br.com.archeion.mbean.ExceptionManagedBean;
import br.com.archeion.modelo.SituacaoExpurgo;
import br.com.archeion.modelo.empresa.Empresa;
import br.com.archeion.modelo.itemdocumental.ItemDocumental;
import br.com.archeion.modelo.local.Local;
import br.com.archeion.modelo.pasta.Pasta;
import br.com.archeion.modelo.ttd.TTD;
import br.com.archeion.negocio.empresa.EmpresaBO;
import br.com.archeion.negocio.itemdocumental.ItemDocumentalBO;
import br.com.archeion.negocio.local.LocalBO;
import br.com.archeion.negocio.pasta.PastaBO;
import br.com.archeion.negocio.relatoriotxt.RelatorioTxtBO;
import br.com.archeion.negocio.ttd.TTDBO;

/**
 * Managed Bean para manipulação de Pastas
 * @author SInforme
 *
 */
public class PastaMBean extends ArcheionBean {
   
       
    /**
     * Lsita de Empresa
     */
    private List<SelectItem> listaEmpresa;
    /**
     * Lista de Local
     */
    private List<SelectItem> listaLocal;
    /**
     * Lista de Item Documental
     */
    private List<SelectItem> listaItemDocumental;
    /**
     * Lista de Situação
     */
    private List<SelectItem> listaSituacao;   
       
    /**
     * Pasta para inclusão/alteração
     */
    private Pasta pasta;
   
    /**
     * TTD para ajuda na inclusão/alteração de Pasta
     */
    private TTD ttd;
   
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
     * BO de Realtorio
     */
    private RelatorioTxtBO relatorioTxtBO = (RelatorioTxtBO) Util.getSpringBean("relatorioTxtBO");
    /**
     * BO de Item Documental
     */
    private ItemDocumentalBO itemDocumentalBO = (ItemDocumentalBO) Util.getSpringBean("itemDocumentalBO");
    /**
     * BO de TTD
     */
    private TTDBO ttdBO = (TTDBO) Util.getSpringBean("ttdBO");   
    /**
     * BO do Controle de Autorização
     */
    private AuthenticationController authenticationController = (AuthenticationController) Util.getManagedBean("authenticationController");
    /**
     * Managed Bean de Localização de Pastas
     */
    private LocalizarPastaMBean localizarPastaBean = (LocalizarPastaMBean) Util.getManagedBean("localizarPastaBean");
   
    /**
     * Nome do Evento de contagem para buscas
     */
    private String nomeEventoContagem;
    /**
     * Indica se a página é de visualização ou edição
     */
    private boolean visualizar = false;
    /**
     * Indica a origem da solicitação recebida
     */
    private String origemAlteracao = "listaPasta";
   
    /**
     * Tabela com resultado da pequisa
     */
    private DataModel dataModel;   
    /**
     * Tamanho de cada página da tabela de pesquisa
     */
    private int pageSize = 25;   
   
    /**
     * Construtor
     */
    public PastaMBean() {
        pasta = new Pasta();
    }
   
    /**
     * Redireciona para a pagina de visualização de Pastas
     * @return Redireciona para a pagina de visualização de Pastas
     */
    public String goToVisualizar() {
        visualizar=true;
        return preparaTelaAlterar();
    }
   
    /**
     * Método acionado quando da alteração no combo de Empresa
     * @param event
     */
    public void valueChangedEmpresa(ValueChangeEvent event) {
        Long empId = (Long)event.getNewValue();
        Empresa empresa = new Empresa();
        empresa.setId(empId);
       
        pasta.getLocal().setEmpresa(empresa);
       
        preencherCombos();
       
        List<Local> locais = localBO.findByEmpresa(empresa);
        if ( locais!=null && locais.size()>0 ) {
            pasta.setLocal(locais.get(0));
        }
       
        ItemDocumental item = pasta.getItemDocumental();
        Local local = pasta.getLocal();
       
        List<TTD> ttdList = ttdBO.findByEmpresaLocalItemDocumental(0,
                local.getId().intValue(), item.getId().intValue());
       
        atualizarTTD(ttdList);
    }
   
    /**
     * Método acionado quando da alteração no combo de Empresa
     * @param event
     */
    public void valueChangedLista(ValueChangeEvent event) {

        Long empId = (Long)event.getNewValue();
        Empresa empresa = new Empresa();
        empresa.setId(empId);
       
        pasta.getLocal().setEmpresa(empresa);
       
        preencherCombos();   
    }

    /**
     * Método acionado quando da alteração na Data de abertura
     * @param event
     */
    public void valueChangedData(ValueChangeEvent event) {

        Date d = (Date)event.getNewValue();
        if ( d!=null ) {
            try {               
                pasta.setDataAbertura(d);
            }
            catch (Exception e) {
                pasta.setPrevisaoRecolhimento(null);
                pasta.setPrevisaoExpurgo(null);
            }
        }
       
        Local local = pasta.getLocal();
        ItemDocumental item = pasta.getItemDocumental();
       
        List<TTD> ttdList = ttdBO.findByEmpresaLocalItemDocumental(0,
                local.getId().intValue(), item.getId().intValue());
       
        atualizarTTD(ttdList);   
    }   
   
    /**
     * Atualiza a lista de TTD e as previsões de recolhimento
     * @param ttdList
     */
    private void atualizarTTD(List<TTD> ttdList) {
        if ( ttdList!=null && ttdList.size()>0 ) {
            ttd = ttdList.get(0);
            if ( ttd.getArquivoIntermediario() ) {
                nomeEventoContagem = "Intermediário";
            }
            else  if ( ttd.getArquivoPermanente() ) {
                nomeEventoContagem = "Permanente";
            }
            else {
                nomeEventoContagem = "";
            }
           

            atualizarPrevisoes();
        }
        else {
            ttd = new TTD();
            nomeEventoContagem = "";

            pasta.setPrevisaoRecolhimento(null);
            pasta.setPrevisaoExpurgo(null);
        }
    }   
   
    /**
     * Atualiza as previsões de Recolhimento e Expurgo
     */
    private void atualizarPrevisoes() {
        Date abertura = pasta.getDataAbertura();
        pasta.setPrevisaoRecolhimento(null);
        pasta.setPrevisaoExpurgo(null);
       
        if ( abertura!=null ) {
            Calendar previsaoRecolhimento = Calendar.getInstance();
            previsaoRecolhimento.setTime(abertura);           
            if ( ttd.getTempoArquivoCorrente()!=null ) {               
                previsaoRecolhimento.add(Calendar.YEAR, ttd.getTempoArquivoCorrente());
            }
           
            if ( ttd.getArquivoIntermediario() ) {
                Calendar previsaoExpurgo = (GregorianCalendar)previsaoRecolhimento.clone();
                if ( ttd.getTempoArquivoIntermediario()!=null ) {
                    previsaoExpurgo.add(Calendar.YEAR, ttd.getTempoArquivoIntermediario());
                    pasta.setPrevisaoExpurgo(previsaoExpurgo.getTime());
                }
            }
           
           
            if ( ttd.getArquivoIntermediario() || ttd.getArquivoPermanente() ) {
                pasta.setPrevisaoRecolhimento(previsaoRecolhimento.getTime());   
            }
            else {
                pasta.setPrevisaoExpurgo(previsaoRecolhimento.getTime());   
            }
           
        }
    }
   
    /**
     * Método acionado quando da alteração no combo de Local
     * @param event
     */
    public void valueChangedLocal(ValueChangeEvent event) {
        Long localId = (Long)event.getNewValue();
        Local local = localBO.findById(localId);
        pasta.setLocal(local);
   
        ItemDocumental item = pasta.getItemDocumental();
       
        List<TTD> ttdList = ttdBO.findByEmpresaLocalItemDocumental(0,
                local.getId().intValue(), item.getId().intValue());
       
        atualizarTTD(ttdList);
    }
   
    /**
     * Método acionado quando da alteração no combo de Item Documental
     * @param event
     */
    public void valueChangedItem(ValueChangeEvent event) {
        Long itemId = (Long)event.getNewValue();
        ItemDocumental item = itemDocumentalBO.findById(itemId);
        pasta.setItemDocumental(item);
       
        Local local = pasta.getLocal();
       
        List<TTD> ttdList = ttdBO.findByEmpresaLocalItemDocumental(0,
                local.getId().intValue(), item.getId().intValue());
       
        atualizarTTD(ttdList);
    }
   
    /**
     * Inclui uma nova Pasta
     * @return Redireciona para listagem de Pasta
     */
    public String incluir() {
        try {
            incluirMBean();
        } catch (AccessDeniedException aex) {
            return Constants.ACCESS_DENIED;
        } catch (BusinessException e) {
            addMessage(FacesMessage.SEVERITY_INFO, e.getMessageCode(),ArcheionBean.PERSIST_FAILURE);
            return "formularioPasta";
        } catch (CadastroDuplicadoException e) {
            addMessage(FacesMessage.SEVERITY_INFO, "error.business.cadastro.duplicado",ArcheionBean.PERSIST_FAILURE);
            return "formularioPasta";
        } catch (Exception e) {
            ExceptionManagedBean excBean = (ExceptionManagedBean) Util.getManagedBean("exceptionManagedBean");
            excBean.setExc(e);
            return Constants.ERROR_HANDLER;
        }           
        return findAll();
    }
   
    /**
     * Inclui uma nova Pasta
     * @return Redireciona para formulário de inclusão de Pasta
     */
    public String incluirMais() {
        try {
            incluirMBean();
        } catch (AccessDeniedException aex) {
            return Constants.ACCESS_DENIED;
        } catch (BusinessException e) {
            addMessage(FacesMessage.SEVERITY_INFO, e.getMessageCode(),ArcheionBean.PERSIST_FAILURE);
            return "formularioPasta";
        } catch (CadastroDuplicadoException e) {
            addMessage(FacesMessage.SEVERITY_INFO, "error.business.cadastro.duplicado",ArcheionBean.PERSIST_FAILURE);
            return "formularioPasta";
        } catch (Exception e) {
            ExceptionManagedBean excBean = (ExceptionManagedBean) Util.getManagedBean("exceptionManagedBean");
            excBean.setExc(e);
            return Constants.ERROR_HANDLER;
        }           
        return goToForm();
    }


    /**
     * Inclui uma nova Pasta
     */
    public void incluirMBean() throws AccessDeniedException, CadastroDuplicadoException, BusinessException {
        pasta.setId(null);
        pasta.setDataReferencia(pasta.getDataAbertura());
        pasta.setSituacao(SituacaoExpurgo.ATIVA);
        pasta = pastaBO.persist(pasta);

        pasta.setLocal(localBO.findById(pasta.getLocal().getId()));
        pasta.setItemDocumental(itemDocumentalBO.findById(pasta.getItemDocumental().getId()));

        addMessage(FacesMessage.SEVERITY_INFO,"geral.inclusao.sucesso",ArcheionBean.PERSIST_SUCESS);
    }
   
    /**
     * Redireciona para página de alteração de Pasta
     * @return Redireciona para página de alteração de Pasta
     */
    public String goToAlterar() {
        visualizar=false;
        return preparaTelaAlterar();
    }

    /**
     * Prepara a página de alteração de Pasta
     * @return Redireciona para página de alteração de Pasta
     */
    private String preparaTelaAlterar() {
        try {           
            Long id = Long.valueOf(Util.getParameter("_id"));
            origemAlteracao = Util.getParameter("_origem");
           
            pasta = pastaBO.findById(id);
           
            preencherCombos();
           
            listaItemDocumental = new ArrayList<SelectItem>();
            List<ItemDocumental> itens = itemDocumentalBO.findAll();
            for(ItemDocumental i: itens) {
                listaItemDocumental.add(new SelectItem(i.getId(), i.getNome()));
            }
           
            Local local = pasta.getLocal();
            ItemDocumental item = pasta.getItemDocumental();
            List<TTD> ttdList = ttdBO.findByEmpresaLocalItemDocumental(0,
                    local.getId().intValue(), item.getId().intValue());
           
            atualizarTTD(ttdList);
           
        } catch (AccessDeniedException aex) {
            return Constants.ACCESS_DENIED;
        } catch (Exception e) {
            ExceptionManagedBean excBean = (ExceptionManagedBean) Util.getManagedBean("exceptionManagedBean");
            excBean.setExc(e);
            return Constants.ERROR_HANDLER;
        }
   
        return "formularioAlterarPasta";
    }   
   
    /**
     * Altera uma Pasta
     * @return Redireciona para página de listagem de Pasta
     */
    public String alterar() {
        try {           
            pasta = pastaBO.merge(pasta);
           
            addMessage(FacesMessage.SEVERITY_INFO,"geral.alteracao.sucesso",ArcheionBean.PERSIST_SUCESS);
        } catch (AccessDeniedException aex) {
            return Constants.ACCESS_DENIED;
        } catch (BusinessException e) {
            addMessage(FacesMessage.SEVERITY_INFO, e.getMessageCode(),ArcheionBean.PERSIST_FAILURE);
            return "formularioAlterarPasta";
        } catch (CadastroDuplicadoException e) {
            addMessage(FacesMessage.SEVERITY_INFO, "error.business.cadastro.duplicado",ArcheionBean.PERSIST_FAILURE);
            return "formularioPasta";
        } catch (Exception e) {
            ExceptionManagedBean excBean = (ExceptionManagedBean) Util.getManagedBean("exceptionManagedBean");
            excBean.setExc(e);
            return Constants.ERROR_HANDLER;
        }
        //return findAll();
        if(origemAlteracao.equals("listaPasta")){
            //return "listaPasta";
            return findByEmpresaLocal();
        } else {
            //return "formularioLocalizarPasta";
            return localizarPastaBean.localizarPasta();
        }
    }   
   
    /**
     * Exclui uma Pasta
     * @return Redireciona para página de listagem de Pasta
     */
    public String remover() {
        try {
            Long id = Long.valueOf(Util.getParameter("_id"));
            pasta.setId( id );
            pasta = pastaBO.findById(id);
            pastaBO.remove(pasta);
           
            addMessage(FacesMessage.SEVERITY_INFO,"geral.remocao.sucesso",ArcheionBean.PERSIST_SUCESS);
        } catch (BusinessException e) {
            addMessage(FacesMessage.SEVERITY_INFO,
                    e.getMessageCode(),ArcheionBean.PERSIST_FAILURE);
        } catch (AccessDeniedException aex) {
            return Constants.ACCESS_DENIED;
        } catch (Exception e) {
            ExceptionManagedBean excBean = (ExceptionManagedBean) Util.getManagedBean("exceptionManagedBean");
            excBean.setExc(e);
            return Constants.ERROR_HANDLER;
        }
        //return findAll();
        return findAllMenu();
    }   
   
    /**
     * Realiza a busca de pasta filtrando por Empresa e/ou Local
     * @return Redireciona para página de listagem de Pasta
     */
    public String findByEmpresaLocal() {       
        try {
            //listaPasta = pastaBO.findByEmpresaLocalSituacao(pasta.getLocal().getEmpresa().getId().intValue(), pasta.getLocal().getId().intValue(), pasta.getSituacao());
            pasta.setBuscaPorCaixeta(false);
            dataModel = new PagedCollectionModel<Pasta,Pasta>(pageSize, pastaBO, pasta);
        }
        catch (AccessDeniedException aex) {
            return Constants.ACCESS_DENIED;
        }
       
        return "listaPasta";
    }
   
    /**
     * Realiza a busca de pasta filtrando por Caixeta
     * @return Redireciona para página de listagem de Pasta
     */
    public String findByCaixeta() {       
        try {
           
            //listaPasta = pastaBO.findByCaixeta(pasta.getCaixeta());
            pasta.setBuscaPorCaixeta(true);
            dataModel = new PagedCollectionModel<Pasta,Pasta>(pageSize, pastaBO, pasta);
           
        } catch (AccessDeniedException aex) {
            return Constants.ACCESS_DENIED;
        }
       
        return "listaPasta";
    }
   
    /**
     * Imprime relatório de Pasta
     * @return Redireciona para página de relatório de Pasta
     */
    public String imprimir() {
        FacesContext context = getContext();
        try {
            HttpServletResponse response = (HttpServletResponse) context
                    .getExternalContext().getResponse();
           
            ServletOutputStream responseStream;
            responseStream = response.getOutputStream();
            String pathJasper = ((ServletContext)context.getExternalContext().getContext()).getRealPath("/WEB-INF/relatorios/")+
            "/ArcheionImprimirPasta.jasper";
            HashMap<String, Object> param = new HashMap<String, Object>();
            ParametrosReport ids = new ParametrosReport();
           
            int count = pastaBO.count(pasta);
            List<Pasta> listaPasta = pastaBO.search(pasta, 0, count);
           
            for(Pasta p: listaPasta) {
                ids.add(p.getId());
            }
           
            param.put("ids", ids.toString());
            param.put("user", authenticationController.getUsuario().getNome());

            Relatorio relatorio = pastaBO.getRelatorio(param, pathJasper);
            relatorio.exportarParaPdfStream(responseStream);
           
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition",
                    "filename=\"ImprimirPasta.pdf\"");
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
     * Imprime relatório de Pasta em TXT
     * @return Redireciona para página de relatório de Pasta em TXT
     */
    public String imprimirTxt() {
        FacesContext context = getContext();
        try {
            HttpServletResponse response = (HttpServletResponse) context
            .getExternalContext().getResponse();
           
            ServletOutputStream responseStream;
            responseStream = response.getOutputStream();
            StringBuilder sb = new StringBuilder("select d.nm_empresa as empresa, b.nm_local as local, c.nm_item_documental as item_documental, ");
            sb.append("a.nm_titulo as titulo, a.nm_caixeta as caixeta, a.dt_expurgo as data_expurgo, (f.vao_endereco_caixa || e.nu_vao_endereco_caixa) as caixa, ");
            sb.append("(case when a.cs_situacao_pasta = 1 then 'Ativa' when a.cs_situacao_pasta = 2 then 'Expurgada' end ) as situacao ");
            sb.append("from tb_pasta a join tb_local b on (a.id_local = b.id_local) ");
            sb.append("join tb_item_documental c on (a.id_item_documental = c.id_item_documental) ");
            sb.append("join tb_empresa d on (b.id_empresa = d.id_empresa) ");
            sb.append("left join tb_caixa e on (a.id_caixa = e.id_caixa) ");
            sb.append("left join tb_endereco_caixa f on (e.id_endereco_caixa = f.id_endereco_caixa) ");
           
            relatorioTxtBO.geraRelatorioTxt(sb.toString(), responseStream);
           
            response.setContentType("application/txt");
            response.setHeader("Content-disposition",
            "filename=\"ImprimirPasta.txt\"");
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
     * Busca todas as Pastas
     * @return Redireciona para a pagina de listagem de Pasta
     */
    public String findAll() {
        try {
           
            if(origemAlteracao==null || origemAlteracao.equals("listaPasta")){
               
                return "listaPasta";
            } else {
                //return "formularioLocalizarPasta";
                return localizarPastaBean.localizarPasta();
            }
           
           
        } catch (AccessDeniedException aex) {
            return Constants.ACCESS_DENIED;
        }
       
    }
   
    /**
     * Busca todas as Pastas
     * @return Redireciona para a pagina de listagem de Pasta
     */
    public String findAllMenu() {
        try {
            visualizar=false;
            pasta = new Pasta();
            pasta.setSituacao(SituacaoExpurgo.TODOS);
            preencherCombos();
           
            //listaPasta = pastaBO.findAll();
            dataModel = new PagedCollectionModel<Pasta,Pasta>(pageSize, pastaBO, pasta);
            dataModel.setRowIndex(0);
           
            return "listaPasta";
        } catch (AccessDeniedException aex) {
            return Constants.ACCESS_DENIED;
        }
       
    }

    /**
     * Prepara os combobox das páginas
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
       
        listaSituacao = new ArrayList<SelectItem>();
        for (SituacaoExpurgo situacao : SituacaoExpurgo.values()) {
            listaSituacao.add(new SelectItem(situacao.toString(), situacao.getDescricao()));
        }
    }
   
    /**
     * Redireciona para página de inclusão de Pasta
     * @return Redireciona para página de inclusão de Pasta
     */
    public String goToForm() {
        pasta = new Pasta();       
        preencherCombos();
       
        listaItemDocumental = new ArrayList<SelectItem>();
        List<ItemDocumental> itens = itemDocumentalBO.findAll();
        for(ItemDocumental i: itens) {
            listaItemDocumental.add(new SelectItem(i.getId(), i.getNome()));
        }
       
        if ( itens!=null && itens.size()>0 )
            pasta.setItemDocumental(itens.get(0));
       
       
        Local local = pasta.getLocal();
        ItemDocumental item = pasta.getItemDocumental();
       
        if ( local.getId()!=null && item.getId()!=null ) {
            List<TTD> ttdList = ttdBO.findByEmpresaLocalItemDocumental(0,
                    local.getId().intValue(), item.getId().intValue());
           
            atualizarTTD(ttdList);
        }
       
        return "formularioPasta";
    }
   
    //-- Gets e Sets

    public Pasta getPasta() {
        return pasta;
    }

    public void setPasta(Pasta pasta) {
        this.pasta = pasta;
    }

    public PastaBO getPastaBO() {
        return pastaBO;
    }

    public void setPastaBO(PastaBO pastaBO) {
        this.pastaBO = pastaBO;
    }

    public EmpresaBO getEmpresaBO() {
        return empresaBO;
    }

    public void setEmpresaBO(EmpresaBO empresaBO) {
        this.empresaBO = empresaBO;
    }

    public LocalBO getLocalBO() {
        return localBO;
    }

    public void setLocalBO(LocalBO localBO) {
        this.localBO = localBO;
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

    public List<SelectItem> getListaItemDocumental() {
        return listaItemDocumental;
    }

    public void setListaItemDocumental(List<SelectItem> listaItemDocumental) {
        this.listaItemDocumental = listaItemDocumental;
    }

    public ItemDocumentalBO getItemDocumentalBO() {
        return itemDocumentalBO;
    }

    public void setItemDocumentalBO(ItemDocumentalBO itemDocumentalBO) {
        this.itemDocumentalBO = itemDocumentalBO;
    }

    public TTD getTtd() {
        return ttd;
    }

    public void setTtd(TTD ttd) {
        this.ttd = ttd;
    }

    public TTDBO getTtdBO() {
        return ttdBO;
    }

    public void setTtdBO(TTDBO ttdBO) {
        this.ttdBO = ttdBO;
    }

    public String getNomeEventoContagem() {
        return nomeEventoContagem;
    }

    public void setNomeEventoContagem(String nomeEventoContagem) {
        this.nomeEventoContagem = nomeEventoContagem;
    }

    public boolean isVisualizar() {
        return visualizar;
    }

    public void setVisualizar(boolean visualizar) {
        this.visualizar = visualizar;
    }

    public List<SelectItem> getListaSituacao() {
        return listaSituacao;
    }

    public void setListaSituacao(List<SelectItem> listaSituacao) {
        this.listaSituacao = listaSituacao;
    }

    public LocalizarPastaMBean getLocalizarPastaBean() {
        return localizarPastaBean;
    }

    public void setLocalizarPastaBean(LocalizarPastaMBean localizarPastaBean) {
        this.localizarPastaBean = localizarPastaBean;
    }

    public AuthenticationController getAuthenticationController() {
        return authenticationController;
    }

    public void setAuthenticationController(
            AuthenticationController authenticationController) {
        this.authenticationController = authenticationController;
    }

    public DataModel getDataModel() {
        return dataModel;
    }

    public void setDataModel(DataModel dataModel) {
        this.dataModel = dataModel;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


   
   
}

//Caixa, Caixeta, Data de Abertura, Data de Referencia, Item Documental, Limite Data,
//Limite Nome, Limite Valor, Local, Observação, Titulo Pasta
enum ChavesPesquisa {
    //CAIXA(1,"Caixa","u.caixa.id"),
    CAIXETA(2,"Caixeta","u.caixeta "),
    DATAABERTURA(3,"Data de Abertura","u.dataAbertura ","yyyy-mm-dd"),
    DATAREFERENCIA(4,"Data de Referência","u.dataReferencia","yyyy-mm-dd"),
    ITEMDOCUMENTAL(5,"Item Documental","u.itemDocumental.nome"),
    LIMITEDATA(6,"Limite Data","u.limiteDataInicial","yyyy-mm-dd"),
    LIMITENOME(7,"Limite Nome","u.limiteNomeInicial"),
    LIMITEVALOR(8,"Limite valor","u.limiteNumeroInicial"),
    LOCAL(9,"Local","u.local.nome"),
    TITULOPASTA(10,"Título Pasta","u.titulo");
   
    ChavesPesquisa(int id, String label, String dataValue) {
        this.id = id;
        this.label = label;
        this.dataValue = dataValue;
        this.conversor = null;
    }
    ChavesPesquisa(int id, String label, String dataValue, String conversor) {
        this.id = id;
        this.label = label;
        this.dataValue = dataValue;
        this.conversor = conversor;
    }
    private int id;
    private String label;
    private String dataValue;
    private String conversor;
   
    public static String getById(int id){
        for(ChavesPesquisa c: ChavesPesquisa.values()){
            if(c.getId() == id){
                return c.getLabel();
            }
        }
        return null;
    }
   
    public static String getDataBaseValue(int id) {
        for(ChavesPesquisa c: ChavesPesquisa.values()){
            if(c.getId() == id){
                return c.getDataValue();
            }
        }
        return null;
    }
   
    public static String getConversorValue(int id) {
        for(ChavesPesquisa c: ChavesPesquisa.values()){
            if(c.getId() == id){
                return c.getConversor();
            }
        }
        return null;
    }
   
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public String getDataValue() {
        return dataValue;
    }
    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }

    public String getConversor() {
        return conversor;
    }

    public void setConversor(String conversor) {
        this.conversor = conversor;
    }
   
   
}

class ParametrosReport extends ArrayList<Object> {
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        String result = "";
        for(int i=0;i<this.size()-1;i++){
            result += this.get(i)+",";
        }
        return result+this.get(this.size()-1);
    }
}