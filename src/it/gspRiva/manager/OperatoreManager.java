package it.gspRiva.manager;

import java.io.IOException;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;

import it.gspRiva.entity.Operatore;
import it.gspRiva.utils.HibernateUtils;

public class OperatoreManager extends StdManager<Operatore> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected Class<Operatore> getEntityClass() {
		// TODO Auto-generated method stub
		return Operatore.class;
	}
	
	@Override
	public boolean checkCampiObbligatoriDelete(Operatore oggetto, List<String> messaggi) throws IOException {
		return true;
	}

	@Override
	public boolean checkCampiObbligatori(Operatore object, List<String> messaggi) throws IOException {
		return true;
	}
	
	@Override
	public boolean checkObjectForInserit(Operatore oggetto, List<String> messaggi) throws IOException {
		return true;
	}

	@Override
	public boolean checkObjectForUpdate(Operatore oggetto, List<String> messaggi) throws IOException {
		return true;
	}

	public Operatore login(String username, String password) {
		
		Session session = null;
		Transaction tx = null;
		Operatore operatoreLog = null;
		try {
			
			session = HibernateUtils.getSessionAnnotationFactory().openSession();
			tx = session.beginTransaction(); 
						
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Operatore> query = builder.createQuery(Operatore.class);
			
			Root<Operatore> operatore = query.from(Operatore.class);
			
			query.select(operatore);
			
			query.where(builder.and(
				builder.equal(operatore.get("username"), username),
				builder.equal(operatore.get("password"), password)
			));
			
			List<Operatore> listOperatore = session.createQuery(query).getResultList();
			operatoreLog = listOperatore.size() > 0 ? listOperatore.get(0) : null;
			
			tx.commit();
			
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return operatoreLog;
	}
}
