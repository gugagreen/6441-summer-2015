1) the names of all group members
	- Armaan Gill
	- Gustavo Gallindo
	- Maxime Lamothe
	- Parth Pareshkumar Patel
	- Ruixiang Tan


2) a description of the files in the archive, clearly indicating the purpose of each file
	- projects “lanterns”, “lanternsentities”
		- the source code of the project
	- project “lanterns-slick2d”
		- the future ui for the game (in progress, but not up to date)
	- Build_2_Documentation
		- Folder containing the project main documentation (“Software_ Architecture_ Lantern_Build_2.docx”), describing architecture and tools used, and images of diagrams for better visualization.

3) the URL where your online documentation may be viewed, if you haven't included the HTML files in the archive itself
	- all javadoc for is inside the “javadoc” folder, for both projects (“lanterns”, “lanternsentities”). But in case you want to regenerate it, a maven plugin is set to do 
		it, so please run "$ mvn javadoc:javadoc", and files should be generated inside folder 
		"/lanterns/target/site/apidocs/". Click on "index.html" to have access to the main page.
	- similar steps should be followed to regenerate javadoc for project “lanternsentities”.

4) instructions for how to compile, build and run your code
	- This project is set up using apache maven (https://maven.apache.org/). So any tasks could be done using it.
	- Please check README.txt file inside the “lanterns” project.
	
	
fyi: the source code and other artifacts for this project can be found in github, on this link:
	- https://github.com/gugagreen/6441-summer-2015/
