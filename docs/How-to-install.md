# EmailQuestionnaire: How to Install

### Get a servlet container
First, make sure you have a java development kit installed, if not, fetch it [from OpenJDK](http://openjdk.java.net) or [from Oracle](http://www.oracle.com/technetwork/java/javase/downloads/index.html). Note that a Java Runtime does not suffice.

Now get a servlet container. Use, for example, the traditional [Apache Tomcat](http://tomcat.apache.org/) or other web-application container. Make sure you spot the _webapps_ directory.

### Download and install the Web-App
Download the web-application from the [snapshot](http://direct.hoplahup.net/tmp/EmailQuestionnaire.war) and install the web-application. With tomcat, this can be done by unzipping the .war file, then putting the produced _EmailQuestionnaire_ directory into the _webapps_ directory and restarting.
Adjust the home page (at _EmailQuestionnaire/index.jsp_) to suit your needs in case that page is visited.

## (Optional) Making it ready for production
By default, servlet-containers are accessible over the port 8080 which is rather not something one wishes to advertise. A simple way to expose EmailQuestionnaire within traditional URLs is to insert a proxy directive inside Apache configs (either _httpd.conf_ or _.htaccess_) such as the following
    ProxyPass /EmailQuestionnaire http://localhost:8080/EmailQuestionnaire
This will forward all requests with path _/EmailQuestionnaire_ to the web application container.

Moreover, if you want to configure the web-application container to last long, you should configure that it start everytime the machine restarts. This is best done by the servlet-container (e.g. as a Windows Service or Linux Daemon).

## Configure the paths
Edit the file _web.xml_ inside the _WEB-INF_ directory of the EmailQuestionnaire web application so that the received data, stored as XML files, are written in a correct place.

## Ready to go
You are now ready to create an HTML questionnaire.
See the [How-to-create-an-email-questionnaire](How-to-create-an-email-questionnaire.md).