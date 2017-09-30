package it.gspRiva.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Type;

@XmlRootElement
@Entity
@Table(name="calendar")
public class Calendar  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5146368935300149409L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	@Column(name = "idCorso")
	private Integer idCorso;
	
	@Column(name="data")
	@Type(type="date")
	private Date data;
	
	@Column(name = "Descrizione")
	private String descrizione;

	public Integer getIdCorso() {
		return idCorso;
	}

	public void setIdCorso(Integer idCorso) {
		this.idCorso = idCorso;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
}
