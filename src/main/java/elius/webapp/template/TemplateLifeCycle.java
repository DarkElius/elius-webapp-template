/**
    Licensed to the Apache Software Foundation (ASF) under one
    or more contributor license agreements.  See the NOTICE file
    distributed with this work for additional information
    regarding copyright ownership.  The ASF licenses this file
    to you under the Apache License, Version 2.0 (the
    "License"); you may not use this file except in compliance
    with the License.  You may obtain a copy of the License at
    
      http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations
    under the License.
*/


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


	/**
	 * Constructor
	 */
	public TemplateLifeCycle() {
		// Application properties
		appProperties = PropertiesManagerFactory.getInstance(TemplateAttributes.TEMPLATE_PROPERTIES_FILE);
	}

	/**
	 * Static global get datasource 
	 * @return DB Datasource
	 */
    public static DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public void onStartup() throws Exception {
        logger.info("Application Start...");

		// Load database information
		String dsUrl = appProperties.get(TemplateAttributes.PROP_DATABASE_APPNAME_DATASOURCE_URL);
		String dsUsername = appProperties.get(TemplateAttributes.PROP_DATABASE_APPNAME_DATASOURCE_USERNAME);
		String dsPassword = appProperties.get(TemplateAttributes.PROP_DATABASE_APPNAME_DATASOURCE_PASSWORD);
		int dsMaxPoolSize = appProperties.getInt(TemplateAttributes.PROP_DATABASE_APPNAME_DATASOURCE_MAXPOOLSIZE, TemplateAttributes.DEFAULT_DATABASE_APPNAME_DATASOURCE_MAXPOOLSIZE);

        dataSource = dbEngine.createPool(
            dsUrl,
            dsUsername, 
            dsPassword, 
            dsMaxPoolSize
        );

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
        
		logger.info("Application shutting down...");

        // Close pool
        dbEngine.closePool();
    }
}