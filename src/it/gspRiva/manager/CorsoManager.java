package it.gspRiva.manager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
import it.gspRiva.model.ModelIscrittiCorsi;
import it.gspRiva.model.ModelRegistrazioneCorso;
import it.gspRiva.model.Partecipanti;
import it.gspRiva.model.ResponsePrint;
import it.gspRiva.utils.Controlli;
import it.gspRiva.utils.HibernateUtils;
import it.gspRiva.utils.PropertiesFile;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

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
	
	
	public List<Corso> list(boolean escludiAnnullati, boolean escludiConvalidati, String dal, String al) {
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
			query.orderBy(builder.asc(corso.get("dal")));
			List<Predicate> predicates = new ArrayList<Predicate>();
			
			if (escludiAnnullati) {
				predicates.add(builder.isNull(corso.get("deletedData")));
			}
			if (escludiConvalidati) {
				predicates.add(
						builder.or(
								builder.equal(corso.get("convalidato"), "F"),
								builder.isNull(corso.get("convalidato"))
						)
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
	 * ritorna un lista di corsi con allegato i suoi partecipanti
	 * @param annnullati 
	 * @param convalidati 
	 * @param tipologia 
	 * @param al 
	 * @param dal 
	 * @return
	 * @throws MyException
	 */
	public List<ModelIscrittiCorsi> listIscrittiByCorsi(String dal, String al, Integer tipologia, String escludiConvalidati, String escludiAnnullati) 
			throws MyException  {
		
		Session session = null;
		Transaction tx = null;
		List<AnagraficaCorso> listAnagraficaCorso = null;
		List<ModelIscrittiCorsi> data = new ArrayList<ModelIscrittiCorsi>();
		IscrittiCorsoManager iscrittiCorsoManager = new IscrittiCorsoManager();
		ModelIscrittiCorsi modelIscrittiCorso = null;
		try {
			session = HibernateUtils.getSessionAnnotationFactory().openSession();
			tx = session.beginTransaction(); 
			List<Corso> listCorsi  = this.list(Controlli.stringCompareTo(escludiAnnullati, "T", false) == 0, Controlli.stringCompareTo(escludiConvalidati, "T", false) == 0, dal, al);
			
			for (Corso corso : listCorsi) {
				listAnagraficaCorso = new ArrayList<AnagraficaCorso>();
				modelIscrittiCorso = new ModelIscrittiCorsi(corso);
				Integer id = corso.getId();
				List<IscrittoCorso> listIscrittiCorso = iscrittiCorsoManager.listIscrittiByIdCorso(id, true);
				
				for (IscrittoCorso iscrittoCorso : listIscrittiCorso) {
					
					listAnagraficaCorso.add(iscrittoCorso.getAnagraficaCorso());
				}
				
				modelIscrittiCorso.setAnagraficaCorso(listAnagraficaCorso);
				data.add(modelIscrittiCorso);
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
	

	public Partecipanti rimuoviPartecipante(Integer id, boolean forceRemove) throws Exception {
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
				if (corsoConvalidato && !forceRemove) {
					iscrittoManager.slim_delete(id);
					iscrittoCorso = iscrittoManager.getById(id).getData();
					partecipante.setId(iscrittoCorso.getId());
					partecipante.setIdAnagrafica(iscrittoCorso.getAnagrafica().getId());
					partecipante.setIdAnagraficaCorso(iscrittoCorso.getAnagraficaCorso().getId());
					partecipante.setNominativo(iscrittoCorso.getAnagraficaCorso().getNominativo());
					partecipante.setAcconto(iscrittoCorso.getAnagraficaCorso().getAcconto());
					partecipante.setDeletedData(iscrittoCorso.getDeletedData());
					partecipante.setSaldo(iscrittoCorso.getSaldo());
					partecipante.setQuota(iscrittoCorso.getQuota());
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

	public ResponsePrint printPresenzeCorsi(Integer idCorso) {
		Boolean success = true;
		JasperReport jasperReport = null;
		JasperPrint jasperPrint = null;
		File catalinaBase = null;
		ModelRegistrazioneCorso data = null;
		List<ModelRegistrazioneCorso> list = new ArrayList<ModelRegistrazioneCorso>();
		HashMap<String, Object> params = new HashMap<String, Object>();
		try {
			
			data = this.listIscrittiByIdCorso(idCorso);
			if (data != null) {
				list.add(data);
				Integer tipologia = data.getCorso().getTipologia();
				params.put("tipologia", tipologia);
				params.put("istruttore",  data.getCorso().getIstruttoreNominativo());
				
				String giorniSett = "";
				
				if (Controlli.stringCompareTo(data.getCorso().getLunedi(), "T", false) == 0) {
					giorniSett += "Lun "; 
				}
				if (Controlli.stringCompareTo(data.getCorso().getMartedi(), "T", false) == 0) {
					giorniSett += "Mar "; 
				}
				if (Controlli.stringCompareTo(data.getCorso().getMercoledi(), "T", false) == 0) {
					giorniSett += "Mer "; 
				}
				if (Controlli.stringCompareTo(data.getCorso().getGiovedi(), "T", false) == 0) {
					giorniSett += "Gio "; 
				}
				if (Controlli.stringCompareTo(data.getCorso().getVenerdi(), "T", false) == 0) {
					giorniSett += "Ven "; 
				}
				if (Controlli.stringCompareTo(data.getCorso().getSabato(), "T", false) == 0) {
					giorniSett += "Sab "; 
				}
				if (Controlli.stringCompareTo(data.getCorso().getPersonalizzato(), "T", false) == 0) {
					giorniSett += "Per "; 
				}
				
				params.put("giorni",  giorniSett);
				
				catalinaBase = new File( System.getProperty( "catalina.base" ) ).getAbsoluteFile();
				InputStream is = getClass().getClassLoader().getResourceAsStream("/it/gspRiva/report/stampaPresenzeCorsi.jrxml");
						
				
				jasperReport = JasperCompileManager.compileReport(is);
				jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JRBeanCollectionDataSource(list));
				JasperExportManager.exportReportToPdfFile(jasperPrint, catalinaBase + "/webapps/rpt/RP.pdf");
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
