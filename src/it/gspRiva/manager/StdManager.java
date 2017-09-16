package it.gspRiva.manager;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import it.gspRiva.emuns.ChiaviEsterne;
import it.gspRiva.emuns.DbOperation;
import it.gspRiva.model.JsonResponse;
import it.gspRiva.utils.Controlli;
import it.gspRiva.utils.HibernateUtils;
import it.gspRiva.utils.PropertiesFile;
import it.gspRiva.utils.StandardUtils;

public abstract class StdManager<T> implements CrudOperation<T> {
	
			
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 * @return
	 */
	protected abstract Class<T> getEntityClass();
	
	/**
	 * 
	 * @param oggetto
	 * @param messaggi
	 * @return
	 */
	public abstract boolean checkCampiObbligatoriDelete(T oggetto, List<String> messaggi)throws IOException;
	/**
	 * 
	 * @param oggetto
	 * @param messaggi
	 * @return
	 * @throws IOException
	 */
	public abstract boolean checkObjectForInserit(T oggetto, List<String> messaggi) throws IOException;
	/**
	 * 
	 * @param oggetto
	 * @param messaggi
	 * @return
	 * @throws IOException
	 */
	public abstract boolean checkObjectForUpdate(T oggetto, List<String> messaggi) throws IOException;

	/**
	 * 
	 * @param object
	 * @param messaggi
	 * @return
	 */
	public abstract boolean checkCampiObbligatori(T object, List<String> messaggi) throws IOException;
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws IOException
	 */
	public JsonResponse<T> getById(Integer id) throws IOException {
		
		Session session = null;
		Transaction tx = null;
		T object = null;
		boolean success = true;
		List<String> msg = new ArrayList<String>();
		
		try {
			session = HibernateUtils.getSessionAnnotationFactory().openSession();
			tx = session.beginTransaction(); 
			object = findById(id, session);
						
			tx.commit();
			msg.add(PropertiesFile.openPropertie().getProperty("operation.success"));
			
		} catch (Exception e) {
			msg.add(PropertiesFile.openPropertie().getProperty("operation.error"));
			success = false;
			tx.rollback();
			e.printStackTrace();
			
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return new JsonResponse<T>(success, msg, object);
	}
	
	/**
	 * 
	 * @param object
	 * @param session
	 * @return
	 * @throws IOException 
	 */
	public JsonResponse<T> operation(T object, DbOperation operation) throws IOException {
		return operation(object, operation, null);
	}
	/**
	 * @param operation
	 * @param id
	 * @return
	 * @throws IOException
	 */
	public JsonResponse<T> operation(DbOperation operation, Integer id) throws IOException {
		return operation(null, operation, id);
	}
	
	/**
	 * 
	 * @param object
	 * @param operation
	 * @param id
	 * @return
	 * @throws IOException
	 */
	public JsonResponse<T> operation(T object, DbOperation operation, Integer id) throws IOException {
		
		boolean success = true;
		boolean result = true;
		
		List<String> msg = new ArrayList<String>();
		switch(operation) {
		case INSERT:
			result = checkCampiObbligatori(object, msg);
			if (result) {
				result = checkObjectForInserit(object, msg);
				if (result) {
					success = create(object);
				}
			}
			break;
		case UPDATE:
			result = checkCampiObbligatori(object, msg);
			if (result) {
				result = checkObjectForUpdate(object, msg);
				if (result) {
					success = update(object);
				}
			}
			break;
		case DELETE:
			//integrita referenziale
			result = checkIntegritaReferenziale(msg, id);
			if (result) {
				success = delete(id);
			}
			break;
		case SLIM_DELETE:
			result = checkIntegritaReferenziale(msg, id);
			if (result) {
				success = slim_delete(id);
			}
			break;
		default:
			break;
			
		}
		if (!success) {
			msg.add(PropertiesFile.openPropertie().getProperty(("operation.error")));
			return new JsonResponse<T>(success, msg); 
		}
		else if (!result) {
			return new JsonResponse<T>(false, msg); 
		}
		else msg.add(PropertiesFile.openPropertie().getProperty("operation.success"));
		
		return new JsonResponse<T>(success, msg, object);
	}
	
	private boolean checkIntegritaReferenziale(List<String> msg, Integer id) {
		
		Class<?> cls = getEntityClass();
		Table tb = cls.getAnnotation(Table.class);
		String nameTable = tb.name();
		boolean success = true;
		
		if (!Controlli.isEmptyString(nameTable)) {
			for (ChiaviEsterne fk : ChiaviEsterne.values()) {
				if (Controlli.stringCompareTo(fk.getEntity(), nameTable, false) == 0) {
					
					String hql = "Select f.ID FROM " + fk.getEntity() + " AS f INNER JOIN " + fk.getEntityToCompare()
						+ " AS s ON f." + fk.getFieldEntity() + "= s."+ fk.getFieldEntityToCompare() + " WHERE f.ID = :id";
						if (!Controlli.isEmptyString(fk.getCondition())) {
							hql += " AND " + fk.getCondition();
						}
					
					Session session = null;
					Transaction tx = null;
					
					try {
						session = HibernateUtils.getSessionAnnotationFactory().openSession();
						tx = session.beginTransaction(); 
						Query query = session.createNativeQuery(hql);
						query.setParameter("id", id);
						List data = query.list();
						tx.commit();
						
						if (data != null && data.size() > 0) {
							success = false;
							msg.add(MessageFormat.format(PropertiesFile.openPropertie().getProperty("record.movimentato"), fk.getMessaggio()));
						}
						
					} catch (Exception e) {
						success = false;
						e.printStackTrace();
						tx.rollback();
					} finally {
						if (session != null) {
							session.close();
						}
					}
				}
			}
		}
		return success;
	}

	@Override
	public boolean create(T t) {
		Session session = null;
		Transaction tx = null;
		boolean success = true;
		try {
			session = HibernateUtils.getSessionAnnotationFactory().openSession();
			tx = session.beginTransaction(); 
			session.save(t);
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
	
	@Override
	public boolean update(T t) {
		Session session = null;
		Transaction tx = null;
		boolean success = true;
		try {
			session = HibernateUtils.getSessionAnnotationFactory().openSession();
			tx = session.beginTransaction(); 
			session.merge(t);
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
	
	@Override
	public boolean delete(Integer id) {
		Session session = null;
		Transaction tx = null;
		boolean success = true;
		try {
			session = HibernateUtils.getSessionAnnotationFactory().openSession();
			tx = session.beginTransaction(); 
			T object = findById(id, session);
			session.delete(object);
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
	
	@Override
	public boolean slim_delete(Integer id) {
		Session session = null;
		Transaction tx = null;
		boolean success = true;
		try {
			session = HibernateUtils.getSessionAnnotationFactory().openSession();
			tx = session.beginTransaction();
			//T object = findById(id, session);
			Class<T> entityClass = getEntityClass();
			String hql = "UPDATE " + entityClass.getName() + " SET DELETED_DATA =:deletedData  WHERE ID=:id";
			Query query = session.createQuery(hql);
			query.setParameter("deletedData", StandardUtils.currentDateTime());
			query.setParameter("id", id);
			query.executeUpdate();
			tx.commit();
			
		} catch (Exception e) {
			success = false;
			tx.rollback();
			
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return success;
	}

	/**
	 * 
	 * @param id
	 * @param s
	 * @return
	 */
	 public T findById(Integer id, Session s){
		return (T)s.get(getEntityClass(), id);
	 }
}
