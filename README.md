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

<!-- TODO: rework -->

We also wanted to continuously check the integrity of our code and add an
additional level of confidence when building features so that all merge request
can seamlessly integrate in the code base.

To achieve this goal, we set up a continuous integration process with GitLab CI
which can be found [here](https://gitlab.telecomnancy.univ-lorraine.fr/sdisapp2021/membership-management/-/pipelines)

This CI is building the code, checking the warnings and running both the unit
and the integration tests.

The prior help us to check that all of our services are still valid; and
the former are to check their integration with the embedded database and the
controllers.
