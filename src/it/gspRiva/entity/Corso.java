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

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Type;

@XmlRootElement
@Entity
@Table(name="corso")
public class Corso extends EntityBase implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	@Column(name="TIPOLOGIA")
	private Integer tipologia;
	
	@Column(name="DAL")
	@Type(type="date")
	private Date dal;
	
	@Column(name="al")
	@Type(type="date")
	private Date al;
	
	@Column(name="IDOPERATORE")
	private Integer idOperatore;
	
	@Column(name="IDISTRUTTORE")
	private Integer idIstruttore;
	
	@Column(name="ORADAL")
	@Type(type="time")
	private Date oraDal;
	
	@Column(name="ORAAL")
	@Type(type="time")
	private Date oraAl;
	
	@Column(name="NUMEROLEZIONI")
	private Integer numeroLezioni;
	
	@Column(name="MINUTILEZIONI")
	private Integer minutiLezioni;
	
	@Column(name="LUNEDI")
	private String lunedi;
	
	@Column(name="MARTEDI")
	private String martedi;
	
	@Column(name="MERCOLEDI")
	private String mercoledi;
	
	@Column(name="GIOVEDI")
	private String giovedi;
	
	@Column(name="VENERDI")
	private String venerdi;
	
	@Column(name="SABATO")
	private String sabato;
	
	@Column(name="PERSONALIZZATO")
	private String personalizzato;
	
	@Column(name="CONVALIDATO")
	private String convalidato;
	
	@Column(name="DESCRIZIONE")
	private String descrizione;
	
	@Formula("(SELECT CONCAT(op.NOME,' ', op.COGNOME) FROM operatori AS op INNER JOIN anagrafica_corso AS anaCorso ON anaCorso.IDOPERATORE = op.ID LIMIT 1)")
	private String operatoreNominativo;
	
	@Formula("(SELECT CONCAT(i.NOME,' ', i.COGNOME) FROM istruttori AS i INNER JOIN corso AS c ON i.ID = c.IDISTRUTTORE WHERE c.ID = ID)")
	private String istruttoreNominativo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTipologia() {
		return tipologia;
	}

	public void setTipologia(Integer tipologia) {
		this.tipologia = tipologia;
	}

	public Date getDal() {
		return dal;
	}

	public void setDal(Date dal) {
		this.dal = dal;
	}

	public Date getAl() {
		return al;
	}

	public void setAl(Date al) {
		this.al = al;
	}

	public Integer getIdOperatore() {
		return idOperatore;
	}

	public void setIdOperatore(Integer idOperatore) {
		this.idOperatore = idOperatore;
	}

	public Integer getIdIstruttore() {
		return idIstruttore;
	}

	public void setIdIstruttore(Integer idIstruttore) {
		this.idIstruttore = idIstruttore;
	}

	public Date getOraDal() {
		return oraDal;
	}

	public void setOraDal(Date oraDal) {
		this.oraDal = oraDal;
	}

	public Date getOraAl() {
		return oraAl;
	}

	public void setOraAl(Date oraAl) {
		this.oraAl = oraAl;
	}

	public String getLunedi() {
		return lunedi;
	}

	public void setLunedi(String lunedi) {
		this.lunedi = lunedi;
	}

	public String getMartedi() {
		return martedi;
	}

	public void setMartedi(String martedi) {
		this.martedi = martedi;
	}

	public String getMercoledi() {
		return mercoledi;
	}

	public void setMercoledi(String mercoledi) {
		this.mercoledi = mercoledi;
	}

	public String getGiovedi() {
		return giovedi;
	}

	public void setGiovedi(String giovedi) {
		this.giovedi = giovedi;
	}

	public String getVenerdi() {
		return venerdi;
	}

	public void setVenerdi(String venerdi) {
		this.venerdi = venerdi;
	}

	public String getSabato() {
		return sabato;
	}

	public void setSabato(String sabato) {
		this.sabato = sabato;
	}

	public String getPersonalizzato() {
		return personalizzato;
	}

	public void setPersonalizzato(String personalizzato) {
		this.personalizzato = personalizzato;
	}

	
	public String getOperatoreNominativo() {
		return operatoreNominativo;
	}

	public void setOperatoreNominativo(String operatoreNominativo) {
		this.operatoreNominativo = operatoreNominativo;
	}

	public Integer getNumeroLezioni() {
		return numeroLezioni;
	}

	public void setNumeroLezioni(Integer numeroLezioni) {
		this.numeroLezioni = numeroLezioni;
	}

	public Integer getMinutiLezioni() {
		return minutiLezioni;
	}

	public void setMinutiLezioni(Integer minutiLezioni) {
		this.minutiLezioni = minutiLezioni;
	}

	public String getConvalidato() {
		if (this.convalidato == null) {
			this.convalidato = "F";
		}
		return convalidato;
	}

	public void setConvalidato(String convalidato) {
		if (convalidato == null) {
			convalidato = "F";
		}
		this.convalidato = convalidato;
	}

	public String getIstruttoreNominativo() {
		return istruttoreNominativo;
	}

	public void setIstruttoreNominativo(String istruttoreNominativo) {
		this.istruttoreNominativo = istruttoreNominativo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	/*public String getDescrCorsoById(Integer id) {
		String dscrCorso = "";
		for (TipologiaCorsi tipologiaCorsi : TipologiaCorsi.values()) {
			if (tipologiaCorsi.getId() == id) {
				dscrCorso = tipologiaCorsi.getDescrizione();
				break;
			}
		}
		return dscrCorso;
		
	}*/

}