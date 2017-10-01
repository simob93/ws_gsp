package it.gspRiva.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class IscrittiPrint {
	
	private String nominativo;
	private Date dataIscrizione;
	private Date dataNascita;
	private Boolean acconto;
	private Boolean certificatoMedico;
	private Boolean assicurazione;
	
	private Boolean saldo;
	private Float quota;
	
	public IscrittiPrint(String nominativo,  Date dataIscrizione, Date dataNascita, Boolean certificatoMedico, Boolean assicurazione, Boolean saldo, Boolean acconto, Float quota) {
		
		this.nominativo = nominativo;
		this.dataIscrizione = dataIscrizione;
		this.dataNascita = dataNascita;
		this.acconto = acconto;
		this.assicurazione = assicurazione;
		this.saldo = saldo;
		this.quota = quota;
		this.certificatoMedico = certificatoMedico;
	}
	
	
	public IscrittiPrint() {
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


	public Boolean getSaldo() {
		return saldo;
	}


	public void setSaldo(Boolean saldo) {
		this.saldo = saldo;
	}


	public Float getQuota() {
		return quota;
	}


	public void setQuota(Float quota) {
		this.quota = quota;
	}


	
}
