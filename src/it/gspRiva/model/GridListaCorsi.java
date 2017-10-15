package it.gspRiva.model;

import it.gspRiva.entity.Corso;

public class GridListaCorsi  extends Corso{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7940891401088775015L;
	private Float totaleTariffa;
	private Integer partecipanti;
	private String deletedData;
	private String progrCorso;
	
	
	public GridListaCorsi(Corso corso) {
		this.setDal(corso.getDal());
		this.setAl(corso.getAl());
		this.setOraAl(corso.getOraAl());
		this.setOraDal(corso.getOraDal());
		this.setIdIstruttore(corso.getIdIstruttore());
		this.setIdOperatore(corso.getIdOperatore());
		this.setId(corso.getId());
		this.setLunedi(corso.getLunedi());
		this.setMartedi(corso.getMartedi());
		this.setMercoledi(corso.getMercoledi());
		this.setSabato(corso.getSabato());
		this.setGiovedi(corso.getGiovedi());
		this.setVenerdi(corso.getVenerdi());
		this.setPersonalizzato(corso.getPersonalizzato());
		this.setMinutiLezioni(corso.getMinutiLezioni());
		this.setNumeroLezioni(corso.getNumeroLezioni());
		this.setTipologia(corso.getTipologia());
		this.setConvalidato(corso.getConvalidato());
		this.setIstruttoreNominativo(corso.getIstruttoreNominativo());
		this.setDescrizione(corso.getDescrizione());
		this.setDescrTipologia(corso.getDescrTipologia());
		this.setDeletedData(corso.getDeletedData());
		this.setOperatoreNominativo(corso.getOperatoreNominativo());
		this.setProgrCorso(corso.getProgrCorso());
	}
	
	public Float getTotaleTariffa() {
		return totaleTariffa;
	}
	public void setTotaleTariffa(Float totaleTariffa) {
		this.totaleTariffa = totaleTariffa;
	}
	public Integer getPartecipanti() {
		return partecipanti;
	}
	public void setPartecipanti(Integer partecipanti) {
		this.partecipanti = partecipanti;
	}

	public String getDeletedData() {
		return deletedData;
	}

	public void setDeletedData(String deletedData) {
		this.deletedData = deletedData;
	}

	public String getProgrCorso() {
		return progrCorso;
	}

	public void setProgrCorso(String progrCorso) {
		this.progrCorso = progrCorso;
	}
	
}
