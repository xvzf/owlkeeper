
![Owlkeeper Logo](src/main/resources/images/logo.png)


[![CircleCI](https://circleci.com/gh/xvzf/owlkeeper/tree/master.svg?style=svg&circle-token=d1cc8d28c19045189e8c4b6fcc112315501d66b0)](https://circleci.com/gh/xvzf/owlkeeper/tree/master)

## Installing dependencies
Make sure you have make, maven, docker and docker-compose installed and all permissions to use them.

On Ubuntu 18.04:

`sudo apt update && sudo apt install make maven docker docker-compose`  

`sudo groupadd docker`  

`sudo usermod -aG docker $USER`  

Reboot to start the docker daemon and set user permissions.


## Starting owlkeeper
Just `make start`.
Owlkeeper will compile, start a local database server and then start itself.

## Database bootstrap
We are using docker based database for development and testing.
In order to use it, make sure your system has the latest version of [Docker](https://github.com/docker/docker-install#usage) installed.

### Creating the database
You can start the database by running `make db-start` and stop it by `make db-stop`

### Bootstrapping tables, functions and triggers
Just run `make bootstrap`

## Running tests
In order to run test against the default database with dummy data, run `make test`

## Compiling
Run `make compile`

## Weekly report
Enter your progress of the week into the report in the discord server
