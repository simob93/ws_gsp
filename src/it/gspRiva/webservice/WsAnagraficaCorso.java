package it.gspRiva.webservice;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import it.gspRiva.entity.AnagraficaCorso;
import it.gspRiva.exception.MyException;
import it.gspRiva.model.Iscritti;
import it.gspRiva.model.JsonResponse;
import it.gspRiva.model.ModelCertificatoMedico;
import it.gspRiva.service.AnagraficaCorsoService;
import it.gspRiva.utils.Controlli;

@Path("/anagraficaCorso")
public class WsAnagraficaCorso {
	
	private  AnagraficaCorsoService anagraficaCorsoService;
	
	public WsAnagraficaCorso (){
		if (anagraficaCorsoService == null) {
			anagraficaCorsoService = new AnagraficaCorsoService();
		}
	}
	
	
	
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse<List<Iscritti>> search(
			@QueryParam("dal") String dal, 
            @QueryParam("al") String al,
            @DefaultValue("2") @QueryParam("tipologia") Integer tipologia,
            @QueryParam("lunedi") String lunedi,
            @QueryParam("martedi") String martedi,
            @QueryParam("mercoledi") String mercoledi,
            @QueryParam("giovedi") String giovedi,
            @QueryParam("venerdi") String venerdi,
            @QueryParam("sabato") String sabato,
            @QueryParam("numLezioni") Integer numLezioni,
            @QueryParam("minLezioni") Integer minLezioni,
            @QueryParam("personalizzato") String personalizzato) throws IOException, MyException {
		
		HashMap<String, String> has = new HashMap<String, String>();
		/* filtro per giorni settimana */
		if (Controlli.stringCompareTo(lunedi, "T", false) == 0) {
			has.put("lunedi", lunedi);
		}
		if (Controlli.stringCompareTo(martedi, "T", false) == 0) {
			has.put("martedi", martedi);
		}
		if (Controlli.stringCompareTo(mercoledi, "T", false) == 0) {
			has.put("mercoledi", mercoledi);
		}
		if (Controlli.stringCompareTo(giovedi, "T", false) == 0) {
			has.put("giovedi", giovedi);
		}
		if (Controlli.stringCompareTo(venerdi, "T", false) == 0) {
			has.put("venerdi", venerdi);
		}
		if (Controlli.stringCompareTo(sabato, "T", false) == 0) {
			has.put("sabato", sabato);
		}
		if (Controlli.stringCompareTo(personalizzato, "T", false) == 0) {
			has.put("personalizzato", personalizzato);
		}
		if (numLezioni != null) {
			has.put("numeroLezioni", numLezioni.toString());
		}
		if (minLezioni != null) {
			has.put("minutiLezioni", minLezioni.toString());
		}
		return anagraficaCorsoService.search(dal, al, tipologia, has);
	}

	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse<List<AnagraficaCorso>> listByIdAnagrafica(@QueryParam("id") Integer id) throws IOException {
		return anagraficaCorsoService.listByIdAnagrafica(id);
	}
	
	@GET
	@Path("/checkCertificatoMedico")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse<ModelCertificatoMedico> checkCertificatoMedico(@QueryParam("idAnagrafica") Integer idAnagrafica) throws IOException {
		return anagraficaCorsoService.checkCertificatoMedico(idAnagrafica);
	}
	
	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse<AnagraficaCorso> getById(@QueryParam("id") Integer id) throws IOException {
		return anagraficaCorsoService.getById(id);
	}
	
	
	@POST
	@Path("/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public JsonResponse<AnagraficaCorso> save(AnagraficaCorso anagrafica) throws IOException {
		return anagraficaCorsoService.save(anagrafica);
	}
	
	@POST
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public JsonResponse<AnagraficaCorso> update(AnagraficaCorso anagrafica) throws IOException {
		return anagraficaCorsoService.update(anagrafica);
	}
	
	@GET
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse<AnagraficaCorso> delete(@QueryParam("id") Integer id) throws IOException {
		return anagraficaCorsoService.delete(id);
	}
	
}
