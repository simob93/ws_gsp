package it.gspRiva.service;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import it.gspRiva.emuns.DbOperation;
import it.gspRiva.entity.Anagrafica;
import it.gspRiva.manager.AnagraficaManager;
import it.gspRiva.model.JsonResponse;
import it.gspRiva.utils.PropertiesFile;

public class AnagraficaService {
	
	public  AnagraficaManager getManager() {
		
		return new AnagraficaManager();
	}
 
	public JsonResponse<Anagrafica> save(Anagrafica anagrafica) throws IOException {
		return this.getManager().operation(anagrafica,  DbOperation.INSERT);
	}

	public JsonResponse<Anagrafica> update(Anagrafica anagrafica) throws IOException {
		return this.getManager().operation(anagrafica, DbOperation.UPDATE);
	}
	
	public JsonResponse<Anagrafica> delete(Integer id) throws IOException {
		return this.getManager().operation(DbOperation.DELETE, id);
	}

	public JsonResponse<Anagrafica> getById(Integer id) throws IOException {
		return this.getManager().getById(id);
	}

	public JsonResponse<List<Anagrafica>> list(String search) throws IOException {
		List<String> msg = new ArrayList<String>();
		List<Anagrafica> listAnagrafica = new ArrayList<Anagrafica>();
		boolean success = true;
		try {
			
			listAnagrafica = this.getManager().list(search);
			msg.add(PropertiesFile.openPropertie().getProperty("operation.success"));
			
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
			msg.add(MessageFormat.format(PropertiesFile.openPropertie().getProperty("operation.error"),  e.getMessage()));
		}
		return new JsonResponse<List<Anagrafica>>(success, msg, listAnagrafica);
	}
	
}
