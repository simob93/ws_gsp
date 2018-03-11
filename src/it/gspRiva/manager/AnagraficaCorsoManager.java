package it.gspRiva.manager;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;

import it.gspRiva.entity.AnagraficaCorso;
import it.gspRiva.exception.MyException;
import it.gspRiva.model.Iscritti;
import it.gspRiva.model.KeyValue;
import it.gspRiva.model.ModelCertificatoMedico;
import it.gspRiva.utils.Controlli;
import it.gspRiva.utils.HibernateUtils;
import it.gspRiva.utils.PropertiesFile;
import it.gspRiva.utils.StandardUtils;

public class AnagraficaCorsoManager extends StdManager<AnagraficaCorso> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected Class<AnagraficaCorso> getEntityClass() {
		return AnagraficaCorso.class;
	}

	@Override
	public boolean checkCampiObbligatoriDelete(AnagraficaCorso oggetto, List<String> messaggi) throws IOException {
		return true;
	}

	@Override
	public boolean checkObjectForUpdate(AnagraficaCorso oggetto, List<String> messaggi) throws IOException {
		return true;
	}
	
	@Override
	public boolean checkObjectForInsert(AnagraficaCorso oggetto, List<String> messaggi) throws IOException {
		return true;
	}

	@Override
	public void operationAfterInsert(AnagraficaCorso object) throws IOException {
		
	}

	@Override
	public void operationAfterUpdate(AnagraficaCorso object) throws IOException {
		
	}



	public List<AnagraficaCorso> listByIdAnagrafica(Integer id) {
		Session session = null;
		Transaction tx = null;
		List<AnagraficaCorso> listAnagraficaCorso = null;
		try {
			
			session = HibernateUtils.getSessionAnnotationFactory().openSession();
			tx = session.beginTransaction(); 
						
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<AnagraficaCorso> query = builder.createQuery(AnagraficaCorso.class);
			
			Root<AnagraficaCorso> anagraficaCorso = query.from(AnagraficaCorso.class);
			
			query.select(anagraficaCorso);
			
			query.where(builder.equal(anagraficaCorso.get("idAnagrafica"), id));
			query.orderBy(builder.desc(anagraficaCorso.get("data")));
			
			listAnagraficaCorso = session.createQuery(query).getResultList();
			tx.commit();
			
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			
		} finally {
 			if (session != null) {
				session.close();
			}
		}
		return listAnagraficaCorso;
	}

	public List<Iscritti> search(String dal, String al, Integer tipologia, HashMap<String, String> has, String nominativo) throws MyException, IOException {
		
		Session session = null;
		Transaction tx = null;
		List<Iscritti> data = null;
		
		if (tipologia == null) {
			throw new MyException(PropertiesFile.openPropertie().getProperty("tipologia.invalid"));
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			Date dataDal = null;
			Date dataAl = null;
			session = HibernateUtils.getSessionAnnotationFactory().openSession();
			StringBuilder hql = new StringBuilder();
			tx = session.beginTransaction(); 
			/*
			 *  escludo le iscrizioni annullate
			 */
			hql.append("SELECT CONCAT(an.nome,' ',an.cognome) as nominativo, an.dataNascita as dataNascita, ac.acconto as acconto, ac.certificatoMedico as certificatoMedico,")
			   .append("ac.data as dataIscrizione, ac.assicurazione as assicurazione, ac.idAnagrafica as idAnagrafica, ")
			   .append("ac.lunedi as lunedi, ac.martedi as martedi, ac.mercoledi as mercoledi, ac.giovedi as giovedi, ac.venerdi as venerdi,")
			   .append("ac.sabato as sabato, ac.personalizzato as personalizzato, ac.minutiLezioni as minutiLezioni, ac.numeroLezioni as numeroLezioni,")
			   .append("ac.tipologia as tipologia, ac.dataInizio as dataInizio, ac.dataFine as dataFine, ac.id as idAnagraficaCorso ")
			   .append(" FROM AnagraficaCorso As ac INNER JOIN Anagrafica AS an ON ac.idAnagrafica = an.id WHERE ")
			   .append(" ac.deletedData is null ")
			   .append(" AND (ac.inserito is null OR ac.inserito='F') AND ac.tipologia=:tipologia ");
					
					//+ "ORDER BY ac.data DESC";
			
			// tipologia corso = di gruppo
			if (tipologia == 2) {
				
				if (!Controlli.isEmptyString(dal)) {
					dataDal = sdf.parse(dal);
					hql.append("AND ac.dataInizio >= :dal ");
				}
				
				if (!Controlli.isEmptyString(al)) {
					dataAl = sdf.parse(al);
					hql.append("AND ac.dataFine <= :al ");
				}
				
			} 
			
			if (!Controlli.isEmptyString(nominativo)) {
				hql.append("AND (an.nome LIKE '%").append(nominativo).append("%')").append("OR (an.cognome LIKE '%").append(nominativo).append("%')");
			}
			
			if (has.size() > 0) {
				String value = "";
				/* implemento i filtri per i giorni della settimana */
			
				for(Entry<String, String> entry : has.entrySet()) {
					value = "'" + entry.getValue() + "'";
					hql.append("AND ac.")
					   .append(entry.getKey())
					   .append("=")
					   .append(value);
				}
			}
			
			hql.append("ORDER BY ac.data DESC");
			
			Query query = session.createQuery(hql.toString());
			query.setParameter("tipologia", tipologia);
			
			if (dataAl != null) {
				query.setParameter("al", dataAl);
			} 
			if (dataDal != null) {
				query.setParameter("dal", dataDal);
			} 
			
			query.setResultTransformer(Transformers.aliasToBean(Iscritti.class));
			data = query.list();
			
			
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

	public ModelCertificatoMedico checkCertificatoMedico(Integer idAnagrafica) {
		Session session = null;
		Transaction tx = null;
		List<AnagraficaCorso> anagCorso = null;
		ModelCertificatoMedico data = new ModelCertificatoMedico();
		try {
			
			session = HibernateUtils.getSessionAnnotationFactory().openSession();
			tx = session.beginTransaction(); 
						
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<AnagraficaCorso> query = builder.createQuery(AnagraficaCorso.class);
			
			Root<AnagraficaCorso> anagraficaCorso = query.from(AnagraficaCorso.class);
			
			query.select(anagraficaCorso);
			
			query.where(
				builder.equal(anagraficaCorso.get("idAnagrafica"), idAnagrafica)
			);
			query.orderBy(builder.desc(anagraficaCorso.get("scadenzaCertificato")));
			
			anagCorso =  session.createQuery(query).setMaxResults(1).getResultList();
			
			if (anagCorso != null && anagCorso.size() > 0) {
				
				Date oggi = StandardUtils.currentDateTime();
				oggi = StandardUtils.azzeraMinutiOreSecondi(oggi);
				Date sc_certMedico = anagCorso.get(0).getScadenzaCertificato();
				
				if (sc_certMedico != null) {
					
					boolean scaduto = sc_certMedico.before(oggi);
					data.setScaduto(scaduto);
					data.setDataScadenza(sc_certMedico);
				}
				
			}
			
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

	@SuppressWarnings({"rawtypes", "deprecation" })
	public List<KeyValue> checkCertificatiScaduti() {
		Session session = null;
		Transaction tx = null;
		List<KeyValue> result = null;

		try {
			
			session = HibernateUtils.getSessionAnnotationFactory().openSession();
			tx = session.beginTransaction(); 
					
			
			String hql = "Select DISTINCT ac.idAnagrafica as extra, ac.nominativo as key, ac.scadenzaCertificato as value FROM AnagraficaCorso AS ac INNER JOIN Anagrafica as a ON a.id = ac.idAnagrafica WHERE ac.scadenzaCertificato >= :oggi AND ac.scadenzaCertificato <= :DataFine ORDER BY ac.scadenzaCertificato DESC";
			Date oggi = StandardUtils.currentDateTime(),
				 fine =  StandardUtils.sommaGiorni(oggi, 15);
			oggi = StandardUtils.azzeraMinutiOreSecondi(oggi);
			
			Query query = session.createQuery(hql);
			query.setParameter("oggi", oggi);
			query.setParameter("DataFine", fine);
			query.setResultTransformer(Transformers.aliasToBean(KeyValue.class));
			result = query.list();
			tx.commit();			
		
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			
		} finally {
 			if (session != null) {
				session.close();
			}
		}
		return result;
	}
}
