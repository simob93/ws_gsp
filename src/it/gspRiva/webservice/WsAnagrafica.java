package it.gspRiva.webservice;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import it.gspRiva.entity.Anagrafica;
import it.gspRiva.entity.Corso;
import it.gspRiva.model.JsonResponse;
import it.gspRiva.service.AnagraficaService;

@Path("/anagrafica")
public class WsAnagrafica {
	
	private  AnagraficaService anagraficaService;
	
	public WsAnagrafica (){
		if (anagraficaService == null) {
			anagraficaService = new AnagraficaService();
		}
	}
	
	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse<Anagrafica> getById(@QueryParam("id") Integer id) throws IOException {
		return anagraficaService.getById(id);
	}
	
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse<List<Anagrafica>> list(@QueryParam("search") String search) throws IOException {
		return anagraficaService.list(search);
	}
	
	
	@POST
	@Path("/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public JsonResponse<Anagrafica> save(Anagrafica anagrafica) throws IOException {
		return anagraficaService.save(anagrafica);
	}
	
	@POST
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public JsonResponse<Anagrafica> update(Anagrafica anagrafica) throws IOException {
		return anagraficaService.update(anagrafica);
	}
	
	@GET
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse<Anagrafica> delete(@QueryParam("id") Integer id) throws IOException {
		return anagraficaService.delete(id);
	}
	
}
