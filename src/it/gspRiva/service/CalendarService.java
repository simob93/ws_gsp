package it.gspRiva.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import it.gspRiva.entity.Calendar;
import it.gspRiva.manager.CalendarManager;
import it.gspRiva.model.JsonResponse;
import it.gspRiva.utils.PropertiesFile;

public class CalendarService {

	public JsonResponse<List<Calendar>> list(String dal, String al) {
		List<String> msg = new ArrayList<String>();
		List<Calendar> data = new ArrayList<Calendar>();
		boolean success = true;
		try {
			data = new CalendarManager().list(dal, al);
			msg.add(PropertiesFile.openPropertie().getProperty("operation.success"));
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
			msg.add(MessageFormat.format(PropertiesFile.openPropertie().getProperty("operation.error"), e.getMessage()));
		}
		return new JsonResponse<List<Calendar>>(success, msg, data);
	}

}
