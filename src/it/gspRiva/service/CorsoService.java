package it.gspRiva.service;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import it.gspRiva.emuns.DbOperation;
import it.gspRiva.entity.AnagraficaCorso;
import it.gspRiva.entity.Corso;
import it.gspRiva.entity.IscrittoCorso;
import it.gspRiva.exception.MyException;
import it.gspRiva.manager.AnagraficaCorsoManager;
import it.gspRiva.manager.CorsoManager;
import it.gspRiva.manager.IscrittiCorsoManager;
import it.gspRiva.model.GridListaCorsi;
import it.gspRiva.model.JsonResponse;
import it.gspRiva.model.ModelRegistrazioneCorso;
import it.gspRiva.model.Partecipanti;
import it.gspRiva.model.ResponsePrint;
import it.gspRiva.utils.Controlli;
import it.gspRiva.utils.PropertiesFile;

public class CorsoService {

	public CorsoManager getManager() {

		return new CorsoManager();
	}
	
	public JsonResponse<Corso> delete(Integer id) throws IOException {
		ModelRegistrazioneCorso regCorso = null;
		List<String>msg = new ArrayList<String>();
		try {
			regCorso = this.getManager().listIscrittiByIdCorso(id);
			List<Partecipanti> listPartecipanti = regCorso.getPartecipanti();
			for (Partecipanti partecipante: listPartecipanti) {
				this.getManager().rimuoviPartecipante(partecipante.getId());
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
			msg.add(MessageFormat.format(PropertiesFile.openPropertie().getProperty("operation.error"), e1.getMessage()));
			return new JsonResponse<Corso>(false, msg, null);
		} 
		return this.getManager().operation(null, DbOperation.SLIM_DELETE, id);
	}
	
	
	public JsonResponse<Corso> getById(Integer id) 
			throws IOException {
		return this.getManager().getById(id);
	}
	
	public JsonResponse<List<IscrittoCorso>> listIscritti() 
			throws IOException, MyException {
		
		List<String> msg = new ArrayList<String>();
		List<IscrittoCorso> data = new ArrayList<IscrittoCorso>();
		boolean success = true;
		try {
			data = new IscrittiCorsoManager().listIscritti();
			msg.add(PropertiesFile.openPropertie().getProperty("operation.success"));
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
			msg.add(MessageFormat.format(PropertiesFile.openPropertie().getProperty("operation.error"), e.getMessage()));
		}
		return new JsonResponse<List<IscrittoCorso>>(success, msg, data);
	}

	public JsonResponse<ModelRegistrazioneCorso> registra(ModelRegistrazioneCorso registrazioneCorso)
			throws IOException {
		
		List<String> msg = new ArrayList<String>();
		ModelRegistrazioneCorso data = null;
		boolean success = true;
		boolean error = false;
		try {
			
			AnagraficaCorsoManager anagraficaCorsoManager = new AnagraficaCorsoManager();
			AnagraficaCorso anagraficaCorso = null;
			List<Partecipanti> listPartecipanti = registrazioneCorso.getPartecipanti();
			
			if (registrazioneCorso.getCorso().getTipologia() == 1 && listPartecipanti != null && listPartecipanti.size() > 1) {
				error = true;
				msg.add("la tipologia corso <b>individuale</b> prevedere un solo iscritto al corso");
			} else {
			
				for (Partecipanti partecipanti : listPartecipanti) {
				
					 error = false;
					if (Controlli.isEmptyString(partecipanti.getDeletedData())) {
						anagraficaCorso = anagraficaCorsoManager.getById(partecipanti.getIdAnagraficaCorso()).getData();
						if (anagraficaCorso != null) {
							if (registrazioneCorso.getCorso().getTipologia() != anagraficaCorso.getTipologia()) {
								msg.add("il partecipante: <b>"+ anagraficaCorso.getNominativo() +"</b> risulta iscritto ad un altra tipologia di corso");
								error = true;
								break;
							}
						}
					}
				}
			}
			if (!error) {
				data = this.getManager().registra(registrazioneCorso);
				msg.add(PropertiesFile.openPropertie().getProperty("operation.success"));
			} else {
				success = false;
			}
			
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
			msg.add(MessageFormat.format(PropertiesFile.openPropertie().getProperty("operation.error"), e.getMessage()));
		}
		return new JsonResponse<ModelRegistrazioneCorso>(success, msg, data);
	}

	public JsonResponse<List<Corso>> list() 
			throws IOException {
		
		List<String> msg = new ArrayList<String>();
		List<Corso> data = new ArrayList<Corso>();
		boolean success = true;
		try {
			data = this.getManager().list(false, false, null, null, null);
			msg.add(PropertiesFile.openPropertie().getProperty("operation.success"));
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
			msg.add(MessageFormat.format(PropertiesFile.openPropertie().getProperty("operation.error"), e.getMessage()));
		}
		return new JsonResponse<List<Corso>>(success, msg, data);
	}

	public JsonResponse< List<GridListaCorsi> > listIscrittiByCorsi(String dal, String al, Integer tipologia, String convalidati, String escludiAnnullati) 
			throws MyException, IOException {
		
		List<String> msg = new ArrayList<String>();
		 List<GridListaCorsi>  data = null;
		boolean success = true;
		try {
			data = this.getManager().listIscrittiByCorsi(dal, al, tipologia, Controlli.stringCompareTo(convalidati, "T", false) == 0, Controlli.stringCompareTo(escludiAnnullati, "T", false) == 0);
			msg.add(PropertiesFile.openPropertie().getProperty("operation.success"));
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
			msg.add(MessageFormat.format(PropertiesFile.openPropertie().getProperty("operation.error"), e.getMessage()));
		}
		return new JsonResponse< List<GridListaCorsi> >(success, msg, data);
	}

	public JsonResponse<ModelRegistrazioneCorso> listIscrittiByIdCorso(Integer idCorso) throws IOException {
		
		List<String> msg = new ArrayList<String>();
		ModelRegistrazioneCorso data = null;
		boolean success = true;
		try {
			data = this.getManager().listIscrittiByIdCorso(idCorso);
			msg.add(PropertiesFile.openPropertie().getProperty("operation.success"));
		} catch (MyException e) {
			success = false;
			e.printStackTrace();
			msg.add(MessageFormat.format(PropertiesFile.openPropertie().getProperty("operation.error"), e.getMessage()));
		}
		return new JsonResponse<ModelRegistrazioneCorso>(success, msg, data);
	}

	public JsonResponse<Boolean> convalida(Integer id) throws IOException {
		List<String> msg = new ArrayList<String>();
		Boolean data = null;
		boolean success = true;
		try {
			data = this.getManager().convalida(id);
			msg.add(PropertiesFile.openPropertie().getProperty("operation.success"));
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
			msg.add(MessageFormat.format(PropertiesFile.openPropertie().getProperty("operation.error"), e.getMessage()));
		}
		return new JsonResponse<Boolean>(success, msg, data);
	}

	public JsonResponse<Partecipanti> rimuoviPartecipante(Integer id) throws IOException {
		List<String> msg = new ArrayList<String>();
		Partecipanti data = null;
		boolean success = true;
		try {
			data = this.getManager().rimuoviPartecipante(id);
			msg.add(PropertiesFile.openPropertie().getProperty("operation.success"));
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
			msg.add(MessageFormat.format(PropertiesFile.openPropertie().getProperty("operation.error"), e.getMessage()));
		}
		return new JsonResponse<Partecipanti>(success, msg, data);
	}

	public JsonResponse<ResponsePrint> printPresenzeCorso(Integer idCorso) {
		List<String> msg = new ArrayList<String>();
		ResponsePrint data = null;
		boolean success = true;
		try {
			data = this.getManager().printPresenzeCorso(idCorso);
			msg.add(PropertiesFile.openPropertie().getProperty("operation.success"));
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
			msg.add(MessageFormat.format(PropertiesFile.openPropertie().getProperty("operation.error"), e.getMessage()));
		}
		return new JsonResponse<ResponsePrint>(success, msg, data);
	}

	public JsonResponse<ResponsePrint> printCorso(Integer idCorso) {
		
		List<String> msg = new ArrayList<String>();
		ResponsePrint data = null;
		boolean success = true;
		try {
			data = this.getManager().printCorso(idCorso);
			msg.add(PropertiesFile.openPropertie().getProperty("operation.success"));
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
			msg.add(MessageFormat.format(PropertiesFile.openPropertie().getProperty("operation.error"), e.getMessage()));
		}
		return new JsonResponse<ResponsePrint>(success, msg, data);
	}
}
