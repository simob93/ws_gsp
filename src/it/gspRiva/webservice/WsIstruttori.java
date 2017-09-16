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

import it.gspRiva.entity.Istruttori;
import it.gspRiva.exception.MyException;
import it.gspRiva.model.JsonResponse;
import it.gspRiva.service.IstruttoriService;

@Path("/istruttori")
public class WsIstruttori {
	
	public WsIstruttori () {}
	
	public IstruttoriService getService() {
		return new IstruttoriService();
		
	}
	
	
	@Path("/list")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse<List<Istruttori>> list() throws IOException, MyException {
		return this.getService().list();
	} 
	
	@POST
	@Path("/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public JsonResponse<Istruttori> save(Istruttori istruttori) throws IOException {
		return this.getService().save(istruttori);
	}
	
	@POST
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public JsonResponse<Istruttori> update(Istruttori istruttori) throws IOException {
		return this.getService().update(istruttori);
	}
	
	@GET
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse<Istruttori> delete(@QueryParam("id") Integer id) throws IOException {
		return this.getService().delete(id);
	}
	
}
