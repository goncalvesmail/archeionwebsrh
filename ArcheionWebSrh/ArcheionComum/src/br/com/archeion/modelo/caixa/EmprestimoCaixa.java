package br.com.archeion.modelo.caixa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.archeion.modelo.AbstractTO;
import br.com.archeion.modelo.usuario.Usuario;

/**
 * Classe que realiza o empréstimos de caixa
 * @author Sinforme
 */
@Entity
@Table(name = "TB_EMPRESTIMO_CAIXA")
public class EmprestimoCaixa extends AbstractTO implements Serializable {

	private static final long serialVersionUID = -4695059059889589442L;

	/**
	 * Identificação única
	 */
	@Id
	@Column(name = "ID_EMPRESTIMO_CAIXA")
	@GeneratedValue(strategy=GenerationType.AUTO)	
	private Long id;
	
	/**
	 * Usuário responsável pelo empréstimo
	 */
	@ManyToOne(optional=false) 
    @JoinColumn(referencedColumnName="ID_USUARIO",name = "ID_USUARIO_RESPONSAVEL", nullable=false)
	private Usuario responsavel;
	
	/**
	 * Usuário que solicitou o empréstimo 
	 */
	@ManyToOne(optional=false) 
    @JoinColumn(referencedColumnName="ID_USUARIO",name = "ID_USUARIO_SOLICITANTE", nullable=true)
	private Usuario solicitante;
	
	/**
	 * Usuário que solicita e não é cadastrado
	 */
	@Column(name = "TX_SOLICITANTE_EXTERNO")
	private String solicitanteExterno;
	
	/**
	 * A caixa que esta sendo desmontada
	 */
	@ManyToOne(cascade={CascadeType.REFRESH})
	@JoinColumn(name="ID_CAIXA")  
	private Caixa caixa;
	
	/**
	 * A data do empréstimo
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_EMPRESTIMO")
	private Date dataEmprestimo;
	
	/**
	 * Previsão para devolução
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_PREVISAO_DEVOLUCAO")
	private Date previsaoDevolucao;
	
	/**
	 * Data da devolução
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_DEVOLUCAO")
	private Date dataDevolucao;
	
	public EmprestimoCaixa() {
		this.responsavel = new Usuario();
		this.solicitante = new Usuario();
		this.caixa = new Caixa();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Usuario responsavel) {
		this.responsavel = responsavel;
	}

	public Usuario getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(Usuario solicitante) {
		this.solicitante = solicitante;
	}

	public String getSolicitanteExterno() {
		return solicitanteExterno;
	}

	public void setSolicitanteExterno(String solicitanteExterno) {
		this.solicitanteExterno = solicitanteExterno;
	}

	public Caixa getCaixa() {
		return caixa;
	}

	public void setCaixa(Caixa caixa) {
		this.caixa = caixa;
	}

	public Date getDataEmprestimo() {
		return dataEmprestimo;
	}

	public void setDataEmprestimo(Date dataEmprestimo) {
		this.dataEmprestimo = dataEmprestimo;
	}

	public Date getPrevisaoDevolucao() {
		return previsaoDevolucao;
	}

	public void setPrevisaoDevolucao(Date previsaoDevolucao) {
		this.previsaoDevolucao = previsaoDevolucao;
	}

	public Date getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(Date dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}
	
	public String getNomeSolicitante() {
		String nome = null;
		if ( solicitante == null ) nome = solicitanteExterno;
		else nome = solicitante.getNome();
		return nome;
	}
	
	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append("EMP. CAIXA[ID:");
		buff.append(this.id);
		buff.append(",");
		
		buff.append("CAIXA:");
		buff.append(this.caixa.getTitulo());
		buff.append(",");
		
		buff.append("RESPONSAVEL:");
		buff.append(this.responsavel.getNome());
		buff.append(",");
		
		buff.append("SOLICITANTE:");
		buff.append(this.getSolicitante());
		buff.append("]");
		
		return buff.toString();
	}
	
}
