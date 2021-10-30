First, we should create a folder where our .class files and resources wil be located
-------------------------------------
mkdir target
-------------------------------------

Then we should copy folder resources from src to target
-------------------------------------
cp -r src/resources target/.
-------------------------------------

Now let's compile the source files
FROM src/java/edu/school21/printer/app/ AND src/java/edu/school21/printer/logic
INTO target/
-------------------------------------
javac -d target src/java/edu/school21/printer/app/*.java src/java/edu/school21/printer/logic/*.java
-------------------------------------

Creating jar file
-------------------------------------
jar cfm target/images-to-chars-printer.jar src/manifest.txt -C target edu -C target resources
-------------------------------------

We can run our Program
-------------------------------------
java -jar target/images-to-chars-printer.jar arg1 arg2
-------------------------------------
