package it.gspRiva.service;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import it.gspRiva.emuns.DbOperation;
import it.gspRiva.entity.Istruttori;
import it.gspRiva.exception.MyException;
import it.gspRiva.manager.IstruttoriManager;
import it.gspRiva.model.JsonResponse;
import it.gspRiva.utils.PropertiesFile;

public class IstruttoriService {
	
	public IstruttoriManager getManager() {
		return new IstruttoriManager();
	}
	
	public JsonResponse<Istruttori> save(Istruttori istruttori) throws IOException {
		return this.getManager().operation(istruttori,  DbOperation.INSERT);
	}

	public JsonResponse<Istruttori> update(Istruttori istruttori) throws IOException {
		return this.getManager().operation(istruttori, DbOperation.UPDATE);
	}
	
	public JsonResponse<Istruttori> delete(Integer id) throws IOException {
		return this.getManager().operation(null, DbOperation.DELETE, id);
	}

	public JsonResponse<Istruttori> getById(Integer id) throws IOException {
		return this.getManager().getById(id);
	}

	public JsonResponse<List<Istruttori>> list() throws IOException, MyException {
		
		List<String> msg = new ArrayList<String>();
		List<Istruttori> data = new ArrayList<Istruttori>();
		boolean success = true;
		try {
			data = this.getManager().list();
			msg.add(PropertiesFile.openPropertie().getProperty("operation.success"));
			
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
			msg.add(MessageFormat.format(PropertiesFile.openPropertie().getProperty("operation.error"), e.getMessage()));
		}
		return new JsonResponse<List<Istruttori>>(success, msg, data);
	}

}
