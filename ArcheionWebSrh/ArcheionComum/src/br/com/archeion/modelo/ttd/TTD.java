package br.com.archeion.modelo.ttd;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.archeion.modelo.AbstractTO;
import br.com.archeion.modelo.eventocontagem.EventoContagem;
import br.com.archeion.modelo.itemdocumental.ItemDocumental;
import br.com.archeion.modelo.local.Local;

/**
 * Classe respons�vel pelas TTDs do sistema
 * @author SInforme
 */
@Entity
@Table(name = "TB_TTD")
public class TTD extends AbstractTO implements Serializable {

	private static final long serialVersionUID = -6336802995979593989L;

	/**
	 * Identificador �nico
	 */
	@Id
	@Column(name = "ID_TTD")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	/**
	 * Local da TTD
	 */
	@ManyToOne(fetch=FetchType.EAGER)  
	@JoinColumn(name="ID_LOCAL")
	private Local local;
	
	/**
	 * Item documental da TTD
	 */
	@ManyToOne(fetch=FetchType.EAGER)  
	@JoinColumn(name="ID_ITEM_DOCUMENTAL")
	private ItemDocumental itemDocumental;
	
	/**
	 * Evento de contagem pra informar a partir de quando foi disparada
	 * a contagem da TTD
	 */
	@ManyToOne(fetch=FetchType.EAGER)  
	@JoinColumn(name="ID_EVENTO_CONTAGEM")
	private EventoContagem eventoContagem;
	
	/**
	 * Tempo da pasta no arquivo corrente
	 */
	@Column(name = "NM_TEMPO_ARQ_CORRENTE")
	private Integer tempoArquivoCorrente;
	
	/**
	 * Tempo da pasta no arquivo intermediario
	 */
	@Column(name = "CS_ARQ_INTERMEDIARIO")
	private Integer arquivoIntermediario;
	
	/**
	 * Informa se a pasta � permanente
	 */
	@Column(name = "CS_ARQ_PERMANENTE")
	private Integer arquivoPermanente;
	
	/**
	 * Informa se a pasta foi microfilmada
	 */
	@Column(name = "CS_MICROFILMAGEM")
	private Integer microfilmagem = 0;
	
	/**
	 * Informa se a pasta foi digitalizada
	 */
	@Column(name = "CS_DIGITALIZACAO")
	private Integer digitalizacao = 0;
	
	/**
	 * Observa��o livre sobre a pasta
	 */
	@Column(name = "TX_OBSERVACAO")
	private String observacao;
	
	/**
	 * Tempo no arquivo intermediario
	 */
	@Column(name = "NM_TEMPO_ARQ_INTERMEDIARIO")
	private Integer tempoArquivoIntermediario;
	
	private transient String temporaliedadeSelecionada;
	
	public TTD(){
		local = new Local();
		itemDocumental = new ItemDocumental();
		eventoContagem = new EventoContagem();
		arquivoIntermediario = 0;
		arquivoPermanente = 1;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

	public ItemDocumental getItemDocumental() {
		return itemDocumental;
	}

	public void setItemDocumental(ItemDocumental itemDocumental) {
		this.itemDocumental = itemDocumental;
	}

	public EventoContagem getEventoContagem() {
		return eventoContagem;
	}

	public void setEventoContagem(EventoContagem eventoContagem) {
		this.eventoContagem = eventoContagem;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	public Boolean isArquivoIntermediario() {
		return arquivoIntermediario==1?Boolean.TRUE:Boolean.FALSE;
	}

	public Boolean getArquivoIntermediario() {
		return arquivoIntermediario==1?Boolean.TRUE:Boolean.FALSE;
	}

	public void setArquivoIntermediario(Boolean arquivoIntermediario) {
		this.arquivoIntermediario = arquivoIntermediario?1:0;
	}

	public Boolean isMicrofilmagem() {
		return microfilmagem==1?Boolean.TRUE:Boolean.FALSE;
	}
	
	public Boolean getMicrofilmagem() {
		return microfilmagem==1?Boolean.TRUE:Boolean.FALSE;
	}

	public void setMicrofilmagem(Boolean microfilmagem) {
		this.microfilmagem = microfilmagem?1:0;
	}
	
	public Boolean isDigitalizacao() {
		return digitalizacao==1?Boolean.TRUE:Boolean.FALSE;
	}

	public Boolean getDigitalizacao() {
		return digitalizacao==1?Boolean.TRUE:Boolean.FALSE;
	}

	public void setDigitalizacao(Boolean digitalizacao) {
		this.digitalizacao = digitalizacao?1:0;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getTempoArquivoCorrente() {
		return tempoArquivoCorrente;
	}

	public void setTempoArquivoCorrente(Integer tempoArquivoCorrente) {
		this.tempoArquivoCorrente = tempoArquivoCorrente;
	}

	public Boolean isArquivoPermanente() {
		return arquivoPermanente==1?Boolean.TRUE:Boolean.FALSE;
	}
	
	public Boolean getArquivoPermanente() {
		return arquivoPermanente==1?Boolean.TRUE:Boolean.FALSE;
	}

	public void setArquivoPermanente(Boolean arquivoPermanente) {
		this.arquivoPermanente = arquivoPermanente?1:0;
	}

	public Integer getTempoArquivoIntermediario() {
		return tempoArquivoIntermediario;
	}

	public void setTempoArquivoIntermediario(Integer tempoArquivoIntermediario) {
		this.tempoArquivoIntermediario = tempoArquivoIntermediario;
	}

	public String getTemporaliedadeSelecionada() {
		return temporaliedadeSelecionada;
	}

	public void setTemporaliedadeSelecionada(String temporaliedadeSelecionada) {
		this.temporaliedadeSelecionada = temporaliedadeSelecionada;
	}
}
