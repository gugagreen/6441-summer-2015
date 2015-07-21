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
