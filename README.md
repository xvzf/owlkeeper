# OWLKEEPER
[![CircleCI](https://circleci.com/gh/xvzf/owlkeeper/tree/master.svg?style=svg&circle-token=d1cc8d28c19045189e8c4b6fcc112315501d66b0)](https://circleci.com/gh/xvzf/owlkeeper/tree/master)

## Database bootstrap
We are using docker based database for development and testing.
In order to use it, make sure your system has the latest version of [Docker](https://github.com/docker/docker-install#usage) installed.

### Creating the database
You can start the database by running `make db-start` and stop it by `make db-stop`

### Starting owlkeeper
Just `make start`

### Bootstrapping tables, functions and triggers
Just run `make bootstrap`

## Running tests
In order to run test against the default database with dummy data, run `make test`

## Weekly report
Enter your progress of this week into the weekly report: [Wochenbericht](https://docs.google.com/document/d/1qGXc_3bbKxmiCtkZZbQJFlLprwdoWGZlPlnf5-h6Jw8/edit?usp=sharing)
