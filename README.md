Elius WebApp Template
-----



Description
-----
This project is an example where I show how to use the Elius WebApp Framework.

I use it as starting point for all my web applications.




Components
-----
When the project is compiled it'll generate a **jar** file

The content of **resources** directory can be placed every where in your file system.

It contains:
 * **ewa.properties**, the framework properties file
 
 
 * **log4j2.xml**, Optional, the Apache Log4j2 log configuration file already included in the jar
 
 
 * **template.properties**, the application properties file




Compilation
-----
To compile this project you need to compile and install the *Elius WebApp Framework* on your local Maven repository.

When the *Elius WebApp Framework* will be stable it'll be released on official Maven repository.




Run
-----
To start the application execute
 *java -jar -Dewa.config.path=/etc/elius-webapp/config target/EliusWebAppTemplate-<release>.jar*

Application will be available under
 *http://localhost:8080/index.html*


**Parameters**

 * **ewa.config.path**, set the resource directory complete path
       *i.e.: -Dewa.config.path=/etc/elius-webapp/config*
       

 * **log4j.configurationFile**, Optional, set an external Apache Log4j2 xml configuration
       *i.e.: -Dlog4j.configurationFile=/my-path-name/resources/log4j2.xml*


API
-----
Please refer to Bruno collection


### License

Apache 2.0 - <https://www.apache.org/licenses/LICENSE-2.0>


