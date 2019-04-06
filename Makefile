PSQL="PGPASSWORD=owlkeeper psql -h 127.0.0.1 owlkeeper owlkeeper"
PG_DUMP="PGPASSWORD=owlkeeper pg_dump -U owlkeeper -h 127.0.0.1 owlkeeper"
SQL_DIR=./src/main/resources/sqls
DOCKER_FILE=./src/main/resources/docker-compose.yml

db-sh:
	@sh -c ${PSQL}

db-dump:
	@sh -c ${PG_DUMP}

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

clean: bootstrap dummydata db-debug

# =======================
#
compile:
	mvn compile

test: bootstrap dummydata
	mvn test

start: compile db-start
	mvn exec:java

start-no-db: compile
	mvn exec:java
