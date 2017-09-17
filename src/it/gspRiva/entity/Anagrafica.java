package it.gspRiva.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Type;

@XmlRootElement
@Entity
@Table(name = "anagrafica")
public class Anagrafica extends EntityBase  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	@Column(name="NOME")
	private String nome;
	
	@Column(name="COGNOME")
	private String cognome;
	
	@Column(name="DATANASCITA")
	@Type(type="date")
	private Date dataNascita;
	
	@Column(name="LUOGONASCITA")
	private String luogoNascita;
	
	@Column(name="INDIRIZZO")
	private String indirizzo;
	
	@Column(name="CAP")
	private Integer cap;
	
	@Column(name="CITTA")
	private String citta;
	
	@Column(name="TELEFONO")
	private String telefono;
	
	@Column(name="COMUNERESIDENZA")
	private String comuneResidenza;
	
	@Column(name="NOMEGENITORE")
	private String nomeGenitore;
	
	@Column(name="CODICEFISCALE")
	private String codiceFiscale;
	
	@Column(name="CODICEFISCALEGENITORE")
	private String codiceFiscaleGenitore;
	
	@Column(name="IDOPERATORE") 
	private Integer idOperatore;
	
	
	@Formula("(SELECT CONCAT(op.NOME,' ', op.COGNOME) FROM operatori AS op INNER JOIN anagrafica AS ana ON ana.IDOPERATORE = op.ID LIMIT 1)")
	private String operatoreNominativo;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getLuogoNascita() {
		return luogoNascita;
	}

	public void setLuogoNascita(String luogoNascita) {
		this.luogoNascita = luogoNascita;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public Integer getCap() {
		return cap;
	}

	public void setCap(Integer cap) {
		this.cap = cap;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getNomeGenitore() {
		return nomeGenitore;
	}

	public void setNomeGenitore(String nomeGenitore) {
		this.nomeGenitore = nomeGenitore;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public Integer getIdOperatore() {
		return idOperatore;
	}

	public void setIdOperatore(Integer idOperatore) {
		this.idOperatore = idOperatore;
	}
	
	public String getOperatoreNominativo() {
		return operatoreNominativo;
	}

	public String getComuneResidenza() {
		return comuneResidenza;
	}

	public void setComuneResidenza(String comuneResidenza) {
		this.comuneResidenza = comuneResidenza;
	}

	public String getCodiceFiscaleGenitore() {
		return codiceFiscaleGenitore;
	}

	public void setCodiceFiscaleGenitore(String codiceFiscaleGenitore) {
		this.codiceFiscaleGenitore = codiceFiscaleGenitore;
	}

	
}