# MembershipManagement web API

![CI Status](https://gitlab.telecomnancy.univ-lorraine.fr/sdisapp2021/membership-management/badges/master/pipeline.svg)

---

## Overview

<!-- TODO -->

## Structure

<!-- TODO -->

### CI and testing

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
