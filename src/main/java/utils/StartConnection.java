package utils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StartConnection implements ServletContextListener {

    public RepositoryMgr repoMgr = new RepositoryMgr();

    /**
     * Initializes the connection when connecting to the DB
     * @param servletContextEvent
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        repoMgr.init("http://149.68.20.144:5820", "ctkr", "admin", "admin");
    }

    /**
     * Closes the connection when exiting the application
     * @param servletContextEvent
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        repoMgr.close();
    }
}
