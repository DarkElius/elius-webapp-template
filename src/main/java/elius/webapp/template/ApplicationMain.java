package elius.webapp.template;


import elius.webapp.framework.application.ApplicationLifecycle;
import elius.webapp.framework.server.ServerWeb;
import elius.webapp.framework.server.ServerConfig;
import elius.webapp.framework.db.DBEngine;



public class ApplicationMain {
public static void main(String[] args) {


    /* 
        WebConfig config = new WebConfig()
            .setPackageJAXRS("test.api")
            .setStaticResources("/Users/eliamilioni/develop/java/WebServer/src/html")
            .addFilter("/api/*", SicurezzaFilter.class); // Filtra SOLO le API, ignora il frontend statico;


*/


        ServerConfig config = new ServerConfig()
            .setStaticResources("src/main/resources/html")
            .setPackageJAXRS("elius.webapp.framework.api","elius.webapp.template.api");
        

        ServerWeb.startWebApp(8080, config, new TemplateLifeCycle());
    }    
}
