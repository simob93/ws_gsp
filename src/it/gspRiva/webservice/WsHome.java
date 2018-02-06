package it.gspRiva.webservice;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import it.gspRiva.model.JsonResponse;
import it.gspRiva.model.KeyValue;
import it.gspRiva.service.AnagraficaCorsoService;

@Path("/home")
public class WsHome {

private  AnagraficaCorsoService anagraficaCorsoService;
	
	public WsHome (){
		if (anagraficaCorsoService == null) {
			anagraficaCorsoService = new AnagraficaCorsoService();
		}
	}
	/**
	 * ritorna tutti gli iscritti con certificato medico scaduto
	 * @return
	 * @throws IOException
	 */
	@GET
	@Path("/certificatiScaduti/src")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse<List<KeyValue>> src() throws IOException {
		return anagraficaCorsoService.checkCertificatiScaduti();
	}
}
