package it.gspRiva.manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;

import it.gspRiva.entity.IscrittoCorso;
import it.gspRiva.exception.MyException;
import it.gspRiva.utils.HibernateUtils;
import it.gspRiva.utils.PropertiesFile;

public class IscrittiCorsoManager extends StdManager<IscrittoCorso> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7915547295894968245L;
	
	@Override
	protected Class<IscrittoCorso> getEntityClass() {
		return IscrittoCorso.class;
	}

	@Override
	public boolean checkCampiObbligatoriDelete(IscrittoCorso oggetto, List<String> messaggi) throws IOException {
		return true;
	}

	@Override
	public boolean checkObjectForUpdate(IscrittoCorso oggetto, List<String> messaggi) throws IOException {
		return true;
	}

	@Override
	public boolean checkObjectForInsert(IscrittoCorso oggetto, List<String> messaggi) throws IOException {
		return true;
	}


	@Override
	public void operationAfterInsert(IscrittoCorso object) throws IOException {
		
	}


	@Override
	public void operationAfterUpdate(IscrittoCorso object) throws IOException {
		
	}
	/**
	 * ritorna tutti gli iscritti nella tabell iscritti corso
	 * @return
	 * @throws MyException
	 */
	public List<IscrittoCorso> listIscritti() throws MyException {
		Session session = null;
		Transaction tx = null;
		List<IscrittoCorso> data = null;
		try {
			
			session = HibernateUtils.getSessionAnnotationFactory().openSession();
			tx = session.beginTransaction(); 
						
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<IscrittoCorso> query = builder.createQuery(IscrittoCorso.class);
			
			Root<IscrittoCorso> iscritti_corso = query.from(IscrittoCorso.class);
			query.select(iscritti_corso);
		
			data = session.createQuery(query).getResultList();
			
			
			
			
			tx.commit();
			
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw new MyException(e.getMessage());
			
		} finally {
 			if (session != null) {
				session.close();
			}
		}
		return data;
	}

	/**
	 *  ritorna gli iscritti relativi ad una tipologia di corso
	 * @param id
	 * @param escludiAnnullati
	 * @return
	 * @throws MyException
	 * @throws IOException
	 */
	public List<IscrittoCorso> listIscrittiByIdCorso(Integer id, boolean escludiAnnullati) 
			throws MyException, IOException {
		
		Session session = null;
		Transaction tx = null;
		List<IscrittoCorso> data = null;
		
		if (id == null) {
			throw new MyException(PropertiesFile.openPropertie().getProperty("id.invalid"));
		}
		
		try {
			
			session = HibernateUtils.getSessionAnnotationFactory().openSession();
			tx = session.beginTransaction(); 
						
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<IscrittoCorso> query = builder.createQuery(IscrittoCorso.class);
			
			Root<IscrittoCorso> iscritti_corso = query.from(IscrittoCorso.class);
			query.select(iscritti_corso);
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.equal(iscritti_corso.get("corso").get("id"), id));
			
			
			if (escludiAnnullati) {
				predicates.add(builder.isNull(iscritti_corso.get("deletedData")));
			}
			query.where(predicates.toArray(new Predicate[]{}));
			
			data = session.createQuery(query).getResultList();
			
			tx.commit();
			
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw new MyException(e.getMessage());
			
		} finally {
 			if (session != null) {
				session.close();
			}
		}
		return data;
	}
}
