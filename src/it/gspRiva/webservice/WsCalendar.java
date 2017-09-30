package it.gspRiva.webservice;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import it.gspRiva.entity.Calendar;
import it.gspRiva.model.JsonResponse;
import it.gspRiva.service.CalendarService;

@Path("/calendar")
public class WsCalendar {
	public WsCalendar() {}
	
	public CalendarService getService() {
		return new CalendarService();
	}
	
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse<List<Calendar>> list(@QueryParam("dal") String dal,
											 @QueryParam("al") String al) throws IOException{
		return this.getService().list(dal, al);
			
	}
	
}
