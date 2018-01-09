package it.gspRiva.webservice;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import it.gspRiva.entity.Corso;
import it.gspRiva.entity.IscrittoCorso;
import it.gspRiva.exception.MyException;
import it.gspRiva.model.GridListaCorsi;
import it.gspRiva.model.JsonResponse;
import it.gspRiva.model.ModelRegistrazioneCorso;
import it.gspRiva.model.Partecipanti;
import it.gspRiva.model.ResponsePrint;
import it.gspRiva.service.CorsoService;

@Path("/corso")
public class WsCorso {
		
	public WsCorso (){}
	
	public CorsoService getService() {
		return new CorsoService();
	}
	
	/**
	 * 
	 * @return
	 * @throws IOException
	 * @throws MyException
	 */
	@GET
	@Path("/listIscritti")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse<List<IscrittoCorso>> listIscritti() throws IOException, MyException{
		return this.getService().listIscritti();
			
	}
	
	/**
	 *  chiamata per la deshaborad 
	 * @param dal
	 * @param al
	 * @param tipologia
	 * @param corsiConvalidati
	 * @param escludiAnnullati
	 * @return
	 * @throws IOException
	 * @throws MyException
	 */
	@GET
	@Path("/partecipanti/list")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse< List<GridListaCorsi> > listIscrittiByCorsi(@QueryParam("dal") String dal,
																	  @QueryParam("al") String al,
																	  @QueryParam("tipologia") Integer tipologia,
																	  @DefaultValue("F") @QueryParam("escludiConvalidati") String corsiConvalidati,
																	  @DefaultValue("T") @QueryParam("escludiAnnullati") String escludiAnnullati,
																	  @QueryParam("idIstruttore") Integer idIstruttore
			) throws IOException, MyException {
		return this.getService().listIscrittiByCorsi(dal, al, tipologia, corsiConvalidati, escludiAnnullati, idIstruttore);
			
	}
	
	@GET
	@Path("/listIscrittiByIdCorso")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse<ModelRegistrazioneCorso> listIscrittiByIdCorso(
			@QueryParam("idCorso") Integer idCorso) throws IOException, MyException {
		return this.getService().listIscrittiByIdCorso(idCorso);
			
	}
	
	/**
	 * 
	 * @return
	 * @throws IOException
	 * @throws MyException
	 */
	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse<Corso> get(
			@QueryParam("id") Integer id) throws IOException{
		return this.getService().getById(id);
			
	}
	
	/**
	 * 
	 * @param registrazioneCorso
	 * @return
	 * @throws IOException
	 */
	@POST
	@Path("/registra")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public JsonResponse<ModelRegistrazioneCorso> save(ModelRegistrazioneCorso registrazioneCorso) throws IOException {
		return this.getService().registra(registrazioneCorso);
	}
	
	/**
	 * 
	 * @param registrazioneCorso
	 * @return
	 * @throws IOException
	 */
	@GET
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse<Corso> delete(@QueryParam("id") Integer id) throws IOException {
		return this.getService().delete(id);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@GET
	@Path("/partecipanti/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse<Partecipanti> rimuoviPartecipante(
			@QueryParam("id") Integer id) throws IOException{
		return this.getService().rimuoviPartecipante(id);
			
	}

	
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@GET
	@Path("/convalida")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse<Boolean> convalida(
			@QueryParam("id") Integer id) throws IOException{
		return this.getService().convalida(id);
			
	}
	
	@GET
	@Path("/report/presenzeCorsi")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse<ResponsePrint> printPresenzeCorsi(
			@QueryParam("idCorso") Integer idCorso) throws IOException{
		return this.getService().printPresenzeCorso(idCorso);
			
	}
	
	@GET
	@Path("/report/dettaglioCorso")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse<ResponsePrint> printCorso(
			@QueryParam("idCorso") Integer idCorso) throws IOException{
		return this.getService().printCorso(idCorso);
			
	}
}
