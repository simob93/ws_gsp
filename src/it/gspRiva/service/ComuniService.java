package it.gspRiva.service;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import it.gspRiva.entity.Anagrafica;
import it.gspRiva.entity.Comuni;
import it.gspRiva.manager.ComuniManager;
import it.gspRiva.manager.CorsoManager;
import it.gspRiva.model.JsonResponse;
import it.gspRiva.utils.PropertiesFile;

public class ComuniService {
	public ComuniManager getManager() {

		return new ComuniManager();
	}
	
	public JsonResponse<List<Comuni>> list() throws IOException {
		List<String> msg = new ArrayList<String>();
		List<Comuni> data = null;
		boolean success = true;
		try {
			
			data = this.getManager().list();
			msg.add(PropertiesFile.openPropertie().getProperty("operation.success"));
			
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
			msg.add(MessageFormat.format(PropertiesFile.openPropertie().getProperty("operation.error"),  e.getMessage()));
		}
		return new JsonResponse<List<Comuni>>(success, msg, data);
	}
}
