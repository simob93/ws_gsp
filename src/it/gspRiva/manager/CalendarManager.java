package it.gspRiva.manager;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;

import it.gspRiva.entity.Calendar;
import it.gspRiva.utils.HibernateUtils;

public class CalendarManager extends StdManager<Calendar> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -869997777342757742L;

	@Override
	protected Class<Calendar> getEntityClass() {
		return Calendar.class;
	}

	@Override
	public boolean checkCampiObbligatoriDelete(Calendar oggetto, List<String> messaggi) throws IOException {
		return true;
	}

	@Override
	public boolean checkObjectForUpdate(Calendar oggetto, List<String> messaggi) throws IOException {
		return true;
	}
	
	@Override
	public boolean checkObjectForInsert(Calendar oggetto, List<String> messaggi) throws IOException {
		return true;
	}

	@Override
	public void operationAfterInsert(Calendar object) throws IOException {
		
	}

	@Override
	public void operationAfterUpdate(Calendar object) throws IOException {
		
	}

	
	public List<Calendar> list(String dal, String al) {
		
		Session session = null;
		Transaction tx = null;
		List<Calendar> data = null;
		try {
			
			session = HibernateUtils.getSessionAnnotationFactory().openSession();
			tx = session.beginTransaction(); 
						
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Calendar> query = builder.createQuery(Calendar.class);
			
			Root<Calendar> calendar = query.from(Calendar.class);
			query.select(calendar);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			query.where(
				builder.greaterThanOrEqualTo(calendar.get("data"), sdf.parse(dal)),
				builder.lessThanOrEqualTo(calendar.get("data"), sdf.parse(al))
			);
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
