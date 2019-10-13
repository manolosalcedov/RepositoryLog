package test.java.com;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@ConfigurationProperties(prefix = "config.dblog")
public class logConfigUtil {

	private String dbms = "mysql";
	private String serverName = "127.0.0.1";
	private String portNumber = "3306";
	private String userName = "root";
	private String password = "eu73M#.jmvf%7";
	private String logFileFolder = "D:\\Manolo";
	
	public String getDbms() {
		return dbms;
	}
	public void setDbms(String dbms) {
		this.dbms = dbms;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getPortNumber() {
		return portNumber;
	}
	public void setPortNumber(String portNumber) {
		this.portNumber = portNumber;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLogFileFolder() {
		return logFileFolder;
	}
	public void setLogFileFolder(String logFileFolder) {
		this.logFileFolder = logFileFolder;
	}
	
	
	
}
