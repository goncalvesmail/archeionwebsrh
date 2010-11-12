package br.com.archeion.modelo.log;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.archeion.modelo.AbstractTO;

/**
 * Classe responsavel por salvar os logs
 * @author SInforme
 */
@Entity
@Table(name = "TB_LOG")
public class Log extends AbstractTO implements Serializable  {

	private static final long serialVersionUID = -2714408210698820985L;

	/**
	 * Identificação única
	 */
	@Id
	@Column(name = "ID_LOG")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	/**
	 * Usuário responsável pela ação logada
	 */
	@Column(name = "NM_USUARIO")
	private String usuario;
	
	/**
	 * Ação logada
	 */
	@Column(name = "TX_ACAO")
	private String acao;
	
	/**
	 * Data do log
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DT_DATA")
	private Date data;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!super.equals(obj)) return false;
		if (!(obj instanceof Log)) return false;
		final Log other = (Log) obj;
		
		if (id == null && other.id != null) {
			return false;
		} 
		else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append("LOG[ID:");
		buff.append(this.id);
		buff.append(",");
		
		buff.append("ACAO:");
		buff.append(this.acao);
		buff.append(",");
		
		buff.append("DATA:");
		buff.append(this.data);
		buff.append("]");
		
		return buff.toString();
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
}
