package it.gspRiva.service;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import it.gspRiva.emuns.DbOperation;
import it.gspRiva.entity.AnagraficaCorso;
import it.gspRiva.exception.MyException;
import it.gspRiva.manager.AnagraficaCorsoManager;
import it.gspRiva.model.Iscritti;
import it.gspRiva.model.JsonResponse;
import it.gspRiva.model.KeyValue;
import it.gspRiva.model.ModelCertificatoMedico;
import it.gspRiva.utils.PropertiesFile;

public class AnagraficaCorsoService {
	
	public  AnagraficaCorsoManager getManager() {
		
		return new AnagraficaCorsoManager();
	}
 
	public JsonResponse<AnagraficaCorso> save(AnagraficaCorso operatore) throws IOException {
		return this.getManager().operation(operatore,  DbOperation.INSERT);
	}

	public JsonResponse<AnagraficaCorso> update(AnagraficaCorso operatore) throws IOException {
		return this.getManager().operation(operatore, DbOperation.UPDATE);
	}
	
	public JsonResponse<AnagraficaCorso> delete(Integer id) throws IOException {
		return this.getManager().operation(null, DbOperation.SLIM_DELETE, id);
	}

	public JsonResponse<AnagraficaCorso> getById(Integer id) throws IOException {
		return this.getManager().getById(id);
	}

	public JsonResponse<List<AnagraficaCorso>> listByIdAnagrafica(Integer id) throws IOException {
		
		List<String> msg = new ArrayList<String>();
		List<AnagraficaCorso> listAnagraficaCorso = new ArrayList<AnagraficaCorso>();
		boolean success = true;
		try {
			
			listAnagraficaCorso = this.getManager().listByIdAnagrafica(id);
			msg.add(PropertiesFile.openPropertie().getProperty("operation.success"));
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
			msg.add(MessageFormat.format(PropertiesFile.openPropertie().getProperty("operation.error"), e.getMessage()));
		}
		return new JsonResponse<List<AnagraficaCorso>>(success, msg, listAnagraficaCorso);
	}
	
	public JsonResponse<List<Iscritti>> search(String dal, String al, Integer tipologia, HashMap<String, String> has, String nominativo) throws MyException, IOException {
		List<String> msg = new ArrayList<String>();
		boolean success = true;
		List<Iscritti> listIscritti = null;
		try {
			listIscritti = this.getManager().search(dal, al, tipologia, has, nominativo);
			msg.add(PropertiesFile.openPropertie().getProperty("operation.success"));
			
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
			msg.add(MessageFormat.format(PropertiesFile.openPropertie().getProperty("operation.error"), e.getMessage()));
		}
		return new JsonResponse<List<Iscritti>>(success, msg, listIscritti);
	}

	public JsonResponse<ModelCertificatoMedico> checkCertificatoMedico(Integer idAnagrafica) {
		List<String> msg = new ArrayList<String>();
		boolean success = true;
		ModelCertificatoMedico data = null;
		try {
			data = this.getManager().checkCertificatoMedico(idAnagrafica);
			msg.add(PropertiesFile.openPropertie().getProperty("operation.success"));
			
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
			msg.add(MessageFormat.format(PropertiesFile.openPropertie().getProperty("operation.error"), e.getMessage()));
		}
		return new JsonResponse<ModelCertificatoMedico>(success, msg, data);
	}

	@SuppressWarnings("rawtypes")
	public JsonResponse<List<KeyValue>> checkCertificatiScaduti() {
		List<String> msg = new ArrayList<String>();
		boolean success = true;
		List<KeyValue> data = null;
		try {
			data = this.getManager().checkCertificatiScaduti();
			msg.add(PropertiesFile.openPropertie().getProperty("operation.success"));
			
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
			msg.add(MessageFormat.format(PropertiesFile.openPropertie().getProperty("operation.error"), e.getMessage()));
		}
		return new JsonResponse<List<KeyValue>>(success, msg, data);
	}
	
}
