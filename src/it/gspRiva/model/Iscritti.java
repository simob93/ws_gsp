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
	
	public Iscritti(String nominativo,  Date dataIscrizione, Date dataNascita, Boolean maggiorenne, Boolean acconto,  Boolean certificatoMedico, Boolean assicurazione, Integer idAnagrafica, Integer idAnagraficaCorso) {
		
		this.nominativo = nominativo;
		this.dataIscrizione = dataIscrizione;
		this.dataNascita = dataNascita;
		this.maggiorenne = maggiorenne;
		this.acconto = acconto;
		this.assicurazione = assicurazione;
		this.idAnagrafica = idAnagrafica;
		this.idAnagraficaCorso = idAnagraficaCorso;
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
	
	
}
