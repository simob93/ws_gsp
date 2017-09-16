package it.gspRiva.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Formula;

@XmlRootElement
@Entity
@Table(name = "iscritto_corso")
public class IscrittoCorso extends EntityBase  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3118134249955694252L;

	/**
	 * 
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="IDCORSO")
	private Corso corso;
	
	@ManyToOne
	@JoinColumn(name="IDANAGRAFICA")
	private Anagrafica anagrafica;
	
	@ManyToOne
	@JoinColumn(name="IDANAGRAFICA_CORSO")
	private AnagraficaCorso anagraficaCorso;
	
	@Formula("(SELECT CONCAT(op.NOME,' ', op.COGNOME) FROM operatori AS op INNER JOIN anagrafica_corso AS anaCorso ON anaCorso.IDOPERATORE = op.ID LIMIT 1)")
	private String operatoreNominativo;
	
	@Column(name = "saldo")
	private String saldo;
	
	@Column(name = "quota")
	private Float quota;

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Corso getCorso() {
		return corso;
	}


	public void setCorso(Corso corso) {
		this.corso = corso;
	}

	public Anagrafica getAnagrafica() {
		return anagrafica;
	}


	public void setAnagrafica(Anagrafica anagrafica) {
		this.anagrafica = anagrafica;
	}


	public AnagraficaCorso getAnagraficaCorso() {
		return anagraficaCorso;
	}


	public void setAnagraficaCorso(AnagraficaCorso anagraficaCorso) {
		this.anagraficaCorso = anagraficaCorso;
	}


	public String getOperatoreNominativo() {
		return operatoreNominativo;
	}


	public void setOperatoreNominativo(String operatoreNominativo) {
		this.operatoreNominativo = operatoreNominativo;
	}


	public String getSaldo() {
		return saldo;
	}


	public void setSaldo(String saldo) {
		this.saldo = saldo;
	}


	public Float getQuota() {
		return quota;
	}


	public void setQuota(Float quota) {
		this.quota = quota;
	}

}