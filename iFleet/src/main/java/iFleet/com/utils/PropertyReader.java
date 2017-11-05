package main.java.iFleet.com.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import javax.servlet.http.HttpServlet;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.traccar.Config;
import org.traccar.Context;
import org.traccar.ServerManager;

public class PropertyReader
  extends HttpServlet
{
  private static final Logger log = LogManager.getLogger(PropertyReader.class);
  private static Properties protocolProperties = new Properties();
  private static PropertyReader propertyObj;
  private static Config config;
  Properties mainProperties = new Properties();
  
  public static PropertyReader getInstance()
  {
    if (propertyObj == null) {
      propertyObj = new PropertyReader();
    }
    return propertyObj;
  }
  
  public Properties getProtocolPortsPorperty()
  {
    try
    {
      File confFile = new File("E:/Thilak/GPS/protocol_ports.properties");
      
      InputStream inputStream = new FileInputStream(confFile);
      
      protocolProperties.load(inputStream);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return protocolProperties;
  }
  
  public Properties loadProperties(String fileName)
  {
    Properties properties = new Properties();
    try
    {
      if ((fileName != null) && (!fileName.isEmpty()))
      {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("/" + fileName);
        if (inputStream != null) {
          properties.loadFromXML(inputStream);
        }
      }
    }
    catch (Exception localException) {}
    return properties;
  }
  
  public void loadConfig()
  {
    log.info("loading configration for tracker server");
    try
    {
      config = new Config();
      
      this.mainProperties.putAll(loadProperties(iFleetConstants.SERVER_CONFIG_FILE));
      this.mainProperties.putAll(loadProperties(iFleetConstants.DATABASE_CONFIG_FILE));
      this.mainProperties.putAll(loadProperties(iFleetConstants.DATABASE_QUERIES_FILE));
      this.mainProperties.putAll(loadProperties(iFleetConstants.PROTOCOL_CONFIG_FILE));
      log.info("osmand.port " + this.mainProperties.getProperty("osmand.port"));
      config.setProperties(this.mainProperties);
      
      boolean useEnvironmentVariables = (Boolean.parseBoolean(System.getenv("CONFIG_USE_ENVIRONMENT_VARIABLES"))) || (Boolean.parseBoolean(this.mainProperties.getProperty("config.useEnvironmentVariables")));
      config.setUseEnvironmentVariables(useEnvironmentVariables);
      Context.initalizer(config);
      Context.getServerManager().start();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
