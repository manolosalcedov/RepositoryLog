package test.java.com;

import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.beans.factory.annotation.Autowired;
import main.java.com.JobLogger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = logConfigUtil.class) 
@ActiveProfiles("test")
public class JobLoggerTest {
	
	Map<String,String> dbParamsMap;
	
	@Autowired
    private logConfigUtil config;
	
	@BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
       
    	dbParamsMap = setConection(config.getDbms(), 
    			                   config.getServerName(), 
    			                   config.getPortNumber(), 
    			                   config.getUserName(), 
    			                   config.getPassword(), 
    			                   config.getLogFileFolder());
        
    }
    
    @After
    public void tearDown() {
    	
    }

	 /**
	 * @throws Exception 
     * Test of LogMessage method, of class JobLogger.
     * @throws Exception
     */
    @Test
    public void testLogMessage() throws Exception {
    	
    	
		JobLogger jobLogger = new JobLogger(true,true,true,true,true,true,dbParamsMap);
		Assert.assertNotNull(jobLogger);
		jobLogger.Inicializar();
		String messageText = "Este es un mensaje";
		JobLogger.LogMessage(messageText, true, true, true);
		
    }
	
	
	public Map<String,String> setConection(String dbms, String serverName, String portNumber, String userName, String password, String logFileFolder) {

		Map<String,String> dbParamsMap = new HashMap<String,String>();
		dbParamsMap.put("dbms", dbms);
		dbParamsMap.put("serverName", serverName);
		dbParamsMap.put("portNumber", portNumber);
		dbParamsMap.put("userName", userName);
		dbParamsMap.put("password", password);
		dbParamsMap.put("logFileFolder", logFileFolder);
		return dbParamsMap;
	}
	
}
