package it.gspRiva.manager;

import java.io.IOException;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;

import it.gspRiva.entity.Anagrafica;
import it.gspRiva.utils.Controlli;
import it.gspRiva.utils.HibernateUtils;

public class AnagraficaManager extends StdManager<Anagrafica> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected Class<Anagrafica> getEntityClass() {
		return Anagrafica.class;
	}
	

	@Override
	public boolean checkObjectForInsert(Anagrafica oggetto, List<String> messaggi) throws IOException {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void operationAfterInsert(Anagrafica object) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void operationAfterUpdate(Anagrafica object) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean checkCampiObbligatoriDelete(Anagrafica oggetto, List<String> messaggi) throws IOException {
		return true;
	}

	@Override
	public boolean checkObjectForUpdate(Anagrafica oggetto, List<String> messaggi) throws IOException {
		return true;
	}
	
	public boolean controllaRecordDoppi(Anagrafica oggetto, List<String> messaggi) {
		/*Session session = HibernateUtils.getSessionAnnotationFactory().openSession();
		Transaction tx = null;
		List<Anagrafica> data = null;
		boolean result = false;
		
		try {
			tx = session.beginTransaction();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Anagrafica> query = builder.createQuery(Anagrafica.class);
			
			Root<Anagrafica> anagrafica = query.from(Anagrafica.class);
			query.select(anagrafica);
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(
				builder.equal(anagrafica.get("codiceFiscale"), oggetto.getCodiceFiscale())
			);
			predicates.add(
					builder.like(anagrafica.get("nome"), "%" + oggetto.getNome() + "%")
			);
			predicates.add(
					builder.like(anagrafica.get("cognome"), "%" + oggetto.getCognome() + "%")
			);
			
			query.where(predicates.toArray(new Predicate[]{}));
			data = session.createQuery(query).getResultList();
			
			result = data.size() == 0;
			
			if (!result) {
				messaggi.add(MessageFormat.format(PropertiesFile.openPropertie().getProperty("record.exists"), "Iscritto"));
			}
			tx.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return result;*/
		return true;
	}

	public List<Anagrafica> list(String search) throws Exception {
		Session session = null;
		Transaction tx = null;
		List<Anagrafica> listAnagrafica = null;
		try {
			
			session = HibernateUtils.getSessionAnnotationFactory().openSession();
			tx = session.beginTransaction(); 
						
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Anagrafica> query = builder.createQuery(Anagrafica.class);
			
			Root<Anagrafica> anagrafica = query.from(Anagrafica.class);
			query.select(anagrafica);
			
			if (!Controlli.isEmptyString(search)) {
				
				query.where(
						builder.or(
							builder.like(anagrafica.get("nome"), "%"+ search + "%"),
							builder.like(anagrafica.get("cognome"), "%"+ search + "%")
						)
				);
			
			}
			query.orderBy(builder.asc(anagrafica.get("cognome")));
			listAnagrafica = session.createQuery(query).getResultList();
			
			tx.commit();
			
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			
		} finally {
 			if (session != null) {
				session.close();
			}
		}
		return listAnagrafica;
	}
}
