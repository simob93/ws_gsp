package it.gspRiva.model;

import java.util.List;

import it.gspRiva.emuns.TipologiaCorsi;
import it.gspRiva.entity.AnagraficaCorso;
import it.gspRiva.entity.Corso;

public class ModelIscrittiCorsi extends Corso {
	
	/**
	 *  model utilizzato per la bacheca corsi
	 */
	private static final long serialVersionUID = -3808555443613851474L;

	
	public ModelIscrittiCorsi(Corso corso) {
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
		this.setGiovedi(corso.getGiovedi());
		this.setVenerdi(corso.getVenerdi());
		this.setPersonalizzato(corso.getPersonalizzato());
		this.setMinutiLezioni(corso.getMinutiLezioni());
		this.setNumeroLezioni(corso.getNumeroLezioni());
		this.setDescrTipologia(corso.getTipologia());
		this.setTipologia(corso.getTipologia());
		this.setConvalidato(corso.getConvalidato());
		this.setIstruttoreNominativo(corso.getIstruttoreNominativo());
		this.setDeletedData(corso.getDeletedData());
		this.setDescrizione(corso.getDescrizione());
	}
	
	private String descrTipologia; 
	private String deletedData; 
	private List<AnagraficaCorso> anagraficaCorso;
	
	public List<AnagraficaCorso> getAnagraficaCorso() {
		return anagraficaCorso;
	}
	
	public void setAnagraficaCorso(List<AnagraficaCorso> anagraficaCorso) {
		this.anagraficaCorso = anagraficaCorso;
	}

	public String getDescrTipologia() {
		return descrTipologia;
		
	}

	public void setDescrTipologia(Integer tipologia) {
		this.descrTipologia = this.getDescrCorsoById(tipologia);
	}
	
	public String getDescrCorsoById(Integer id) {
		String dscrCorso = "";
		for (TipologiaCorsi tipologiaCorsi : TipologiaCorsi.values()) {
			if (tipologiaCorsi.getId() == id) {
				dscrCorso = tipologiaCorsi.getDescrizione();
				break;
			}
		}
		return dscrCorso;
		
	}

	public String getDeletedData() {
		return deletedData;
	}

	public void setDeletedData(String deletedData) {
		this.deletedData = deletedData;
	}

}
