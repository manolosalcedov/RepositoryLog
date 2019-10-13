package main.java.com;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JobLogger {
	private static boolean logToFile;
	private static boolean logToConsole;
	private static boolean logMessage;
	private static boolean logWarning;
	private static boolean logError;
	private static boolean logToDatabase;
	private boolean initialized;
	private static Map<String,String> dbParams;
	private static Logger logger;

	public JobLogger(boolean logToFileParam, boolean logToConsoleParam, boolean logToDatabaseParam,
			boolean logMessageParam, boolean logWarningParam, boolean logErrorParam, Map<String,String> dbParamsMap) {
		
		logger = Logger.getLogger("MyLog");  
		logError = logErrorParam;
		logMessage = logMessageParam;
		logWarning = logWarningParam;
		logToDatabase = logToDatabaseParam;
		logToFile = logToFileParam;
		logToConsole = logToConsoleParam;
		dbParams = dbParamsMap;
		
	}
	
	public void Inicializar() {
		initialized = true;
		logger.info("initialized "+initialized);
	}
	
	public static void LogMessage(String messageText, boolean message, boolean warning, boolean error) throws Exception {
		// Se agrego estos booleanos para un control adecuado
		boolean encontro = false;
		boolean encontrado = false;
		messageText.trim();
		// esta seccion se prevee los posibles errores
		if (messageText == null || messageText.length() == 0) {
			return;
		}
		if (!logToConsole && !logToFile && !logToDatabase) {
			throw new Exception("Invalid configuration");
		}
		if ((!logError && !logMessage && !logWarning) || (!message && !warning && !error)) {
			throw new Exception("Error or Warning or Message must be specified");
		}
        // Se reaiza la conexion con la base de datos en este caso MySql con los parametros de conexion dados en el archivo de configuracion
		Connection connection = null;
		Properties connectionProps = new Properties();
		connectionProps.put("user", dbParams.get("userName"));
		connectionProps.put("password", dbParams.get("password"));

		connection = DriverManager.getConnection("jdbc:" + dbParams.get("dbms") + "://" + dbParams.get("serverName")
				+ ":" + dbParams.get("portNumber") + "/", connectionProps);
        // Se carga el valor t segun los valores booleanos para saber que tipo es
		// Se reordeno en esta forma message error warning
		int t = 0;
		if (message && logMessage) {
			if (!encontro) {
			     t = 1;
			     encontro = true;
			}
		}

		if (error && logError) {
			if (!encontro) {
				t = 2;
				encontro = true;
			}
		}

		if (warning && logWarning) {
			if (!encontro) {
				t = 3;
				encontro = true;
			}
		}

		Statement stmt = connection.createStatement();
        
		// Se cambio de null a vacio
		String l = "";
		File logFile = new File(dbParams.get("logFileFolder") + "/logFile.txt");
		if (!logFile.exists()) {
			logFile.createNewFile();
		}
		
		FileHandler fh = new FileHandler(dbParams.get("logFileFolder") + "/logFile.txt");
		ConsoleHandler ch = new ConsoleHandler();
		
		// Nunca se utilizaba el valor de l cual tenia la informacion mas completa por eso se utilizara este valor
		// Se reordeno en esta forma message error warning
		if (message && logMessage) {
			if (!encontrado) {
				l = l + "message " +DateFormat.getDateInstance(DateFormat.LONG).format(new Date()) + " " + messageText;
				encontrado = true;
			}
		}
		
		if (error && logError) {
			if (!encontrado) {
			    l = l + "error " + DateFormat.getDateInstance(DateFormat.LONG).format(new Date()) + " " + messageText;
			    encontrado = true;
			}
		}

		if (warning && logWarning) {
			if (!encontrado) {
				l = l + "warning " +DateFormat.getDateInstance(DateFormat.LONG).format(new Date()) + " " + messageText;
				encontrado = true;
			}
		}

		// Segun esto llena el archivo y carga el valor de l
		if(logToFile) {
			logger.addHandler(fh);
			logger.log(Level.INFO, l);
		}
		// Segun esto llena la consola y muestra el valor de l
		if(logToConsole) {
			logger.addHandler(ch);
			logger.log(Level.INFO, l);
		}
		// Se tuvo que modificar el armado del insert a una base de datos MySql y se detecto error en la sintaxis ya esta correjido
		// ademas se inserta el valor de l que es un mensaje mas completo y t representa el tipo de mensaje
		if(logToDatabase) {
			stmt.executeUpdate("insert into schemaprueba.Log_Values(messagelog,valuelog) values('" + l + "', '" + String.valueOf(t) + "')");
		}
	}
}
