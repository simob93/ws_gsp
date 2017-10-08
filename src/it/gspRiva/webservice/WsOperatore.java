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

import it.gspRiva.entity.Operatore;
import it.gspRiva.manager.OperatoreManager;
import it.gspRiva.model.JsonResponse;
import it.gspRiva.service.OperatoreService;

@Path("/operatore")
public class WsOperatore {
	private  OperatoreService serviceOperatore;
	private  OperatoreManager operatoreManager;
	
	public WsOperatore (){
		
		if(serviceOperatore == null) {
			serviceOperatore = new OperatoreService();
		}
		
		if(operatoreManager == null) {
			operatoreManager = new OperatoreManager();
		}
	}
	
	@Path("/auth")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse<Operatore> getListaModuli(@QueryParam("username") String username, 
												  @QueryParam("password") String password) throws IOException {
		
		return serviceOperatore.getOperatore(username, password);
	} 
	
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse<List<Operatore>> list() throws IOException {
		return serviceOperatore.list();
	}
	
	@POST
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public JsonResponse<Operatore> update(Operatore operatore) throws IOException {
		return serviceOperatore.update(operatore);
	}
	
	@POST
	@Path("/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public JsonResponse<Operatore> save(Operatore operatore) throws IOException{
		return serviceOperatore.save(operatore);
	}
	
	@GET
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse<Operatore> delete(@QueryParam("id") Integer id) throws IOException {
		return serviceOperatore.delete(id);
	}
}