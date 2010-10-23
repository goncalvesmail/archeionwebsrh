package br.com.archeion.modelo.pasta;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.archeion.modelo.AbstractTO;
import br.com.archeion.modelo.SituacaoExpurgo;
import br.com.archeion.modelo.caixa.Caixa;
import br.com.archeion.modelo.itemdocumental.ItemDocumental;
import br.com.archeion.modelo.local.Local;

/**
 * Classe que representa uma Pasta
 * @author SInforme
 */
@Entity
@Table(name = "TB_PASTA")
public class Pasta extends AbstractTO implements Serializable {

	private static final long serialVersionUID = -3321067683202053657L;
	
	public Pasta() {
		this.local = new Local();
		this.itemDocumental = new ItemDocumental();
		this.situacao = SituacaoExpurgo.ATIVA;
	}

	/**
	 * Identificação única
	 */
	@Id
	@Column(name = "ID_PASTA")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	/**
	 * Local da pasta
	 */
	@ManyToOne(fetch=FetchType.EAGER)  
	@JoinColumn(name="ID_LOCAL")
	private Local local;
	
	/**
	 * Item documental contido na pasta
	 */
	@ManyToOne(fetch=FetchType.EAGER)  
	@JoinColumn(name="ID_ITEM_DOCUMENTAL")
	private ItemDocumental itemDocumental;
	
	/**
	 * Título da pasta
	 */
	@Column(name = "NM_TITULO")
	private String titulo;
	
	/**
	 * Descrição da pasta
	 */
	@Column(name = "NM_DESCRICAO")
	private String descricao;
	
	/**
	 * Número de protocolo que identifica a pasta
	 */
	@Column(name = "NM_NUMERO_PROTOCOLO")
	private String numeroProtocolo;
	
	/**
	 * Caixeta onde fica a pasta
	 */
	@Column(name = "NM_CAIXETA")
	private String caixeta;
	
	/**
	 * Data de abertura da pasta
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_ABERTURA")
	private Date dataAbertura;
	
	/**
	 * Data de referencia da pasta
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_REFERENCIA")
	private Date dataReferencia;
	
	/**
	 * Data limite inicial para buscas
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_DATA_LIMITE_INICIAL")
	private Date limiteDataInicial;
	
	/**
	 * Data limite final para buscas
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_DATA_LIMITE_FINAL")
	private Date limiteDataFinal;
	
	/**
	 * Número limite inicial para buscas
	 */
	@Column(name = "NU_NUMERO_LIMITE_INICIAL")
	private Integer limiteNumeroInicial;
	
	/**
	 * Número limite final para buscas
	 */
	@Column(name = "NU_NUMERO_LIMITE_FINAL")
	private Integer limiteNumeroFinal;
	
	/**
	 * Nome limite inicial para buscas
	 */
	@Column(name = "NM_NOME_LIMITE_INICIAL")
	private String limiteNomeInicial;
	
	/**
	 * Nome limite final para buscas
	 */
	@Column(name = "NM_NOME_LIMITE_FINAL")
	private String limiteNomeFinal;
	
	/**
	 * Observações sobre a pasta
	 */
	@Column(name = "TX_OBSERVACAO")
	private String observacao;
	
	/**
	 * Data de previsão de recolhimento da pasta
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_PREVISAO_RECOLHIMENTO")
	private Date previsaoRecolhimento;
	
	/**
	 * Data de previsão de expurgo
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_PREVISAO_EXPURGO")
	private Date previsaoExpurgo;
	
	/**
	 * Caixa da pasta
	 */
	@ManyToOne(fetch=FetchType.EAGER, cascade={CascadeType.REFRESH})
	@JoinColumn(name="ID_CAIXA")
	private Caixa caixa;
	
	/**
	 * Data de Expurgo
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_EXPURGO")
	private Date dataExpurgo;	
	
	/**
	 * Situação da pasta
	 */
	@Column(name = "CS_SITUACAO_PASTA")
	private SituacaoExpurgo situacao;
	
