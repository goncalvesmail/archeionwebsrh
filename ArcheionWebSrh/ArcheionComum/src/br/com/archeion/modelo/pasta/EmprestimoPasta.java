package br.com.archeion.modelo.pasta;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.archeion.modelo.AbstractTO;

@Entity
@Table(name = "TB_EMPRESTIMO_PASTA")
public class EmprestimoPasta extends AbstractTO implements Serializable {

	private static final long serialVersionUID = -4695059059889589442L;

	@Id
	@Column(name = "ID_EMPRESTIMO_PASTA")
	@GeneratedValue(strategy=GenerationType.AUTO)	
	private Long id;
		
	@OneToOne(cascade={CascadeType.REFRESH})
	//@JoinColumn(name="ID_PASTA")  
	private Pasta pasta;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_DEVOLUCAO")
	private Date dataDevolucao;
	
	public EmprestimoPasta() {
	}

	public EmprestimoPasta(Pasta pasta) {
		this.pasta = pasta;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Pasta getPasta() {
		return pasta;
	}

	public void setPasta(Pasta pasta) {
		this.pasta = pasta;
	}

	public Date getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(Date dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}
	
	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append("EMP. PASTA[ID:");
		buff.append(this.id);
		buff.append(",");
		
		buff.append("PASTA:");
		buff.append(this.pasta.getTitulo());
		
		buff.append("]");
		
		return buff.toString();
	}

}
