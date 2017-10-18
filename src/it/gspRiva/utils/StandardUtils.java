package it.gspRiva.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import it.gspRiva.model.PrintSchedaCorso;
import it.gspRiva.model.ResponsePrint;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class StandardUtils {
	
	public static Date currentDateTime() {
		Date d = new Date();
		return d;
	}
	
	public static Date azzeraMinutiOreSecondi(Date data) {
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(data);
		 cal.set(Calendar.HOUR_OF_DAY, 0);
		 cal.set(Calendar.MINUTE, 0);
		 cal.set(Calendar.SECOND, 0);
		return cal.getTime();
		
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
	
	public static <T> ResponsePrint doPrint(HashMap<String, Object> params, String fileName, List<T> dataSource) throws IOException {
		JasperReport jasperReport = null;
		JasperPrint jasperPrint = null;
		File catalinaBase = null;
		InputStream is = null;
		ResponsePrint data = new ResponsePrint();
		try {
			
			
			catalinaBase = new File( System.getProperty( "catalina.base" ) ).getAbsoluteFile();
			is = PropertiesFile.class.getClassLoader().getResourceAsStream("/it/gspRiva/report/"+ fileName +".jrxml");
			
			params.put("SUBREPORT_DIR", catalinaBase + "\\webapps\\gspRiva\\WEB-INF\\classes\\it\\gspRiva\\report\\");
			params.put("IMAGE_PATH", catalinaBase + "\\webapps\\rpt\\image\\");
			
			
			jasperReport = JasperCompileManager.compileReport(is);
			jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JRBeanCollectionDataSource(dataSource));
			System.out.println(catalinaBase);
			JasperExportManager.exportReportToPdfFile(jasperPrint, catalinaBase + "/webapps/rpt/"+ fileName +".pdf");
			data.setPathFile("../rpt/"+ fileName +".pdf");			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			is.close();
		}
		return data;
	}
}
