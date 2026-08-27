package elius.webapp.template;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import elius.webapp.framework.application.ApplicationLifecycle; 
import elius.webapp.framework.db.DBEngine;
import elius.webapp.framework.properties.PropertiesManager;
import elius.webapp.framework.properties.PropertiesManagerFactory;
import elius.webapp.template.db.DBTables;

import javax.sql.DataSource;

public class TemplateLifeCycle implements ApplicationLifecycle {

    // Get logger
	private static Logger logger = LogManager.getLogger(TemplateLifeCycle.class);

	// Properties file
	private PropertiesManager appProperties;
	
	// Initialization status
	private boolean initialized;

    private final DBEngine dbEngine = new DBEngine();
    private static DataSource dataSource;

    // Getter statico globale per consentire a DBManager/API di prendere il DataSource
    public static DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public void onStartup() throws Exception {
        System.out.println("-> [Lifecycle] Avvio applicazione e connessione DB...");

        dataSource = dbEngine.createPool(
            "jdbc:derby:memory:ewadb;create=true",
            "", 
            "", 
            15
        );
        
        // (Opzionale) Se usi un file .properties:
        // dataSource = dbEngine.creaPoolDaFile("./database.properties");


		// Set initial value
		initialized = true;
		
		// Application properties
		appProperties = PropertiesManagerFactory.getInstance(TemplateAttributes.TEMPLATE_PROPERTIES_FILE);
		
		// Initialize database (drop/create) if required
		if("Y".equalsIgnoreCase(appProperties.get(TemplateAttributes.PROP_DATABASE_INIT_ON_BOOT))) {
			
			// Log request received
			logger.debug("Database initialization request received");
			
			// Application database instance
			DBTables db = new DBTables();
			
			// Database Initialization
			if (0 != db.init()) {
				// Log error
				logger.error("Error during database initialization");
				// Change initialization status
				initialized = false;
			} else {
				// Log successful
				logger.info("Database initialized");			
			}
		}
		
		// Log initialization
		if(initialized)
			logger.info("Template service initialized");
		else
			logger.error("Template was not correctly initialized");
    }

    @Override
    public void onShutdown() throws Exception {
        System.out.println("-> [Lifecycle] Spegnimento applicazione e chiusura pool DB...");
        
        dbEngine.closePool();
    }
}