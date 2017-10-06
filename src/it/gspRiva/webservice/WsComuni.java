package it.gspRiva.webservice;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import it.gspRiva.entity.Comuni;
import it.gspRiva.model.JsonResponse;
import it.gspRiva.service.ComuniService;
@Path("/comuni")
public class WsComuni {
public WsComuni() {}
	
	public ComuniService getService() {
		return new ComuniService();
	}
	
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse<List<Comuni>> list() throws IOException{
		return this.getService().list();			
	}
}
