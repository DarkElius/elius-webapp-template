/*
 * Copyright 2023 Elia Milioni
 *
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


package elius.webapp.template.api;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import elius.webapp.template.myobj.MyObjDatabase;
import elius.webapp.template.myobj.MyObject;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/ewa/object")
public class ObjectInterface extends Application {

	// Get logger
	private static Logger logger = LogManager.getLogger(ObjectInterface.class);
	
	// Object Database
	private MyObjDatabase dbObject;
	
	/**
	 * Constructor
	 */
	public ObjectInterface() {
		// Object Database instance
		dbObject = new MyObjDatabase();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response add(MyObject myObj) {
		
		// Log request received
		logger.trace("Object add-to-db request received");
		
		// Initialize dbObject
		if (0 != dbObject.init())
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		
		// Add object to queue
		if (0 != dbObject.add(myObj))
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

		// Log request successfully
		logger.debug("New object added");
		
		// Return Created
		return Response.status(Response.Status.CREATED).build();
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		
		// Log request received
		logger.trace("Object get request received");
		
		// Initialize dbObject
		if (0 != dbObject.init())
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		
		// Fetch object from database
		List<MyObject> objectList = dbObject.getAll(999);
		
		// Check errors
		if (null == objectList) {
			// Log request error
			logger.error("Error on get object request");
			
			// Return error
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			
		} else if (0 == objectList.size()) {
			// Log empty result
			logger.debug("No objects fetched");
			
			// Return created
			return Response.status(Response.Status.NO_CONTENT).build();	
		}
	
		// Log request successfully
		logger.debug("Object fetched");
		
		// Return created
		return Response.ok().entity(objectList).build();
	}
}
