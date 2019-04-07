PSQL="PGPASSWORD=owlkeeper1337 psql -h lanarama.rocketlan.de owlkeeper owlkeeper"
SQL_DIR=./src/main/resources/sqls
DOCKER_FILE=./src/main/resources/docker-compose.yml

db-sh:
	@sh -c ${PSQL}

db-start:
	docker-compose -f ${DOCKER_FILE} up -d

db-stop:
	docker-compose -f ${DOCKER_FILE} down

db-debug:
	@sh -c ${PSQL} < ${SQL_DIR}/debugview.sql


bootstrap:
	@sh -c ${PSQL} < ${SQL_DIR}/bootstrap/destroy.sql
	@sh -c ${PSQL} < ${SQL_DIR}/bootstrap/tables.sql
	@sh -c ${PSQL} < ${SQL_DIR}/bootstrap/functions.sql

dummydata:
	@sh -c ${PSQL} < ${SQL_DIR}/dummydata.sql

dummydata-demo:
	@sh -c ${PSQL} < ${SQL_DIR}/dummydatademo.sql

clean: bootstrap dummydata db-debug

# =======================
#
compile:
	mvn compile

test: bootstrap dummydata
	mvn test

start: compile db-start
	mvn exec:java

deploy-demo: bootstrap dummydata-demo

start-demo: compile
	mvn exec:java

start-no-db: compile
	mvn exec:java
