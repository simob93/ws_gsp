package it.gspRiva.model;

import java.util.Date;

public class ModelCertificatoMedico {

	private boolean scaduto;
	private Date dataScadenza;
	
	
	public boolean getScaduto() {
		return scaduto;
	}
	public void setScaduto(boolean scaduto) {
		this.scaduto = scaduto;
	}
	public Date getDataScadenza() {
		return dataScadenza;
	}
	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}
	
}
