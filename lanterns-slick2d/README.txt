How to setup eclipse to run slick2d:

1) Go to menu "Run" > "Run Configurations..."
2) On left menu, look for "Maven Build". Right click on it, then select "New".
3) This should create a "New_configuration". You can rename it to something like "lanterns-slick2d-exec"
4) On the "Main" tab, set:
	a) Base directory:
		${workspace_loc:/lanterns-slick2d}
	b) Goals:
		exec:java
5) On the "JRE" tab, set:
	a) VM arguments:
		-Djava.library.path=target/natives
6) Apply changes, then you should be able to run it.

==============================

How to run "lanterns-slick2d" from command line

1) make sure the server (project "lanterns") is up and running
	Note: if the server is not up, when you run the client project you will get an error message like that:
			Unnable to connect to [http://localhost:8080/rest/game]
2) export native libraries to OS path
	a) windows:
		$ set MAVEN_OPTS="-Djava.library.path=target/natives"
	b) unix:
		$ export MAVEN_OPTS=-Djava.library.path=target/natives
3) compile code:
	$ mvn clean compile
4) run the main class
	$ mvn exec:java

