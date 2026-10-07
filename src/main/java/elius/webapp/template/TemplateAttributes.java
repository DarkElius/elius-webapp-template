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


package elius.webapp.template;

public class TemplateAttributes {
	
	// Application properties file
	public static final String PROPERTIES_FILE = "template.properties";
	
	
	// Default - Database jdbc max pool size
	public static final int DEFAULT_DATABASE_DATASOURCE_MAXPOOLSIZE = 15;

	
	// Properties - Database initialization on startup (cold start) (y/n) , default is n
	public static final String PROP_DATABASE_INIT_ON_BOOT = "database.initOnBoot";

	// Properties - Database jdbc url
	public static final String PROP_DATABASE_DATASOURCE_URL = "database.datasource.url";	

	// Properties - Database jdbc username
	public static final String PROP_DATABASE_DATASOURCE_USERNAME = "database.datasource.username";

  	// Properties - Database jdbc password
	public static final String PROP_DATABASE_DATASOURCE_PASSWORD = "database.datasource.password";	

  	// Properties - Database jdbc max pool size
	public static final String PROP_DATABASE_DATASOURCE_MAXPOOLSIZE = "database.datasource.maxpoolsize";
}
