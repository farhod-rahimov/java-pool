First, we should create a folder where our .class files wil be located
-------------------------------------
mkdir target
-------------------------------------

Now let's compile the source files
FROM src/java/edu/school21/printer/app/ AND src/java/edu/school21/printer/logic
INTO target/
-------------------------------------
javac -d target src/java/edu/school21/printer/app/* src/java/edu/school21/printer/logic/*
-------------------------------------

We can run our Program
-------------------------------------
java -classpath target edu.school21.printer.app.Program arg1 arg2 FullPathToImge
-------------------------------------
