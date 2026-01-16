Study Buddy

Study Buddy is a full-stack web application that enables students to connect for peer-to-peer academic support. Users can post help requests, accept requests from others, and communicate through direct messaging in a secure, multi-user environment.

Features

User authentication and account management

Create, view, accept, and manage academic help requests

Direct user-to-user messaging

Search and filtering of requests by subject or keywords

Tech Stack

Frontend: JSP, HTML, CSS

Backend: Java Servlets, JDBC

Database: MySQL

Architecture: Three-tier architecture (presentation, logic, data)

Design Patterns: DAO pattern, normalized relational schema

System Overview

The application follows a three-tier architecture:

Presentation Layer: JSP-based UI for user interaction

Logic Layer: Java Servlets handling business logic and request routing

Data Layer: MySQL database accessed via JDBC and DAOs

Database Design

The database is fully normalized and enforces relational integrity using primary and foreign keys across core entities such as users, posts (requests), acceptances, and messages.

What I Learned

Building and structuring a full-stack Java web application

Implementing authentication, CRUD operations, and relational database design

Working with Java Servlets, JDBC, and Apache Tomcat

Designing scalable backend logic using DAOs and SQL

Future Improvements

Real-time messaging using WebSockets

User ratings and reviews

Notification system for new requests and messages

Mobile-friendly UI or native mobile app
