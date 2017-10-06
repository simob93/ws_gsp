package it.gspRiva.manager;

import java.io.IOException;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;

import it.gspRiva.entity.Comuni;
import it.gspRiva.utils.HibernateUtils;

public class ComuniManager extends StdManager<Comuni> {

	@Override
	protected Class<Comuni> getEntityClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkCampiObbligatoriDelete(Comuni oggetto, List<String> messaggi) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkObjectForInsert(Comuni oggetto, List<String> messaggi) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkObjectForUpdate(Comuni oggetto, List<String> messaggi) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void operationAfterInsert(Comuni object) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void operationAfterUpdate(Comuni object) throws IOException {
		// TODO Auto-generated method stub
		
	}

	public List<Comuni> list() {
		Session session = null;
		Transaction tx = null;
		List<Comuni> data = null;
		try {
			
			session = HibernateUtils.getSessionAnnotationFactory().openSession();
			tx = session.beginTransaction(); 
						
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Comuni> query = builder.createQuery(Comuni.class);
			
			Root<Comuni> anagrafica = query.from(Comuni.class);
			query.select(anagrafica);
		
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

}
