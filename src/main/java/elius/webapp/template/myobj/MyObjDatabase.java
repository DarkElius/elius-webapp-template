/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package elius.webapp.template.myobj;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import elius.webapp.template.TemplateAttributes;
import elius.webapp.template.TemplateLifeCycle;
import elius.webapp.framework.application.ApplicationContext;
import elius.webapp.framework.db.DbManager;
import elius.webapp.framework.properties.PropertiesManager;
import elius.webapp.framework.properties.PropertiesManagerFactory;


/**
 * MyObjDatabase.
 * <p>
 * Manage objects inside database.
 */
public class MyObjDatabase {
	
	// Get logger
	private static Logger logger = LogManager.getLogger(MyObjDatabase.class);

	// Properties file
	private PropertiesManager appProperties;
	
	// Database interface
	private DbManager db;

	// Insert myobject in the Object table
	private static final String SQL_INSERT = "INSERT INTO OBJECT (ID, NAME) VALUES (?, ?)";

	// Select all myobject in the Object table with a limit
	// Order by Name
	private static final String SQL_SELECT_ALL_MAX_ROWS = "SELECT * FROM OBJECT ORDER BY NAME FETCH FIRST ? ROWS ONLY";
	
	// Update Object name
	private static final String SQL_UPDATE_NAME = "UPDATE OBJECT SET NAME = ? WHERE ID = ?";


	
	/**
	 * Constructor
	 */
	public MyObjDatabase() {
		// Application properties
		appProperties = PropertiesManagerFactory.getInstance(TemplateAttributes.PROPERTIES_FILE);
	}
	
	
	/**
	 * Initialize objects database
	 * @return 0 Successfully initialized, 1 Error
	 */
	public int init() {
		
		// Load default properties
		if(!appProperties.isLoaded()) {
			// Return error
			return 1;
		}

		// Initialize DbManager
		db = new DbManager(ApplicationContext.get(DataSource.class));
			
		// Log initialization 
		logger.trace("Database initizialed");
		
		// Set return code
		return  0;
	}
	
	
	/**
	 * Add object to list
	 * @param object Object
	 * @return 0 Successfully, 1 Error
	 */
	public int add(MyObject object) {
		// Log add object
		logger.debug("Add object={}", object.getUuid());

		// Check the number of rows inserted
		if (db.update(SQL_INSERT, object.getUuid().toString(), object.getName()) > 0) {
			// Log the error
			logger.error("No rows inserted");
			// Return error
			return 1;
		}
		
		// Return successfully
		return 0;			
	}
	
	
	
	
	/**
	 * Update object owner 
	 * @param uuid Object UUID
	 * @param name Name
	 * @return 0 Successfully, 1 Error
	 */
	public int updateName(UUID uuid, String name) {
		// Log update object
		logger.debug("Update uuid={} set name={}", uuid, name);

		// Check the number of rows updated
		if (db.update(SQL_UPDATE_NAME, name, uuid) > 0) {
			// Log the error
			logger.error("No rows uptdated");
			// Return error
			return 1;
		}
		
		// Return successfully
		return 0;		
	}
	
	

	
	/**
	 * Get all my objects
	 * @param maxRows The maximum number of objects returned 
	 * @return MyObject list or null in case of errors
	 */
 	public List<MyObject> getAll(int maxRows) {
 		return get(SQL_SELECT_ALL_MAX_ROWS, maxRows);
 	}
 	
	
	/**
	 * Get MyObject 
	 * @param sql SQL to be executed
	 * @param maxRows The maximum number of object
	 * @return MyObject list or null in case of errors
	 */
 	private List<MyObject> get(String sql, int maxRows) {
		// Log get object
		logger.debug("Get objects");

		// Result table
		List<Map<String, Object>> table = db.executeQuery(sql, maxRows);
				
		// Check the result
		if (null == table) {
			// Log the error
			logger.error("No rows fetched");
			// Return error
			return Collections.emptyList();
		}	

		// List of objects
		List<MyObject> myObjectList =  new ArrayList<MyObject>();

		// Read rows from database
		for(Map<String, Object> tableRow : table) {
			
			// Define new object
			MyObject o = new MyObject();
			// Set object id
			o.setUuid(UUID.fromString((String)tableRow.get("ID")));
			// Set object name
			o.setName((String)tableRow.get("NAME"));

			
			// Add to list
			myObjectList.add(o);
		}

		// Return object list
		return myObjectList;
	}

}
