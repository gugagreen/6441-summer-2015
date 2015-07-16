1) the names of all group members
	- Armaan Gill
	- Biswajit Banik
	- Gustavo Gallindo
	- Maxime Lamothe
	- Parth Pareshkumar Patel
	- Ruixiang Tan


2) a description of the files in the archive, clearly indicating the purpose of each file
	- lanterns.zip
		- the source code of the project
	- Build 1 documentation.docx
		- The project main documentation, describing architecture and tools used.

3) the URL where your online documentation may be viewed, if you haven't included the HTML files in the archive itself
	- all javadoc is inside the lanterns.zip file. But in case you want to regenerate it, a maven plugin is set to do 
		it, so please run "$ mvn javadoc:javadoc", and files should be generated inside folder 
		"/lanterns/target/site/apidocs/". Click on "index.html" to have access to the main page.

4) instructions for how to compile, build and run your code
	- This project is set up using apache maven (https://maven.apache.org/). So any tasks could be done using it.
	- compile: $ mvn clean compile
	- run tests: $ mvn test
	- run code: $ mvn exec:java -Dexec.mainClass="ca.concordia.lanterns.Controller.GameController"
	
	
fyi: the source code and other artifacts for this project can be found in github, on this link:
	- https://github.com/gugagreen/6441-summer-2015/
