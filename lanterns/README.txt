1) the names of all group members
	- See README.txt on the root folder of the zip

2) a description of the files in the archive, clearly indicating the purpose of each file
	- See README.txt on the root folder of the zip

3) the URL where your online documentation may be viewed, if you haven't included the HTML files in the archive itself
	- See README.txt on the root folder of the zip

4) instructions for how to compile, build and run your code
	- This project is set up using apache maven (https://maven.apache.org/). So any tasks could be done using it.
	- Before anything, make sure the dependency "lanternsentities" is installed.
		Go to "lanternsentities" project folder and run: $ mvn clean install
	- Then go back to the "lanterns" project folder.
	- compile: $ mvn clean compile -U
	- run tests: $ mvn test
	- run code: $ mvn exec:java -Dexec.mainClass="ca.concordia.lanterns.ui.GameCommandClient"

	- run code(with GUI): 
		- $ mvm jetty:run
		- Open a browser and go to adress: localhost:8080
		- Please note: This option will not work if a human player is selected.
	
5) instructions for loading save files at any point in the game:
	- With the steps above, start the application
	- Choose option "2" (Load game from file)
	- Enter file name of a previously saved file (with .xml)


	
fyi: the source code and other artifacts for this project can be found in github, on this link:
	- https://github.com/gugagreen/6441-summer-2015/
