package it.gspRiva.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class StandardUtils {
	
	public static Date currentDateTime() {
		Date d = new Date();
		return d;
	}
	
	public static int dateBetween(Date d1, Date d2, String type) {
		
		long diffDate = d2.getTime() - d1.getTime();
		long result = -1;
		switch (type) {
		case "d":
			result = TimeUnit.DAYS.convert(diffDate, TimeUnit.MILLISECONDS);
			break;
		default:
			break;
		}
		return (int)result;
		
	}
	
	public static List<String> pulisciStringa(String stringa) {
		String[] result = {};
		List<String> tmpResult = new ArrayList<String>();
		if (!Controlli.isEmptyString(stringa)) {
			result = stringa.split(";");
			for (int i = 0; i < result.length; i++) {
				if (Controlli.stringCompareTo(result[i], "", false) == -1) {
					tmpResult.add(result[i]);
				} 
			}
		}
		return tmpResult;
		
	}
	
	
}
