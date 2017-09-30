package it.gspRiva.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Iscritti {
	
	private Integer idAnagrafica;
	private Integer idAnagraficaCorso;
	private String nominativo;
	private Date dataIscrizione;
	private Date dataNascita;
	private Boolean maggiorenne;
	private Boolean acconto;
	private Boolean certificatoMedico;
	private Boolean assicurazione;
	private Integer tipologia;
	
	private String lunedi;
	private String martedi;
	private String mercoledi;
	private String giovedi;
	private String venerdi;
	private String sabato;
	private String personalizzato;
	private Integer numeroLezioni;
	private Integer minutiLezioni;
	
	public Iscritti(String nominativo,  Date dataIscrizione, Date dataNascita, Boolean maggiorenne, Boolean acconto,  Boolean certificatoMedico, Boolean assicurazione, Integer idAnagrafica, Integer idAnagraficaCorso, Integer tipologia, String lunedi, String martedi, String mercoledi, String giovedi, String venerdi, String sabato, String personalizzato, Integer numeroLezioni, Integer minutiLezioni) {
		
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
	public Boolean getMaggiorenne() {
		return maggiorenne;
	}
	public void setMaggiorenne(Boolean maggiorenne) {
		this.maggiorenne = maggiorenne;
	}
	public Boolean getAcconto() {
		return acconto;
	}
	public void setAcconto(Boolean acconto) {
		this.acconto = acconto;
	}
	public Boolean getCertificatoMedico() {
		return certificatoMedico;
	}
	public void setCertificatoMedico(Boolean certificatoMedico) {
		this.certificatoMedico = certificatoMedico;
	}
	public Boolean getAssicurazione() {
		return assicurazione;
	}


	public void setAssicurazione(Boolean assicurazione) {
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
	
	
}
