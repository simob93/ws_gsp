package it.gspRiva.manager;

import java.io.IOException;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;

import it.gspRiva.entity.Istruttori;
import it.gspRiva.exception.MyException;
import it.gspRiva.utils.HibernateUtils;

public class IstruttoriManager extends StdManager<Istruttori>{

	
	private static final long serialVersionUID = 9122875729042324223L;

	@Override
	protected Class<Istruttori> getEntityClass() {
		// TODO Auto-generated method stub
		return Istruttori.class;
	}

	@Override
	public boolean checkCampiObbligatoriDelete(Istruttori oggetto, List<String> messaggi) throws IOException {
		return true;
	}


	@Override
	public boolean checkObjectForUpdate(Istruttori oggetto, List<String> messaggi) throws IOException {
		return true;
	}

	@Override
	public boolean checkObjectForInsert(Istruttori oggetto, List<String> messaggi) throws IOException {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void operationAfterInsert(Istruttori object) throws IOException {
		
	}

	@Override
	public void operationAfterUpdate(Istruttori object) throws IOException {
		
	}
	
	public List<Istruttori> list() throws MyException {
		Session session = null;
		Transaction tx = null;
		List<Istruttori> data = null;
		try {
			
			session = HibernateUtils.getSessionAnnotationFactory().openSession();
			tx = session.beginTransaction(); 
						
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Istruttori> query = builder.createQuery(Istruttori.class);
			
			Root<Istruttori> istruttori = query.from(Istruttori.class);
			query.select(istruttori);
			query.orderBy(builder.asc(istruttori.get("cognome")));
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
