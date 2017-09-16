package it.gspRiva.model;

import java.util.List;

import it.gspRiva.entity.Corso;

public class ModelRegistrazioneCorso {
	
	
	private Corso corso;
	private List<Partecipanti> partecipanti;
	
	public Corso getCorso() {
		return corso;
	}
	public void setCorso(Corso corso) {
		this.corso = corso;
	}
	public List<Partecipanti> getPartecipanti() {
		return partecipanti;
	}
	public void setPartecipanti(List<Partecipanti> partecipanti) {
		this.partecipanti = partecipanti;
	}
	
	
}
