# MembershipManagement web API

![CI Status](https://gitlab.telecomnancy.univ-lorraine.fr/sdisapp2021/membership-management/badges/master/pipeline.svg)

---

# Overview

This project is developped by **[Pierre Bouillon](https://www.linkedin.com/in/pierre-bouillon/)**
and **[Victor Varnier](https://www.linkedin.com/in/victor-varnier/)**

`MembershipManagement` is a RESTful web API to manage teams, users and their memberships.  
Made with **Java 15**, **Java Spring** and **Apache Derby**, a web interface is accessible through the **Swagger UI**

![Swagger UI](./docs/images/swagger-overview-v1.png)

## Structure

> For the initial API documentation, please refer to [the wiki](https://gitlab.telecomnancy.univ-lorraine.fr/sdisapp2021/membership-management/-/wikis/home)

The overall architecture is the following:

![architecture](./docs/images/architecture-overview-v1.jpg)

## Architecture principles and other tools

In order to help us to build a robust and efficient API, we created our
project around some specific additional technologies.

### CQRS

CQRS, or Command Query Responsibility Segregation, is a way to design
a system in a way such that the read operations are completely
separated from the write operation.  

We applied this principle to our controllers by splitting them between
read and write only controllers. By doing so, each payload received
is holding all the data needed to perform an operation, without any
additional one which may be misused or provide too much information.

This is resulting in several components with a specific
closed scope that can only access and use the minimal amount
of data it needs to operate and in an increased loose coupling
(see the [Law of Demeter](https://en.wikipedia.org/wiki/Law_of_Demeter)).

### Mapping

## Code Quality

### Logging

### CI

### Unit tests

### Integration tests
