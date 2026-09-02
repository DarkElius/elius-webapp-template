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

import elius.webapp.framework.server.ServerWeb;
import elius.webapp.framework.security.authentication.AuthenticationFilter;
import elius.webapp.framework.server.ServerConfig;

public class ApplicationMain {

    /**
     * Main program
     * @param args Program arguments
     */
    public static void main(String[] args) {

        // Define server with static resources, api packages and filters
        ServerConfig config = new ServerConfig()
            .setStaticResources("src/main/resources/html")
            .setPackageJAXRS("elius.webapp.framework.api","elius.webapp.template.api")
            .addFilter("/api/ewa/*", AuthenticationFilter.class);
        
        // Start web application server with the selected life cycle
        ServerWeb.startWebApp(8080, config, new TemplateLifeCycle());
    }    
}
