
package com.syard.common.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropsUtil {

	private static Logger log = LoggerFactory.getLogger(PropsUtil.class);
	
	private static final String _dne_ext = "program.properties";
	
    private static  Properties prop = new Properties();
	    
   public static String get(String key){
	   String path = Thread.currentThread().getContextClassLoader()
				.getResource("").getPath();
		try {
			prop.load(new FileInputStream(path+_dne_ext));
		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	   if(StringUtils.isBlank(key)){
		   return "";
	   }
	   return prop.getProperty(key);
   }
   
   public static void main(String[] args) {
	  String str = PropsUtil.get("passwords.digest.encoding");
	  System.out.println(str);
   }
}
