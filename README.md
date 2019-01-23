# OWLKEEPER
[![CircleCI](https://circleci.com/gh/xvzf/owlkeeper/tree/master.svg?style=svg)](https://circleci.com/gh/xvzf/owlkeeper/tree/master)

## Database bootstrap
We are using docker based database for development and testing.
In order to use it, make sure your system has the latest version of [Docker](https://github.com/docker/docker-install#usage) installed.

### Creating the database
You can start the database by running `make db-start` and stop it by `make db-stop`

### Bootstrapping tables, functions and triggers
Just run `make bootstrap`

## Running tests
In order to run test against the default database with dummy data, run `make test`
