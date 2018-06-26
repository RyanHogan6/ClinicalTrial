package utils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StartConnection implements ServletContextListener {

    public RepositoryMgr repoMgr = new RepositoryMgr();

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        repoMgr.init("http://149.68.20.144:5820", "ctkr", "admin", "admin");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        repoMgr.close();
    }
}
