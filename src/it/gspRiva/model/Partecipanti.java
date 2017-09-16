package it.gspRiva.model;

public class Partecipanti {
	private Integer idAnagraficaCorso;
	private Integer idAnagrafica;
	private Integer id;
	private String saldo;
	private Float quota;
	private String nominativo;
	private String acconto;
	private String deletedData;
	
	public Integer getIdAnagraficaCorso() {
		return idAnagraficaCorso;
	}
	public void setIdAnagraficaCorso(Integer idAnagraficaCorso) {
		this.idAnagraficaCorso = idAnagraficaCorso;
	}
	public String getSaldo() {
		return saldo;
	}
	public void setSaldo(String saldo) {
		this.saldo = saldo;
	}
	public Float getQuota() {
		return quota;
	}
	public void setQuota(Float quota) {
		this.quota = quota;
	}
	public Integer getIdAnagrafica() {
		return idAnagrafica;
	}
	public void setIdAnagrafica(Integer idAnagrafica) {
		this.idAnagrafica = idAnagrafica;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNominativo() {
		return nominativo;
	}
	public void setNominativo(String nominativo) {
		this.nominativo = nominativo;
	}
	public String getAcconto() {
		return acconto;
	}
	public void setAcconto(String acconto) {
		this.acconto = acconto;
	}
	public String getDeletedData() {
		return deletedData;
	}
	public void setDeletedData(String deletedData) {
		this.deletedData = deletedData;
	}
}
