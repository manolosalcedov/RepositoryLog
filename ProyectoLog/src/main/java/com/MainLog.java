package main.java.com;

import java.util.HashMap;
import java.util.Map;

public class MainLog {

	public static void main(String[] args) throws Exception {
		
		MainLog main = new MainLog();
		Map<String,String> dbParamsMap = main.setConection();
		JobLogger jobLogger = new JobLogger(true,true,true,true,true,true,dbParamsMap);
		jobLogger.Inicializar();
		String messageText = "Este es un mensaje";
		JobLogger.LogMessage(messageText, true, true, true);
	}
	
	public Map<String,String> setConection() {

		Map<String,String> dbParamsMap = new HashMap<String,String>();
		dbParamsMap.put("dbms", "mysql");
		dbParamsMap.put("serverName", "127.0.0.1");
		dbParamsMap.put("portNumber", "3306");
		dbParamsMap.put("userName", "root");
		dbParamsMap.put("password", "eu73M#.jmvf%7");
		dbParamsMap.put("logFileFolder","D:\\Manolo");
		return dbParamsMap;
	}
	
}
