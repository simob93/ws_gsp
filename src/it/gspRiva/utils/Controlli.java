package it.gspRiva.utils;

import java.util.List;

public class Controlli {
	public static <T> boolean isEmptyList (List<T> list) {
		return list == null || list.size() == 0;
	}
	
	public static  <T> boolean isEmptyString (String str) {
		boolean vuota = true;
		if (str != null) {
			vuota =  str.compareTo("") == 0;
		}
		return vuota;
	}
	
	public static int stringCompareTo(String str1, String str2, boolean ignoreCase) {
		String str = "";
		if (isEmptyString(str2)) {
			str2 = str;
		}
		if (isEmptyString(str1)) {
			str1 = str;
		}
		if(!ignoreCase) {
			return str1.compareTo(str2) == 0 ? 0 : -1;
		} else {
			return str1.compareToIgnoreCase(str2) == 0 ? 0 : -1;
		}
	}
}
