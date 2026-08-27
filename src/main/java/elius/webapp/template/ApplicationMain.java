package elius.webapp.template;


import elius.webapp.framework.application.ApplicationLifecycle;
import elius.webapp.framework.engine.EngineWeb;
import elius.webapp.framework.engine.EngineConfig;

public class ApplicationMain {
public static void main(String[] args) {
    /* 
        WebConfig config = new WebConfig()
            .setPackageJAXRS("test.api")
            .setStaticResources("/Users/eliamilioni/develop/java/WebServer/src/html")
            .addFilter("/api/*", SicurezzaFilter.class); // Filtra SOLO le API, ignora il frontend statico;

        // Istanziamo il gestore del DB della nostra libreria
        final DbEngine dbEngine = new DbEngine();
*/
        EngineConfig config = new EngineConfig()
            .setStaticResources("src/main/resources/html")
            .setPackageJAXRS("elius.webapp.framework.api","elius.webapp.template.api");
        
        ApplicationLifecycle lifecycle = new ApplicationLifecycle() {
            @Override
            public void onStartup() throws Exception {
                System.out.println("-> [App] Inizializzazione Database tramite la libreria...");
                
                // Passiamo solo le stringhe, al resto ci pensa la libreria internamente
                /*
                dataSource = dbEngine.creaPool(
                    "jdbc:mysql://localhost:3306/il_tuo_db", 
                    "root", 
                    "password", 
                    15
                );*/
            }

            @Override
            public void onShutdown() throws Exception {
                System.out.println("-> [App] Spegnimento Database...");
                //dbEngine.chiudiPool();
            }
        };

        EngineWeb.startWebApp(8080, config, lifecycle);
    }    
}
