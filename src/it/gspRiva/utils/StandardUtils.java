package it.gspRiva.utils;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
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
	
	public static <T> ResponsePrint doPrint(HashMap<String, Object> params, String fileName, List<T> dataSource) {
		JasperReport jasperReport = null;
		JasperPrint jasperPrint = null;
		File catalinaBase = null;
		ResponsePrint data = new ResponsePrint();
		try {
			
			catalinaBase = new File( System.getProperty( "catalina.base" ) ).getAbsoluteFile();
			InputStream is = PropertiesFile.class.getClassLoader().getResourceAsStream("/it/gspRiva/report/"+ fileName +".jrxml");
			jasperReport = JasperCompileManager.compileReport(is);
			jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JRBeanCollectionDataSource(dataSource));
			JasperExportManager.exportReportToPdfFile(jasperPrint, catalinaBase + "/webapps/rpt/"+ fileName +".pdf");
			data.setPathFile("../rpt/"+ fileName +".pdf");			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
}
