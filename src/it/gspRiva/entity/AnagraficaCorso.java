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

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Type;

import it.gspRiva.utils.Controlli;

@XmlRootElement
@Entity
@Table(name = "anagrafica_corso")
public class AnagraficaCorso extends EntityBase implements Serializable {
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
	
	@Column(name="DATA")
	@Type(type="date")
	private Date data;
	
	@Column(name="IDOPERATORE")
	private Integer idOperatore;
	
	@Column(name="IDANAGRAFICA")
	private Integer idAnagrafica;
	
	@Column(name="NRLEZIONI")
	private Integer numeroLezioni;
	
	@Column(name="MINLEZIONI")
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
	
	@Column(name="ASSICURAZIONE")
	private String assicurazione;
	
	@Column(name="CERTIFICATOMEDICO")
	private String certificatoMedico;
	
	@Column(name="ACCONTO")
	private String acconto;
	
	@Column(name="INSERITO")
	private String inserito;
	
	@Column(name="DATAINIZIO")
	@Type(type="date")
	private Date dataInizio;
	
	@Column(name="DATAFINE")
	@Type(type="date")
	private Date dataFine;
	
	@Column(name="SCADENZACERTIFICATO")
	@Type(type="date")
	private Date scadenzaCertificato;
	
	@Column(name="NOTE")
	private String note;
	
	@Formula("(SELECT CONCAT(op.NOME,' ', op.COGNOME) FROM operatori AS op  WHERE op.ID = IDOPERATORE LIMIT 1)")
	private String operatoreNominativo;
	
	@Formula("(SELECT CONCAT(an.NOME,' ', an.COGNOME) FROM anagrafica AS an INNER JOIN anagrafica_corso AS anaCorso ON anaCorso.IDANAGRAFICA = an.ID WHERE anaCorso.ID = ID)")
	private String nominativo;

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

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Integer getIdOperatore() {
		return idOperatore;
	}

	public void setIdOperatore(Integer idOperatore) {
		this.idOperatore = idOperatore;
	}

	public Integer getIdAnagrafica() {
		return idAnagrafica;
	}

	public void setIdAnagrafica(Integer idAnagrafica) {
		this.idAnagrafica = idAnagrafica;
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

	public String getAssicurazione() {
		return assicurazione;
	}

	public void setAssicurazione(String assicurazione) {
		this.assicurazione = assicurazione;
	}

	public String getCertificatoMedico() {
		return certificatoMedico;
	}

	public void setCertificatoMedico(String certificatoMedico) {
		this.certificatoMedico = certificatoMedico;
	}

	public String getAcconto() {
		return acconto;
	}

	public void setAcconto(String acconto) {
		this.acconto = acconto;
	}

	public String getInserito() {
		if (Controlli.isEmptyString(inserito)) {
			inserito = "F";
		}
		return inserito;
	}

	public void setInserito(String inserito) {
		this.inserito = inserito;
	}

	public String getNominativo() {
		return nominativo;
	}

	public void setNominativo(String nominativo) {
		this.nominativo = nominativo;
	}

	public Date getScadenzaCertificato() {
		return scadenzaCertificato;
	}

	public void setScadenzaCertificato(Date scadenzaCertificato) {
		this.scadenzaCertificato = scadenzaCertificato;
	}

	public Date getDataInizio() {
		return dataInizio;
	}

	public void setDataIinzio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}

	public Date getDataFine() {
		return dataFine;
	}

	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}
	
}