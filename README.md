# WGU-Software-II-C195
This is a JavaFX GUI application for WGU Software II C195. The application models an appointment scheduling tool which uses a database and tracks user time zone and language setting.

## Requirements
1. IntelliJ (download and install)
2. JavaFX  (download and extract file)
3. Database connector file (downloaded from Oracle)

## Setting up Intellij to recognize JavaFx
1. Create your JavaFX project with Intellij
2. Goto Run -> Edit Configurations  
    a. In the VM options field enter the following:  
    --module-path ${PATH_TO_FX} --add-modules javafx.fxml,javafx.controls,javafx.graphics
3. Goto File -> Settings -> Path Variables -> click the + to add a new path variable  
    a. Name: PATH_TO_FX  
       Value: your_path_to_FX (where ever you extracted the FX files)
4. Goto File -> Project Structure -> Libraries -> click the + to add a new library  
    a. Select Java -> Browse to your JavaFX installation folder -> select the lib subfolder
        and hit ok.  
    b. Press ok and apply.  JavaFX is ready to use.
## Setting up the database
1. Download the platform-independent JDBC connector file from https://dev.mysql.com/downloads/connector/j/ and extract the zip file. Inside this file is the “mysql-connector” JAR file.
 
2. Goto File -> Project Structure -> Libraries -> click the + to add a new library  
    a. Select Java -> Browse to your database connector file -> select the jar file  
    b. Press ok and apply. The connector should be listed as an external library. 
