SETUP-README

1. Open the directory of the jar file in the command prompt. The name of the jar file is gstocks.jar

2. Give the command "java -jar gstocks.jar gui". This should execute the jar file opening the GUI
interface application.

If you do not specify "gui" argument or specify any other argument in terminal while giving the
command to run jar you will see a text based interface application.

Follow the instructions displayed in the application to use the jar.

Libraries used

1. GSON : It is used to Serialize and Deserialize JSON objects.

The link for the license of this library is https://github
.com/google/gson/blob/master/LICENSE.

It is based on Apache Licence 2.0

Instructions to run jar in IntelliJ:

1. Download jar file for GSON library from here
https://search.maven.org/artifact/com.google.code.gson/gson/2.8.5/jar.

2. Open the intellij project.

3. Go to File -> Project Structure -> Project Settings -> Dependencies Tab

4. Click on + sign and choose jars or Directories

5. select the downloaded jar file path.

6. Now your application is ready to use the jar.

STEPS TO SETUP src, test , res files

To include these files and run a project in Intellij make the src folder as sources root, test
folder as tests root, and res folder as resources root folders respectively.
