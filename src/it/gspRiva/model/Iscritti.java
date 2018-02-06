package it.gspRiva.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
public class Iscritti {
	
	private Integer idAnagrafica;
	private Integer idAnagraficaCorso;
	private String nominativo;
	private Date dataIscrizione;
	private Date dataNascita;
	private String maggiorenne;
	private String acconto;
	private String certificatoMedico;
	private String assicurazione;
	private Integer tipologia;
	private Date dataInizio;
	private Date dataFine;
	private String lunedi;
	private String martedi;
	private String mercoledi;
	private String giovedi;
	private String venerdi;
	private String sabato;
	private String personalizzato;
	private Integer numeroLezioni;
	private Integer minutiLezioni;
	
	public Iscritti(String nominativo,  Date dataIscrizione, Date dataNascita, String maggiorenne, String acconto,  String certificatoMedico, String assicurazione, Integer idAnagrafica, Integer idAnagraficaCorso, Integer tipologia, String lunedi, String martedi, String mercoledi, String giovedi, String venerdi, String sabato, String personalizzato, Integer numeroLezioni, Integer minutiLezioni, Date dataInizio, Date dataFine) {
		
		this.nominativo = nominativo;
		this.dataIscrizione = dataIscrizione;
		this.dataNascita = dataNascita;
		this.maggiorenne = maggiorenne;
		this.acconto = acconto;
		this.assicurazione = assicurazione;
		this.idAnagrafica = idAnagrafica;
		this.idAnagraficaCorso = idAnagraficaCorso;
		this.tipologia = tipologia;
		this.lunedi = lunedi;
		this.martedi = martedi;
		this.mercoledi = mercoledi;
		this.giovedi = giovedi;
		this.venerdi = venerdi;
		this.sabato = sabato;
		this.personalizzato = personalizzato;
		this.numeroLezioni = numeroLezioni;
		this.minutiLezioni = minutiLezioni;
		this.dataFine = dataFine;
		this.dataInizio = dataInizio;
	}
	
	
	public Iscritti() {
	}


	public String getNominativo() {
		return nominativo;
	}
	public void setNominativo(String nominativo) {
		this.nominativo = nominativo;
	}
	public Date getDataIscrizione() {
		return dataIscrizione;
	}
	public void setDataIscrizione(Date dataIscrizione) {
		this.dataIscrizione = dataIscrizione;
	}
	public Date getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}
	public String getMaggiorenne() {
		return maggiorenne;
	}
	public void setMaggiorenne(String maggiorenne) {
		this.maggiorenne = maggiorenne;
	}
	public String getAcconto() {
		return acconto;
	}
	public void setAcconto(String acconto) {
		this.acconto = acconto;
	}
	public String getCertificatoMedico() {
		return certificatoMedico;
	}
	public void setCertificatoMedico(String certificatoMedico) {
		this.certificatoMedico = certificatoMedico;
	}
	public String getAssicurazione() {
		return assicurazione;
	}


	public void setAssicurazione(String assicurazione) {
		this.assicurazione = assicurazione;
	}


	public Integer getIdAnagrafica() {
		return idAnagrafica;
	}


	public void setIdAnagrafica(Integer idAnagrafica) {
		this.idAnagrafica = idAnagrafica;
	}


	public Integer getIdAnagraficaCorso() {
		return idAnagraficaCorso;
	}


	public void setIdAnagraficaCorso(Integer idAnagraficaCorso) {
		this.idAnagraficaCorso = idAnagraficaCorso;
	}


	public Integer getTipologia() {
		return tipologia;
	}


	public void setTipologia(Integer tipologia) {
		this.tipologia = tipologia;
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


	public Date getDataInizio() {
		return dataInizio;
	}


	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}


	public Date getDataFine() {
		return dataFine;
	}


	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}
	
	
}
