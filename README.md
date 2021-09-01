# WGU-Software-II-C195
Title: WGU Software II C195 - Desktop Scheduling Application  
Purpose:  
This is a JavaFX GUI application for WGU Software II C195. The application models an appointment scheduling tool which uses a database and tracks user time zone and language setting.  Login screen text is displayed in English or French.  Timezones are stored in UTC and displayed in the user's computers timezone.  
Available Reports:  
Reports are available through buttons which display custom data based on the query.
1. The total number of customer appointments by type and month.  
2. A schedule for each contact in your organization that includes appointment ID, title, type and description, start date and time, end date and time, and customer ID.  
3. A custom report filtered by location.  
  
Author: Justin Bourbonniere  
Contact: jbourbo@wgu.edu  
Version: 1.0  
Date: 2020-12-31  
Made with:  
 IntelliJ IDEA 2020.2.2 (Edu)  
 OpenJDK Platform binary 11.0.4.0  
 JavaFX-SDK-11.0.2  

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
## Usage
Open IntelliJ, load the porject folder and run mainLauncher.java.  Note, firewalls or VPN services may interfere with connecting to the database.  Upon loading the application users are promted to log in in French or English based on their windows settings.  
<img src="https://github.com/justinbourb/WGU-Software-II-C195/blob/main/images/login.JPG">  
Successful login loads the main dashboard. Where existing customers and appointments can be viewed.  
<img src="https://github.com/justinbourb/WGU-Software-II-C195/blob/main/images/main_dashboard.JPG">  
Customers and Appointments can be added, edited or deleted.  Table data can be filtered via the search field.  All appointments are stored in UTC and converted into the users timezone, based on their windows settings.  Overlapping appointments are not allowed.  Customers can be added from the USA, Canada or UK.  
<img src="https://github.com/justinbourb/WGU-Software-II-C195/blob/main/images/edit_customers.jpg">  
<img src="https://github.com/justinbourb/WGU-Software-II-C195/blob/main/images/edit_appointments.JPG">
