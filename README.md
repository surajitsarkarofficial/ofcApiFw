# Glow-API-TestAutomation

## Setup and configuration :
1. Clone this repo or download the .zip file.     
![Cloning Repo](https://github.corp.globant.com/storage/user/1382/files/b9c5d200-895b-11ea-928e-3eb4dd5d66e2)

2. To clone the repository- Open git bash ->navigate to the path where you want to clone the URL and put following command:      **git clone https://github.corp.globant.com/ISTEA/Glow-API-TestAutomation.git**    

3. Go to eclipse -> Import maven project  ->Select the cloned or downloaded project from local folder
(Note : Make sure you have the latest eclipse version)                 

![Eclipse version](https://github.corp.globant.com/storage/user/1382/files/ed096080-895d-11ea-833f-4deaf3fc0bf2)

4. Once the Project is imported you can see project structure, as shown in below screenshot.     

![Project_structure_after_import](https://github.corp.globant.com/storage/user/1382/files/1d50ff00-895e-11ea-9c72-bd9d56334723)

5. In order to have package structure in hierarchical format (as shown in the below screenshot) follow the steps mentioned below:

![Project_structure_in_Hierarchical_Format](https://github.corp.globant.com/storage/user/1382/files/42de0880-895e-11ea-835c-58f298a44bd1)

* Steps to have hierarchical package structure :

-> Click on the ellipsis which we can see in the package explorer section  

![Project_structure_in_Hierarchical_Format_Step_1](https://github.corp.globant.com/storage/user/1382/files/55584200-895e-11ea-8a98-8f56bdeec0be)                            

-> Goto Package Presentation and click on ‘Hierarchical’ 

![Project_structure_in_Hierarchical_Format_Step2](https://github.corp.globant.com/storage/user/1382/files/6bfe9900-895e-11ea-8d2e-e11e9e4c8a05)

## Folder Structure of the test framework :

#### 1. BaseFramework :
  * This module contains packages used for only framework specific functionality
   * Example :
    
      * database package contains actions related to database like establishing connection with db
      * dataproviders package contains classes having methods used to extract data from the XML file
      * utils package contains classes having methods used to create report, executing the request and returning the response, setting request time out, etc.

![Baseframework](https://github.corp.globant.com/storage/user/1382/files/7caf0f00-895e-11ea-9dc2-d2bf7e06b1c0)

#### 2. Demo-Delivery, Demo-Staffing :
   * These are the two of the modules of Glow used by different Glow PODs.
   
   ![Module(Staffing Delivery)](https://github.corp.globant.com/storage/user/1382/files/9fd9be80-895e-11ea-944b-57a1ba244989)            
   ![Delivery_Web](https://github.corp.globant.com/storage/user/1382/files/ba139c80-895e-11ea-9dd4-e23923b3e4a7)                  
   ![Staffing_web](https://github.corp.globant.com/storage/user/1382/files/cbf53f80-895e-11ea-83d7-3e8238ad6882)                
  * You can refer these module while creating any new module
  * In order to add new module please follow below steps :                                                                          
  File -> New -> Project -> Maven -> Select Maven Module -> Next -> select check box of ‘Create a simple project’ -> Enter module name(e.g Demo-Staffing) -> select Parent Project as ParentModule -> Next -> Finish


![CreateNewModule](https://github.corp.globant.com/storage/user/1382/files/dd3e4c00-895e-11ea-8ced-aa1ffee965a0)

#### 3. GLOW :                            

  * This module contains common methods which can be used across all the glow pods.
 * Example :
 
    * endpoints package contains common end points used by all the glow pods like fake login API
    * properties package contains methods used to set properties required for the environment
    * tests package contains beforemethod and aftermethod
    * utils package contains enum which has list of environments
    
![GLOW](https://github.corp.globant.com/storage/user/1382/files/f3e4a300-895e-11ea-8c30-b3729d792315)

#### 4. ParentModule :                         
* This is the parent of all the modules contained in the framework since this is a multi module framework.

![ParentModule](https://github.corp.globant.com/storage/user/1382/files/0828a000-895f-11ea-8643-e34fe02d423b)

#### 5. TestRunners:
* This is testng suite runner which is used to execute test suites.
* You can execute test suites by including them in an XML file.

![TestRunners](https://github.corp.globant.com/storage/user/1382/files/1e366080-895f-11ea-918f-5fc33e1a4a2f)

## Setting Environment and Running Scripts :
* In order to install latest testng please visit the below link and follow the steps :                                
https://www.toolsqa.com/testng/install-testng/
* In order to execute your test script, set environment in  **```<environment> </environment>```** tag in your respective module’s pom.xml.
Currently **Preprod** is set as default value.

![SettingEnvEclipse](https://github.corp.globant.com/storage/user/1382/files/34442100-895f-11ea-9a3a-cc5f43101f1c)

  * To execute scripts using maven in command prompt follow the following steps :
    * Open command prompt from the location where you have your project and navigate to the path till ParentModule location :            
    ![MvnExecutionParentModule](https://github.corp.globant.com/storage/user/1382/files/48881e00-895f-11ea-9145-b10e13c35241)
    
    * Type **‘mvn clean’**
    * Once maven clean is done,then type **‘mvn install’**
    * To execute scripts from particular module in a specific environment, navigate till your module’s path(if you are in ParentModule then type ‘cd ModuleName’) and use following command :            
    **mvn clean install -Denvironment=EnvironmentName**        
(e.g mvn clean install -Denvironment=Preprod)       
If no environment value is passed then the tests will be executed in the default environment set i.e. Preprod.                          
![MvnExecutionPODWiseModule](https://github.corp.globant.com/storage/user/1382/files/5c338480-895f-11ea-919f-e708be56b18b)

## Reports :    
Reports will be generated under the **Reports** folder. Which will reside under the respective Project from where the execution was triggered.
