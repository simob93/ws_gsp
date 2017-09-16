package it.gspRiva.manager;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import it.gspRiva.entity.Anagrafica;
import it.gspRiva.entity.AnagraficaCorso;
import it.gspRiva.exception.MyException;
import it.gspRiva.model.Iscritti;
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
	public boolean checkObjectForInserit(AnagraficaCorso oggetto, List<String> messaggi) throws IOException {
		return true;
	}

	@Override
	public boolean checkObjectForUpdate(AnagraficaCorso oggetto, List<String> messaggi) throws IOException {
		return true;
	}

	@Override
	public boolean checkCampiObbligatori(AnagraficaCorso object, List<String> messaggi) throws IOException {
		
//		Session session = null;
//		Transaction tx = null;
//		boolean okInsert = false;
//		List<AnagraficaCorso> listAnagraficaCorso = null;
//		try {
//			
//			session = HibernateUtils.getSessionAnnotationFactory().openSession();
//			tx = session.beginTransaction(); 
//						
//			CriteriaBuilder builder = session.getCriteriaBuilder();
//			CriteriaQuery<AnagraficaCorso> query = builder.createQuery(AnagraficaCorso.class);
//			Root<AnagraficaCorso> anagraficaCorso = query.from(AnagraficaCorso.class);
//			query.select(anagraficaCorso);
//			query.where(builder.equal(anagraficaCorso.get("data"), object.getData()));
//			listAnagraficaCorso = session.createQuery(query).getResultList();
//			okInsert = listAnagraficaCorso.size() == 0;
//			
//			if (!okInsert) {
//				messaggi.add(new PropertiesFile().getPropValues("data.invalid"));
//			}
//			
//			tx.commit();
//			
//		} catch (Exception e) {
//			tx.rollback();
//			e.printStackTrace();
//			
//		} finally {
// 			if (session != null) {
//				session.close();
//			}
//		}
		return true;
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

	public List<Iscritti> search(String dal, String al, Integer tipologia, HashMap<String, String> has) throws MyException, IOException {
		
		Session session = null;
		Transaction tx = null;
		List<Iscritti> listaIscritti = null;
		
		if (dal == null) {
			throw new MyException(PropertiesFile.openPropertie().getProperty("dal.invalid"));
		}
		if (al == null) {
			throw new MyException(PropertiesFile.openPropertie().getProperty("dal.invalid"));
		}
		if (tipologia == null) {
			throw new MyException(PropertiesFile.openPropertie().getProperty("tipologia.invalid"));
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			Date dataAl = sdf.parse(al);
			Date dataDal = sdf.parse(dal);
			session = HibernateUtils.getSessionAnnotationFactory().openSession();
			tx = session.beginTransaction(); 
			/*
			 *  escludo le iscrizioni annullate
			 */
			String hql = "FROM AnagraficaCorso As ac INNER JOIN Anagrafica AS an ON ac.idAnagrafica = an.id WHERE "
					+ "ac.data >= :dal "
					+ "AND ac.data <= :al "
					+ "AND ac.deletedData is null "
					+ "AND (ac.inserito is null OR ac.inserito='F') ";
					//+ "ORDER BY ac.data DESC";
			
			if (tipologia != null) {
				hql += "AND ac.tipologia=" + tipologia + " ";
			}
			
			if (has.size() > 0) {
				String value = "";
				/* implemento i filtri per i giorni della settimana */
			
				for(Entry<String, String> entry : has.entrySet()) {
					value = "'" + entry.getValue() + "'";
					hql += "AND "+ entry.getKey() + "=" + value;
				}
			}
			
			hql += "ORDER BY ac.data DESC";
			
			Query query = session.createQuery(hql);
			query.setParameter("dal", dataDal);
			query.setParameter("al", dataAl);
			
			listaIscritti = new ArrayList<Iscritti>();
			List result = query.list();
			
			if (result != null && result.size() > 0) {
				for (int i =0, len = result.size(); i< len; i++) {
					Object[] element = (Object[]) result.get(i);
					Iscritti modelIscritti = new Iscritti();
					for (Object res:  element) {
						if (res instanceof AnagraficaCorso) {
							AnagraficaCorso ac = (AnagraficaCorso) res;
							modelIscritti.setAcconto(ac.getAcconto() != null && ac.getAcconto().compareTo("T") == 0 );
							modelIscritti.setCertificatoMedico(ac.getCertificatoMedico() != null && ac.getCertificatoMedico().compareTo("T") == 0);
							modelIscritti.setDataIscrizione(ac.getData());
							modelIscritti.setNominativo(ac.getNominativo());
							modelIscritti.setAssicurazione(ac.getAssicurazione() != null && ac.getAssicurazione().compareTo("T") == 0);
							modelIscritti.setIdAnagraficaCorso(ac.getId());
							
						}
						if (res instanceof Anagrafica) {
							Anagrafica an = (Anagrafica) res;
							modelIscritti.setDataNascita(an.getDataNascita());
							int day = StandardUtils.dateBetween(an.getDataNascita(), StandardUtils.currentDateTime(), "d");
							modelIscritti.setMaggiorenne(day >= 18);
							modelIscritti.setIdAnagrafica(an.getId());
						}
					}
					listaIscritti.add(modelIscritti);
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
		return listaIscritti;
	}

}
