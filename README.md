# MPP/X Converter to JSON

[![Build Status](https://api.travis-ci.org/smulyono/mppconverter-java.png)](https://travis-ci.org/smulyono/mppconverter-java)

        * Using MPXJ Library
        * Spring MVC - REST


## Getting Started

    * mvn clean package

_under jetty configuration, we can add our **OAUTH_CLIENT_KEY and OAUTH_CLIENT_SECRET** into the
system properties (see pom.xml -- jetty plugin configuration)._

_By default the application will look into system environment variable 
if those environment cannot be found then it will look into system properties. This is usefull for development
purposes without having to setup the OS environment variables_

	* mvn jetty:run

OR

	* java -jar target/dependency/jetty-runner.jar target/mppconverter.war

### To Push into Heroku

	* heroku login
	
	__setup a new instance for this java app__
	
	* heroku create
	
	* heroku config:add OAUTH_CLIENT_KEY=<sfdc customer key>
	
	  heroku config:add OAUTH_CLIENT_SECRET=<sfdc customer secret>
	  
	* git push heroku master
 

## Heroku Instance (currently)

Live working instance [here](https://glacial-bayou-4282.herokuapp.com) 

## Overview

Simple Java / Spring MVC application to convert MPP/X files into JSON. This java application can also be tested / hosted in Heroku.

This application is built for [cloudspokes challenge](http://www.cloudspokes.com/challenges/2287/), where this application will act as REST endpoint. The client (Force.com) can submit POST request to either generate JSON file or MPP/X file. 

Overall Architecture View [https://www.lucidchart.com/publicSegments/view/51b4e4a5-dae4-47c1-9123-79770a008629/image.png](https://www.lucidchart.com/publicSegments/view/51b4e4a5-dae4-47c1-9123-79770a008629/image.png)

## Demo

[Video - showing how to import project from Salesforce which communicate to this Java Application](http://www.screencast.com/t/zOxUZ65dM1dN)

[Video - showing how to crete project file (mpx only) from Salesforce Object; do OAuth to the java application project and posted the file back to Salesforce Record] (http://www.screencast.com/t/PKPU7fTq) 


### Supported API

> URL : */convertmpp*
>
> Method : **POST**
>
> Parameter : _MultiPart File_ ; Client can pass this through Ajax by serializing the File Input
>
> Description : Convert any Project File (.mpp, .mpx) into JSON

> URL : */creatempp*
>
> Method : **POST**
>
> Parameter : _JSON_ in RequestBody
>
> Description : Based on the JSON Payload, construct and generate the Project File (.mpx). __This version, the generated file are stored in Server; there are no produces output of file yet. 

### Convertion Page From Salesforce

> URL : */secure/creatempx/{projectid}*
>
> Method : **GET** 
>
> Description : This page will receive project ID from Salesforce and will create OAUTH authentication and Retrieve Project Information from Salesforce. 
After the project information retrieved, then it will create MPX file and push them back into Salesforce -- into 
project attachment record (based on the passed projectid)






