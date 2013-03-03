SAW Java Tools
==============
About this project
------------------
Several tools useful for the development of Java applications.

This project has been developed using the Eclipse IDE (http://www.eclipse.org).

	Programming Language: 	Java (JavaSE-1.6)
	Dependency management:	Maven (http://maven.apache.org)

Download / Integrate
--------------------

You can simply download or integrate this project via Maven. Just add the following lines to your pom.xml:

	...
	<dependencies>
		...
		<dependency>
			<groupId>de.wsdevel</groupId>
			<artifactId>SAW_Java-Tools</artifactId>
			<version>0.1.0-SNAPSHOT</version>
		</dependency>
		...
	</dependencies>
	...
	
	<repositories>
		...
		<repository>
			<id>sebastian-weiss</id>
			<name>Sebastian's Repository</name>
			<url>http://www.sebastian-weiss.de/mvn_repo</url>
		</repository>
		...
	</repositories>
	...

Author
------
(c) 2003 - 2013 Sebastian A. Weiß

Depends on the following libraries/projects
-------------------------------------------
* commons-logging-1.1.jar
