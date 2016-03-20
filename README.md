Vouchering Administration System
================================
Vouchering System is simple web application based on standard Java technology, provide the basic functionalities for the process administration.
 * Voucher import (Based on standard csv sheet)
 * Beneficiary import (Based on standard csv sheet)
 * Create a distribution 
 * Assign vouchers to beneficiaries
 * Import the information from the given excel sheet

Development Environment
-----------------------	
Used development frameworks and libraries:
 * [Java](https://www.java.com/en/) (J2ee) Enterprise Edition. 
 * [Java Servlets](https://en.wikipedia.org/wiki/Java_servlet), is a Java program that extends the capabilities of a server and can respond to any types of requests. 
 * [Hibernate](http://hibernate.org/), an open source Java persistence framework project. Perform powerful object relational mapping and query databases using HQL and SQL 
 * [Spring Framework](https://projects.spring.io/spring-framework/), provides a comprehensive programming and configuration model for modern Java-based enterprise applications, used for Dependency Injection and Aspect-Oriented Programming. 
 * [Apache Ivy](http://ant.apache.org/ivy/history/latest-milestone/) is a transitive dependency manager. It is a sub-project of the Apache Ant project, with which Ivy works to resolve project dependencies. 
 * [Vaddin](https://vaadin.com/home), is an open source Web application framework for rich Internet applications. In contrast to JavaScript libraries and browser-plugin based solutions 
 * [Junit](http://junit.org/), is a simple framework to write repeatable tests. 
 
 
Setup
-----
 * Link libraries found in `JeeLib/jeeUserLibrary.userlibraries`
 * Run MySql database script: `voucher-db.sql`
 * Change database configuration: `JeeApi\server-config.properties`
