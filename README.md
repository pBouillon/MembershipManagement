# Membership Management project

![CI Status](https://gitlab.telecomnancy.univ-lorraine.fr/sdisapp2021/membership-management/badges/master/pipeline.svg)

---

## Overview

The `MembershipManagement` project provides a way to manage teams, users and
their memberships.

This project is developed by **[Pierre Bouillon](https://www.linkedin.com/in/pierre-bouillon/)**
and **[Victor Varnier](https://www.linkedin.com/in/victor-varnier/)**

It is is split among three sub-projects:

- The [web API](./membership-management), which exposes the managed resources
  through a web REST API,
- A [logging client](./rabbitmq-clients/logger), which is logging every
  operation performed on the API,
- A [monitoring client](./rabbitmq-clients/monitoring), which is displaying the
  count, in real time, of the resources managed by the API.

## Structure

<!-- TODO: Swagger / RabbitMQ / etc. -->

## Pipelines

In order to continuously check the consistency and the correctness of our code,
we set up a continuous integration process with GitLab CI which can be found
[here](https://gitlab.telecomnancy.univ-lorraine.fr/sdisapp2021/membership-management/-/pipelines).

The CI is building all the projects using Gradle and JRE 15, and then running
the unit tests. Integration tests are available but should be run locally, with
your own RabbitMQ instance up and running. A `docker-compose` file is available
to run the development stack.