	/**
	 * campo temporario utilizado para realizar as bucas nas pastas
	 */
	@Transient
	private boolean buscaPorCaixeta;
	
	/**
	 * campo temporário utilizado pra dizer se a pasta foi selecionada
	 */
	@Transient
	private int selecionado;

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

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getCaixeta() {
		return caixeta;
	}

	public void setCaixeta(String caixeta) {
		this.caixeta = caixeta;
	}

	public Date getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(Date dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public Date getDataReferencia() {
		return dataReferencia;
	}

	public void setDataReferencia(Date dataReferencia) {
		this.dataReferencia = dataReferencia;
	}

	public Date getLimiteDataInicial() {
		return limiteDataInicial;
	}

	public void setLimiteDataInicial(Date limiteDataInicial) {
		this.limiteDataInicial = limiteDataInicial;
	}

	public Date getLimiteDataFinal() {
		return limiteDataFinal;
	}

	public void setLimiteDataFinal(Date limiteDataFinal) {
		this.limiteDataFinal = limiteDataFinal;
	}

	public Integer getLimiteNumeroInicial() {
		return limiteNumeroInicial;
	}

	public void setLimiteNumeroInicial(Integer limiteNumeroInicial) {
		this.limiteNumeroInicial = limiteNumeroInicial;
	}

	public Integer getLimiteNumeroFinal() {
		return limiteNumeroFinal;
	}

	public void setLimiteNumeroFinal(Integer limiteNumeroFinal) {
		this.limiteNumeroFinal = limiteNumeroFinal;
	}

	public String getLimiteNomeInicial() {
		return limiteNomeInicial;
	}

	public void setLimiteNomeInicial(String limiteNomeInicial) {
		this.limiteNomeInicial = limiteNomeInicial;
	}

	public String getLimiteNomeFinal() {
		return limiteNomeFinal;
	}

	public void setLimiteNomeFinal(String limiteNomeFinal) {
		this.limiteNomeFinal = limiteNomeFinal;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Date getPrevisaoRecolhimento() {
		return previsaoRecolhimento;
	}

	public void setPrevisaoRecolhimento(Date previsaoRecolhimento) {
		this.previsaoRecolhimento = previsaoRecolhimento;
	}

	public Date getPrevisaoExpurgo() {
		return previsaoExpurgo;
	}

	public void setPrevisaoExpurgo(Date previsaoExpurgo) {
		this.previsaoExpurgo = previsaoExpurgo;
	}

	public Caixa getCaixa() {
		return caixa;
	}

	public void setCaixa(Caixa caixa) {
		this.caixa = caixa;
	}

	public Date getDataExpurgo() {
		return dataExpurgo;
	}

	public void setDataExpurgo(Date dataExpurgo) {
		this.dataExpurgo = dataExpurgo;
	}

	public SituacaoExpurgo getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoExpurgo situacao) {
		this.situacao = situacao;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append("PASTA[ID:");
		buff.append(this.id);
		buff.append(",");
		
		buff.append("TITULO:");
		buff.append(this.titulo);		
		buff.append(",");
		
		buff.append("DESCRICAO:");
		buff.append(this.descricao);		
		buff.append(",");
		
		buff.append("LOCAL:");
		buff.append(this.local.getNome());		
		buff.append(",");
		
		buff.append("ABERTURA:");
		buff.append(this.dataAbertura);	
		
		buff.append("]");
		
		return buff.toString();
	}

	public String getNumeroProtocolo() {
		return numeroProtocolo;
	}

	public void setNumeroProtocolo(String numeroProtocolo) {
		this.numeroProtocolo = numeroProtocolo;
	}

	public boolean isBuscaPorCaixeta() {
		return buscaPorCaixeta;
	}

	public void setBuscaPorCaixeta(boolean buscaPorCaixeta) {
		this.buscaPorCaixeta = buscaPorCaixeta;
	}

	public int getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(int selecionado) {
		this.selecionado = selecionado;
	}
	
}
