# 6441-summer-2015

Commit guidelines:

Before committing anything to the master branch, please do the following:

1) Make sure that eclipse is not giving you any warning or errors.
    If you are getting warnings and errors please fix them before commiting.
  
2) Run the JUnit test Suite
    Make sure that your code does not fail or break any of the unit tests.
    If it does, please fix before commiting, all tests should pass.

3) If any comments were added to the Javadoc, make sure that the Javadoc can be built.

4) Use maven to validate points 1, 2 and 3, and also to check if proejct still runs:
	- compile: $ mvn clean compile
	- run tests: $ mvn test
	- generate javadoc: $ mvn javadoc:javadoc
	- run code: $ mvn exec:java -Dexec.mainClass="ca.concordia.lanterns.Controller.GameController"

5) Use branches
	The safest way to guarantee you are not disrupting the "master" branch is to:
		- make your changes in your own branch
		- merge the latest master into your branch
		- run validation steps mentioned in point 4, fixing any issues until validation passes
		- finally merge your branch into master

6) If you still pushed broken code at any point, please revert your changes

7) Do not add any unnecessary changes. That will just make merging harder for other team members. For instance, three completely unnecessary changes were quite common during the 1st release:
	a) unnecessary variable name changes:
		for example, if you have an attribute "players" inside a class "Game", there is no need to change its name to "gamePlayers", it is implicit which entity it belongs to, and just make naming unnecessary large.
	b) unnecessary tests/assertations:
		whenever you write a test, think of what you actually want to test. You should never try to test something that is out of the game scope. For example:
				public void testExample() {
				 	final Player player = new Player();
				 	// Completely unnecessary assert. It is not validating any business logic, it is actually just checking if Java instantiation works. What is the point?
					assertNotNull(player);
				}
	c) unnecessary javadoc:
		getters and setters should not have javadoc, unless they have some important logic going on that user of the API should be aware of. Same for any of reimplemented Object methods (toString, hashCode, equals).


Enjoy!
