package main.java.iFleet.com;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import main.java.iFleet.com.utils.PropertyReader;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.traccar.ServerManager;

public class MainClassListener
  implements ServletContextListener
{
  private static final Logger log = LogManager.getLogger(MainClassListener.class);
  
  public void contextDestroyed(ServletContextEvent arg0) {}
  
  public void contextInitialized(ServletContextEvent arg0)
  {
    log.info("===================application Started=========================");
    try
    {
      PropertyReader.getInstance().loadConfig();
      ServerManager localServerManager = new ServerManager();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
