package it.gspRiva.manager;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.persistence.StoredProcedureQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import it.gspRiva.entity.Anagrafica;
import it.gspRiva.entity.AnagraficaCorso;
import it.gspRiva.entity.Corso;
import it.gspRiva.entity.IscrittoCorso;
import it.gspRiva.exception.MyException;
import it.gspRiva.model.GridListaCorsi;
import it.gspRiva.model.IscrittiPrint;
import it.gspRiva.model.ModelRegistrazioneCorso;
import it.gspRiva.model.Partecipanti;
import it.gspRiva.model.PrintSchedaCorso;
import it.gspRiva.model.ResponsePrint;
import it.gspRiva.utils.Controlli;
import it.gspRiva.utils.HibernateUtils;
import it.gspRiva.utils.PropertiesFile;
import it.gspRiva.utils.StandardUtils;

public class CorsoManager extends StdManager<Corso> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4622793186358068336L;
	
	@Override
	public boolean checkObjectForUpdate(Corso oggetto, List<String> messaggi) throws IOException {
		return true;
	}
	
	@Override
	public boolean checkCampiObbligatoriDelete(Corso oggetto, List<String> messaggi) throws IOException {
		return true;
	}


	@Override
	protected Class<Corso> getEntityClass() {
		return Corso.class;
	}
	
	@Override
	public void operationAfterInsert(Corso corso) throws IOException {
		executeStoreProcedure(corso.getDal(), corso.getAl(), corso.getId(), corso.getLunedi(), corso.getMartedi(), corso.getMercoledi(),corso.getGiovedi(), corso.getVenerdi(), corso.getSabato(), corso.getDescrizione());

	}
	@Override
	public void operationAfterUpdate(Corso corso) throws IOException {
		executeStoreProcedure(corso.getDal(), corso.getAl(), corso.getId(), corso.getLunedi(), corso.getMartedi(), corso.getMercoledi(),corso.getGiovedi(), corso.getVenerdi(), corso.getSabato(), corso.getDescrizione());

	}

	@Override
	public boolean checkObjectForInsert(Corso oggetto, List<String> messaggi) throws IOException {
		return true;
	}
	
	
	public List<Corso> list(boolean escludiConvalidati, boolean escludiAnnullati, String dal, String al, Integer tipologia, Integer idIstruttore) {
		Session session = null;
		Transaction tx = null;
		List<Corso> data = null;
		try {
			
			session = HibernateUtils.getSessionAnnotationFactory().openSession();
			tx = session.beginTransaction(); 
						
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Corso> query = builder.createQuery(Corso.class);
			
			Root<Corso> corso = query.from(Corso.class);
			query.select(corso);
			query.orderBy(builder.desc(corso.get("dal")));
			List<Predicate> predicates = new ArrayList<Predicate>();
			/* recuper i record che non sono annullatti  */
			if (escludiAnnullati) {
				predicates.add(builder.isNull(corso.get("deletedData")));
			}
			/* prendo solo quelli in attessa di essere convalidati  */
			if (escludiConvalidati) {
				predicates.add(
						builder.or(
								builder.equal(corso.get("convalidato"), "F"),
								builder.isNull(corso.get("convalidato"))
						)
				);
						
			}
			
			if (idIstruttore != null) {
				predicates.add(
					builder.equal(corso.get("idIstruttore"), idIstruttore)
				);
			}
			
			
			/* tipologia corso  */
			if (tipologia != null) {
				predicates.add(
						builder.equal(corso.get("tipologia"), tipologia)
				);
			}
			
			if(!Controlli.isEmptyString(dal) || !Controlli.isEmptyString(al)) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				if (!Controlli.isEmptyString(dal)) {
					predicates.add(builder.greaterThanOrEqualTo(corso.get("dal"), sdf.parse(dal)));
				}
				if (!Controlli.isEmptyString(al)) {
					predicates.add(builder.lessThanOrEqualTo(corso.get("al"), sdf.parse(al)));
				}
			}
			
			query.where(predicates.toArray(new Predicate[]{}));
			
			data = session.createQuery(query).getResultList();
			
			tx.commit();
			
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			
		} finally {
 			if (session != null) {
				session.close();
			}
		}
		return data;
	}
	/**
	 * ritorna un lista di corsi con allegato i suoi partecipanti usata per la dashboard corsi 
	 * @param annnullati 
	 * @param convalidati 
	 * @param tipologia 
	 * @param al 
	 * @param dal 
	 * @param  
	 * @return
	 * @throws MyException
	 */
	public List<GridListaCorsi>  listIscrittiByCorsi(String dal, String al, Integer tipologia, boolean escludiConvalidati, boolean escludiAnnullati, Integer idIstruttore) 
			throws MyException  {
		
		Session session = null;
		Transaction tx = null;
		List<GridListaCorsi> data = new ArrayList<GridListaCorsi>();
		try {
			session = HibernateUtils.getSessionAnnotationFactory().openSession();
			tx = session.beginTransaction(); 
			List<Corso> corsi = this.list(escludiConvalidati, escludiAnnullati, dal, al, tipologia, idIstruttore);
			
			double totale = 0;
			Set<IscrittoCorso> iscritti = null; 
			for (Corso c : corsi) {
				totale = 0;
				GridListaCorsi gridListaCorsi = new GridListaCorsi(c);
				iscritti = c.getIscrittoCorso();
				if ( iscritti != null && iscritti.size() > 0) {
					for (IscrittoCorso iscritto: iscritti) {
						totale += (iscritto.getQuota() == null ? 0 : iscritto.getQuota());
					}
					gridListaCorsi.setTotaleTariffa((float) totale);
					gridListaCorsi.setPartecipanti(iscritti.size());
				}
				data.add(gridListaCorsi);
			}
			tx.commit();
			 
		} catch (Exception e) {
			tx.rollback();
			throw new MyException(e.getMessage());
			
		} finally {
 			if (session != null) {
				session.close();
			}
		}
		return data;
	}
	/**
	 * 
	 * @param idCorso
	 * @return 
	 * @throws IOException
	 * @throws MyException
	 */
	public ModelRegistrazioneCorso listIscrittiByIdCorso(Integer idCorso)
			throws IOException, MyException {
		
		Corso corso = this.getById(idCorso).getData();
		ModelRegistrazioneCorso modelRegistrazioneCorso  = null;
		Partecipanti partecipanti = null;
		List<IscrittoCorso> listIscrittiCorso = null;
		List<Partecipanti> listPartecipanti = new ArrayList<Partecipanti>();
		
		if (corso != null) {
			modelRegistrazioneCorso = new ModelRegistrazioneCorso();
			IscrittiCorsoManager iscrittiCorsoManager = new IscrittiCorsoManager();
			corso.setIscrittoCorso(null);
			
			try {
				listIscrittiCorso = iscrittiCorsoManager.listIscrittiByIdCorso(corso.getId(), false);
			} catch (MyException e) {
				throw new MyException(e.getMessage());
			}
			
			for (IscrittoCorso iscrittoCorso : listIscrittiCorso) {
				partecipanti = new Partecipanti();
				partecipanti.setId(iscrittoCorso.getId());
				partecipanti.setIdAnagrafica(iscrittoCorso.getAnagrafica().getId());
				partecipanti.setIdAnagraficaCorso(iscrittoCorso.getAnagraficaCorso().getId());
				partecipanti.setNominativo(iscrittoCorso.getAnagraficaCorso().getNominativo());
				partecipanti.setAcconto(iscrittoCorso.getAnagraficaCorso().getAcconto());
				partecipanti.setDeletedData(iscrittoCorso.getDeletedData());
				partecipanti.setSaldo(iscrittoCorso.getSaldo());
				partecipanti.setQuota(iscrittoCorso.getQuota());
				listPartecipanti.add(partecipanti);
			}
			modelRegistrazioneCorso.setCorso(corso);
			modelRegistrazioneCorso.setPartecipanti(listPartecipanti);
			
		}
		
		return modelRegistrazioneCorso;
	}
	
	public Boolean convalida(Integer id) 
			throws MyException, IOException {
		
		Session session = null;
		Transaction tx = null;
		Boolean success = true;
		
		if(id == null) {
			throw new MyException(PropertiesFile.openPropertie().getProperty("id.invalid"));
		}
		try {
			
			session = HibernateUtils.getSessionAnnotationFactory().openSession();
			tx = session.beginTransaction(); 
			
			String hql = "UPDATE Corso As c SET c.convalidato = 'T' WHERE c.id = :id";
			Query query = session.createQuery(hql);
			query.setParameter("id", id);
			query.executeUpdate();
			tx.commit();
			 
		} catch (Exception e) {
			success = false;
			tx.rollback();
			e.printStackTrace();
			
		} finally {
 			if (session != null) {
				session.close();
			}
		}
		return success;
	}
	
	public void executeStoreProcedure(Date start, Date end, Integer idCorso, String lunedi, String martedi, String mercoledi, String giovedi, String venerdi, String sabato, String descrizione) {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtils.getSessionAnnotationFactory().openSession();
			tx = session.beginTransaction(); 
			StoredProcedureQuery query = session.createNamedStoredProcedureQuery("filldates");
			
			query.setParameter("dateStart", start);
			query.setParameter("dateEnd", end);
			query.setParameter("corsoId", idCorso);
			query.setParameter("lunedi", lunedi);
			query.setParameter("martedi", martedi);
			query.setParameter("mercoledi", mercoledi);
			query.setParameter("giovedi", giovedi);
			query.setParameter("venerdi", venerdi);
			query.setParameter("sabato", sabato);
			query.setParameter("descrizione", descrizione);
			query.execute();
			tx.commit();
			 
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			
		} finally {
 			if (session != null) {
				session.close();
			}
		}
	}

	public ModelRegistrazioneCorso registra(ModelRegistrazioneCorso registrazioneCorso)
			throws MyException {
		
		List<Partecipanti> listPartecipanti = registrazioneCorso.getPartecipanti();
		Corso corso = registrazioneCorso.getCorso();
		IscrittoCorso iscrittiCorso = null;
		
		/***************************************************************************
		 **********	DICHIARAZIONE DEI MANAGER DA UTILIZZARE*************************
		 ***************************************************************************/
		AnagraficaCorsoManager anagraficaCorsoManager = new AnagraficaCorsoManager();
		AnagraficaManager anagraficaManager = new AnagraficaManager();
		
		AnagraficaCorso anagraficaCorso = null;
		Anagrafica anagrafica = null;
		Session session = null;
		Transaction tx = null;
		boolean error = false;
		try {
			
			session = HibernateUtils.getSessionAnnotationFactory().openSession();
			tx = session.beginTransaction();
			Partecipanti tmpPartecipante = null;
			
			if (corso.getId() == null) {
				this.create(corso);
			} else {
				this.update(corso);
			}
			for (Partecipanti partecipanti : listPartecipanti) {
				
				if (Controlli.isEmptyString(partecipanti.getDeletedData())) {
					anagraficaCorso = anagraficaCorsoManager.getById(partecipanti.getIdAnagraficaCorso()).getData();
					iscrittiCorso = new IscrittoCorso();
					
					if (anagraficaCorso != null) {
						anagrafica = anagraficaManager.getById(anagraficaCorso.getIdAnagrafica()).getData();
						if (anagrafica != null) {
							
							iscrittiCorso.setAnagraficaCorso(anagraficaCorso);
							iscrittiCorso.setAnagrafica(anagrafica);
							iscrittiCorso.setCorso(corso);
							iscrittiCorso.setQuota(partecipanti.getQuota());
							iscrittiCorso.setSaldo(partecipanti.getSaldo());
							iscrittiCorso.setId(partecipanti.getId());
							session.saveOrUpdate(iscrittiCorso);
							
							anagraficaCorso.setInserito("T");
							session.update(anagraficaCorso);
							
						}
					}
				}
			}
			
			tx.commit();
			 
		} catch (Exception e) {
			tx.rollback();
			throw new MyException(e.getMessage());
		} finally {
 			if (session != null) {
				session.close();
			}
		}
		return registrazioneCorso;
	}
	

	public Partecipanti rimuoviPartecipante(Integer id) throws Exception {
		IscrittoCorso iscrittoCorso = null;
		IscrittiCorsoManager iscrittoManager = new IscrittiCorsoManager();
		AnagraficaCorsoManager anagraficaCorsoManager = new AnagraficaCorsoManager();
		Partecipanti partecipante = new Partecipanti();
		try {
			
			iscrittoCorso = iscrittoManager.getById(id).getData();
			
			if (iscrittoCorso != null) {
				boolean corsoConvalidato = Controlli.stringCompareTo(iscrittoCorso.getCorso().getConvalidato(), "T", false) == 0;
				AnagraficaCorso anagraficaCorso = iscrittoCorso.getAnagraficaCorso();
				anagraficaCorso.setInserito("F");
				anagraficaCorsoManager.update(anagraficaCorso);
				if (corsoConvalidato) {
					iscrittoManager.slim_delete(id);
				} else {
					iscrittoManager.delete(id);
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} 
		return partecipante;
	}
	
	public ResponsePrint printCorso(Integer idCorso) {
		
		IscrittiPrint iscritto = null;
		List<IscrittoCorso> list = new ArrayList<IscrittoCorso>();
		List<IscrittiPrint> listIscritti = new ArrayList<IscrittiPrint>();
		List<PrintSchedaCorso> listScedaCorso = new ArrayList<PrintSchedaCorso>();
		PrintSchedaCorso schedaCorso = new PrintSchedaCorso();
		HashMap<String, Object> params = new HashMap<String, Object>();
		double totaleCorso = 0;
		ResponsePrint data = null;
		try {
			
			list = new IscrittiCorsoManager().listIscrittiByIdCorso(idCorso, false);
			if (list.size() > 0) {
				
				for(IscrittoCorso iscritti : list) {
					
					Anagrafica anagrafica = iscritti.getAnagrafica();
					AnagraficaCorso anagraficaCorso = iscritti.getAnagraficaCorso();
					totaleCorso += iscritti.getQuota();
					boolean certificato = Controlli.stringCompareTo(anagraficaCorso.getCertificatoMedico(), "T", false) == 0;
					boolean assicurazione = Controlli.stringCompareTo(anagraficaCorso.getAssicurazione(), "T", false) == 0;
					boolean saldo = Controlli.stringCompareTo(iscritti.getSaldo(), "T", false) == 0;
					boolean acconto = Controlli.stringCompareTo(anagraficaCorso.getAcconto(), "T", false) == 0;
					
					iscritto = new IscrittiPrint(anagrafica.getNome() + " " + anagrafica.getCognome(), anagraficaCorso.getData(), anagrafica.getDataNascita(), certificato, assicurazione, saldo ,acconto, iscritti.getQuota(), anagrafica.getComune().getNome());
					listIscritti.add(iscritto);
				}
				
				params = createDettaglioCorso(params, list.get(0).getCorso());
				params.put("numeroPartecipanti",  listIscritti.size());
				params.put("totaleCorso", (float) totaleCorso);
				params.put("titleReport", "Scheda rosa");
				schedaCorso.setPartecipanti(listIscritti);
				listScedaCorso.add(schedaCorso);
				
				data = StandardUtils.doPrint(params, "stampaCorso", listScedaCorso);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	public ResponsePrint printPresenzeCorso(Integer idCorso) {

		ModelRegistrazioneCorso modelRegistazione = null;
		ResponsePrint data = null;
		List<ModelRegistrazioneCorso> list = new ArrayList<ModelRegistrazioneCorso>();
		HashMap<String, Object> params = new HashMap<String, Object>();

		try {
			
			modelRegistazione = this.listIscrittiByIdCorso(idCorso);
			if (modelRegistazione != null) {
				list.add(modelRegistazione);
				Corso cors = modelRegistazione.getCorso();
				
				params = createDettaglioCorso(params, cors);
				params.put("numeroPartecipanti",  modelRegistazione.getPartecipanti().size());
				params.put("titleReport", "Scheda bianca");
				data = StandardUtils.doPrint(params, "stampaPresenzeCorsi", list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	private HashMap<String, Object> createDettaglioCorso(HashMap<String, Object> params, Corso corso) {
		params.put("tipologia", corso.getTipologia());
		params.put("istruttore",  corso.getIstruttoreNominativo());
		
		String giorniSett = "";
		
		if (Controlli.stringCompareTo(corso.getLunedi(), "T", false) == 0) {
			giorniSett += "Lun "; 
		}
		if (Controlli.stringCompareTo(corso.getMartedi(), "T", false) == 0) {
			giorniSett += "Mar "; 
		}
		if (Controlli.stringCompareTo(corso.getMercoledi(), "T", false) == 0) {
			giorniSett += "Mer "; 
		}
		if (Controlli.stringCompareTo(corso.getGiovedi(), "T", false) == 0) {
			giorniSett += "Gio "; 
		}
		if (Controlli.stringCompareTo(corso.getVenerdi(), "T", false) == 0) {
			giorniSett += "Ven "; 
		}
		if (Controlli.stringCompareTo(corso.getSabato(), "T", false) == 0) {
			giorniSett += "Sab "; 
		}
		if (Controlli.stringCompareTo(corso.getPersonalizzato(), "T", false) == 0) {
			giorniSett += "Per "; 
		}
		
		params.put("giorni",  giorniSett);
		params.put("orario",  corso.getOraAl() + " - " + corso.getOraDal());
		params.put("minuti",  corso.getMinutiLezioni());
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		params.put("periodo",  sdf.format(corso.getDal()) + " - " + sdf.format(corso.getAl()));
		params.put("numeroLezioni",  corso.getNumeroLezioni());
		
		return params;
	}
}
