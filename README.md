# MPP/X Converter to JSON

[![Build Status](https://api.travis-ci.org/smulyono/mppconverter-java.png)](https://api.travis-ci.org/smulyono/mppconverter-java)

        * Using MPXJ Library
        * Spring MVC - REST


## Getting Started

* mvn package
* mvn jetty:run

OR

* java -jar target/dependency/jetty-runner.jar target/mppconverter.war
 

## Overview

Simple Java / Spring MVC application to convert MPP/X files into JSON. This java application can also be tested / hosted in Heroku.

This application is built for [cloudspokes challenge](http://www.cloudspokes.com/challenges/2287/), where this application will act as REST endpoint. The client (Force.com) can submit POST request to either generate JSON file or MPP/X file. 

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







