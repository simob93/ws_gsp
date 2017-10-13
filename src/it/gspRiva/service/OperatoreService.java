package it.gspRiva.service;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import it.gspRiva.emuns.DbOperation;
import it.gspRiva.entity.Istruttori;
import it.gspRiva.entity.Operatore;
import it.gspRiva.manager.OperatoreManager;
import it.gspRiva.model.JsonResponse;
import it.gspRiva.utils.Controlli;
import it.gspRiva.utils.PropertiesFile;

public class OperatoreService {
	
	public OperatoreService() {
		
	}
	
	public  OperatoreManager getManager() {
		
		return new OperatoreManager();
	}
	
	public JsonResponse<Operatore> getOperatore(String username, String password, HttpServletRequest req) throws IOException {
		List<String> msg = new ArrayList<String>();
		
		if (Controlli.isEmptyString(username)) {
			msg.add(PropertiesFile.openPropertie().getProperty("login.username"));
		}
		
		if (Controlli.isEmptyString(password)) {
			msg.add(PropertiesFile.openPropertie().getProperty("login.password"));
		}
		if(!Controlli.isEmptyList(msg)) {
			return new JsonResponse<Operatore>(false, msg);
		} else {
			
			boolean success = true;
			Operatore operatoreLog = null;
			try {
				operatoreLog = this.getManager().login(username, password, req);
				if (operatoreLog == null) {
					success = false;
					msg.add(PropertiesFile.openPropertie().getProperty("username.invalid"));
					msg.add(PropertiesFile.openPropertie().getProperty("password.invalid"));
				} else {
					msg.add(PropertiesFile.openPropertie().getProperty("operation.success"));
				}
				
			} catch (Exception e) {
				success = false;
				e.printStackTrace();
				msg.add(MessageFormat.format(PropertiesFile.openPropertie().getProperty("operation.error"), e.getMessage()));
			}
			return new JsonResponse<>(success, msg, operatoreLog);
		}
	}

	public JsonResponse<Operatore> save(Operatore operatore) throws IOException {
		return this.getManager().operation(operatore,  DbOperation.INSERT);
	}


	public JsonResponse<Operatore> update(Operatore operatore) throws IOException {
		return this.getManager().operation(operatore, DbOperation.UPDATE);
	}
	
	public JsonResponse<Operatore> delete(Integer id) throws IOException {
		return this.getManager().operation(null, DbOperation.DELETE, id);
	}

	public JsonResponse<List<Operatore>> list() {
		List<String> msg = new ArrayList<String>();
		List<Operatore> data = new ArrayList<Operatore>();
		boolean success = true;
		try {
			data = this.getManager().list();
			msg.add(PropertiesFile.openPropertie().getProperty("operation.success"));
			
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
			msg.add(MessageFormat.format(PropertiesFile.openPropertie().getProperty("operation.error"), e.getMessage()));
		}
		return new JsonResponse<List<Operatore>>(success, msg, data);
	}
	
	
	
}


