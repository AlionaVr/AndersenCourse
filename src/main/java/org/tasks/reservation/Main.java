package org.tasks.reservation;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.tasks.reservation.config.ProjectConfig;

public class Main {

//    public static void main(String[] args) {
//        var springContext = new AnnotationConfigApplicationContext(ProjectConfig.class);
//        System.out.println("Registered Beans: " + Arrays.toString(springContext.getBeanDefinitionNames()));
//        MenuLauncher menuLauncher = springContext.getBean(MenuLauncher.class);
//        menuLauncher.showMainMenu();
//    }


    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getConnector();

        String contextPath = "";
        Context context = tomcat.addContext(contextPath, null);

        // Set up Spring application context
        AnnotationConfigWebApplicationContext springContext = new AnnotationConfigWebApplicationContext();
        springContext.register(ProjectConfig.class);

        // Create and configure the DispatcherServlet
        DispatcherServlet dispatcherServlet = new DispatcherServlet(springContext);
        Tomcat.addServlet(context, "dispatcher", dispatcherServlet).setLoadOnStartup(1);
        context.addServletMappingDecoded("/", "dispatcher");

        tomcat.start();

        System.out.println("Application started at: http://localhost:8080/");
        tomcat.getServer().await();
    }
}
