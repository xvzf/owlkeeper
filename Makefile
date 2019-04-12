PSQL="PGPASSWORD=owlkeeper psql -h 127.0.0.1 owlkeeper owlkeeper"
PSQL_DEMO="PGPASSWORD=REMOVED_PASSSWORD psql -h REMOVED_HOSTNAME owlkeeper owlkeeper"
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

clean: bootstrap dummydata db-debug

# =======================
#
compile:
	mvn compile

test: bootstrap dummydata
	mvn test

start-locally: compile db-start
	mvn exec:java -Dexec.args="/owlkeeper_local.properties"

deploy-demo:
	@sh -c ${PSQL_DEMO} < ${SQL_DIR}/bootstrap/destroy.sql
	@sh -c ${PSQL_DEMO} < ${SQL_DIR}/bootstrap/tables.sql
	@sh -c ${PSQL_DEMO} < ${SQL_DIR}/bootstrap/functions.sql
	@sh -c ${PSQL_DEMO} < ${SQL_DIR}/dummydatademo.sql

deploy-demo-locally:
	@sh -c ${PSQL} < ${SQL_DIR}/bootstrap/destroy.sql
	@sh -c ${PSQL} < ${SQL_DIR}/bootstrap/tables.sql
	@sh -c ${PSQL} < ${SQL_DIR}/bootstrap/functions.sql
	@sh -c ${PSQL} < ${SQL_DIR}/dummydatademo.sql

start-demo-remote: compile
	mvn exec:java

start-demo-locally: compile db-start deploy-demo-locally
	mvn exec:java -Dexec.args="/owlkeeper_local.properties"
