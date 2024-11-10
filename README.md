# notes_app
Notes and associated ppriority level

# how to tun

# NOTE: before running, change this so you create a new note to avoid duplicates
Note addNote = mydb.addNote("Celebrate our win", "Too very high"); 

# steps to run
in the directory with src file
run the folowing commands

# to ensure all dependencies are downloaded
1. mvn clean install 

# to build the fat JAR (target/myapp-1.0-SNAPSHOT.jar). 
2. mvn clean package

# to start your application
3. java -jar target/myapp-1.0-SNAPSHOT.jar 

