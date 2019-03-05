PSQL="PGPASSWORD=owlkeeper psql -h 127.0.0.1 owlkeeper owlkeeper"
SQL_DIR=./src/main/resources/sqls

db-sh:
	@sh -c ${PSQL}

db-start:
	docker-compose up -d

db-stop:
	docker-compose down

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
