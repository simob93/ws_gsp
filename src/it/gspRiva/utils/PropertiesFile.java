package it.gspRiva.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

import org.hibernate.SessionFactory;

public class PropertiesFile  {

	String result = "";
	static InputStream inputStream;
	
	private static Properties prop = null;
	
	public static Properties openPropertie () {
		if (prop == null) {
			prop = loadPropertyFiel();
		}
		return prop;
	}
	
	private static  Properties loadPropertyFiel() {
		try {
			prop = new Properties();
			String propFileName = "config.properties";
			inputStream = PropertiesFile.class.getClassLoader().getResourceAsStream(propFileName);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
			// get the property value and print it out
		
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
		return prop; 
	}
}