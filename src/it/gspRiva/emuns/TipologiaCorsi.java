package it.gspRiva.emuns;

public enum TipologiaCorsi {
	
	INDIVIDUALE(1, "Individuale"),
	GRUPPO(2, "Gruppo"),
	DISABILI(3, "disabili"),
	APSS(4, "Apss"),
	RISERVATO(5, "Riservato");

	private Integer id;
	private String descrizione;

	TipologiaCorsi(Integer id, String descrizione) {
		this.id = id;
		this.descrizione = descrizione;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
}
